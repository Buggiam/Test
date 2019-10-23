import java.util.Scanner;

public class bela {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);   

        int N = scn.nextInt();
        char B = scn.next().charAt(0);

        int sum = 0;

        for (int i = 0; i < 4 * N; i++) {
            String input = scn.next();
            char value = input.charAt(0);
            char suit = input.charAt(1);

            sum += getValue(value, suit == B);
        }

        System.out.println(sum);
        
        scn.close();
    }

    private static int getValue(char c, boolean dominant) {
        switch(c) {
            case 'A':
                return 11;
            case 'K':
                return 4;
            case 'Q':
                return 3;
            case 'J':
                return dominant ? 20 : 2;
            case 'T':
                return 10;
            case '9':
                return dominant ? 14 : 0;
            default:
                return 0;                
        }
    }
}