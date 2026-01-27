import java.util.*;
import java.util.function.*;

public class LambdaRefactor {

    public static void main(String[] args) {

        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Christopher", "Anna"));
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        
        Collections.sort(names, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        List<Integer> evens = new ArrayList<>();
        for (Integer n : numbers) {
            if (n % 2 == 0) {
                evens.add(n);
            }
        }

        for (String s : names) {
            System.out.println(s);
        }

        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("Running");
            }
        });
        t.start();

        List<String> upper = new ArrayList<>();
        for (String s : names) {
            upper.add(s.toUpperCase());
        }

       
        names.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));

        List<Integer> evenNumbers =
                numbers.stream()
                       .filter(n -> n % 2 == 0)
                       .toList();

        names.forEach(System.out::println);

        Thread thread = new Thread(() -> System.out.println("Running"));
        thread.start();

        List<String> upperCase =
                names.stream()
                     .map(String::toUpperCase)
                     .toList();
    }
}
