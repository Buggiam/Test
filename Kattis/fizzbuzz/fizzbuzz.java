import java.io.InputStreamReader;
import java.io.BufferedReader;

public class fizzbuzz {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        String[] input = scn.readLine().split(" ");
        int X = Integer.parseInt(input[0]);
        int Y = Integer.parseInt(input[1]);
        int N = Integer.parseInt(input[2]);

        StringBuilder output = new StringBuilder();

        for (int i = 1; i <= N; i++) {
            if (i % X == 0) output.append("Fizz");
            if (i % Y == 0) output.append("Buzz");
            if (output.length() == 0 || output.charAt(output.length() - 1) != 'z') 
                output.append(i);

            if (i < N) output.append("\n");
        }

        System.out.println(output.toString());

        scn.close();
    }
}