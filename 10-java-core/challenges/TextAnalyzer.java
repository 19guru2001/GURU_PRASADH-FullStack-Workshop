import java.util.*;
import java.util.stream.*;

public class TextAnalyzer {

    public static Map<Integer, List<String>> groupByLength(List<String> words) {
        return words.stream()
                    .collect(Collectors.groupingBy(w -> w.length()));
    }

    public static Map<Character, Long> charFrequency(List<String> words) {
        return words.stream()
                    .flatMap(s -> s.chars().boxed())
                    .map(n -> (char) n.intValue())
                    .collect(Collectors.groupingBy(
                        ch -> ch,
                        Collectors.counting()
                    ));
    }

    public static void main(String[] args) {
        System.out.println(
            groupByLength(Arrays.asList("hi", "bye", "hello", "ok"))
        );

        System.out.println(
            charFrequency(Arrays.asList("aab", "bc"))
        );
    }
}
