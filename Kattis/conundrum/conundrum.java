import java.io.InputStreamReader;
import java.io.BufferedReader;

public class conundrum {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        String s = scn.readLine().toLowerCase();

        int days = 0;

        String filler = "per";
        int fillerIndex = 0;

        for (int i = 0; i < s.length(); i++) {
            
            if (s.charAt(i) != filler.charAt(fillerIndex)) days++;

            fillerIndex++;
            if (fillerIndex >= filler.length()) fillerIndex = 0;
        }

        System.out.println(days);
    }
}