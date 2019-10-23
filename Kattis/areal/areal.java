import java.util.Scanner;

public class areal {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int input = scn.nextInt();

        double side_length = Math.sqrt(input);

        System.out.println("wall: 4 x " + side_length);

        scn.close();
    }
}