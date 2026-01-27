import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CsvFileProcessor {

    public List<Employee> readEmployees(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new CsvFileNotFoundException("File not found: " + filePath);
        }

        List<String> lines = Files.readAllLines(filePath);
        List<Employee> employees = new ArrayList<>();

        if (lines.isEmpty()) {
            return employees;
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            try {
                Employee employee = parseLine(line);
                employees.add(employee);
            } catch (Exception e) {
                System.err.println("Error parsing line " + (i + 1) + ": " + line);
                System.err.println("Reason: " + e.getMessage());
            }
        }

        return employees;
    }

    private Employee parseLine(String line) throws Exception {
        String[] fields = line.split(",");

        if (fields.length < 6) {
            throw new Exception("Missing columns - expected 6, found " + fields.length);
        }

        try {
            int id = Integer.parseInt(fields[0].trim());
            String firstName = fields[1].trim();
            String lastName = fields[2].trim();
            String department = fields[3].trim();
            double salary = Double.parseDouble(fields[4].trim());
            LocalDate hireDate = LocalDate.parse(fields[5].trim());

            return new Employee(id, firstName, lastName, department, salary, hireDate);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid number format: " + e.getMessage());
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid date format: " + e.getMessage());
        }
    }

    public void writeEmployees(Path filePath, List<Employee> employees) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("id,firstName,lastName,department,salary,hireDate");

        for (Employee emp : employees) {
            lines.add(emp.toCsvRow());
        }

        Files.write(filePath, lines);
    }

    public void filterAndWrite(Path source, Path destination, Predicate<Employee> filter) throws IOException {
        List<Employee> employees = readEmployees(source);
        List<Employee> filtered = employees.stream()
                .filter(filter)
                .collect(Collectors.toList());
        writeEmployees(destination, filtered);
    }

    public void generateDepartmentReport(Path source, Path reportPath) throws IOException {
        List<Employee> employees = readEmployees(source);

        Map<String, List<Employee>> byDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));

        List<String> reportLines = new ArrayList<>();
        reportLines.add("Department,EmployeeCount,TotalSalary,AverageSalary");

        byDepartment.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String department = entry.getKey();
                    List<Employee> deptEmployees = entry.getValue();
                    long count = deptEmployees.size();
                    double totalSalary = deptEmployees.stream()
                            .mapToDouble(Employee::salary)
                            .sum();
                    double avgSalary = totalSalary / count;

                    reportLines.add(String.format("%s,%d,%.2f,%.2f",
                            department, count, totalSalary, avgSalary));
                });

        Files.write(reportPath, reportLines);
    }
}