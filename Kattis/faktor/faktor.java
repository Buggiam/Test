import java.util.Scanner;

public class faktor {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int A = scn.nextInt();
        int I = scn.nextInt();

        int required = A * (I - 1) + 1;
        System.out.println(required);

        scn.close();
    }
}