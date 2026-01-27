import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class LogAnalyzerTest {
    public static void main(String[] args) {
        try {
            LogAnalyzer analyzer = new LogAnalyzer();
            
            List<LogEntry> logs = analyzer.readLogs(Path.of("app.log"));
            System.out.println("Total logs read: " + logs.size());
            
            Map<String, Long> counts = analyzer.countByLevel(logs);
            System.out.println("\nCounts by level:");
            counts.forEach((level, count) -> 
                System.out.println("  " + level + ": " + count));
            
            List<LogEntry> errors = analyzer.getErrors(logs);
            System.out.println("\nTotal errors: " + errors.size());
            System.out.println("Error entries:");
            errors.forEach(error -> System.out.println("  " + error));
            
            analyzer.writeSummary(Path.of("summary.txt"), logs);
            System.out.println("\nSummary written to summary.txt");
            
        } catch (Exception e) {
            System.err.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}