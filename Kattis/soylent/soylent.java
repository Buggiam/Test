import java.io.BufferedReader;
import java.io.InputStreamReader;

public class soylent {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());

        for (int i = 0; i < N; i++) {
            int calories = Integer.parseInt(in.readLine());

            int toDrink = calories / 400;
            if (toDrink * 400 < calories) toDrink++;
            System.out.println(toDrink);
        }
    }
}