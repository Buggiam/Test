import java.util.Scanner;

public class Chocolate {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Long m = scn.nextLong();
		Long n = scn.nextLong();
		Long a = scn.nextLong();

		if (a % n == 0 || a % m == 0) {
			System.out.println(1);
			return;
		}

		for (int i = 1; i < Math.min(m, n); i++) {
			if (a % i == 0 && a / i < Math.max(m, n)) {
				System.out.println(2);
				return;
			}

			if (((m * n) - a) % i == 0 && ((m * n) - a) / i < Math.max(m, n)) {
				System.out.println(2);
				return;
			}
		}

		System.out.println(3);
	}
}
