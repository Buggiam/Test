import java.util.Scanner;

public class tgif {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String[] line1 = scn.nextLine().split(" ");

        int date = Integer.parseInt(line1[0]);
        int month = getMonthNumber(line1[1]);

        int day = getDayNumber(scn.nextLine());

        for (int m = 1; m <= month; m++) {
            for (int d = 1; d <= getDaysInMonth(m); d++) {

                if (m == month && d == date) {
                    break;
                }

                day++;
                if (day > 7) day = 1;
            }
        }

        if (month > 2 && (day == 4 || day == 5)) {
            System.out.println("not sure");
        } else if (day == 5) {
            System.out.println("TGIF");
        } else {
            System.out.println(":(");
        }

        scn.close();
    }

    private static int getMonthNumber(String name) {
        switch (name) {
            case "JAN": return 1;
            case "FEB": return 2;
            case "MAR": return 3;
            case "APR": return 4;
            case "MAY": return 5;
            case "JUN": return 6;
            case "JUL": return 7;
            case "AUG": return 8;
            case "SEP": return 9;
            case "OCT": return 10;
            case "NOV": return 11;
            case "DEC": return 12;
        }
        System.exit(0);
        return -1;
    }

    private static int getDayNumber(String name) {
        switch (name) {
            case "MON": return 1;
            case "TUE": return 2;
            case "WED": return 3;
            case "THU": return 4;
            case "FRI": return 5;
            case "SAT": return 6;
            case "SUN": return 7;
        }
        System.exit(0);
        return -1;
    }

    private static int getDaysInMonth(int monthNum) {
        switch(monthNum) {
            case 1: return 31;
            case 2: return 28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
        }

        System.exit(0);
        return -1;
    }
}