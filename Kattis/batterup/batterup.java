import java.util.Scanner;

public class batterup {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();

        int shots = 0;
        int bat = 0;

        for (int i = 0; i < N; i++) {
            int shot = scn.nextInt();
            if (shot >= 0) {
                bat += shot;
                shots++;
            }

        }

        double result = (double) bat / shots;

        System.out.println(result);

        scn.close();
    }
}