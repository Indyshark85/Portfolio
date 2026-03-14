#include <stdio.h>

int main() {

    char empCode, vacaTake, isMarried;
    double salary, annualIncome =0.0;
    int overT=0, prodSold= 0, hoursWorked=0;

    //had to cut down for the autograder
    printf("Input employee type:");
    scanf(" %c", &empCode);

    switch (empCode) {
        case 'A': { // Administrator
            printf("Enter monthly salary: ");
            scanf("%lf", &salary);
            if (salary < 0) {
                printf("Error: Salary cannot be negative.\n");
                return 0;
            }
            annualIncome = salary * 12;
            break;
        }

        case 'S': { // Staff
            printf("Enter monthly salary: ");
            scanf("%lf", &salary);
            if (salary < 0) {
                printf("Error: Salary cannot be negative.\n");
                return 0;
            }
            printf("Enter overtime hours per month: ");
            scanf("%d", &overT);
            if (overT < 0) {
                printf("Error: Overtime hours cannot be negative.\n");
                return 0;
            }
            if (overT > 10) {
                printf("Overtime hours exceed limit. Only 10 hours will be counted.\n");
                overT = 10;
            }
            // 20 workdays * 8 hours = 160 hours/month
            double hourlyRate = salary / 160.0;
            annualIncome = (salary * 12) + (overT * 12 * hourlyRate * 1.5);
            break;
        }

        case 'E': { // Salesperson
            printf("Enter monthly salary: ");
            scanf("%lf", &salary);
            if (salary < 0) {
                printf("Error: Salary cannot be negative.\n");
                return 0;
            }
            printf("Enter overtime hours per day: ");
            scanf("%d", &overT);
            if (overT < 0) {
                printf("Error: Overtime hours cannot be negative.\n");
                return 0;
            }
            if (overT > 1) {
                printf("Overtime hours exceed daily limit. Only 1 hour per day will be counted.\n");
                overT = 1;
            }
            printf("Is the employee going to take a vacation for a month? (y/n):");
            scanf(" %c", &vacaTake);
            if (vacaTake != 'y' && vacaTake != 'n') {
                printf("Error: Invalid input. Please enter 'y' or 'n'.\n");
                return 0;
            }
            printf("Enter number of products sold this year: ");
            scanf("%d", &prodSold);
            if (prodSold < 0) {
                printf("Error: Product count cannot be negative.\n");
                return 0;
            }

            double baseIncome = salary * 12;
            if (vacaTake == 'y') {
    		baseIncome = (salary * 11) + (salary * 0.5);
	    }else {
    		baseIncome = salary * 12;
	    }	

            double hourlyRate = salary / 160.0;
            annualIncome = baseIncome + (overT * 20 * 12 * hourlyRate * 1.35) + (prodSold * 600);
            break;
        }

        case 'P': {
            printf("Enter weekly salary: ");
            scanf("%lf", &salary);
            if (salary < 0) {
                printf("Error: Salary cannot be negative.\n");
                return 0;
            }
            printf("Is the employee going to take a vacation for a month? (y/n):");
            scanf("%d", &overT);
            if (overT < 0) {
                printf("Error: Overtime hours cannot be negative.\n");
                return 0;
            }
            if (overT > 10) {
                printf("Overtime hours exceed weekly limit. Only 10 hours will be counted.\n");
                overT = 10;
            }
            printf("Enter number of products sold this year: ");
            scanf("%d", &prodSold);
            if (prodSold < 0) {
                printf("Error: Product count cannot be negative.\n");
		return 0;
	    }

            double hourlyRate = salary / 40.0; // 5 days * 8 hours
            annualIncome = (salary * 48) + (overT * 48 * hourlyRate) + (prodSold * 600);
            break;
        }

        case 'H': { // Hourly
            printf("Enter hourly salary: ");
            scanf("%lf", &salary);
            if (salary < 0) {
                printf("Error: Salary cannot be negative.\n");
                return 0;
            }
            printf("Enter total hours worked per week: ");
            scanf("%d", &hoursWorked);
            if (hoursWorked < 0) {
                printf("Error: Hours worked cannot be negative.\n");
                return 0;
            }

            int normalHours = (hoursWorked > 10) ? 10 : hoursWorked;
            int overtimeHours = (hoursWorked > 10) ? (hoursWorked - 10) : 0;
            if (overtimeHours > 10) {
                printf("Overtime hours exceed weekly limit. Only 10 hours will be counted.\n");
                overtimeHours = 10;
            }
            annualIncome = (normalHours * salary * 48) + (overtimeHours * salary * 1.25 * 48);
            break;
        }

        default:
            printf("Error: Invalid employee type.\n");
            return 0;
    }

    // Tax calculation
    printf("Is the employee married? (y/n): ");
    scanf(" %c", &isMarried);
    if (isMarried != 'y' && isMarried != 'n') {
        printf("Error: Invalid input. Please enter 'y' or 'n'.\n");
        return 0;
    }

    double taxRate = 0.10;
    if (isMarried == 'y') {
        if (annualIncome > 32000) taxRate = 0.25;
    } else {
        if (annualIncome > 64000) taxRate = 0.25;
    }

    double afterTax = annualIncome * (1 - taxRate);
    printf("This employee's annual income is $%.2f before tax and $%.2f after tax.\n",
           annualIncome, afterTax);

    return 0;
}
