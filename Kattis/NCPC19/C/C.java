import java.util.Scanner;

class C 
{
    public static long n, m, a;
    public static void main(String[] args) {
        readIn();

        if(a % n == 0 || a % m == 0) {
            System.out.println(1);
            return;
        }
/*
        if (a > (n - 1) * m) {
            System.out.println(2);
            return;
        }

        if (a > (m - 1) * n) {
            System.out.println(2);
            return;
        }

        if (a < Math.max(n, m)) {
            System.out.println(2);
            return;
        }
*/
        for (int i = 1; i < Math.max(m, n); i++) {    
            if (a % i == 0 && a/i <= Math.max(n, m)) {
                System.out.println(2);   
                return;                 
            } 
        }    
        for (int i = 1; i < Math.max(m, n); i++) {    
            if (((m*n)-a) % i == 0 && ((m*n)-a)/i <= Math.max(n, m)) {
                System.out.println(2);   
                return;                 
            } 
        }     
    
        System.out.println(3);
    
    }   

    private static void readIn()
    {
        Scanner s = new Scanner(System.in);

        n = s.nextLong();
        m = s.nextLong();
        a = s.nextLong();
        
        s.close();
    }
}