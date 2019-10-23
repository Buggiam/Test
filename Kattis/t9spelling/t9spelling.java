import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class t9spelling {

    private static HashMap<Character, String> characters = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(in.readLine());
        initiateMap();

        for (int i = 1; i <= N; i++) {
            StringBuilder output = new StringBuilder("Case #" + i + ": ");
            String line = in.readLine();

            String lastTyped = null;
            for (char c : line.toCharArray()) {
                String typed = characters.get(c);

                if (lastTyped != null && lastTyped.charAt(0) == typed.charAt(0)) {
                    output.append(" ");
                }

                output.append(typed);
                lastTyped = typed;
            }
            System.out.println(output.toString());
        }
    }

    private static void initiateMap() {
        characters.put(' ', "0");
        characters.put('a', "2");
        characters.put('b', "22");
        characters.put('c', "222");
        characters.put('d', "3");
        characters.put('e', "33");
        characters.put('f', "333");
        characters.put('g', "4");
        characters.put('h', "44");
        characters.put('i', "444");
        characters.put('j', "5");
        characters.put('k', "55");
        characters.put('l', "555");
        characters.put('m', "6");
        characters.put('n', "66");
        characters.put('o', "666");
        characters.put('p', "7");
        characters.put('q', "77");
        characters.put('r', "777");
        characters.put('s', "7777");
        characters.put('t', "8");
        characters.put('u', "88");
        characters.put('v', "888");
        characters.put('w', "9");
        characters.put('x', "99");
        characters.put('y', "999");
        characters.put('z', "9999");
    }
}