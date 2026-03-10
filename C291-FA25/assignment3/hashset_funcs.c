#include "hashset.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
// hashset_funcs.c: utility functions for operating on hash sets. Most
// functions are used in the hashset_main.c which provides an
// application to work with the functions.

int hashcode(char key[]){
  int hc = 0;
  for(int i=0; key[i]!='\0'; i++){
    hc = hc*31 + key[i];
  }
  return hc;
}
// PROVIDED: Compute a simple hash code for the given character
// string. The code is "computed" by casting the first 8 characters of
// the string to a numbers and returning their xor. The empty string
// has hash code 0. Longer strings will have numbers which are the
// integer interpretation of up to their first 8 bytes.  ADVANTAGE:
// constant time to compute the hash code. DISADVANTAGE: poor
// distribution for long strings; all strings with same first 8 chars
// hash to the same location.

void hashset_init(hashset_t *hs, int table_size){
	hs->table_size  = table_size;
    	hs->elem_count  = 0;
    	hs->table       = calloc(table_size, sizeof(hashnode_t*));
    	hs->order_first = NULL;
    	hs->order_last  = NULL;
}

// Initialize the hash set 'hs' to have given size and elem_count
// 0. Ensures that the 'table' field is initialized to an array of
// size 'table_size' and is filled with NULLs. Also ensures that the
// first/last pointers are initialized to NULL

int hashset_contains(hashset_t *hs, char elem[]){
	if (elem == NULL || elem[0] == '\0') return 0;

	int hc=hashcode(elem);

	if(hc<0)hc = -hc;

	int index = hc%hs->table_size;
	
	hashnode_t *node = hs->table[index];
	
	while(node != NULL){
		if (strcmp(node->elem,elem)==0){
			return 1; //found you
		}
		node = node ->table_next;
	}
	return 0; //I didnt find you
}

// Returns 1 if the parameter `elem` is in the hash set and 0
// otherwise. Uses hashcode() and field `table_size` to determine
// which index in table to search.  Iterates through the list at that
// table index using strcmp() to check for `elem`. NOTE: The
// `hashcode()` function may return positive or negative
// values. Negative values are negated to make them positive. The
// "bucket" (index in hs->table) for `elem` is determined by with
// 'hashcode(key) modulo table_siz
int hashset_add(hashset_t *hs, char elem[]) {
    	if (!hs || !elem) return 0;

    	/*already present, print message and return 0 */
    	if (hashset_contains(hs, elem)) {
        	printf("Elem already present, no changes made\n");
        	return 0;
    	}

    	/* compute bucket index */
    	int hc = hashcode(elem);
    	if (hc < 0) hc = -hc;
    	int index = hc % hs->table_size;

    	/* allocate a new node and zero it so pointer fields start (like at all WHY ARE YOU FAILING ME????)(oh yay its fixed now :) )*/
    	hashnode_t *new_node = malloc(sizeof(hashnode_t));
    	if (new_node == NULL) {
        	fprintf(stderr, "Error: memory allocation failed in hashset_add.\n");
        	exit(1);
    	}
    	memset(new_node, 0, sizeof(hashnode_t)); /* critical I HAVE FOUND THAT IF THIS ISNT RIGHT IT FAILS EVERYTHING DO NOT TOUCH */

    	/* copy element into fixed-size array */
    	strncpy(new_node->elem, elem, sizeof(new_node->elem) - 1);
    	new_node->elem[sizeof(new_node->elem) - 1] = '\0';

	new_node->table_next = hs->table[index];
	hs->table[index] = new_node;


    	/* append to insertion-order linked list */
    	new_node->order_next = NULL;
    	if (hs->order_first == NULL) {
        	hs->order_first = new_node;
        	hs->order_last = new_node;
    	} else {
        	hs->order_last->order_next = new_node;
        	hs->order_last = new_node;
    	}

    	hs->elem_count++;
    	return 1;
}

// If the element is already present in the hash set, makes no changes
// to the hash set and returns 0. hashset_contains() may be used for
// this. Otherwise determines the bucket to add `elem` at via the same
// process as in hashset_contains() and adds it to the FRONT of the
// list at that table index. Adjusts the `hs->order_last` pointer to
// append the new element to the ordered list of elems. If this is the
// first element added, also adjusts the `hs->first` pointer. Updates the
// `elem_count` field and returns 1 to indicate a successful addition.
//
// NOTE: Adding elems at the front of each bucket list allows much
// simplified logic that does not need any looping/iteration.

