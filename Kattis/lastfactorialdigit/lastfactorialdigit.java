import java.util.Scanner;

public class lastfactorialdigit {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int T = scn.nextInt();

        for (int i = 0; i < T; i++) {
            int fac = factorial(scn.nextInt());
            String reversed = new StringBuilder(fac + "").reverse().toString();
            System.out.println(reversed.charAt(0));
        }

        scn.close();
    }

    private static int factorial(int n) {
        int sum = 1;
        for (int i = 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }
}