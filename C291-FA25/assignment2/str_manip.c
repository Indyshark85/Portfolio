
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_INPUT 1024
#define MAX_ARGS  20

char* str_rev(int length, char* input);
int word_count(char* input);
char* to_upper(int length, char* input);
char* str_cat(int num_strings, char** input);
char* interleave(int num_strings, char** input);
char** split_on(char delim, char* input);
int search(char* key, char* input);
char** fuzzy_search(char* key, char* input);
int parse_hex(char* input);
char* grammar_check(int length, char* input);


void print_help() {
    	printf("Possible commands:\n\n");
    	printf("  reverse <str>                      : Reverses the input string\n");
    	printf("  word_count <str>                   : Counts the number of words\n");
    	printf("  to_upper <str>                     : Capitalizes all letters\n");
    	printf("  cat <str1> <str2> ...              : Concatenates strings together\n");
    	printf("  interleave <str1> <str2> ...       : Interleaves strings together\n");
    	printf("  split_on <delim> <str>             : Splits a string by a delimiter\n");
    	printf("  search <key> <str>                 : Counts exact matches of key in str\n");
    	printf("  fuzzy_search <key> <str>           : Finds words similar to key\n");
    	printf("  parse_hex <hex_str>                : Converts hex string to an integer\n");
    	printf("  grammar_check <str>                : Capitalizes first letter of sentences\n");
    	printf("  help                               : Prints this help text\n");
    	printf("  quit                               : Terminates the program\n");
}

char* str_rev(int length,char* input){
	int lenAct=strlen(input);
	if(length<lenAct) return NULL;

	char* result = malloc(lenAct+1);
	if(!result) return NULL;

	for(int i=0;i<lenAct; i++){
		result[i] = input[lenAct-1-i];
	}
	result[lenAct]='\0';
	return result;
}

int word_count(char* input){
	int count=0;
	int in_word=0;

	for(int i=0;input[i]!='\0';i++){
		if(isspace(input[i])){
			in_word=0;
		}else if(!in_word){
			count++;
			in_word=1;
		}
	}
	return count;
}

char* to_upper(int length, char* input){
	int lenAct=strlen(input);
	if(length<lenAct) return NULL;

	char* result = malloc(lenAct+1);

	if(!result) return NULL;
	for(int i=0;i<lenAct; i++){
                result[i] = toupper((unsigned char)input[i]);
	}
        result[lenAct]='\0';
	return result;
}


char* str_cat(int num_strings, char** input) {
	int total = 0;
    	for (int i = 0; i < num_strings; i++) {
        	total += strlen(input[i]);
        	if (i < num_strings - 1) total++; // add space
    	}

    	char* result = malloc(total + 1);
    	if (!result) return NULL;

    	int pos = 0;
    	for (int i = 0; i < num_strings; i++) {
        	for (int j = 0; input[i][j] != '\0'; j++) {
            		result[pos++] = input[i][j];
        	}
        	if (i < num_strings - 1) result[pos++] = ' ';
    	}
    	result[pos] = '\0';
    	return result;

}

char* interleave(int num_strings, char** input) {
	//need to find that length
	int max_len=0;
	for(int i=0;i<num_strings;i++){
		int len=strlen(input[i]);
		if(len>max_len) max_len=len;
	}
	//ew allocation of memory (I still struggle to grasp this)
	char* result=malloc(num_strings*max_len+1);	
	if (!result) return NULL;
	//now its writing time
	int pos=0;
	for(int x=0; x<max_len;x++){
		for(int y=0;y<num_strings;y++){
			if (x<(int)strlen(input[y])){
				result[pos++] = input[y][x];
			}
		}
	}
	//hey it says return its gonna return (i hope)
	//update its 11pm it did not :(
	result[pos]='\0';
	return result;

}

