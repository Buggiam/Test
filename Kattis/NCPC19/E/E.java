import java.util.*;

public class E 
{
    public static int rhyme = 0;
    public static int n = 0;
    public static List<String> students;


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        rhyme = s.nextLine().trim().split(" ").length-1;
        n = s.nextInt();
        students = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            students.add(s.next());
        }

        ArrayList<String>[] teams = new ArrayList[] {
            new ArrayList<>(),
            new ArrayList<>()
        };

        int even = 0;
        int index = 0;
        while(!students.isEmpty()) {
            index = pickstudent(index);
            teams[even%2].add(students.get(index));
            students.remove(index);

            if(index == students.size()) index=0; 
            even++;
        }

        StringBuilder b = new StringBuilder();
        b.append(teams[0].size() + "\n");
        for(String str : teams[0]){
            b.append(str+ "\n");
        }

        b.append(teams[1].size() + "\n");
        for(String str : teams[1]){
            b.append(str+ "\n");
        }

        System.out.println(b.toString());
    }

    public static int pickstudent(int i){
        if(students.size() == 1) return 0;
        return (i+rhyme)%(students.size());        
    }
}