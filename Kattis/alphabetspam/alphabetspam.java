import java.util.Scanner;

public class alphabetspam {

    private static String r_whitespace = "_";
    private static String r_lowercase = "[a-z]";
    private static String r_uppercase = "[A-Z]";
    private static String r_symbols = "[^a-zA-Z_]";

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();

        int whitespace = 0;
        int lowercase = 0;
        int uppercase = 0;
        int symbols = 0;

        for (char c : input.toCharArray()) {
            String s = "" + c;
            if (s.matches(r_whitespace)) whitespace++;
            else if (s.matches(r_lowercase)) lowercase++;
            else if (s.matches(r_uppercase)) uppercase++;
            else if (s.matches(r_symbols)) symbols++;
        }

        System.out.println((double) whitespace / input.length());
        System.out.println((double) lowercase / input.length());
        System.out.println((double) uppercase / input.length());
        System.out.println((double) symbols / input.length());

        scn.close();
    }
}