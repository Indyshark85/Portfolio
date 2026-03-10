#include<stdio.h>
#include<stdlib.h>

//Linked lists and basic operations
typedef struct int_node {
  int data;
  struct int_node *next;
} list_node_t;


list_node_t* cons(int head, list_node_t* tail){
  list_node_t* new_node = malloc(sizeof(list_node_t));
  new_node->data = head;
  new_node->next = tail;
  return new_node;
}

void clean_up_list(list_node_t* list){
  if(list == NULL){
    return;
  }
  list_node_t* head = list; 
  while(head != NULL){
    list = head->next;
    free(head);
    head = list;
  }
}


// Binary search trees:
typedef struct tree_node {
  struct tree_node *left;
  int data;
  struct tree_node *right;
} tree_node_t;

tree_node_t* create_tree_node(int data) {
  tree_node_t  *tree = malloc(sizeof(tree_node_t));
  tree->data = data;
  tree->left = NULL;
  tree->right = NULL;
  return tree;
}

tree_node_t *add_node(tree_node_t* tree, int data) {
  if(tree == NULL){
    return create_tree_node(data);
  }
  if(data < tree->data){
    tree->left = add_node(tree->left,data);
  } else if(data > tree->data) {
    tree->right = add_node(tree->right,data);
  } 
  return tree;
}

void clean_up_tree(tree_node_t* tree){
  if(tree == NULL){
    return;
  }
  clean_up_tree(tree->left);
  clean_up_tree(tree->right);
  free(tree);
}


void print_in_order(tree_node_t* tree) {
  if(tree == NULL){
    return;
  }
  print_in_order(tree->left);
  printf("%d\n",tree->data);
  print_in_order(tree->right);
}

typedef enum {
  List,
  Array,
  Tree
} tag_t;


typedef struct {
  tag_t tag;
  union {
   list_node_t* list;
   struct {
     int* array;
     int length;
   };
   tree_node_t* tree;
  };
} container_t;



//TO FINISH
void print_all(container_t item){
  if(item.tag == List){
    list_node_t* list = item.list;
    while(list !=NULL){
      printf("%d ",list->data);
      list = list->next;
    }
    printf("\n");
  } 
  //TODO: Finish this function. What are the other 2 cases?
  if (item.tag == Array){
	  for(int i=0;i<item.length; i++){
		  printf("%d ",item.array[i]);
	  }
	  printf("\n");
  }
  if (item.tag == Tree){
          print_in_order(item.tree);
          printf("\n");
  }

}
//TO WRITE
tree_node_t* tree_map(tree_node_t* tree, int (*fn)(int input)) {
  //TODO
  if (tree == NULL) {
        return NULL;
    }

    tree_node_t* new_node = create_tree_node(fn(tree->data));
    new_node->left  = tree_map(tree->left, fn);
    new_node->right = tree_map(tree->right, fn);
    return new_node;
}

//TO WRITE
void map_in_place(container_t item, int (*fn)(int input)){
  //TODO
  //deleted the file on accident copied all code incase. Why looks wierd
  if (item.tag == List) {
        list_node_t* cur = item.list;
        while (cur != NULL) {
            cur->data = fn(cur->data);
            cur = cur->next;
        }
    }
    else if (item.tag == Array) {
        for (int i = 0; i < item.length; i++) {
            item.array[i] = fn(item.array[i]);
        }
    }
    else if (item.tag == Tree) {
        tree_node_t* t = item.tree;
        if (t == NULL) return;

        // recur
        tree_node_t* stack[1000];  
        int sp = 0;

        stack[sp++] = t;
        while (sp > 0) {
            tree_node_t* node = stack[--sp];
            node->data = fn(node->data);
            if (node->right) stack[sp++] = node->right;
            if (node->left)  stack[sp++] = node->left;
        }
    }
}

int add_2(int a){
  return a + 2;
}

//BONUS: how can we write print_in_order using tree_map? 
//For 1 extra point of credit, write the following functions that
//print all of the same elements of a tree exactly like print_in_order
//but only uses tree_map, with print_tree_map_fn as an argument

//TO WRITE: BONUS
int print_tree_map_fn(int node_data){
  //TODO
  return node_data;
}

//TO WRITE: BONUS
void print_tree_map(tree_node_t* tree){
  tree_map(tree, print_tree_map_fn);
}


int main(){
   container_t tc;
   tc.tag = Tree;
   tc.tree = create_tree_node(20);
   tc.tree = add_node(tc.tree,10);
   tc.tree = add_node(tc.tree, 30);

   container_t lc;
   lc.tag = List;
   lc.list = cons(3,NULL);
   lc.list = cons(2,lc.list);
   lc.list = cons(1,lc.list);

   container_t ac;
   int arr[] = {100,200,300};
   ac.tag = Array;
   ac.array = arr;
   ac.length = 3;

   puts("Initial Tree:");
   print_all(tc);
   puts("Initial List:");
   print_all(lc);
   puts("Initial Array:");
   print_all(ac);

   map_in_place(tc,add_2);
   map_in_place(lc,add_2);
   map_in_place(ac,add_2);

   puts("Mapped Tree:");
   print_all(tc);
   puts("Mapped List:");
   print_all(lc);
   puts("Mapped Array:");
   print_all(ac);

   clean_up_tree(tc.tree);
   clean_up_list(lc.list);
}
