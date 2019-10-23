import java.util.Scanner;

public class anthonyanddiablo {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String[] input = scn.nextLine().split(" ");

        double A = Double.parseDouble(input[0]);
        double N = Double.parseDouble(input[1]);

        double rad = N / (2 * Math.PI);
        double area = Math.PI * (Math.pow(rad, 2));

        boolean possible = area >= A;
        String output = possible ? "Diablo is happy!" : "Need more materials!";
        System.out.println(output);

        scn.close();
    }

}