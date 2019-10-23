import java.util.Scanner;

public class pet {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int bestContestant = -1;
        int bestScore = -1;

        for (int c = 1; c <= 5; c++) {
            int score = 0;
            for (int g = 0; g < 4; g++) {
                score += scn.nextInt();
            }

            if (score > bestScore) {
                bestContestant = c;
                bestScore = score;
            }
        }

        System.out.println(bestContestant + " " + bestScore);

        scn.close();
    }

}