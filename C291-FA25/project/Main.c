#include <stdio.h>
#include <stdlib.h>

#include "Eval.h"
#include "Parser.h"

int main(int argc, char** argv) {
  FILE* file;
  env_t* env = NULL;
  //read the file provided from the command-line arguments
  if (argc < 1) {
    printf("%s expects a file as argument\n", argv[0]);
    return 1;
  } else {
    file = fopen(argv[1],"r");
    if (file == NULL) {
      printf("Could not read file %s, aborting\n",argv[1]);
      return 1;
    }
  }
  //parse the program in the file
  TopLevel parse_tree = pTopLevel(file);
  if(parse_tree == NULL){
    return 1;
  }
  func_list_t* funcs = parse_to_func_list(parse_tree);

  //lookup and execute the main function
  fundecl_t* fn = lookup_func("main", funcs);
  if (fn == NULL) {
    puts("ERROR: NO MAIN FUNCTION!");
  } else {
    val_t result = eval_func(fn, NULL, env, funcs);
    if(result.is_invalid){
     puts("main returned an invalid result!"); 
    } else {
      if(result.is_bool){
        printf("main returned %s\n", result.val ? "true" : "false");
      } else {
        printf("main returned %d\n", result.val );
      }
    }
  }

  //clean up
  free_env(env);
  free_func_list(funcs);
  free_TopLevel(parse_tree);
  fclose(file);
}
