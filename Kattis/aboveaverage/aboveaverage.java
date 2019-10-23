import java.util.Scanner;
import java.text.DecimalFormat;

public class aboveaverage {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.000");

        int C = scn.nextInt();
        for (int i = 0; i < C; i++) {
            int N = scn.nextInt();
            int[] grades = new int[N];
            double average = 0;
            for (int j = 0; j < N; j++) {
                grades[j] = scn.nextInt();
                average += grades[j];
            }
            average /= N;

            int above = 0;
            for (int grade : grades) {
                if (grade > average) above++;
            }

            double percentageAbove = (double) above / N * 100;
            System.out.println(df.format(percentageAbove).replace(",", ".") + "%");
        }

        scn.close();
    }
}