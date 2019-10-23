import java.util.Scanner;

public class nastyhacks {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = scn.nextInt();

        for (int i = 0; i < n; i++) {
            int r = scn.nextInt();
            int e = scn.nextInt();
            int c = scn.nextInt();

            int earnings = e - r - c;
            if (earnings > 0) System.out.println("advertise");
            else if (earnings < 0) System.out.println("do not advertise");
            else System.out.println("does not matter");
        }

        scn.close();
    }

}