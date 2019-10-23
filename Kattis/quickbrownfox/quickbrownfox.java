import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class quickbrownfox {

    private static Set<Character> letters;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());

        for (int i = 0; i < N; i++) {
            letters = alphabet();

            String line = in.readLine().toLowerCase();

            for (char c : line.toCharArray()) {
                letters.remove(c);
            }

            if (letters.isEmpty()) {
                System.out.println("pangram");
            } else {
                System.out.print("missing ");
                for (char c : letters) System.out.print(c);
                System.out.println();
            }
        }
    }

    private static Set<Character> alphabet() {
        return new HashSet<>(Arrays.asList
            ('a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z')
        );
    }
}