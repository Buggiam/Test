import java.util.Scanner;

public class drinkingsong {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = Integer.parseInt(scn.nextLine().trim());
        String bev = scn.nextLine().trim();

        String form = "bottles";

        for (int i = n; i >= 1; i--) {
            if (i == 1) form = "bottle";

            System.out.println(i + " " + form + " of " + bev + " on the wall, " + i + " " + form + " of milk.");
            if (i > 1) {
                if (i > 2)
                    System.out.println("Take one down, pass it around, " + (i-1) + " bottles of " + bev + " on the wall.");
                else
                    System.out.println("Take one down, pass it around, " + (i-1) + " bottle of " + bev + " on the wall.");    
            }
            else
                System.out.print("Take it down, pass it around, no more bottles of " + bev + ".");

            if (i != 1) System.out.println();
        }

        scn.close();
    }
}