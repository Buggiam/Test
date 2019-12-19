import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        //fullSortingTest(10000, 10);
        ArrayList<String> list = random(10000, 10);
        sortingTest(list, Sorter.Algorithm.MERGE_SORT, Sorter.Order.ASC);
        sortingTest(list, Sorter.Algorithm.QUICK_SORT, Sorter.Order.ASC);
        sortingTest(list, Sorter.Algorithm.TIM_SORT, Sorter.Order.ASC);
    }

    private static void sortingTest(ArrayList<String> list, Sorter.Algorithm alg, Sorter.Order ord) {
        Sorter sorter = new Sorter<String>(list, alg);
        Timer timer = new Timer();

        timer.start(String.format("%s <%s>", alg, ord));
        sorter.sort();
        timer.stop();

        System.out.println("  Sorted? " + (sorter.isSorted() ? "YES" : "NO"));
    }

    private static void sortingTest(Sorter.Algorithm alg, Sorter.Order ord, int listSize, int stringLength) {
        ArrayList<String> list = random(listSize, stringLength);
        sortingTest(list, alg, ord);
    }

    private static void fullSortingTest(int listSize, int stringLength) {
        System.out.printf("Generating list[%d]:%d%n", listSize, stringLength);
        ArrayList<String> list = random(listSize, stringLength);

        for (Sorter.Algorithm alg : Sorter.Algorithm.values()) {
            for (Sorter.Order ord : Sorter.Order.values()) {
                sortingTest(list, alg, ord);
            }
        }
    }

    private static ArrayList<String> random(int amount, int length) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            list.add(randomString(length));

        return list;
    }

    private static String randomString(int length) {

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