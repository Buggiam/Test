import java.util.Scanner;

public class qaly {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();
        double QALY = 0;

        for (int i = 0; i < N; i++) {
            double q = Double.parseDouble(scn.next());
            double y = Double.parseDouble(scn.next());
            QALY += q * y;
        }

        System.out.println(QALY);

        scn.close();
    }
}