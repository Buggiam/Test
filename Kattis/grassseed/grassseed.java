import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class grassseed {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        double C = Double.parseDouble(scn.readLine());
        double L = Double.parseDouble(scn.readLine());

        double price = 0;

        for (int i = 0; i < L; i++) {
            String[] lawn = scn.readLine().split(" ");
            double w = Double.parseDouble(lawn[0]);
            double h = Double.parseDouble(lawn[1]);

            double lawnPrice = w * h * C;
            price += lawnPrice;
        }

        System.out.println(price);

        scn.close();
    }
}