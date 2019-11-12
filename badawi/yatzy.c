#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

int play(int N);
int *roll_multiple_dies(int N);
int rollDice(void);
int occurancesOfEyes(int throw[], int N, int val);
int highestEyeCountOccuringAmount(int throw[], int N, int amount, int eyesExempt);
int containsEyes(int throw[], int N, int eyes);

int main(void) {
    srand(time(NULL));

    int N;
    printf("Vaelg antal terninger: ");
    scanf("%d", &N);

    int result = play(N);
    printf("Du fik %d point.\n", result);

    return 0;
}

/*Starter et nyt Yatzy spil og returnerer resultatet (point).*/
int play(int N) {
    int scoreboard[15];

    /*Itererer igennem alle spillets runder. 15 i alt.*/
    for (int round = 0; round < 15; round++) {

        /*Denne rundes terningekast. Inderholder N øjetal.*/
        int *throw = roll_multiple_dies(N);

        int roundPoints = 0;

        /*Midlertidige variabler til brug i inden i switch.*/
        int pair1, pair2, val;
        int availableDice = 5;

        switch (round) {
            case 0: /*Etere*/
                printf("Enere");
                roundPoints = occurancesOfEyes(throw, N, 1) * 1;
                break;
            case 1: /*Toere*/
                printf("Toere");
                roundPoints = occurancesOfEyes(throw, N, 2) * 2;
                break;
            case 2: /*Treere*/
                printf("Treere");
                roundPoints = occurancesOfEyes(throw, N, 3) * 3;
                break;
            case 3: /*Firere*/
                printf("Firere");
                roundPoints = occurancesOfEyes(throw, N, 4) * 4;
                break;
            case 4: /*Femere*/
                printf("Femere");
                roundPoints = occurancesOfEyes(throw, N, 5) * 5;
                break;
            case 5: /*Seksere*/
                printf("Seksere");
                roundPoints = occurancesOfEyes(throw, N, 6) * 6;
                break;
            case 6: /*Et par*/
                printf("Et par");
                roundPoints = highestEyeCountOccuringAmount(throw, N, 2, 0) * 2;
                break;
            case 7: /*To par*/
                printf("To par");
                pair1 = highestEyeCountOccuringAmount(throw, N, 2, 0);
                pair2 = highestEyeCountOccuringAmount(throw, N, 2, pair1);
                roundPoints = pair1 * 2 + pair2 * 2;
                break;
            case 8: /*Tre ens*/
                printf("Tre ens");
                roundPoints = highestEyeCountOccuringAmount(throw, N, 3, 0) * 3;
                break;
            case 9: /*Fire ens*/
                printf("Fire ens");
                roundPoints = highestEyeCountOccuringAmount(throw, N, 4, 0) * 4;
                break;
            case 10: /*Lille straight*/
                printf("Lille straight");
                if (containsEyes(throw, N, 1) &&
                    containsEyes(throw, N, 2) &&
                    containsEyes(throw, N, 3) &&
                    containsEyes(throw, N, 4) &&
                    containsEyes(throw, N, 5)) {
                        roundPoints = 1 + 2 + 3 + 4 + 5;
                    }
                break;
            case 11: /*Stor straight*/
                printf("Stor straight");
                if (containsEyes(throw, N, 2) &&
                    containsEyes(throw, N, 3) &&
                    containsEyes(throw, N, 4) &&
                    containsEyes(throw, N, 5) &&
                    containsEyes(throw, N, 6)) {
                        roundPoints = 2 + 3 + 4 + 5 + 6;
                    }
                break;
            case 12: /*Fuldt hus*/
                printf("Fuldt hus");
                pair1 = highestEyeCountOccuringAmount(throw, N, 3, 0);
                pair2 = highestEyeCountOccuringAmount(throw, N, 2, pair1);
                roundPoints = pair1 * 3 + pair2 * 2;
                break;
            case 13: /*Chance*/
                printf("Chance");
                for (int eyes = 6; eyes >= 1; eyes--) {
                    val = occurancesOfEyes(throw, N, eyes);
                    for (int i = 0; i < val; i++) {

                        if (availableDice == 0) {
                            break;
                        }
                        roundPoints += eyes;
                        availableDice--;
                    }
                }
                break;
            case 14: /*Yatzy*/
                printf("Yatzy");
                if (highestEyeCountOccuringAmount(throw, N, 5, 0) != 0) {
                    roundPoints = 50;
                }
                break;
        }

        printf(": +%d point\n\n", roundPoints);
        scoreboard[round] = roundPoints;
    }

    /*Beregn total score.*/
    int totalScore = 0;
    for (int i = 0; i < 15; i++) {
        totalScore += scoreboard[i];
    }

    /*Beregner sum af den første sektion af runder.*/
    int firstSectionSum = 0;
    for (int i = 0; i < 6; i++) {
        firstSectionSum += scoreboard[i];
    }

    /*Tilføjer bonus, hvis 63 point blev nået i første sektion.*/
    if (firstSectionSum >= 63) {
        totalScore += 50;
        printf("Tillykke! Du fik din bonus!\n");
        printf("  (+%d points)\n\n", 50);
    } else {
        printf("Oev, du fik ikke din bonus. (%d < %d)\n\n", firstSectionSum, 63);
    }


    return totalScore;
}

//Kaster N terniner og returnerer int array af øjnene på hver terning.
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

//Returnerer 1, hvis throw[] indeholder en terning med eyes øjne. Eller 0.
int containsEyes(int throw[], int N, int eyes) {
    for (int i = 0; i < N; i++) {
        if (throw[i] == eyes) return 1;
    }
    return 0;
}

/*Returnerer antan af terninger med eyes øjne i throw[].*/
int occurancesOfEyes(int throw[], int N, int eyes) {
    int amount = 0;
    for (int i = 0; i < N; i++) {
        if (throw[i] == eyes) amount++;
    }

    if (amount > 5) {
        amount = 5;
    }

    return amount;
}

/*Returnerer højeste øjetal, som throw[] indeholder amount af. eyeCountExempt er udelukket som mulighed.*/
int highestEyeCountOccuringAmount(int throw[], int N, int amount, int eyeCountExempt) {
    for (int eyes = 6; eyes >= 1; eyes--) {
        if (eyes == eyeCountExempt) continue;

        int diceWithEyes = 0;
        for (int dice = 0; dice < N; dice++) {
            if (throw[dice] == eyes) {
                diceWithEyes++;
            }
        }

        if (diceWithEyes >= amount) {
            return eyes;
        }
    }

    return 0;
}
