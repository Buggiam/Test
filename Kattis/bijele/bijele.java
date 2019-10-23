import java.util.Scanner;

public class bijele {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);   

        int kings = scn.nextInt();
        int queens = scn.nextInt();
        int rooks = scn.nextInt();
        int bishops = scn.nextInt();
        int knights = scn.nextInt();
        int pawns = scn.nextInt();

        System.out.print(1 - kings + " ");
        System.out.print(1 - queens + " ");
        System.out.print(2 - rooks + " ");
        System.out.print(2 - bishops + " ");
        System.out.print(2 - knights + " ");
        System.out.print(8 - pawns);
        
        scn.close();
    }
}