import java.util.Scanner;

public class planina {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();

        int side = 2;
        for (int i = 0; i < N; i++) {
            side = 2 * side - 1;
        }

        int result = side * side;

        System.out.println(result);

        scn.close();
    }
}