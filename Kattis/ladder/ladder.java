import java.util.Scanner;

public class ladder {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int h = scn.nextInt();
        int v = scn.nextInt();

        System.out.println((int)Math.ceil(h/Math.sin((Math.toRadians(v)))));

        scn.close();
    }
}