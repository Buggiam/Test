import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class missingnumbers {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int amount = Integer.parseInt(in.readLine());

        List<Integer> missing = new ArrayList<>();

        int expected = 1;

        for (int i = 0; i < amount; i++) {
            int number = Integer.parseInt(in.readLine());

            if (number != expected) {
                for (int missed = expected; missed < number; missed++) {
                    missing.add(missed);
                }
                expected = number + 1;    
            } else {
                expected++;
            }
        }

        if (!missing.isEmpty()) {
            for (int n : missing) System.out.println(n);
        } else System.out.println("good job");
    }
}