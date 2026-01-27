import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LogEntry(LocalDateTime timestamp, String level, String message) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static LogEntry parse(String line) {
        String[] parts = line.split(" ", 4);
        LocalDateTime timestamp = LocalDateTime.parse(parts[0] + " " + parts[1], FORMATTER);
        String level = parts[2];
        String message = parts[3];
        return new LogEntry(timestamp, level, message);
    }
    
    @Override
    public String toString() {
        return timestamp.format(FORMATTER) + " " + level + " " + message;
    }
}