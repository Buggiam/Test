import java.util.Scanner;

public class towers {

    private static Node root = new Node();

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int M = scn.nextInt();

        while (M-- > 0) {
            String input = scn.next();
            root.add(new Tower(input));
        }

        root.print();

        scn.close();
    }

    private static class Tower implements Comparable<Tower> {
        private String s;
        private int[] powers;

        public Tower(String s) {
            this.s = s;

            String[] parts = s.split("\\^");

            powers = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                powers[i] = Integer.parseInt(parts[i]);
            }
        }

        public String toString() {
            return s;
        }

        public int compareTo(Tower other) {
            return 1;
        }
    }

    private static class Node {
        public Tower t = null;
        public Node next;

        public void add(Tower input) {
            if (t == null) {
                t = input;
                return;
            }

            if (next == null) {
                next = new Node();
            }

            if (input.compareTo(t) < 0) {
                next.add(t);
                t = input;
            } else {
                next.add(input);
            }
        }

        public void print() {
            if (t == null)
                return;

            System.out.println(t);
            if (next != null)
                next.print();
        }
    }
}