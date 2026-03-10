#include "Ast.h"
#include "Eval.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>


//BEGIN PROVIDED  CODE


block_t* block_insert(ident_t name, val_t val, block_t* env){
  block_t* new = malloc(sizeof(block_t));
  new->name = name;
  new->val = val;
  new->next = env;
  return new;
}

val_t* block_lookup(ident_t name, block_t* env){
  while(env){
    if(strcmp(name,env->name) == 0){
      return &(env->val);
    }
    env = env->next;
  }
  return NULL;
}

void free_block(block_t* env){
  if (env == NULL){
    return;
  }
  if(env->next == NULL){
    free(env);
    return;
  }
  block_t* h = env;
  env = env->next;
  while(env != NULL){
    free(h);
    h = env;
    env = env->next;
  }
  free(h);
}


env_t* env_init() {
  env_t* env = malloc(sizeof(env_t));
  env->block = NULL;
  env->next = NULL;
  return env;
}

void free_env(env_t** env){
  while(*env != NULL){
    pop_scope(env);
  }
}

void env_insert(ident_t name, val_t val, env_t* env){
  if(env == NULL)
    return;
  env->block = block_insert(name,val,env->block);
  return;
}

void push_scope(env_t** env){
  env_t* new = malloc(sizeof(env_t));
  new->block = NULL;
  new->next = *env;
  *env = new;
}

void pop_scope(env_t** env){
  if(!env || !(*env)){
    return;
  }
  free_block((*env)->block);
  env_t* tmp = *env; 
  *env = (*env)->next;
  free(tmp);
}

val_t* env_lookup(ident_t name, env_t* env){
  if(!env)
    return NULL;
  val_t* result = block_lookup(name,env->block);
  if(result)
    return result;
  else
    return env_lookup(name,env->next);
}
void free_expr(expr_t* e){
   if(e->tag == is_binop) {
     free_expr(e->binop_expr.left_expr);
     free_expr(e->binop_expr.right_expr);
     free(e);
   } else {
     free(e);
  }
}

//END PROVIDED CODE

//TODO
val_t eval_expr(expr_t* e, env_t* env){
	switch(e->tag){
  	
		case is_var: {
    			val_t* res = env_lookup(e->var,env);
    			if(res){
      				return *res;
    			} else {
      				printf("envirnoment error: couldn't find variable %s in scope", e->var);
      				exit(EXIT_FAILURE);
    			}
  		}
  //TODO what are the other cases?
  		case is_val: {
			     return e->val;
		}
		case is_binop: {
			val_t left = eval_expr(e->binop_expr.left_expr,env);
			val_t right = eval_expr(e->binop_expr.right_expr,env);
			switch(e->binop_expr.op){
				case op_add: return left + right;
				case op_sub: return left - right;
				case op_eq:  return left == right;
				case op_lt:  return left < right;
				case op_and: return left && right;
				case op_div: 
					if (right == 0){
						printf("evaluation error:divide by zero\n");
						exit(EXIT_FAILURE);
					}
			   		return left/right;		
					     
				default: 
					 printf("Unknown binary operator\n");
					 exit(EXIT_FAILURE);	     
			}		
		}
		case is_let:{
			    val_t v = eval_expr(e->let_expr.value_expr,env);
			    push_scope(&env);
			    env_insert(e->let_expr.name,v,env);
			    val_t result = eval_expr(e->let_expr.body_expr,env);
			    pop_scope(&env);
			    return result;
		}	       
		default:
			    printf("Unknown expression tag in eval_expr\n");
			    exit(EXIT_FAILURE);
	}

}

