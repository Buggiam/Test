import java.io.BufferedReader;
import java.io.InputStreamReader;

public class sibice {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] input = in.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int W = Integer.parseInt(input[1]);
        int H = Integer.parseInt(input[2]);

        double maxLength = Math.sqrt(Math.pow(W, 2) + Math.pow(H, 2));

        for (int i = 0; i < N; i++) {
            int match = Integer.parseInt(in.readLine());
            System.out.println(match <= maxLength ? "DA" : "NE");
        }
    }
}