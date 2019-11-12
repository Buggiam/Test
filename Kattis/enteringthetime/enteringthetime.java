import java.util.*;

public class enteringthetime {

    private static List<String> results = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        String[] from = scn.nextLine().split(":");
        String[] to = scn.nextLine().split(":");

        int fromD1 = Integer.parseInt(from[0].split("")[0]);
        int fromD2 = Integer.parseInt(from[0].split("")[1]);
        int fromD3 = Integer.parseInt(from[1].split("")[0]);
        int fromD4 = Integer.parseInt(from[1].split("")[1]);

        int toD1 = Integer.parseInt(to[0].split("")[0]);
        int toD2 = Integer.parseInt(to[0].split("")[1]);
        int toD3 = Integer.parseInt(to[1].split("")[0]);
        int toD4 = Integer.parseInt(to[1].split("")[1]);

        Dig h10 = new Dig(fromD1, 2, toD1);
        Dig h1 = new Dig(fromD2, 9, toD2);
        Dig m10 = new Dig(fromD3, 5, toD3);
        Dig m1 = new Dig(fromD4, 59, toD4);

        for (int step : m1.adjust()) 
            addTimeString(h10.value, h1.value, m10.value, m1.value);

        for (int step : m10.adjust()) 
            addTimeString(h10.value, h1.value, m10.value, m1.value);    

        printResults();

        scn.close();
    }

    private static ArrayList<Integer> adjustNumber(int from, int to, int max) {
        List<Integer> steps = new ArrayList<>();

        return steps;
    }

    private static void printResult() {
        System.out.println(results.size());
        for (String result : results) System.out.println(result);
    }

    private static String addTimeString(int hr10, int h1, int mi10, int mi1) {
        results.add(String.format("%d%d:%d%d", hr10, hr1, mi10, mi1));
    }

    private static class Dig {

        public int value;
        private int max;
        private int goal;

        public Dig(int value, int parentMax, int goal) {
            this.value = value;
            this.max = parentMax;
            this.goal = goal;
        }

        public ArrayList<Integer> adjust() {
            List<Integer> steps = new ArrayList<>();
            
            int add = 0;
            if (max + goal - value < goal - value) {
                add = -1;
            } else {
                add = 1;
            }

            while (value != goal) {
                value += add;
            }

            return steps;
        }
    }
}