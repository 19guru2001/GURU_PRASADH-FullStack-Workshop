import java.util.*;
import java.util.stream.*;

public class Main {

    public static OptionalDouble averageOfEvens(List<Integer> numbers) {
        return numbers.stream()
                      .filter(n -> n % 2 == 0)
                      .mapToInt(n -> n)
                      .average();
    }

    static class Pair<K, V> {

        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Pair<V, K> swap() {
            return new Pair<>(value, key);
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Pair<?, ?> p)) return false;
            return Objects.equals(key, p.key) &&
                   Objects.equals(value, p.value);
        }

        public int hashCode() {
            return Objects.hash(key, value);
        }

        public String toString() {
            return "Pair(" + key + ", " + value + ")";
        }
    }

    public static Map<Integer, List<String>> groupByLength(List<String> words) {
        return words.stream()
                    .collect(Collectors.groupingBy(w -> w.length()));
    }

    public static Map<Character, Long> charFrequency(List<String> words) {
        return words.stream()
                    .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                    .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                    ));
    }

    public static void main(String[] args) {

        System.out.println(averageOfEvens(Arrays.asList(1, 2, 3, 4, 5, 6)));
        System.out.println(averageOfEvens(Arrays.asList(1, 3, 5)));

        Pair<String, Integer> pair = new Pair<>("age", 25);
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
        System.out.println(pair.swap());

        System.out.println(groupByLength(Arrays.asList("hi", "bye", "hello", "ok")));
        System.out.println(charFrequency(Arrays.asList("aab", "bc")));
    }
}
