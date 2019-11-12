#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

int play(int N);
int rollDice(void);
int *roll_multiple_dies(int N);
int countOccurances(int throw[], int N, int val);
int highestEyesTimes(int throw[], int N, int amount, int eyesExempt);

int main(void) {
    int N;
    srand(time(NULL));
    printf("Vaelg antal terninger: ");
    scanf("%d", &N);

    int result = play(N);
    printf("Du fik %d point.\n", result);

    return 0;
}



int play(int N) {
    int scoreboard[15];

    int total = 0;

    //Rounds
    for (int round = 0; round < 6; round++) {
        int *throw = roll_multiple_dies(N);
        int points = 0;

        switch (round) {
            case 0: points = countOccurances(throw, N, 1) * 1;
                break;
            case 1: points = countOccurances(throw, N, 2) * 2;
                break;
            case 2: points = countOccurances(throw, N, 3) * 3;
                break;
            case 3: points = countOccurances(throw, N, 4) * 4;
                break;
            case 4: points = countOccurances(throw, N, 5) * 5;
                break;
            case 5: points = countOccurances(throw, N, 6) * 6;
                break;
        }

        printf("  (+ %d points)\n\n", points);
        scoreboard[round] = points;
    }

    //Calculate total score
    for (int i = 0; i < 6; i++) {
        total += scoreboard[i];
    }

    //Add bonus
    int firstSectionSum = 0;
    for (int i = 0; i < 6; i++) {
        firstSectionSum += scoreboard[i];
    }

    if (firstSectionSum >= 63) {
        total += 50;
        printf("Tillykke! Du fik din bonus!\n");
        printf("  (+ %d points)\n\n", 50);
    }

    return total;
}

int *roll_multiple_dies(int N) {
    int *dice = malloc(N * sizeof(int));
    for (int i = 0; i < N; i++) {
        int throw = rollDice();
        dice[i] = throw;

        printf("%d, ", throw);
    }

    printf("\n");

    return dice;
}

int rollDice(void) {
    return 1 + rand() % 6;
}

int countOccurances(int throw[], int N, int val) {
    int amount = 0;
    for (int i = 0; i < N; i++) {
        if (throw[i] == val) amount++;
    }

    if (amount > 5) {
        amount = 5;
    }

    return amount;
}

int highestEyesTimes(int throw[], int N, int amount, int eyesExempt) {
    for (int eyes = 6; eyes >= 1; eyes--) {
        if (eyes == eyesExempt) continue;

        int matchingDice = 0;
        for (int dice = 0; dice < N; dice++) {
            if (dice == eyes) {
                matchingDice++;
            }
        }

        if (matchingDice >= amount) {
            return eyes;
        }
    }

    return 0;
}

enum Rounds {Ones, Twos, Threes, Fours, Fives, Sixes,
             OnePair, TwoPairs, ThreeOfAKind, FourOfACount,
             SmallStraight, LargeStraight, FullHouse, Chance, Yatzy};
