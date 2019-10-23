import java.util.Scanner;

public class isithalloween {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String input = scn.nextLine();

        if (input.equals("OCT 31") || input.equals("DEC 25")) {
            System.out.println("yup");
        } else {
            System.out.println("nope");
        }

        scn.close();
    }
}