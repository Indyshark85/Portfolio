#include "Ast.h"
#include <stdio.h>
#include <stdlib.h>

func_list_t *parse_to_func_list(TopLevel parse_tree) {
  if (parse_tree->kind == is_TLNil) {
    return NULL;
  }
  if (parse_tree->kind == is_TLCons) {
    func_list_t *to_ret = malloc(sizeof(func_list_t));
    to_ret->fundecl = parse_to_fundecl(parse_tree->u.tLCons_.fundecl_);
    to_ret->next = parse_to_func_list(parse_tree->u.tLCons_.toplevel_);
    return to_ret;
  }
  //should never occur
  return NULL;
}

expr_t *parse_to_expr(Expr pt) {
  expr_t *ex = malloc(sizeof(expr_t));
  if (pt->kind == is_EVar) {
    ex->tag = is_var;
    ex->var = pt->u.eVar_.ident_;
    return ex;
  } else if (pt->kind == is_ETrue) {
    ex->tag = is_true;
    return ex;
  } else if (pt->kind == is_EFalse) {
    ex->tag = is_false;
    return ex;
  } else if (pt->kind == is_EInt) {
    ex->tag = is_int;
    ex->integer = pt->u.eInt_.integer_;
    return ex;
  } else if (pt->kind == is_EFnCall) {
    ex->tag = is_fn_call;
    ex->fn_name = pt->u.eFnCall_.ident_;

    if (pt->u.eFnCall_.fargs_->kind == is_FANone) {
      ex->args = NULL;
      return ex;
    }

    FArgsRest fra = pt->u.eFnCall_.fargs_->u.fAArgs_.fargsrest_;
    unsigned int length = 1;
    while (fra->kind == is_FACons) {
      length++;
      fra = fra->u.fACons_.fargsrest_;
    }
    ex->args = malloc(sizeof(expr_t *) * (length + 1));
    ex->args[0] = parse_to_expr(pt->u.eFnCall_.fargs_->u.fAArgs_.expr_);
    fra = pt->u.eFnCall_.fargs_->u.fAArgs_.fargsrest_;
    for (unsigned int i = 1; i < length; i++) {
      ex->args[i] = parse_to_expr(fra->u.fACons_.expr_);
    }
    ex->args[length] = NULL;
    return ex;

  } else if (pt->kind == is_EBinOp) {
    ex->tag = is_binop;
    binop_t op;
    switch (pt->u.eBinOp_.binop_->kind) {
    case is_Add:
      op = is_add;
      break;
    case is_Sub:
      op = is_sub;
      break;
    case is_Mul:
      op = is_mul;
      break;
    case is_Div:
      op = is_div;
      break;
    case is_Lte:
      op = is_lte;
      break;
    case is_Eq:
      op = is_eq;
      break;
    case is_Neq:
      op = is_neq;
      break;
    case is_Gte:
      op = is_gte;
      break;
    case is_Lt:
      op = is_lt;
      break;
    case is_Gt:
      op = is_gt;
      break;
    case is_And:
      op = is_and;
      break;
    case is_or:
      op = is_or;
      break;
    default:
      //should never run
      puts("ERROR: Invalid operation in binop! Defaulting to add");
      op = is_add;
      break;

    }
    ex->binop_expr.tag = op;
    ex->binop_expr.left_expr = parse_to_expr(pt->u.eBinOp_.expr_1);
    ex->binop_expr.right_expr = parse_to_expr(pt->u.eBinOp_.expr_2);
    return ex;
  }
  //should never occur
  return NULL;
}

statement_t **parse_to_block(Block pt) {
  unsigned int length = 0;
  Block tmp = pt;
  while (tmp->kind != is_BNil) {
    length++;
    tmp = tmp->u.bCons_.block_;
  }
  // add an extra 1 for the nullptr
  statement_t **state_arr = malloc(sizeof(statement_t *) * (length + 1));
  for (unsigned int i = 0; i < length; i++) {
    state_arr[i] = parse_to_state(pt->u.bCons_.state_);
    pt = pt->u.bCons_.block_;
  }
  state_arr[length] = NULL;
  return state_arr;
}

