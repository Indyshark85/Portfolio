#include <stdio.h>

int main() {
    char morn;
    char mom;
    char sleep;
    int shouldAnswer = 1;  // had to rewrite my entire code bc i decided to do if else slop at first

    printf("Is it morning?: ");
    scanf(" %c", &morn);

    printf("Is mom calling?: ");
    scanf(" %c", &mom);

    printf("Am I sleeping?: ");
    scanf(" %c", &sleep);

    if (sleep == 'y') {
        shouldAnswer = 0;
    } else if (morn == 'y' && mom != 'y') {
        shouldAnswer = 0;
    }

    if (shouldAnswer == 1) {
        printf("You answer the phone\n");
    } else {
        printf("You don't answer the phone\n");
    }

    return 0;
}

