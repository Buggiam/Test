import java.util.Scanner;
import java.util.ArrayList;

public class CalculatingDartScores {

    private static ArrayList<String> shots = new ArrayList<>();
    private static int sum;
    private static String[] mn = new String[] { "", "single", "double", "triple" };

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        sum = scn.nextInt();

        execute();

        if (sum == 0) {
            for (String shot : shots) {
                System.out.println(shot);
            }
        } else {
            System.out.println("impossible");
        }

        scn.close();
    }

    private static void execute() {
        if (sum <= 60) {
            while (sum > 0) {
                if (sum >= 40) {
                    shots.add(getDart(20, 2));  // 1-20
                } else {
                    shots.add(getDart(Math.min(20, sum)));
                }
            }
            return;
        }

        shots.add(getDart(20, 3));              // 1-120

        if (sum <= 60) {
            if (sum >= 40) {
                shots.add(getDart(20, 2));      // 1-20
                shots.add(getDart(sum));     // 0
            } else {
                if (sum > 20) {
                    shots.add(getDart(20));  // 1-20
                }
                shots.add(getDart(sum));     // 0 
            }
            return;
        }

        String[] lastThrow = comboDart(sum);    // 61-120
        if (lastThrow != null) {
            shots.add(lastThrow[0]);
            shots.add(lastThrow[1]);
        }
    }

    private static String[] comboDart(int val) {
        for (int m1 = 3; m1 >= 1; m1--) {
            for (int s1 = 20; s1 >= 1; s1--) {
                for (int m2 = 3; m2 >= 1; m2--) {
                    for (int s2 = 20; s2 >= 1; s2--) {

                        if ((m1 * s1) + (m2 * s2) == sum) {
                            return new String[] {getDart(s1, m1), getDart(s2, m2)};
                        }
                    }
                }
            }
        }
        return null;
    }

    private static String getDart(int val, int multiplier) {
        sum -= multiplier * val;
        return mn[multiplier] + " " + val;
    }
    private static String getDart(int val) { return getDart(val, 1); }
}