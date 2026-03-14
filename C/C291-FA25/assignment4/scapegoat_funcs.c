#include "scapegoat.h"
#define TODO 1

//PROVIDED
//computes the logarithm of a value with respect to a particular base
double log_base(double arg, double base){
  return log(arg)/log(base);
}

//PROVIDED
//computes the maximum allowed height of a tree before we need to rebalance
//if ALPHA is smaller, then so will this value, so nodes that are less deep will force a reblance
//compared to if ALPHA is closer to 1
unsigned int alpha_height(tree_t* tree){
  return (unsigned int) log_base(tree->size,1/ALPHA);

}

//TO WRITE 
//computes size of the node, which is the number of nodes in the tree rooted at this node
unsigned int size(node_t* x){
  //TODO
  if(x==0) return 0;
  return 1+size(x->left)+size(x->right);
}

//PROVIDED
//returns 1 if the node is "close enough to balanced."
//if ALPHA is closer to 0.5, then it must be closer to perfectly balanced to return 1.
int is_alpha_weight_balanced(node_t* x){
  if(x == NULL)
    return 0;
  double left_size = (double) size(x->left);
  double right_size = (double) size(x->right);
  double size = left_size + right_size + 1;
  return left_size <= ALPHA * size && right_size <= ALPHA * size;
  
}

//PROVIDED
//create a node with all of the specified fields
node_t* create_node(node_t* left, node_t* right, node_t* parent, int key, double value){
      node_t* new_node = malloc(sizeof(node_t));
      new_node->left = left;
      new_node->right = right;
      new_node->parent = parent;
      new_node->key = key;
      new_node->value = value;
      return new_node;
}

//PROVIDED
//intialize a tree with an empty node and correponding size
void init_tree(tree_t* tree){
  tree->root = NULL;
  tree->size = 0;
  tree->max_size = 0;
  return;
}

//PROVIDED
void clean_up_tree(node_t* root){
  if(root == NULL){
    return;
  }
  clean_up_tree(root->left);
  clean_up_tree(root->right);
  free(root);
}

//PROVIDED
/// prints all of the elements of a tree, indenting based on how nested the elements are
void print_all(int idx, node_t* root){
  if(root == NULL)
    return;
  for(int i = 0; i < idx; i++){
    printf("  ");
  }
  printf("%d\n",root->key);
  print_all(idx+1,root->left);
  print_all(idx+1,root->right);
}




//PROVIDED: 
//scapegoat tree lookup. Works exactly like normal BST lookup.
double* lookup(tree_t* tree, int key){
  node_t* root = tree->root;
  while(root != NULL){
    if(root->key == key){
      return &root->value;
    } else if (root->key < key) {
      root = root->right;
    } else {
      root = root->left;
    }
  }
  return NULL;

}
//TO WRITE:
//traverses all of the node in the tree, writing each node to the array.
//returns the new index to the array after writing.
int flatten(node_t** arr, int idx, node_t* node) {
  //TODO
  if(node==NULL) return idx;

  //order of traversal left,node,right

  idx=flatten(arr,idx, node->left);
  arr[idx++] =node;
  idx=flatten(arr,idx,node->right);
  return idx;
}

//TO WRITE:
//start: the starting index of the sub array
//end: the ending index of the sub array +1
node_t* rebuild_from_arr(node_t** arr, int start, int end){
  //TODO
  if(start>=end) return NULL;
  int middle = start+ (end-start)/2;
  node_t* root= arr[middle];

  node_t* left = rebuild_from_arr(arr,start,middle);
  node_t* right = rebuild_from_arr(arr, middle+1, end);

  root->left=left;
  if(left != NULL) left -> parent = root;

  root->right= right;
  if(right != NULL) right -> parent = root;

  return root;
}

