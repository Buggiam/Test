import java.util.Scanner;

public class trik {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int cup = 1;

        String input = scn.nextLine();

        for (char c : input.toCharArray()) {
            switch (c) {
                case 'A':
                    if (cup == 1) cup = 2;
                    else if (cup == 2) cup = 1;
                    break;
                case 'B':
                    if (cup == 2) cup = 3;
                    else if (cup == 3) cup = 2;
                    break;
                case 'C':
                    if (cup == 1) cup = 3;
                    else if (cup == 3) cup = 1;
                    break;    
            }
        }

        System.out.println(cup);

        scn.close();
    }
}