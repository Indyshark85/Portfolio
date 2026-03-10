#include <stdio.h>

int main(){
int number;
printf("Enter a number to find the reverse binary representation:");
scanf("%d",&number);

if(number<0){
	printf("Error: Number must be a positive integer\n");
	return 1;
}

if (number ==0){
	printf("0\n");
	return 0;
}
    int bits[32];
    int count = 0;

    while(number > 0){
        bits[count++] = number % 2;
        number /= 2;
    }

    //fixed to add zeros
    for(int i = 0; i < 8 - count; i++){
        printf("0");
    }

    for(int i = 0; i < count; i++){
        printf("%d", bits[i]);
    }
printf("\n");
return 0;
}

