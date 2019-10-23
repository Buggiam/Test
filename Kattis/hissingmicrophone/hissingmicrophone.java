import java.util.Scanner;

public class hissingmicrophone {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String line = scn.nextLine();
        System.out.println(line.contains("ss") ? "hiss" : "no hiss");

        scn.close();
    }
}