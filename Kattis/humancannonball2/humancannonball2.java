import java.util.Scanner;

public class humancannonball2 {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();

        double v0, angle, x1, h1, h2;

        for (int i = 0; i < N; i++) {
            v0 = Double.parseDouble(scn.next());
            angle = Double.parseDouble(scn.next());
            x1 = Double.parseDouble(scn.next());
            h1 = Double.parseDouble(scn.next());
            h2 = Double.parseDouble(scn.next());
            
            angle = Math.toRadians(angle);
            double t = x1 / (v0 * Math.cos(angle));            
            double y = v0 * t * Math.sin(angle) - 0.5 * 9.81 * t * t;

            System.out.println(y > h1 + 1 && y < h2 - 1 ? "Safe" : "Not Safe");
        }

        scn.close();
    }
}