#include <stdio.h>
#include <stdlib.h>

#include "Ast.h"
#include "Eval.h"
#include "Parser.h"

int main(){
  parse_tree_t parse_tree = pExpr(stdin);
  expr_t* ex = parse_to_expr(parse_tree);
  env_t* env = env_init();

  env_insert("a", (val_t) {.val = 1, .is_bool = 1},env);
  env_insert("b", (val_t) {.val = 99, .is_bool = 0},env);

  val_t result = eval_expr(ex,env);

  printf("result: (.val = %d, .is_bool = %d)",result.val,result.is_bool);

  free_env(&env);
  free_expr(ex);
  free_parse_tree(parse_tree);

}
