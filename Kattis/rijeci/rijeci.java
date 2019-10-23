import java.io.BufferedReader;
import java.io.InputStreamReader;

public class rijeci {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());

        int a = 1;
        int b = 0;

        for (int i = 0; i < N; i++) {
            b = a + b;
            a = b - a;
        }

        System.out.println(a + " " + b);
    }
}