//TO FINISH
void insert(tree_t* tree, int key, double value){
  node_t* node = tree->root;
  int depth = 0;
  if(node == NULL){
    tree->root = create_node(NULL,NULL,NULL,key,value);
    tree->size++;
    tree->max_size = tree->max_size < tree->size ? tree->size : tree->max_size;
    return;
  }
  while(1){
    depth++;
    if(node->key < key){
      if(node->right == NULL){
        //insert a new node in the correct spot with the right fields using create_node
        //update tree->size and tree->max_size correctly
        //TODO
	node->right = create_node(NULL,NULL,node,key,value);
        node = node->right;
        tree->size++;
        tree->max_size = tree->max_size < tree->size ? tree->size : tree->max_size;
        break;

      } else {
        //how should we update node here?
	node = node->right;
      }
    } else if (node->key > key) {
      if(node->left == NULL){
        //insert a new node in the correct spot with the right fields using create_node
        //update tree->size and tree->max_size correctly
	node->left= create_node(NULL,NULL,node,key,value);
	node = node -> left;
	tree -> size++;
	tree -> max_size = tree -> max_size < tree -> size ? tree -> size : tree -> max_size;
        break;
      } else {
        //how should we update node here?
	node = node -> left;
      }
    } else {
      //match!
      node->value = value;
      return;
    }
  }
  //now that the loop is finished, we know that `node` is the node that was just inserted
  //check if the new node is too deep. If it isn't, we're done! otherwise, we need to rebalance.
  if(depth > (int) alpha_height(tree)){
    //first, find the scapegoat node
    node_t* scapegoat = node -> parent;
    while (scapegoat != NULL && is_alpha_weight_balanced(scapegoat)){
    	scapegoat= scapegoat -> parent;
    }
    //now that we have found the scaepgoat node, we must rebuild it.
    //first, create a new array whose length is the size of the scapegoat node
    if(scapegoat != NULL){
    	unsigned int  s_size=size(scapegoat);
	node_t** arr = malloc(sizeof(node_t*) * s_size);
    
    //then flatten the node into the array
    flatten (arr,0,scapegoat);
    //now, we need to rebuild the array, making sure the parent node of the scapegoat node points to the node we just rebuilt
    node_t* parent = scapegoat -> parent;
    node_t* rebuilt = rebuild_from_arr(arr,0,(int)s_size);
    if (rebuilt != NULL) rebuilt -> parent = parent;
    
    if(parent == NULL){
	    //This means we want to rebuild the root node. What should we do?
    	tree -> root = rebuilt;
    }else if(parent->left == scapegoat){
      //We need to rebuild the ndoe and put it in the right place in the tree.
      parent -> left = rebuilt;
      if(rebuilt != NULL) rebuilt->parent = parent;
      //Then we have to set the parent node of the rebuilt node correctly
    }else {
      //We need to rebuild the ndoe and put it in the right place in the tree.
      parent -> right = rebuilt;
      if(rebuilt != NULL) rebuilt -> parent = parent;
      //Then we have to set the parent node of the rebuilt node correctly
      
    }
    free(arr);
    }
  }
  return;
}

//PROVIDED:
//gets the element that has the largest key in the tree smaller than root->data
int get_pred(node_t* root){
  root = root->left;
  while(root->right != NULL){
    root = root->right;
  }
  return root->key;
}

//TO WRITE
//deletes a node with a given key from the tree based at the root
//when we delete, we need to decrement the size, so we pass the entire tree as well as the root
//works just like the normal BST delete, but with the extra bit of work of modifying the tree size
node_t* delete_node(tree_t* tree, node_t* root, int key) {
	if(root == NULL) return NULL;

  	if(key < root->key){
    		root->left = delete_node(tree, root->left, key);
    		if(root->left != NULL) root->left->parent = root;
    		return root;
  	}else if (key > root->key){
    		root->right = delete_node(tree, root->right, key);
    		if(root->right != NULL) root->right->parent = root;
    		return root;
  	}else{

    	//found node to delete
    	//case 1: no child
    		if(root->left == NULL && root->right == NULL){
      			free(root);
      			tree->size--;
      			return NULL;
    		}

    	// case 2: one child
    		else if (root->left == NULL){
      			node_t* tmp = root->right;
      			tmp->parent = root->parent;
      			free(root);
      			tree->size--;
      			return tmp;
    		}
    		else if (root->right == NULL){
      			node_t* tmp = root->left;
      			tmp->parent = root->parent;
      			free(root);
      			tree->size--;
      			return tmp;
    		}

    //case 3: two children->replace with prev
    		else {
      			node_t* tmp = root->left;
      			while(tmp->right != NULL){
			       	tmp = tmp->right;
			}
      			root->key = tmp->key;
      			root->value = tmp->value;
      			root->left = delete_node(tree, root->left, tmp->key);
      			if(root->left != NULL){ 
				root->left->parent = root;
			}
      			return root;
		}
	}
}

//TO WRITE
void delete_tree(tree_t* tree, int key) {
	tree->root = delete_node(tree, tree->root, key);
  	if(tree->root != NULL) tree->root->parent = NULL;

  	if((double) tree->size < ALPHA * (double) tree->max_size){
    		if(tree->size == 0){
      			tree->root = NULL;
      			tree->max_size = 0;
      			return;
		}
    	
    		unsigned int s = tree->size;
    		node_t** arr = malloc(sizeof(node_t*) * s);
    		flatten(arr, 0, tree->root);
    		node_t* rebuilt = rebuild_from_arr(arr, 0, (int)s);
    		
		if(rebuilt != NULL) rebuilt->parent = NULL;
    		
		tree->root = rebuilt;
    		tree->max_size = tree->size;
    		free(arr);
	}
}

