#include "Eval.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/* Eval.c: Code for evaluatiob of the abstract syntax tree
 * Makes heavy use of data types defined in Eval.h. 
 * Most funcions take a list of functions, and enviroment.
 * scope) eval things normally :) vals: see below
 */


// BEGIN PROVIDED  CODE
//insert a new variable-name/value pair into the enviroment
// all entries allocated on the heap, so must be free'd.
void env_insert(ident_t name, val_t val, env_t **env) {
  env_t* copy = *env;
  //try to update in place
  while(copy != NULL){
    if(strcmp(copy->name,name) == 0){
      copy->val = val;
      return;
    }
    copy = copy->next;
  }

  //can't update in place: make new entry
  env_t *new = malloc(sizeof(env_t));
  new->name = name;
  new->val = val;
  new->next = *env;
  *env = new;
}

//look up a variable in enviroment
//returns a bunk value (with .is_invalid = 1) if not found
val_t env_lookup(ident_t name, env_t *env) {
  while (env) {
    if (strcmp(name, env->name) == 0) {
      return env->val;
    }
    env = env->next;
  }
  return (val_t) {.is_invalid = 1};
}

void free_env(env_t *env) {
  if (env == NULL) {
    return;
  }
  if (env->next == NULL) {
    free(env);
    return;
  }
  env_t *h = env;
  env = env->next;
  while (env != NULL) {
    free(h);
    h = env;
    env = env->next;
  }
  free(h);
}

//looks up a function from the function list
//returns NULL if not found
fundecl_t *lookup_func(ident_t name, func_list_t *funcs) {
  while (funcs != NULL) {
    if (strcmp(name, funcs->fundecl->name) == 0) {
      return funcs->fundecl;
    } else {
      funcs = funcs->next;
    }
  }
  return NULL;
}

//END PROVIDED CODE

//Evaluates a function. See the project writeup for details.
static unsigned int count_expr_arr(expr_t **arr){
	if(!arr) return 0;
	unsigned int c =0;
	while (arr[c] != NULL) c++;
	return c;
}

static unsigned int count_ident_arr(ident_t *arr){
	if(!arr) return 0;
	unsigned int c=0;
	while(arr[c] != NULL) c++;
	return c;
}


val_t eval_func(fundecl_t *fn, expr_t **arg_expr_arr, env_t *arg_env,
                func_list_t *funcs) {
 	env_t *fn_env = NULL;

  	ident_t *arg_names = fn->args;
 	unsigned int nargs_decl = count_ident_arr(arg_names);
  	unsigned int nargs_passed = count_expr_arr(arg_expr_arr);

	if(nargs_decl != nargs_passed){
		printf("ERROR: Function %s called with too few arguments!\n",fn->name);
		return (val_t) {.is_invalid =1};
	}
	for (unsigned int i = 0; i < nargs_passed; i++) {
    		val_t v = eval_expr(arg_expr_arr[i], arg_env, funcs);
    		if (v.is_invalid) {
      
      			free_env(fn_env);
      			return (val_t) {.is_invalid = 1};
    		}
  	env_insert(arg_names[i],v,&fn_env);
	}

	val_t local_ret = {.is_invalid = 1};

	//int block_ret = eval_block(fn->body,&local_ret,&fn_env,funcs);
	int r = eval_block(fn->body,&local_ret,&fn_env,funcs);

	free_env(fn_env);

	if (r == 1 && local_ret.is_invalid) {
    		return (val_t){.is_invalid = 1};
	}

	if (local_ret.is_invalid) {
    		printf("ERROR: Function %s did not return!\n", fn->name);
    		return (val_t){.is_invalid = 1};
	}

	return local_ret;
}

