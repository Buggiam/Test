import java.io.InputStreamReader;
import java.io.BufferedReader;

public class detaileddifferences {

    public static void main(String[] args) throws Exception {
        BufferedReader scn = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(scn.readLine());

        for (int i = 0; i < n; i++) {
            String s1 = scn.readLine();
            String s2 = scn.readLine();
            char[] c1 = s1.toCharArray();
            char[] c2 = s2.toCharArray();

            StringBuilder output = new StringBuilder();
            for (int j = 0; j < c1.length; j++) {
                output.append(c1[j] == c2[j] ? "." : "*");
            }

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(output.toString());
        }
    }
}