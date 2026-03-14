#include "Absyn.h"
typedef char* ident_t;

typedef enum {
  is_add,
  is_sub,
  is_mul,
  is_div,
  is_lte,
  is_eq,
  is_neq,
  is_gte,
  is_lt,
  is_gt,
  is_and,
  is_or
} binop_t;

typedef struct expr_ {
  enum { is_var, is_true, is_false, is_int, is_fn_call, is_binop} tag;
  union {
    ident_t var;
    int integer;
    struct {
      ident_t fn_name;
      //NULL terminated array of expression pointers
      struct expr_** args;
    };
    struct {
      binop_t tag;
      struct expr_* left_expr;
      struct expr_* right_expr;
    } binop_expr;
  };
} expr_t;

typedef struct statement_ {
  enum {
    is_assn,
    is_print,
    is_if,
    is_if_else,
    is_while,
    is_state_expr,
    is_return
  } tag;
  union {
    struct {ident_t to_assn; expr_t* expr;};
    struct {expr_t* to_print;};
    struct {
      expr_t* if_condition;
      //NULL terminated array of statements
      struct statement_** then_block; 
      //NULL if no else branch
      //(also NULL terminated)
      struct statement_** else_block;
    };
    struct {expr_t* while_condition; struct statement_** body;};
    expr_t* expr_state;
    expr_t* to_return;
  };
} statement_t;

typedef struct fundecl {
  ident_t name;
  ident_t* args;
  statement_t** body;
} fundecl_t;


typedef struct func_list {
  fundecl_t* fundecl;
  struct func_list* next;
} func_list_t;

func_list_t* parse_to_func_list(TopLevel parse_tree);
expr_t* parse_to_expr(Expr pt);
statement_t* parse_to_state(State pt);
fundecl_t* parse_to_fundecl(FunDecl fn);

void free_block(statement_t** state);
void free_statement(statement_t* state);
void free_func_list(func_list_t* funcs);
void free_expr(expr_t* expr);
