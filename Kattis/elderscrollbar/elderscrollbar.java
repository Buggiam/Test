import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class elderscrollbar {

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        String[] inputLine = scn.nextLine().split(" ");
        
        int W = Integer.parseInt(inputLine[0]);
        int H = Integer.parseInt(inputLine[1]);
        int F = Integer.parseInt(inputLine[2]);
        int N = Integer.parseInt(inputLine[3]);
        int L = -1;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            String line = scn.nextLine();
            sb.append(line);
            sb.append(" ");
        }

        String input = sb.toString().trim();
        ArrayList<String> lines = splitOntoLines(input, W);

        L = lines.size();

        int T = getThumb(H, L, F);

        System.out.println("+" + repeat('-', W) + "+-+");
        for (int l = F; l < F + H; l++) {
            System.out.print("|" + lines.get(l) + "|");
            if (l == F) System.out.print("^");
            else if (l == F + T + 1) System.out.print("X");
            else if (l == F + H - 1) System.out.print("v");
            else System.out.print(" ");
            System.out.println("|");
        }
        System.out.println("+" + repeat('-', W) + "+-+");

        scn.close();
    }

    private static ArrayList<String> splitOntoLines(String input, int lineWidth) {
        ArrayList<String> lines = new ArrayList<>();
        
        StringBuilder line = new StringBuilder();

        String saved = null;

        boolean firstOnLine = true;
        
        for (String word : input.split(" ")) {
            String added = "";

            String wordEnd = null;

            if (word.length() > lineWidth) {
                wordEnd = word.substring(lineWidth);
                word = word.substring(0, lineWidth);
            }

            if (saved != null) added = saved + " "; 
            if (!firstOnLine) added = " " + added;
            added = added + word;
            saved = null;

            saved = wordEnd;

            if (line.length() + added.length() <= lineWidth) {
                line.append(added);
                firstOnLine = false;
            } else {
                line.append(repeat(' ', lineWidth - line.length()));
                lines.add(line.toString());

                line = new StringBuilder();
                line.append(word);
                firstOnLine = false;
            }
        }

        return lines;
    }

    private static int getThumb(int H, int L, int F) {       
        int denominator = L - H;
        H -= 3;
        int numerator = F * H;
        int T = numerator % denominator;
        
        return T;
    }

    private static String repeat(char c, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++)
            sb.append(c);

        return sb.toString();    
    }
}