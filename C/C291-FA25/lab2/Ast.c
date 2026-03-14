#include "Ast.h"
#include <stdio.h>
#include <stdlib.h>

expr_t* parse_to_expr(Expr pt){
  expr_t* ex = malloc(sizeof(expr_t));
  if(pt->kind == is_EVar){
    ex->tag = is_var;
    ex->var = pt->u.eVar_.ident_;
    return ex;
  } else if (pt->kind == is_ETrue){
    ex->tag = is_true;
    return ex;
  } else if (pt->kind == is_EFalse){
    ex->tag = is_false;
    return ex;
  } else if (pt->kind == is_EInt){
    ex->tag = is_int;
    ex->integer = pt->u.eInt_.integer_;
    return ex;
    
  } else if (pt->kind == is_EBinOp) {
    ex->tag = is_binop;
    binop_t op;
    switch (pt->u.eBinOp_.binop_->kind){
      case is_Add:
        op = is_add;
        break;
      case is_Sub:
        op = is_sub;
        break;
      case is_Eq:
        op = is_eq;
        break;
      case is_Lt:
        op = is_lt;
      case is_And:
        op = is_and;
        break;
    }
    ex->binop_expr.tag = op;
    ex->binop_expr.left_expr  = parse_to_expr(pt->u.eBinOp_.expr_1);
    ex->binop_expr.right_expr = parse_to_expr(pt->u.eBinOp_.expr_2);
    return ex;
  }
}

void free_parse_tree(parse_tree_t pt){
  free_Expr(pt);
}
