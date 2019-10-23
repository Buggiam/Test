import java.util.Scanner;

public class spavanac {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int H = scn.nextInt();
        int M = scn.nextInt();

        M -= 45;
        if (M < 0) {
            H--;
            M = 60 + M;

            if (H < 0) {
                H = 24 + H;
            }
        }
        
        System.out.println(H + " " + M);

        scn.close();
    }
}