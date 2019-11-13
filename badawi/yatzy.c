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
	int N, result;
	char input;

    srand(time(NULL));

	do {
		printf("Vaelg antal terninger: ");
		scanf("%d", &N);

		result = play(N);
    	printf("Du fik %d point.\n\n", result);

		printf("Vil du spille Yatzy? (y/n)\n");
		scanf(" %c", &input);
	} while (input != 'n');

    return 0;
}

/*Starter et nyt Yatzy spil og returnerer resultatet (point).*/
int play(int N) {
	int scoreboard[15];
    int round, firstSectionSum, totalScore, *throw, roundPoints, pair1, pair2, val, availableDice, eyes, i;

    /*Itererer igennem alle spillets runder. 15 i alt.*/
    for (round = 0; round < 15; round++) {

        /*Denne rundes terningekast. Inderholder N terninger.*/
        throw = roll_multiple_dies(N);

        roundPoints = 0;
        availableDice = 5;

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
				/*Hvis pair1 eksempelvis består af 2 seksere - Må pair2 ikke også bestå af seksere.
				  Derfor udelukkes pair1 (antal øjne) som mulighed i søgningen på pair2.*/
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
                for (eyes = 6; eyes >= 1; eyes--) {
                    val = occurancesOfEyes(throw, N, eyes);
                    for (i = 0; i < val; i++) {

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
                if (highestEyeCountOccuringAmount(throw, N, 5, 0)) {
                    roundPoints = 50;
                }
                break;
        }

		free(throw);

        scoreboard[round] = roundPoints;
        printf(": +%d point\n\n", roundPoints);
    }

    /*Beregn total score.*/
	totalScore = 0;

    for (i = 0; i < 15; i++) {
        totalScore += scoreboard[i];
    }

    /*Beregner sum af den første sektion af runder.*/
    firstSectionSum = 0;

    for (i = 0; i < 6; i++) {
        firstSectionSum += scoreboard[i];
    }

    /*Tilføjer bonus, hvis 63 point blev nået i første sektion.*/
    if (firstSectionSum >= 63) {
        totalScore += 50;
        printf("Tillykke! Du fik din bonus! (%d >= %d)\n", firstSectionSum, 63);
        printf("  (+%d points)\n\n", 50);
    } else {
        printf("Oev, du fik ikke din bonus. (%d < %d)\n\n", firstSectionSum, 63);
    }

    return totalScore;
}

/*Kaster N terniner og returnerer int array af hver terning.*/
int *roll_multiple_dies(int N) {
    int *dice = malloc(N * sizeof(int));
	int i, throw;
    for (i = 0; i < N; i++) {
        throw = rollDice();
        dice[i] = throw;
        printf("%d, ", throw);
    }

    printf("\n");
    return dice;
}

/*Genererer et tilfældigt tal mellem 1 og 6.*/
int rollDice(void) {
    return 1 + rand() % 6;
}

/*Returnerer antallet af terninger med antal øjne i kast.*/
int occurancesOfEyes(int throw[], int N, int eyes) {
	int amount = 0;
	int i;
    for (i = 0; i < N; i++) {
        if (throw[i] == eyes) amount++;
    }

    if (amount > 5) {
        amount = 5;
    }

    return amount;
}

/*Returnerer højeste øjetal, som throw[] indeholder amount af. eyeCountExempt er udelukket som mulighed.*/
int highestEyeCountOccuringAmount(int throw[], int N, int amount, int eyeCountExempt) {
	int eyes, diceWithEyes;
    for (eyes = 6; eyes >= 1; eyes--) {
        if (eyes == eyeCountExempt) continue;

        diceWithEyes = occurancesOfEyes(throw, N, eyes);

        if (diceWithEyes >= amount) {
            return eyes;
        }
    }

    return 0;
}

/*Returnerer 1, hvis throw[] indeholder minds én terning med eyes øjne. Ellers 0.*/
int containsEyes(int throw[], int N, int eyes) {
    int i;
    for (i = 0; i < N; i++) {
        if (throw[i] == eyes) return 1;
    }
    return 0;
}
