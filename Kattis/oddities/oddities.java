import java.util.Scanner;

public class oddities {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = scn.nextInt();

        for (int i = 0; i < n; i++) {
            int x = scn.nextInt();
            System.out.println(x + " is " + (x % 2 == 0 ? "even" : "odd"));
        }

        scn.close();
    }

}