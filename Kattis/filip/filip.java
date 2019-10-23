import java.util.Scanner;

public class filip {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int A = scn.nextInt();
        int B = scn.nextInt();

        System.out.println(Math.max(getReversed(A), getReversed(B)));

        scn.close();
    }

    private static int getReversed(int n) {
        StringBuilder sb = new StringBuilder("" + n).reverse();
        return Integer.parseInt(sb.toString());
    }
}