#include <stdio.h>

int main(){
//the fun var parking lot
int angle1, angle2, angle3;

//call this a hospital bill that i forgot to pay the way its in collections ... im so sorry
printf("Input first angle: ");
scanf("%d", &angle1);
printf("Input second angle: ");
scanf("%d", &angle2);
printf("Input third angle: ");
scanf("%d", &angle3);

if(angle1+angle2+angle3 != 180 || angle1<0 || angle2 < 0 || angle3 <0){
	printf ("Invalid\n");
	return 0;
}
printf("valid ");

if ( angle1==90 || angle2 == 90 || angle3 == 90){
	printf("right ");

}else if(angle1>90 || angle2>90 || angle3>90){
	printf("obtuse ");
}else{
	printf("acute ");
}

if (angle1==angle2 || angle1==angle3 || angle2 == angle3){
	printf("isosceles ");
	if (angle1==60 && angle2==60 && angle3==60){
		printf("equilateral ");
	}
}

printf("\n");
return 0;
}
