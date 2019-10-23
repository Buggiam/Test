import java.util.Scanner;

public class tarifa {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int X = scn.nextInt();
        int N = scn.nextInt();

        int available = 0;
        for (int i = 0; i < N; i++) {
            available += X;
            int used = scn.nextInt();
            available -= used;
        }

        System.out.println(available + X);

        scn.close();
    }
}