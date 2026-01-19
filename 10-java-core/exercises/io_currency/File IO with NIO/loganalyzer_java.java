import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    
    public List<LogEntry> readLogs(Path file) throws IOException {
        return Files.readAllLines(file).stream()
                .filter(line -> !line.trim().isEmpty())
                .map(LogEntry::parse)
                .collect(Collectors.toList());
    }
    
    public void writeSummary(Path output, List<LogEntry> logs) throws IOException {
        Map<String, Long> counts = countByLevel(logs);
        List<LogEntry> errors = getErrors(logs);
        
        StringBuilder summary = new StringBuilder();
        summary.append("Log Summary Report\n");
        summary.append("==================\n\n");
        summary.append("Total entries: ").append(logs.size()).append("\n\n");
        summary.append("Counts by level:\n");
        counts.forEach((level, count) -> 
            summary.append("  ").append(level).append(": ").append(count).append("\n"));
        summary.append("\nError entries (").append(errors.size()).append("):\n");
        errors.forEach(error -> 
            summary.append("  ").append(error.toString()).append("\n"));
        
        Files.writeString(output, summary.toString());
    }
    
    public Map<String, Long> countByLevel(List<LogEntry> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(LogEntry::level, Collectors.counting()));
    }
    
    public List<LogEntry> getErrors(List<LogEntry> logs) {
        return logs.stream()
                .filter(log -> "ERROR".equals(log.level()))
                .collect(Collectors.toList());
    }
}