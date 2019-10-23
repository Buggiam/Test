import java.io.BufferedReader;
import java.io.InputStreamReader;

public class lineup {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());

        int order = 0;
        String lastName = null;

        for (int i = 0; i < N; i++) {
            String name = in.readLine();

            if (lastName != null) {
                int compared = name.compareTo(lastName) < 0 ? -1 : 1;
                if (order != 0 && compared != order) {
                    System.out.println("NEITHER");
                    return;
                }

                order = compared;
            }
            lastName = name;
        }

        System.out.println(order == 1 ? "INCREASING" : "DECREASING");
    }
}