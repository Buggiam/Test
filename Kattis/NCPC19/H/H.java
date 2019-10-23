import java.util.Scanner;

class H 
{
    public static void main(String[] args) {
        int[] temp = readIn();

        int firstDay, lastDay, bestI = -1;
        int bestTemp = Integer.MAX_VALUE;

        for (int i = 0; i < temp.length - 2; i++) {
            firstDay = temp[i];
            lastDay = temp[i+2];
            if(firstDay < bestTemp && lastDay < bestTemp )
            {
                bestTemp = Math.max(firstDay, lastDay);
                bestI = i;
            }
        }

        System.out.println(bestI + 1 + " " + Math.max(temp[bestI], temp[bestI+2]));
    }   

    private static int[] readIn()
    {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int[] temp = new int[n];

        for(int i = 0; i < n; i++) {
            temp[i] = s.nextInt();
        }

        return temp;
    }
}