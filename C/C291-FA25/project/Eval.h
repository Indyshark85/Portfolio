#include "Ast.h"

typedef struct {
  int val : 30;
  //the enum behavior with bitfields is weird (compiler dependent :|), so we'll use these
  // 1 means that val should be viewed as a boolean (val == 0 means val is false, val != 0 means val is true)
  // 0 means view val as its underlying integer
  unsigned int is_bool : 1;
  //if this is 1, then the  whole value is bunk and we need to propogate up the error
  //if it's 0, then we're good and can use the value
  unsigned int is_invalid : 1;
} val_t;


typedef struct enviroment {
  ident_t name;
  val_t val;
  struct enviroment* next;
} env_t;



void env_insert(ident_t name, val_t val, env_t** env);
val_t env_lookup(ident_t name, env_t* env);
void free_env(env_t* env);

val_t eval_expr(expr_t* e, env_t* env, func_list_t* funcs);
int eval_block(statement_t** s, val_t* ret_val, env_t** env, func_list_t* funcs);
fundecl_t* lookup_func(ident_t name, func_list_t* funcs);

val_t eval_func(fundecl_t* fn, expr_t** arg_expr_arr, env_t* arg_env, func_list_t* funcs);
int eval_block(statement_t **s, val_t *ret_val, env_t **env, func_list_t *funcs);
int eval_statement(statement_t *s, val_t *ret_val, env_t **env, func_list_t *funcs);
val_t eval_expr(expr_t *e, env_t *env, func_list_t *funcs);


