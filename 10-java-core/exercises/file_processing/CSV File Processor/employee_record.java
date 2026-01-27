import java.time.LocalDate;

public record Employee(
    int id,
    String firstName,
    String lastName,
    String department,
    double salary,
    LocalDate hireDate
) {
    public String toCsvRow() {
        return String.format("%d,%s,%s,%s,%.2f,%s",
            id, firstName, lastName, department, salary, hireDate);
    }
}