import java.util.Scanner;

public class Stockbroker {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int amount = scn.nextInt();
        int[] prices = new int[amount];

        for (int i = 0; i < amount; i++) {
            prices[i] = scn.nextInt();
        }

        int balance = 100;
        int owned = 0;

        for (int i = 0; i < amount; i++) {

            int price = prices[i];
            boolean lastDay = i == amount - 1;

            int nextDayPrice = Integer.MIN_VALUE;
            if (!lastDay) {
                nextDayPrice = prices[i + 1];
            }

            if (owned > 0 && (lastDay || price > nextDayPrice)) {
                balance += price * owned;
                owned = 0;
            } else if (!lastDay && nextDayPrice > price) {
                int toBuy = balance / price;

                if (owned + toBuy > 100000) {
                    toBuy = 100000 - owned;
                }

                if (toBuy > 0) {
                    owned += toBuy;
                    balance -= price * toBuy;
                }
            }
        }

        System.out.println(balance);
        scn.close();
    }
}