#include <stdio.h>
#include <string.h>
#include "hashset.h"
//I have now completely copied this into a txt file to try and debug it and it still gives me issues aaaaaaaaaaah
int main(int argc, char *argv[]) {
    	int echo = (argc > 1 && strcmp(argv[1], "-echo") == 0);

    	hashset_t set;
    	hashset_init(&set, 5); // initialize hash set with small default size

    	char cmd[256], arg[256];
	
	//my wall of prints its to keep out the bug army
    	printf("Hashset Application\n");
    	printf("Commands:\n");
    	printf("  hashcode <elem>  : prints out the numeric hash code for the given key (does not change the hash set)\n");
    	printf("  contains <elem>  : prints the value associated with the given element or NOT PRESENT\n");
    	printf("  add <elem>       : inserts the given element into the hash set, reports existing element\n");
    	printf("  print            : prints all elements in the hash set in the order they were addded\n");
    	printf("  structure        : prints detailed structure of the hash set\n");
    	printf("  clear            : reinitializes hash set to be empty with default size\n");
    	printf("  save <file>      : writes the contents of the hash set to the given file\n");
    	printf("  load <file>      : clears the current hash set and loads the one in the given file\n");
    	printf("  next_prime <int> : if <int> is prime, prints it, otherwise finds the next prime and prints it\n");
    	printf("  expand           : expands memory size of hash set to reduce its load factor\n");
    	printf("  quit             : exit the program\n");

/*this took more debugging than frankly I am willing to admit*/
    	
	while (1) {
        	printf("HS>> ");
        	if (scanf("%127s", cmd) != 1) break;
        	if (echo) printf("%s", cmd);

        	if (strcmp(cmd, "quit") == 0) break;

        	if (strcmp(cmd, "add") == 0) {
            		scanf("%127s", arg);
            	if (echo) printf(" %s\n", arg);
            		hashset_add(&set, arg);
       
       		} else if (strcmp(cmd, "print") == 0) {
            		if (echo) printf("\n");
            		hashset_write_elems_ordered(&set, stdout);
        
		} else if (strcmp(cmd, "structure") == 0) {
            		if (echo) printf("\n");
           		hashset_show_structure(&set);
        
		} else if (strcmp(cmd, "clear") == 0) {
            		if (echo) printf("\n");
            			hashset_free_fields(&set);
            			hashset_init(&set, HASHSET_DEFAULT_TABLE_SIZE);
        
		} else if (strcmp(cmd, "save") == 0) {
            		scanf("%127s", arg);
            
	    		if (echo) printf(" %s\n", arg);
            			hashset_save(&set, arg);
        
		} else if (strcmp(cmd, "load") == 0) {
            		scanf("%127s", arg);
            		if (echo) printf(" %s\n", arg);
            			hashset_load(&set, arg);
        
		} else if (strcmp(cmd, "expand") == 0) {
            		if (echo) printf("\n");
            			hashset_expand(&set);
        
		} else if (strcmp(cmd, "hashcode") == 0) {
            		scanf("%127s", arg);
            		if (echo) printf(" %s\n", arg);
            			printf("%d\n", hashcode(arg));
        
		} else if (strcmp(cmd, "contains") == 0) {
            		scanf("%127s", arg);
            		if(echo) printf(" %s\n", arg);
			
			if (hashset_contains(&set, arg))
    				printf("FOUND: %s\n", arg);
			else
    				printf("NOT PRESENT\n");
        	
		} else if (strcmp(cmd, "next_prime") == 0) {
            		int num;
            		scanf("%d", &num);
            		if (echo) printf(" %d\n", num);
            			printf("%d\n", next_prime(num));
        	} else {
            		if (echo) printf("\n");
            			printf("Unknown command: %s\n", cmd);
        	}
    	}

    	hashset_free_fields(&set);
    	return 0;
}

