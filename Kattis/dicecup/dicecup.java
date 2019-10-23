import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class dicecup {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        String[] input = scn.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);

        int[] results = new int[41];
        ArrayList<Integer> mostLikely = new ArrayList<>();
        int highestProbability = 0;

        for (int n = 1; n <= N; n++) {
            for (int m = 1; m <= M; m++) {
                int sum = n + m;
                results[sum]++;

                if (results[sum] > highestProbability) {
                    mostLikely.clear();
                    mostLikely.add(sum);
                    highestProbability = results[sum];
                } else if (results[sum] == highestProbability) {
                    mostLikely.add(sum);
                }
            }
        }

        for (int n : mostLikely) {
            System.out.println(n);
        }
    }
}