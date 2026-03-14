#include <stdio.h>


int main(){
int number;
printf("Enter the starting Fizz Buzz number:");
scanf("%d",&number);

if (number<0){
	printf("Error: Cannot compute Fizz Buzz of %d\n",number);
	return 1;
}


for(int x = number; x>0; x--){
	
	if(x%3==0 && x%5==0){
		printf("Fizz-Buzz");
	}else if (x%3==0){
		printf("Fizz");
	}else if(x%5==0){
		printf("Buzz");
	}else{
		printf("%d",x);
	}
	if(x != 1) printf(" ");
}

printf("\n");
return 0;

}

