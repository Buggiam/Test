import java.io.BufferedReader;
import java.io.InputStreamReader;

public class quickestimate {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());

        for (int i = 0; i < N; i++) {
            System.out.println(in.readLine().length());
        }
    }
}