//Evaluates an expression, returning an invalid val_t on error.
//See the project writeup for details.
val_t eval_expr(expr_t *e, env_t *env, func_list_t *funcs) {
  
  	if (!e){
		return (val_t) {.is_invalid =1};
	}

  		switch (e -> tag){
  			case is_var:{
				val_t v = env_lookup(e->var, env);
				if (v.is_invalid) {
					printf("ERROR: Couldn't find variable %s in scope\n", e->var);
					return (val_t) {.is_invalid = 1};
				}
				return v;
			}
	
			case is_true:
				return (val_t){.val =1,.is_bool =1, .is_invalid =0};
			case is_false:
				return (val_t){.val =0,.is_bool =1, .is_invalid =0};
			case is_int:
                		return (val_t){.val = e->integer,.is_bool =0, .is_invalid =0};
			case is_fn_call:{
				fundecl_t *fn = lookup_func(e->fn_name, funcs);
    				if (!fn) {
        				printf("ERROR: Tried to call invalid function %s\n", e->fn_name);
        				return (val_t){.is_invalid = 1};
    				}

    				unsigned int nargs_decl = count_ident_arr(fn->args);
    				unsigned int nargs_passed = count_expr_arr(e->args);

    				if (nargs_decl != nargs_passed){
        				printf("ERROR: Function %s called with too few arguments!\n", fn->name);
        				return (val_t){.is_invalid = 1};
    				}

    				return eval_func(fn, e->args, env, funcs);
			}
			case is_binop:{
				if (e->binop_expr.tag == is_and) {
        				val_t L = eval_expr(e->binop_expr.left_expr, env, funcs);
       				 	if (L.is_invalid || !L.is_bool)
            					return (val_t){.is_invalid = 1};

        				if (!L.val)
            					return (val_t){.val = 0, .is_bool = 1, .is_invalid = 0};

        				val_t R = eval_expr(e->binop_expr.right_expr, env, funcs);
        				if (R.is_invalid || !R.is_bool)
            					return (val_t){.is_invalid = 1};

        				return (val_t){.val = R.val, .is_bool = 1, .is_invalid = 0};
    				}

    				if (e->binop_expr.tag == is_or) {
        				val_t L = eval_expr(e->binop_expr.left_expr, env, funcs);
        				if (L.is_invalid || !L.is_bool)
            					return (val_t){.is_invalid = 1};

        				if (L.val)
            					return (val_t){.val = 1, .is_bool = 1, .is_invalid = 0};

       					val_t R = eval_expr(e->binop_expr.right_expr, env, funcs);
        				if (R.is_invalid || !R.is_bool)
            					return (val_t){.is_invalid = 1};

        				return (val_t){.val = R.val, .is_bool = 1, .is_invalid = 0};
    				}

   				val_t L = eval_expr(e->binop_expr.left_expr, env, funcs);
    				val_t R = eval_expr(e->binop_expr.right_expr, env, funcs);
    				if (L.is_invalid || R.is_invalid)
        				return (val_t){.is_invalid = 1};

    				switch (e->binop_expr.tag) {
        				case is_add: return (val_t){.val = L.val + R.val};
        				case is_sub: return (val_t){.val = L.val - R.val};
        				case is_mul: return (val_t){.val = L.val * R.val};
        				case is_div:
            					if (R.val == 0) return (val_t){.is_invalid = 1};
            					return (val_t){.val = L.val / R.val};

        				case is_eq:  return (val_t){.val = (L.val == R.val), .is_bool = 1};
        				case is_neq: return (val_t){.val = (L.val != R.val), .is_bool = 1};
        				case is_lt:  return (val_t){.val = (L.val <  R.val), .is_bool = 1};
        				case is_lte: return (val_t){.val = (L.val <= R.val), .is_bool = 1};
        				case is_gt:  return (val_t){.val = (L.val >  R.val), .is_bool = 1};
        				case is_gte: return (val_t){.val = (L.val >= R.val), .is_bool = 1};

        				default:
            					return (val_t){.is_invalid = 1};
				}
			}
	}
	return (val_t){.is_invalid = 1};
}

//Evalutates a statement.
//returns 1 if errors or returns, 0 otherwise
//See the project writeup for details.
int eval_statement(statement_t *s, val_t *ret_val, env_t **env,
                   func_list_t *funcs) {
  if (!s) return 0;
  switch (s->tag) {
	case is_assn:{
		val_t v = eval_expr(s->expr,*env,funcs);
		if (v.is_invalid) return 1;
		env_insert(s->to_assn,v,env);
		return 0;
	}
	case is_print:{
		val_t v = eval_expr(s->to_print,*env,funcs);
		if (v.is_invalid) return 1;
		if(v.is_bool){
			printf(v.val ? "true\n" : "false\n");
		}else{
		     	printf("%d\n",v.val);
		}
		return 0;
	}
	case is_if:{
		val_t cond = eval_expr(s->if_condition, *env,funcs);
		if(cond.is_invalid) return 1;
		if(!cond.is_bool) return 1;
		if(cond.val){
			return eval_block(s->then_block, ret_val, env, funcs);
		}
		return 0;
	}
	case is_if_else:{
		val_t cond = eval_expr(s->if_condition, *env, funcs);
		if (cond.is_invalid) return 1;
		if( !cond.is_bool) return 1;

		if (cond.val){
        		return eval_block(s->then_block, ret_val, env, funcs);
    		} else {
        		return eval_block(s->else_block, ret_val, env, funcs);
    		}
	}
	case is_while:{
		while (1){
			val_t cond = eval_expr(s->while_condition, *env, funcs);
        	
			if (cond.is_invalid) return 1;
        		if (!cond.is_bool) return 1;
        		if (!cond.val) return 0;

        		int br = eval_block(s->body, ret_val, env, funcs);
        		if (br == 1)
            			return 1;
		}
		break;
    	}			

	case is_state_expr:{
		val_t v = eval_expr(s->expr_state, *env, funcs);
		if(v.is_invalid) return 1;
		return 0;
	}
	case is_return:{
		val_t v = eval_expr(s->expr_state, *env, funcs);
 		if(v.is_invalid) return 1;
		*ret_val = v;
		return 1;
	}
	default:
		return 1;
  }
  return 0;
}

//evaluates a null-terminated block of statements
//See the project writeup for details.
int eval_block(statement_t **s, val_t *ret_val, env_t **env,
               func_list_t *funcs) {
  	if (!s) return 0;

    	env_t *saved_env = *env;

    	for (int i = 0; s[i] != NULL; i++) {
        	int r = eval_statement(s[i], ret_val, env, funcs);
       	 	if (r == 1) {
            //AAAAAAAH MEMORY HOW COULD I FAIL AT C SO HARD
            		while (*env != saved_env) {
                		env_t *tmp = *env;
                		*env = (*env)->next;
                		free(tmp);
            		}
            		return 1;
        	}
    	}
    	return 0;
}
