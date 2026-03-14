#include "scapegoat.h"

//BONUS TO WRITE: write a representation of a tree to a file
void write_tree(FILE* file, node_t* root){
  if(root ==NULL) return;

  fprintf(file,"{%d:%0.6f}\n", root->key, root->value);
  write_tree(file,root->left);
  write_tree(file,root->right);
}


//TO FINISH
int main(int argc, char** argv){
  if(argc <= 2){
    printf("invalid number of arguments to %s.\n",argv[0]);
    printf("USAGE: %s <INPUT FILE> <OUTPUT FILE> <COMMAND>+\n",argv[0]);
    printf("COMMANDS:\n");
    printf("          delete <KEY>         : deletes a key from the tree\n");
    printf("          insert <KEY> <VALUE> : inserts a new node with integer key <KEY> and floating point value <VALUE>\n");
    printf("          lookup <KEY>         : looks for a node with key <KEY>, and prints its value if found\n");
    printf("          print                : prints all of the nodes in a tree in a structured way\n");
    printf("          output <FILENAME>    : writes all of the nodes of a tree to a file \n");
    return 1;
  } 
  FILE* tree_file = fopen(argv[1],"r+");
  if(tree_file == NULL){
    printf("ERROR: Failed to open tree file %s\n",argv[1]);
    return 1;
  }

  tree_t tree;
  init_tree(&tree);
  int key;
  double value;

  int scan_result = fscanf(tree_file,"%d:%lf\n",&key,&value);
  while(scan_result != EOF && scan_result != 0){
    insert(&tree,key,value);
    scan_result = fscanf(tree_file,"%d:%lf\n",&key,&value);
  }
  fclose(tree_file);


  for(int i = 2; i < argc; i++){
   if(strcmp(argv[i],"lookup")==0){
      int key = atoi(argv[++i]);
      double* value = lookup(&tree,key);
      if(value == NULL){
        printf("Key %d not found in tree\n",key);
      } else {
        printf("Key %d found in tree with value %lf\n",key,*value);
      }
    } else if(strcmp(argv[i],"print")==0){
      print_all(0,tree.root);
    }
    //TODO / TO WRITE:
    //write the cases for `delete` and `insert`. 
    //All these involve is calling the corresponding function with the next argument for delete,
    //and the next 2 arguments for insert, both similar to the lookup case.
    //HINT: atoi() converts a string like "123" to an integer, and atof() converts a string to a double.
    
    //FOR THE BONUS CREDIT: write an additional case to output the current try to a file.
    //when we encounter "output", you should open the next argument as a file, and then write to it using `write_tree`.
    //remember to close the file when you/re done!
  	else if(strcmp(argv[i],"delete") == 0){
      		int del_key = atoi(argv[++i]);
      		delete_tree(&tree, del_key);
    	} else if(strcmp(argv[i],"insert") == 0){
      		int ins_key = atoi(argv[++i]);
      		double ins_val = atof(argv[++i]);
      		insert(&tree, ins_key, ins_val);
    	} else if(strcmp(argv[i],"output") == 0){
      		char* outname = argv[++i];
      		FILE* out = fopen(outname,"w");
      		if(out == NULL){
        		printf("ERROR: failed to open output file %s\n", outname);
      	} else {
        	write_tree(out, tree.root);
        	fclose(out);
      	}
    } else {
      // unknown command -> skip (or you can print an error)
      printf("Unknown command: %s\n", argv[i]);
    }
  
  
  }

  clean_up_tree(tree.root);
}
