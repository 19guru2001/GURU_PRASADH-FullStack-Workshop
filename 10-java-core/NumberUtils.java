import java.util.*;

public class NumberUtils {

    public static OptionalDouble averageOfEvens(List<Integer> numbers) {
        return numbers.stream()
                      .filter(n -> n % 2 == 0)
                      .mapToInt(n -> n)
                      .average();
    }

    public static void main(String[] args) {
        System.out.println(
            averageOfEvens(Arrays.asList(1, 2, 3, 4, 5, 6))
        );

        System.out.println(
            averageOfEvens(Arrays.asList(1, 3, 5))
        );
    }
}