statement_t *parse_to_state(State pt) {
  statement_t *stmt = malloc(sizeof(statement_t));
  if (pt->kind == is_SAssn) {
    stmt->tag = is_assn;
    stmt->expr = parse_to_expr(pt->u.sAssn_.expr_);
    stmt->to_assn = pt->u.sAssn_.ident_;
    return stmt;
  } else if (pt->kind == is_SPrint) {
    stmt->tag = is_print;
    stmt->to_print = parse_to_expr(pt->u.sPrint_.expr_);
    return stmt;
  } else if (pt->kind == is_SIf) {
    stmt->tag = is_if;
    stmt->if_condition = parse_to_expr(pt->u.sIf_.expr_);
    stmt->then_block = parse_to_block(pt->u.sIf_.block_);
    return stmt;
  } else if (pt->kind == is_SIfEl) {
    stmt->tag = is_if_else;
    stmt->if_condition = parse_to_expr(pt->u.sIfEl_.expr_);
    stmt->then_block = parse_to_block(pt->u.sIfEl_.block_1);
    stmt->else_block = parse_to_block(pt->u.sIfEl_.block_2);
    return stmt;
  } else if (pt->kind == is_SWhile) {
    stmt->tag = is_while;
    stmt->while_condition = parse_to_expr(pt->u.sIf_.expr_);
    stmt->body = parse_to_block(pt->u.sIf_.block_);
    return stmt;
  } else if (pt->kind == is_SExpr) {
    stmt->tag = is_state_expr;
    stmt->expr_state = parse_to_expr(pt->u.sExpr_.expr_);
    return stmt;
  } else if (pt->kind == is_SRet) {
    stmt->tag = is_return;
    stmt->expr_state = parse_to_expr(pt->u.sRet_.expr_);
    return stmt;
  }
  //should never occur
  return NULL;
}

fundecl_t *parse_to_fundecl(FunDecl pt) {
  fundecl_t *fn = malloc(sizeof(fundecl_t));
  fn->name = pt->u.fDecl_.ident_;
  fn->body = parse_to_block(pt->u.fDecl_.block_);
  if (pt->u.fDecl_.args_->kind == is_ANone) {
    fn->args = NULL;
  } else {
    ArgsRest a = pt->u.fDecl_.args_->u.aArgs_.argsrest_;
    unsigned int length = 1;
    while (a->kind != is_ANil) {
      length++;
      a = a->u.aCons_.argsrest_;
    }
    // NOTE: The idents are not copied, they're shared between the trees!
    fn->args = malloc(sizeof(ident_t) * (length + 1));
    fn->args[0] = pt->u.fDecl_.args_->u.aArgs_.ident_;
    a = pt->u.fDecl_.args_->u.aArgs_.argsrest_;
    for (unsigned int i = 1; i < length; i++) {
      fn->args[i] = a->u.aCons_.ident_;
      a = a->u.aCons_.argsrest_;
    }
    fn->args[length] = NULL;
  }
  return fn;
}

void free_expr(expr_t *expr) {
  if (expr->tag == is_fn_call) {
    for (int i = 0; expr->args[i] != NULL; i++) {
      free_expr(expr->args[i]);
    }
    free(expr->args);
  } else if (expr->tag == is_binop) {
    free_expr(expr->binop_expr.left_expr);
    free_expr(expr->binop_expr.right_expr);
  }
  free(expr);
}

void free_fundecl(fundecl_t *fundecl) {
  free_block(fundecl->body);
  // frees the array pointer, not the args themsevles
  free(fundecl->args);
  free(fundecl);
}

void free_func_list(func_list_t *funcs) {
  if (funcs == NULL) {
    return;
  }
  free_fundecl(funcs->fundecl);
  free_func_list(funcs->next);
  free(funcs);
}
// no need to free the strings which all live in the parse tree

void free_statement(statement_t *state) {
  if (state->tag == is_assn) {
    free_expr(state->expr);
  } else if (state->tag == is_print) {
    free_expr(state->to_print);
  } else if (state->tag == is_if) {
    free_expr(state->if_condition);
    free_block(state->then_block);
  } else if (state->tag == is_if_else) {
    free_expr(state->if_condition);
    free_block(state->then_block);
    free_block(state->else_block);
  } else if (state->tag == is_while) {
    free_expr(state->while_condition);
    free_block(state->body);
  } else if (state->tag == is_state_expr) {
    free_expr(state->expr_state);
  } else {
    free_expr(state->to_return);
  }
  free(state);
}

void free_block(statement_t **state) {
  for (int i = 0; state[i] != NULL; i++) {
    free_statement(state[i]);
  }
  free(state);
}
