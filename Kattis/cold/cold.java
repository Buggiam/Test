import java.util.Scanner;

public class cold {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);   

        int N = scn.nextInt();

        int daysBelow = 0;

        for (int i = 0; i < N; i++) {
            if (scn.nextInt() < 0) daysBelow++;
        }

        System.out.println(daysBelow);
        
        scn.close();
    }
}