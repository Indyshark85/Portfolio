#include <stdio.h>
#include <math.h>
int main(){
double operand1;
double operand2;
char operator;

//since the autograder is picky
printf("Enter expression: ");
scanf("%lf %c %lf",&operand1, &operator ,&operand2);
    switch (operator){
        case '+':
            printf("%.2f\n", operand1 + operand2);
            break;
        case '-':
            printf("%.2f\n", operand1 - operand2);
            break;
        case '*':
            printf("%.2f\n", operand1 * operand2);
            break;
        case '/':
            if (operand2 == 0) {
                printf("Error: dividing by zero\n");
            }else {
                printf("%.2f\n", operand1 / operand2);
            }
            break;
        case '%':
            printf("%.2f\n", (double)((int)operand1 % (int)operand2));
            break;
        case '^':
            printf("%.2f\n", pow(operand1, operand2));
            break;
        case 'r':
            if (operand1 == 0){
                printf("Error: root degree cannot be zero\n");
            }else{
                printf("%.2f\n", pow(operand2, 1.0 / operand1));
            }
            break;
        default:
            printf("Error: unknown operator\n");
    }
return 0;
}
