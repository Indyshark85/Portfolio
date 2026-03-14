typedef struct {
  int val : 30;
  //the enum behavior with bitfields is weird (compiler dependent :|), so we'll just use this.
  // 1 means that val should be viewed as a boolean (val == 0 means val is false, val != 0 means val is true)
  // 0 means view val as its underlying integer
  int is_bool : 1;
} val_t;

typedef struct block {
  ident_t name;
  val_t val;
  struct block* next;
} block_t;

typedef struct enviroment {
  block_t* block;
  struct enviroment* next;
} env_t;

env_t* env_init();

void env_insert(ident_t name, val_t val, env_t* env);

void push_scope(env_t** env);

void pop_scope(env_t** env);

void free_env(env_t** env);

val_t* env_lookup(ident_t name, env_t* env);
val_t eval_expr(expr_t* e, env_t* env);
