import java.util.Scanner;

public class pieceofcake2 {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int n = scn.nextInt();
        int h = scn.nextInt();
        int v = scn.nextInt();
        int height = 4;

        int[] vol = new int[4];

        vol[0] = v * h * height;                //Upper Left
        vol[1] = (n - v) * h * height;          //Upper Right
        vol[2] = v * (n - h) * height;          //Lower Left
        vol[3] = (n - v) * (n - h) * height;    //Lower Right

        int max = 0;
        for (int vo : vol) if (vo > max) max = vo;
        
        System.out.println(max);

        scn.close();
    }
}