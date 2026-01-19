import java.io.IOException;

public class CsvFileNotFoundException extends IOException {
    public CsvFileNotFoundException(String message) {
        super(message);
    }

    public CsvFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}