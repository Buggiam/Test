import java.io.BufferedReader;
import java.io.InputStreamReader;

public class onechicken {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] input = in.readLine().split(" ");

        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);

        int overload = M - N;

        if (overload > 0) {
            System.out.println("Dr. Chaz will have " + overload + " piece" + (overload > 1 ? "s" : "") + " of chicken left over!");
        } else {
            System.out.println("Dr. Chaz needs " + -overload + " more piece" + (overload < -1 ? "s" : "") + " of chicken!");
        }
    }
}