import java.util.*;

public class everywhere {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        Set<String> cities = new HashSet<>();

        int T = Integer.parseInt(scn.nextLine());
        for (int i = 0; i < T; i++) {

            int n = Integer.parseInt(scn.nextLine());
            for (int j = 0; j < n; j++) {

                cities.add(scn.nextLine());
            }

            System.out.println(cities.size());
            cities.clear();
        }
        

        scn.close();
    }
}