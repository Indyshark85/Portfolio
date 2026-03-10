#include <stdio.h>
#include <math.h>

int main(){
int n;

printf("Enter a positive integer that you wish to find prime factors of:");
scanf("%d",&n);

if (n<=1){
	printf("%d has no prime factorization",n);
}else{
	printf("The prime factorization of %d is: ",n);
}
while (n%2==0){
	printf("2 ");
	n/=2;
}

for(int i=3;i<= sqrt(n); i+=2){
	while(n%i==0){
		printf(" %d ",i);
		n /= i;
	}
}

if (n>2){
	printf(" %d",n);
}
printf("\n");
return 0;


}
