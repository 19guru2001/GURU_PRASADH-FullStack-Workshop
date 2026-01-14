import java.util.Optional;

interface SafeSupplier<T> {
    T supply() throws Exception;
}

public class SafeExecutor {

    public static <T> Optional<T> executeSafely(SafeSupplier<T> task) {
        try {
            return Optional.ofNullable(task.supply());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {

        System.out.println(
            executeSafely(() -> Integer.parseInt("123"))
        );

        System.out.println(
            executeSafely(() -> Integer.parseInt("abc"))
        );
    }
}
