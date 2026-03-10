#include <stdio.h>


int main(){
int digi;
char choice;

printf("Enter a four-digit integer:");
scanf("%d",&digi);

printf("Enter 'e' for encryption or 'd' for decryption: ");
scanf(" %c",&choice);

int d1 = (digi / 1000) % 10;
int d2 = (digi / 100) % 10;
int d3 = (digi / 10) % 10;
int d4 = digi % 10;

if (choice=='e'){
	d1 = (d1 + 7) % 10;
        d2 = (d2 + 7) % 10;
        d3 = (d3 + 7) % 10;
        d4 = (d4 + 7) % 10;

	int temp = d1; d1 = d3; d3 = temp;
        temp = d2; d2 = d4; d4 = temp;

        printf("Encrypted number: %d%d%d%d\n", d1, d2, d3, d4);
}else if (choice =='d'){
	int temp = d1; d1 = d3; d3 = temp;
        temp = d2; d2 = d4; d4 = temp;
	d1 = (d1 + 3) % 10;
        d2 = (d2 + 3) % 10;
        d3 = (d3 + 3) % 10;
        d4 = (d4 + 3) % 10;
	
	printf("Decrypted number: %d%d%d%d\n", d1, d2, d3, d4);
} else {
        printf("Error: Invalid choice. Use 'e' or 'd'.\n");
}
return 0;

}
