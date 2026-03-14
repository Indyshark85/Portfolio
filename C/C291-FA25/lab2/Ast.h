#include "Absyn.h"
typedef char* ident_t;
typedef Expr parse_tree_t;

typedef enum {
  is_add,
  is_sub,
  is_eq,
  is_lt,
  is_and,
} binop_t;

typedef struct expr_ {
  enum { is_var, is_true, is_false, is_int,  is_binop} tag;
  union {
    ident_t var;
    int integer;
    struct {
      binop_t tag;
      struct expr_* left_expr;
      struct expr_* right_expr;
    } binop_expr;
  };
} expr_t;

expr_t* parse_to_expr(Expr pt);
void free_parse_tree(parse_tree_t pt);
void free_expr(expr_t* e);
