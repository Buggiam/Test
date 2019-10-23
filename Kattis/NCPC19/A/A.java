import java.util.*;

public class A
{
    private static String lastWord = "";
    
    private static int n;

    private static HashMap<Character, List<String>> map = new HashMap<>();

    public static void main(String[] args)
    {
        readIn();

        List<String> options = getPossibleWords(lastWord);

        if(options == null)
        {
            System.out.println("?");
            return;
        }

        for (String option : options) {
            List<String> answers = getPossibleWords(option);
            if(answers == null || (answers.size() == 1 && answers.get(0) == option))
            {
                System.out.println(option + "!");
                return;
            }
        }

        System.out.println(options.get(0));
    }

    private static List<String> getPossibleWords(String word)
    {
        char nextStartChar = word.charAt(word.length() - 1);

        return map.get(nextStartChar);
    }

    private static void readIn()
    {
        Scanner s = new Scanner(System.in);
        lastWord = s.next();
        n = s.nextInt();
        
        for (int i = 0; i < n; i++) {
            String w = s.next();
            char c = w.charAt(0);
            List<String> list = map.get(c);

            if(list == null) {
                list = new ArrayList<>();
            }
            
            list.add(w);

            map.put(c, list);
        }
    }
}