void hashset_free_fields(hashset_t *hs){
	//freeing all nodes in order (insert)
	hashnode_t *node = hs -> order_first;
	
	while(node != NULL){
		hashnode_t *next = node -> order_next;
		free(node);
		node = next;
	}

	free(hs->table);
	
	//reset everything big 'ol factory reset
	hs -> elem_count =0;
	hs -> table_size =0;
	hs -> table =NULL;
	hs -> order_first =NULL;
	hs -> order_last = NULL;
}
// De-allocates nodes/table for `hs`. Iterates through the ordered
// list of the hash set starting at the `order_first` field and
// de-allocates all nodes in the list. Also free's the `table`
// field. Sets all relevant fields to 0 or NULL as appropriate to
// indicate that the hash set has no more usable space. Does NOT
// attempt to de-allocate the `hs` itself as it may not be
// heap-allocated (e.g. in the stack or a global).

void hashset_show_structure(hashset_t *hs){
	printf("elem_count: %d\n", hs->elem_count);
    	printf("table_size: %d\n", hs->table_size);

    	if (hs->order_first)
        	printf("order_first: %s\n", hs->order_first->elem);
    	else
        	printf("order_first: NULL\n");

    	if (hs->order_last)
        	printf("order_last : %s\n", hs->order_last->elem);
    	else
        	printf("order_last : NULL\n");

    	double load_factor = (hs->table_size == 0) ? 0.0 :
                         (double)hs->elem_count / hs->table_size;
    	printf("load_factor: %.4f\n",load_factor);

    
    	for (int i = 0; i < hs->table_size; i++) {
        	printf("[%2d] : ", i);
        	hashnode_t *node = hs->table[i];
        	while (node != NULL) {

            		int hc = hashcode(node->elem);
            		if (node->order_next)
                		printf("{%d %s >>%s} ", hc, node->elem, node->order_next->elem);
            		else
                		printf("{%d %s >>NULL} ", hc, node->elem);
            		node = node->table_next;
        	}

        	printf("\n");
    	}
}

// Displays detailed structure of the hash set. Shows stats for the
// hash set as below including the load factor (element count divided
// by table_size) to 4 digits of accuracy.  Then shows each table
// array index ("bucket") on its own line with the linked list of
// elems in the bucket on the same line. 
// 
// EXAMPLE:
// elem_count: 4
// table_size: 5
// order_first: Nepeta
// order_last : Vriska 
// load_factor: 0.8000
// [ 0] : {-1724336932 Vriska >>NULL} 
// [ 1] : 
// [ 2] : 
// [ 3] : {2553199 Rose >>Vriska} {-1965180551 Nepeta >>Karkat} 
// [ 4] : {-2054697310 Karkat >>Rose} 
//
// NOTES:
// - Uses format specifier "[%2d] : " to print the table indices
// - Nodes in buckets have the following format:
//   {2314539 John >>Rose}
//    |          |       |        
//    |          |       +-> order_next->elem OR NULL if last node
//    |          +->`elem` string     
//    +-> hashcode("John"), print using format specifier "%d" 
// 


int next_prime(int num){
  	if (num <= 2) return 2;
  	if (num %2==0) num++;

  	while (1) {
  		int is_prime = 1;
		for (int i =2; i <= num/2; i++){
			if (num%i==0){
				is_prime=0;
				break;
			}
		}
		if (is_prime) return num;
		num += 2;
  	}
}
// If 'num' is a prime number, returns 'num'. Otherwise, returns the
// first prime that is larger than 'num'. Uses a simple algorithm to
// calculate primeness: check if any number between 2 and (num/2)
// divide num. If none do, it is prime. If not, tries next odd number
// above num. Loops this approach until a prime number is located and
// returns this. Used to ensure that hash table_size stays prime which
// theoretically distributes elements better among the array indices
// of the table.

void hashset_expand(hashset_t *hs){
	if (!hs) return;
    	int new_size = next_prime(2 * hs->table_size + 1);

   	hashset_t new_hs;
    	hashset_init(&new_hs, new_size);

    	/* re-add every single element from the old set into the new one */
   	 for (hashnode_t *n = hs->order_first; n; n = n->order_next) {
        	hashset_add(&new_hs, n->elem);
    	}

    	/* free them old nodes*/
    	hashset_free_fields(hs);

    	/*shallow copy new_hs into hs */
    	*hs = new_hs;
}

