#include <stdio.h>
#include <stdlib.h>

//Q1: 
//TODO write result_t
typedef struct {
	enum{ RES_INT,RES_CHAR_ARR } tag;
	union{
		int i;
		char arr[8]
	};
}result_t;

//Q2:
typedef struct {
  enum { is_int, is_string, is_int_array } tag;
  union {
    int i;
    char* s;
    struct {
      int* arr;
      unsigned int size;
    };
  }
} eq_t;

//TODO write equal
result_t make_err(){
	result_t r;
	r.tag = RES_CHAR_ARR;
	strcpy(r.arr,"ERR");
	return r;
}
result_t make_int(int x){
	result_t r;
	r.tag = RES_INT;
	r.i=x;
	return r;
}
result_t equal(eq_t* a, eq_t b){
	if(a->tag != b.tag){
		return make_err();
	} // different types -> return err
	//same type -> compare values
	switch (a->tag){
		case is_int:
			return make_int(a-> == b.i ? 1:0);
		case is_string:
			if (strcmp(a->s,b.s)==0)
				return make_int(1);
			else
				return make_int(0);
		case is_int_array:
			if(a->size != b.size)
				return make_int(0);
			for (unsigned int i=0; i<a->size;i++){
				if(a->arr[i] !=b.arr[i])
					return make_int(0);
			}
			return make_int(1);
	}
	return make_err();
}

//Q3:
typedef struct node {
  int data;
  struct node* next;
} node_t;

//TODO write fold_list
int fold_list(node_t* list,int(*fn)(int,int)){
	if(list == NULL)
		return -1;
	int acc = list -> data;
	list = list->next;

	while(list != NULL){
		acc = fn(acc,list->data);
		list = list ->next;
	}
	return acc;
}


/// END LAB SECTION
/// Below is code used for testing fold_list. 
/// We can't test your first 2 questions automatically, so those will be done my manual inspection. It's up to you to make sure you wrote them correctly.
node_t* add(int new_data, node_t* old){
  node_t* new_node = malloc(sizeof(node_t));
  new_node->data = new_data;
  new_node->next = old;
  return new_node;
}

void clean_up(node_t* list){
  if (list == NULL){
    return;
  }
  if(list->next == NULL){
    free(list);
    return;
  }
  node_t* h = list;
  list = list->next;
  while(list != NULL){
    free(h);
    h = list;
    list = list->next;
  }
  free(h);
}

int add_int(int a, int b){
  return a + b;
}
int mult_int(int a, int b){
  return a * b;
}


int main() {
  node_t* list = add(3,NULL);
  list = add(5,list);
  list = add(7,list);
  printf("add: %d\n",fold_list(list,add_int));
  printf("mult: %d\n",fold_list(list,mult_int));
  clean_up(list);
}



