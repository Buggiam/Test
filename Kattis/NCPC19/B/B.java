import java.util.*;

class B
{
    public B(int a1, int a2, int b1, int b2, int c1, int c2)
    {
        List<Building> b = new ArrayList<>();
        b.add(new Building(a1, a2));
        b.add(new Building(b1, b2));
        b.add(new Building(c1, c2));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    b.get(i).x;
                    b.get(j).area();
                    b.get(k);
                }
            }   
        }

    }

    private class Building
    {
        public int x,y;
        public Building(int _x, int _y)
        {
            x = _x;
            y = _y;
        }

        public int area()
        {
            return x * y;
        }

        public int min()
        {
            return Math.min(x, y);
        }

        public int max()
        {
            return Math.max(x,y);
        }

        @Override
        public String toString()
        {
            return "(" + x + ", " + y + ") = " + area();
        }
    }

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        
        int t = s.nextInt();

        for (int i = 0; i < t; i++) {
            int a1 = s.nextInt();
            int a2 = s.nextInt();
            int b1 = s.nextInt();
            int b2 = s.nextInt();
            int c1 = s.nextInt();
            int c2 = s.nextInt();
            new B(a1, a2, b1, b2, c1, c2);
            break;
        }
    }
}