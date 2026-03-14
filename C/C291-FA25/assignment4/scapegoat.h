#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdio.h>
#define ALPHA 0.60
typedef struct tree_node {
  //the key that we use as an index 
  int key;
  //the value stored at the node
  double value;
  struct tree_node* parent;
  struct tree_node* left;
  struct tree_node* right;
} node_t;


typedef struct {
  node_t* root;
  unsigned int size; 
  unsigned int max_size; 
} tree_t;

double log_base(double arg, double base);
unsigned int h_alpha(tree_t* tree);
unsigned int size(node_t* x);
int is_alpha_weight_balanced(node_t* x);
node_t* create_node(node_t* left, node_t* right, node_t* parent, int key, double value);
void init_tree(tree_t* tree);
void clean_up_tree(node_t* root);
void print_all(int idx, node_t* root);
double* lookup(tree_t* tree, int key);
void insert(tree_t* tree, int key, double value);
void delete_tree(tree_t* tree, int key);
// int flatten(node_t** arr, int idx, node_t* node);
// node_t* rebuild_from_arr(node_t** arr, int start, int end);
