import java.util.Scanner;

public class Compass {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int a = scn.nextInt();
        int b = scn.nextInt();

        int plus;
        int minus;

        if (a < b) {
            plus = b - a;
            minus = a + (360 - b);
        } else {
            plus = (360 - a) + b;
            minus = a - b;
        }

        if (plus <= minus) {
            System.out.println(plus);
        } else {
            System.out.println(-minus);
        }

        scn.close();
    }
}