import java.util.Scanner;

public class hangingout {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String[] line1 = scn.nextLine().split(" ");

        int L = Integer.parseInt(line1[0]);
        int x = Integer.parseInt(line1[1]);

        int onTerrace = 0;
        int notAllowed = 0;

        for (int i = 0; i < x; i++) {
            String[] input = scn.nextLine().split(" ");

            String action = input[0];
            int amount = Integer.parseInt(input[1]);

            if (action.equals("enter")) {
                if (onTerrace + amount <= L)
                    onTerrace += amount;
                else
                    notAllowed++;   
            } else if (action.equals("leave")) {
                onTerrace -= amount;
            }
        }

        System.out.println(notAllowed);
    }
}