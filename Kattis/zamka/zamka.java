import java.io.BufferedReader;
import java.io.InputStreamReader;

public class zamka {

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int L = Integer.parseInt(in.readLine());
        int D = Integer.parseInt(in.readLine());
        int X = Integer.parseInt(in.readLine());
       
        for (int N = L; N <= D; N++) {
            if (digitSum(N) == X) {
                System.out.println(N);
                break;
            }
        } 
        
        for (int M = D; M >= L; M--) {
            if (digitSum(M) == X) {
                System.out.println(M);
                break;
            }
        }  
    }

    private static int digitSum(int n) {
        String s = "" + n;
        int sum = 0;
        for (char c : s.toCharArray()) {
            sum += Integer.parseInt("" + c);
        }
        return sum;
    }
}