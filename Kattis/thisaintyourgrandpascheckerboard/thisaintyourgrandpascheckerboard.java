import java.util.Scanner;

public class thisaintyourgrandpascheckerboard {

    public static  void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = Integer.parseInt(scn.nextLine());

        String[] rows = new String[n];
        String[] columns = new String[n];

        for (int r = 0; r < n; r++) {
            rows[r] = scn.nextLine();
        }

        for (int c = 0; c < n; c++) {
            StringBuilder sb = new StringBuilder();

            for (int r = 0; r < n; r++) 
                sb.append(rows[r].charAt(c));

            columns[c] = sb.toString();
        }

        boolean approved = true;

        if (!check(rows)) approved = false;
        else if (!check(columns)) approved = false;

        System.out.println(approved ? 1 : 0);
    }

    public static boolean check(String[] lines) {
        for (String line : lines) {
            int blacks = 0;
            int whites = 0;
            int consecutive = 0;
            char consecutiveC = ' ';

            for (char c : line.toCharArray()) {
                if (c == 'B') blacks++;
                else if (c == 'W') whites++;

                if (c != consecutiveC) {
                    consecutiveC = c;
                    consecutive = 0;
                }

                consecutive++;

                if (consecutive >= 3) return false;
            }

            if (blacks != whites) return false;
        }

        return true;
    }
}