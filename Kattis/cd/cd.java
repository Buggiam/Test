import java.util.HashSet;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class cd {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        String line = scn.readLine();
        while (!line.equals("0 0")) {

            String[] input = line.split(" ");
            int N = Integer.parseInt(input[0]);
            int M = Integer.parseInt(input[0]);

            HashSet<String> owned = new HashSet<>();

            for (int i = 0; i < N; i++) {
                owned.add(scn.readLine());
            }

            int duplicates = 0;

            for (int i = 0; i < M; i++) {
                if (owned.contains(scn.readLine())) duplicates++;
            }

            System.out.println(duplicates);
            line = scn.readLine();
        }
    }
}