// Allocates a new, larger area of memory for the `table` field and
// re-adds all current elems to it. The size of the new table is
// next_prime(2*table_size+1) which keeps the size prime.  After
// allocating the new table, all table entries are initialized to NULL
// then the old table is iterated through and all elems are added to
// the new table according to their hash code. The memory for the old
// table is de-allocated and the new table assigned to the hash set
// fields "table" and "table_size".  This function increases
// "table_size" while keeping "elem_count" the same thereby reducing
// the load of the hash table. Ensures that the memory associated with
// the old table is free()'d. Makes NO special effort to preserve old
// nodes: re-adds everything into the new table and then frees the old
// one along with its nodes. Uses functions such as hashset_init(),
// hashset_add(), hashset_free_fields() to accomplish the transfer.

void hashset_write_elems_ordered(hashset_t *hs, FILE *out){
	int index = 1;
    	for (hashnode_t *node = hs->order_first; node; node = node->order_next) {
        	fprintf(out, " %d %s\n", index++, node->elem);
    	}
}
// Outputs all elements of the hash set according to the order they
// were added. Starts at the `order_first` field and iterates through
// the list defined there. Each element is printed on its own line
// preceded by its add position with 1 for the first elem, 2 for the
// second, etc. Prints output to `FILE *out` which should be an open
// handle. NOTE: the output can be printed to the terminal screen by
// passing in the `stdout` file handle for `out`.

int hashset_load(hashset_t *hs, char *filename){
	if (!hs || !filename) return 0;
    	FILE *in = fopen(filename, "r");
    	if (!in) {
        	printf("ERROR: could not open file '%s'\n", filename);
        	printf("load failed\n");
		return 0;
    	}

    	int table_size = 0, elem_count = 0;
    	if (fscanf(in, "%d %d\n", &table_size, &elem_count) != 2) {
        	fclose(in);
		printf("load failed\n");
        	return 0;
    	}

    	hashset_t tmp;
    	hashset_init(&tmp, table_size);

    	int idx;
    	char elem[64];
    	for (int i = 0; i < elem_count; i++) {
        	if (fscanf(in, "%d %63s\n", &idx, elem) != 2) {
            		hashset_free_fields(&tmp);
            		fclose(in);
	    		printf("load failed\n");
            		return 0;
        	}
        	hashset_add(&tmp, elem);
    	}
    	fclose(in);

    	/*IT WORKS YAYYAYAYAYAYA*/
    	hashset_free_fields(hs);
    	*hs = tmp;
    	return 1;
}
// Loads a hash set file created with hashset_save(). If the file
// cannot be opened, prints the message
// 
// ERROR: could not open file 'somefile.hs'
//
// and returns 0 without changing anything. Otherwise clears out the
// current hash set `hs`, initializes a new one based on the size
// present in the file, and adds all elems from the file into the new
// hash set. Ignores the indices at the start of each line and uses
// hashset_add() to insert elems in the order they appear in the
// file. Returns 1 on successful loading (FIXED: previously indicated
// a different return value on success) . This function does no error
// checking of the contents of the file so if they are corrupted, it
// may cause an application to crash or loop infinitely.


void hashset_save(hashset_t *hs, char *filename){
	FILE *out = fopen(filename, "w");
	if (out == NULL){
		printf("ERROR: could not open file '%s'\n",filename);
		return;
	}

	fprintf(out, "%d %d\n", hs -> table_size, hs->elem_count);
	hashset_write_elems_ordered(hs,out);
	fclose(out);
}
// Writes the given hash set to the given `filename` so that it can be
// loaded later.  Opens the file and writes its 'table_size' and
// 'elem_count' to the file. Then uses the hashset_write_elems_ordered()
// function to output all elems in the hash set into the file.
// If the file cannot be opened, prints the message
// 
// ERROR: could not open file 'somefile.hs'
//
// EXAMPLE FILE CONTENTS:
// 
// 5 6
//   1 John
//   2 Nepeta
//   3 Vriska
//   4 Sollux
//   5 Karkat
//   6 Dave
// 
// First two numbers are the 'table_size' and 'elem_count' field and
// remaining text is the output of hashset_write_elems_ordered();
// e.g. insertion position and element.
