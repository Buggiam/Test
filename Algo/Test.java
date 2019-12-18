import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        int amount = 10000;
        ArrayList<String> list = random(amount, 10);

        Sorter sorter = new Sorter<String>(list, Sorter.Algorithm.MERGE_SORT, Sorter.Order.ASC);
        Timer timer = new Timer();

        timer.start("Sort on " + amount);
        sorter.sort();
        timer.stop();

        System.out.println("Sorted? " + (sorter.isSorted() ? "YES" : "NO"));
    }

    private static ArrayList<String> random(int amount, int length) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            list.add(randomString(length));

        return list;
    }

    public static String randomString(int length) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}