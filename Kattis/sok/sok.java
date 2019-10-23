import java.io.BufferedReader;
import java.io.InputStreamReader;

public class sok {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] bought = in.readLine().split(" ");
        int[] b = new int[]{Integer.parseInt(bought[0]), Integer.parseInt(bought[1]), Integer.parseInt(bought[2])};

        String[] ratio = in.readLine().split(" ");
        int r = new int[] {Integer.parseInt(ratio[0]), Integer.parseInt(ratio[1]), Integer.parseInt(ratio[2])};

        int leastUsed = 0;
        if (b1 * r1 < b2 * r2 && b1 * r1 < b3 * r3) leastUsed = 1;
        else if (b2 * r2 < b1 * r1 && b2 * r2 < b3 * r3) leastUsed = 2;
        else if (b3 * r3 < b1 * r1 && b3 * r3 < b2 * r2) leastUsed = 3;

        double useRatio = 0;
        if (leastUsed == 1) {
            useRatio = 
        }



        double n1 = 

        double o1, o2, o3 = 0;
    }
}