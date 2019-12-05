import java.util.ArrayList;
import java.util.Scanner;

public class symmetricorder {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = Integer.parseInt(scn.nextLine());

        ArrayList<String[]> sets = new ArrayList<>();

        do {
            String[] set = new String[n];
            int addIndex = 0;

            for (int i = 0; i < n; i++) {
                String name = scn.nextLine();
                set[addIndex] = name;

                //Change index
                if (addIndex < ((double) n) / 2) {
                    addIndex = n - addIndex - 1;
                } else {
                    addIndex = n - addIndex;
                }
            }

            sets.add(set);

            n = Integer.parseInt(scn.nextLine());
        } while (n != 0);
        
        for (int s = 0; s < sets.size(); s++) {
            String[] set = sets.get(s);
            System.out.println("SET " + (s + 1));
            for (String name : set) {
                System.out.println(name);
            }
        }
    }
}