import java.util.Scanner;

class K 
{
    public static int newSoda, m, slots, d;
    public static int[] in, out;

    public static void main(String[] args) {
        readIn();

        int emptiestSlot = Integer.MAX_VALUE;


        while(newSoda > 0){
            emptiestSlot = findEmptiest();
            int sodasAdded = Math.min(d-in[emptiestSlot], newSoda);
            out[emptiestSlot] = sodasAdded;
            in[emptiestSlot] = -1;
            newSoda -= sodasAdded;
        }

        if(impossible()){
            System.out.print("impossible");
            return;
        } 

        StringBuilder b = new StringBuilder();
        for(int outSoda : out){
            b.append(outSoda + " ");
        }
        System.out.println(b.toString().trim());

    }   

    private static int findEmptiest(){
        int currentE = Integer.MAX_VALUE;
        int currentI = 0;
        for(int i = 0; i < in.length ;i++ ){
            if (in[i] < 0) continue;
            if (in[i] == 0 ) return i;
            if (in[i] < currentE){
                currentE = in[i];
                currentI = i;
            } 
        }
        return currentI;
    }


    public static boolean impossible(){
        int coldSodas = 0;
        for(int soda: in){
            if(soda > -1) coldSodas += soda;
        }
        return coldSodas < m;
    }


    private static void readIn()
    {
        Scanner scan = new Scanner(System.in);

        newSoda = scan.nextInt();
        m = scan.nextInt();
        slots = scan.nextInt();
        d = scan.nextInt();
        in = new int[slots];
        out = new int[slots];

        for(int i = 0; i < slots; i++) {
            in[i] = scan.nextInt();
        }

        scan.close();
    }
}