char** split_on(char delim, char* input) {
	int count = 1;
	for (int i = 0; input[i] != '\0'; i++) {
        	if (input[i] == delim) count++;
    	}

    	char** result = malloc((count + 1) * sizeof(char*));
    	if (!result) return NULL;

    	int idx = 0;
    	int start = 0;
    	for (int i = 0; ; i++) {
        	if (input[i] == delim || input[i] == '\0') {
            	int len = i - start;

            	// skip leading spaces in this token
            	while (len > 0 && input[start] == ' ') {
                	start++;
                	len--;
            	}

            	result[idx] = malloc(len + 1);
            	if (!result[idx]) return NULL;
            	strncpy(result[idx], &input[start], len);
            	result[idx][len] = '\0';
            	idx++;

            	if (input[i] == '\0') break;
            	start = i + 1;
        	}
    	}
    	result[idx] = NULL;
    	return result;

}

int search(char* key, char* input) {
	//counters and measurements
	int count=0;
	int key_len=strlen(key);
	int input_len=strlen(input);
	//for loop that actually compares and adds
	//I had to create then delete a creation of an Int i bc the compiler was throwing a hissy fit about the below for loop
	for(int i=0;i<=input_len-key_len;i++){
		if(strncmp(&input[i],key,key_len)==0){
			count++;
		}
	}
	return count;
}
//helper
int lev(const char* a, const char* b){
    	int lenA = strlen(a);
    	int lenB = strlen(b);
    	int dp[lenA+1][lenB+1];

    	for (int i = 0; i <= lenA; i++) dp[i][0] = i;
    	for (int j = 0; j <= lenB; j++) dp[0][j] = j;

	for (int i = 1; i <= lenA; i++) {
        for (int j = 1; j <= lenB; j++) {
            	if (a[i-1] == b[j-1]) {
                	dp[i][j] = dp[i-1][j-1];
            	} else {
                	int del = dp[i-1][j] + 1;
                	int ins = dp[i][j-1] + 1;
                	int sub = dp[i-1][j-1] + 1;
                	int min = del < ins ? del : ins;
                	if (sub < min) min = sub;
                	dp[i][j] = min;
            	}
        }
    	}
    	return dp[lenA][lenB];

}
//helper
char* my_strdup(const char* s) {
    	int len = strlen(s);
   	char* copy = malloc(len + 1);
    	if (copy) {
        	strcpy(copy, s);
    	}
    	return copy;
}

char** fuzzy_search(char* key, char* input){
	char* copy = my_strdup(input);
    	int capacity = 10;
    	char** matches = malloc(capacity * sizeof(char*));
    	int count = 0;

    	int start = 0;
    	for (int i = 0; ; i++) {
        	if (copy[i] == ' ' || copy[i] == '\t' || copy[i] == '\n' || copy[i] == '\0') {
            	if (i > start) {
                	int len = i - start;
                	char* token = malloc(len + 1);
                	strncpy(token, &copy[start], len);
                	token[len] = '\0';

                	if (lev(key, token) <= 2) {   // <-- lev used here
                    		if (count >= capacity - 1) {
                        		capacity *= 2;
                        		matches = realloc(matches, capacity * sizeof(char*));
                    		}
                    		matches[count++] = token;
                	} else {
                    		free(token);
                	}
            	}
            	if (copy[i] == '\0') break;
            	start = i + 1;
        	}
    	}

    	matches[count] = NULL;
    	free(copy);
    	return matches;

}


int parse_hex(char* input) {
    	return (int) strtol(input, NULL, 16);
}

char* grammar_check(int length, char* input) {
	int actual_len = strlen(input);
	if (length < actual_len) return NULL;
	
	char* result = malloc(actual_len+1);
	if (!result) return NULL;
	strcpy(result,input);

	int capitalize_nxt=1;
	for (int i=0; result[i]!='\0';i++){
		if(capitalize_nxt && isalpha((unsigned char)result[i])){
			result[i]=toupper((unsigned char)result[i]);
			capitalize_nxt=0;
		}
		if(result[i]=='.'||result[i]=='!'||result[i]=='?'){
			capitalize_nxt=1;
		}
	}
	return result;
}

// helper to strip surrounding quotes from a string
void strip_quotes(char* s) {
    	int len = strlen(s);
    	if (len >= 2 && s[0] == '"' && s[len-1] == '"') {
        	memmove(s, s+1, len-2);
        	s[len-2] = '\0';
    	}
}

