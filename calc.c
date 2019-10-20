#include <stdio.h>
#include <math.h>

double run_calculator();
void scan_data(char *operator, double *operand);
void do_next_op(double *acculumator, char operator, double operand);

int main(void) {
    double result = run_calculator();
    printf("Final result is: %.2lf\n", result);

    return 0;
}

double run_calculator() {
    double accumulator = 0.0;
    double operand;
    char operator;

    scan_data(&operator, &operand);

    while (operator != 'q') {
        do_next_op(&accumulator, operator, operand);
        printf("Result so far is %.2lf.\n", accumulator);
        scan_data(&operator, &operand);
    }

    return accumulator;
}

void scan_data(char *operator, double *operand) {
    printf("Enter operator, and an optional operand: ");
    scanf("%s", &*operator);

    if (*operator == '+' || *operator == '-' || *operator == '*' ||
        *operator == '/' || *operator == '^') {
        scanf("%lf", &*operand);
    }
}

void do_next_op(double *accumulator, char operator, double operand) {
    switch(operator) {
        case '+':
            *accumulator += operand;
            break;
        case '-':
            *accumulator -= operand;
            break;
        case '*':
            *accumulator *= operand;
            break;
        case '/':
            *accumulator /= operand;
            break;
        case '^':
            *accumulator = pow(*accumulator, operand);
            break;
        case '#':
            *accumulator = sqrt(*accumulator);
            break;
        case '%':
            *accumulator = -*accumulator;
            break;
        case '!':
            *accumulator = 1 / *accumulator;
            break;
        default:
            printf("Invalid operator: '%c'.\n", operator);
    }
}
