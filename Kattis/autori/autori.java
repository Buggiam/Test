import java.util.Scanner;

public class autori {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String input = scn.nextLine();

        boolean firstLetter = true;

        String result = "";

        for (char c : input.toCharArray()) {
            if (firstLetter) {
                result += c;
                firstLetter = false;
                continue;
            }

            if (c == '-') firstLetter = true;
        }

        System.out.println(result);

        scn.close();
    }
}