int tokenize(char* input, char* args[], int max_args) {
    	int argc = 0;
    	int i = 0;
    	while (input[i] != '\0' && argc < max_args) {
        	while (isspace((unsigned char)input[i])) i++; // skip spaces
        	if (input[i] == '\0') break;

        	if (input[i] == '"') {
            		i++; // skip opening quote
            		args[argc++] = &input[i];
            		while (input[i] && input[i] != '"') i++;
            		if (input[i] == '"') {
                		input[i] = '\0';
                		i++;
            		}
        	} else {
            		args[argc++] = &input[i];
            		while (input[i] && !isspace((unsigned char)input[i])) i++;
            		if (input[i]) {
                		input[i] = '\0';
                		i++;
            		}
        	}
    	}
    	return argc;
}

int main(int argc, char* argv[]) {
    	char input[MAX_INPUT];

    	while (1) {
        	if (!fgets(input, sizeof(input), stdin)) break;

        	// remove trailing newline
        	input[strcspn(input, "\n")] = '\0';

        	// split into tokens
		char* args[MAX_ARGS];
		int argc = tokenize(input, args, MAX_ARGS);
		if (argc == 0) continue;



        	// dispatch
        	if (strcmp(args[0], "help") == 0) {
            		print_help();
        	}
        	else if (strcmp(args[0], "reverse") == 0 && argc == 2) {
            		char* result = str_rev(strlen(args[1]), args[1]);
            		printf("\"%s\"\n", result);
            		free(result);
        	}
        	else if (strcmp(args[0], "word_count") == 0 && argc == 2) {
            		printf("%d\n", word_count(args[1]));
        	}
        	else if (strcmp(args[0], "to_upper") == 0 && argc == 2) {
            		char* result = to_upper(strlen(args[1]), args[1]);
            		printf("\"%s\"\n", result);
            		free(result);
        	}
        	else if (strcmp(args[0], "cat") == 0 && argc > 1) {
            		char* result = str_cat(argc - 1, &args[1]);
            		printf("\"%s\"\n", result);
            		free(result);
        	}
        	else if (strcmp(args[0], "interleave") == 0 && argc > 1) {
            		char* result = interleave(argc - 1, &args[1]);
            		printf("\"%s\"\n", result);
            		free(result);
        	}
        	else if (strcmp(args[0], "split_on") == 0 && argc == 3) {
            		char** parts = split_on(args[1][0], args[2]);
            		printf("[");
            		for (int i = 0; parts[i] != NULL; i++) {
                		if (i > 0) printf(", ");
                		printf("\"%s\"", parts[i]);
                		free(parts[i]);
            	}
            	printf("]\n");
            	free(parts);
        	}
        	else if (strcmp(args[0], "search") == 0 && argc == 3) {
            		int matches = search(args[1], args[2]);
            		printf("found %d matches.\n", matches);
        	}
        	else if (strcmp(args[0], "fuzzy_search") == 0 && argc == 3) {
            		char** matches = fuzzy_search(args[1], args[2]);
            		int count = 0;
            		while (matches[count] != NULL) count++;
            		printf("found %d matches:\n", count);
            		for (int i = 0; i < count; i++) {
                		printf("\"%s\"\n", matches[i]);
                		free(matches[i]);
            		}
            		free(matches);
        	}
        	else if (strcmp(args[0], "parse_hex") == 0 && argc == 2) {
			int val = parse_hex(args[1]);
			for (int i = 0; args[1][i]; i++) {
				args[1][i] = toupper((unsigned char)args[1][i]);
			}
			printf("as hex: %s\n", args[1]);
			printf("as decimal: %d\n", val);
		}
        	else if (strcmp(args[0], "grammar_check") == 0 && argc == 2) {
            		char* result = grammar_check(strlen(args[1]), args[1]);
            		printf("\"%s\"\n", result);
            		free(result);
        	}
        	else if (strcmp(args[0], "quit") == 0) {
            		break;
        	}
        	else {
            		printf("Unknown command or incorrect number of arguments. Type 'help' for a list of commands.\n");
        	}
    	}
	return 0;
}

