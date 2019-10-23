import java.util.Scanner;

public class yoda {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		int num1 = scn.nextInt();
		int num2 = scn.nextInt();

		int res1 = -1;
		int res2 = -1;

		int step1 = 1;
		int step2 = 1;

		while (num1 > 0 || num2 > 0) {
			int d1 = -1;
			int d2 = -1;

			if (num1 > 0) {
				d1 = num1 - (num1 / 10 * 10);
				num1 = num1 / 10;
			}
			if (num2 > 0) {
				d2 = num2 - (num2 / 10 * 10);
				num2 = num2 / 10;
			}

			if (d1 != -1 && d1 >= d2) {
				if (res1 <= -1) res1 = 0;
				res1+=d1 * step1;
				step1 *= 10;
			}
			if (d2 != -1 && d2 >= d1) {
				if (res2 <= -1) res2 = 0;
				res2+=d2 * step2;
				step2 *= 10;
			}
		}

		System.out.println(res1 > -1 ? res1 : "YODA");
		System.out.println(res2 > -1 ? res2 : "YODA");

		scn.close();
	}
}
