import java.util.Scanner;

public class speedlimit {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = scn.nextInt();
        while (n != -1) {

            int milesDriven = 0;

            int lastT = 0;

            for (int i = 0; i < n; i++) {
                int s = scn.nextInt();
                int t = scn.nextInt();

                milesDriven += s * (t - lastT);
                lastT = t;
            }

            System.out.println(milesDriven + " miles");

            n = scn.nextInt();
        }

        scn.close();
    }
}