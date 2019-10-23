import java.util.Scanner;

public class heartrate {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();

        for (int i = 0; i < N; i++) {
            int b = scn.nextInt();
            double p = Double.parseDouble(scn.nextLine());

            double min = (b - 1) * 60 / p;

            double bpm = 60 * b / p;

            double max = (b + 1) * 60 / p;

            System.out.println(min + " " + bpm + " " + max);
        }

        scn.close();
    }
}