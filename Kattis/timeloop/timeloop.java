import java.util.Scanner;

public class timeloop {


    public static void main(String[] args) {
        java.util.Scanner scn = new Scanner(System.in);

        int amount = scn.nextInt();

        for (int i = 0; i < amount; i++) {
            System.out.println((i+1) + " Abracadabra");
        }

        scn.close();
    }
}