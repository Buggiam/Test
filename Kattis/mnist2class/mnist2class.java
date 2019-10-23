public class mnist2class {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 51; i++) { 
            if (i < 51) sb.append(" ");
            sb.append(1);
        }

        String s = sb.toString();
        for (int i = 0; i < 30; i++) {
            System.out.println(s);
        }
    }
}