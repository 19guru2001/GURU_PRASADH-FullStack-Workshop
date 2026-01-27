import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import static java.util.stream.Collectors.*;

public class StreamExercise {

    record Employee(
        Long id,
        String name,
        String department,
        double salary,
        int yearsOfService,
        List<String> skills
    ) {}

    public static void main(String[] args) {

        List<Employee> employees = List.of(
            new Employee(1L, "Alice", "Engineering", 85000, 5, List.of("Java", "Python")),
            new Employee(2L, "Bob", "Engineering", 75000, 3, List.of("Java", "JavaScript")),
            new Employee(3L, "Charlie", "Sales", 65000, 7, List.of("Communication", "CRM")),
            new Employee(4L, "Diana", "Engineering", 95000, 8, List.of("Java", "Kotlin", "Go")),
            new Employee(5L, "Eve", "HR", 55000, 2, List.of("Recruiting", "Communication")),
            new Employee(6L, "Frank", "Sales", 70000, 4, List.of("Negotiation", "CRM"))
        );

        System.out.println("1. Engineering sorted by salary: " + filterAndSort(employees));
        System.out.println("2. All names uppercase: " + namesUppercase(employees));
        System.out.println("3. Grouped by department: " + groupByDepartment(employees));
        System.out.println("4. Salary statistics: " + salaryStatistics(employees));
        System.out.println("5. Unique skills: " + uniqueSkills(employees));
        System.out.println("6. Partition by salary > 70000: " + partitionBySalary(employees));
        System.out.println("7. Total years of service: " + totalYearsOfService(employees));
        System.out.println("8. Departments with avg salary > 70000: " + departmentsAvgSalaryAbove(employees, 70000));
    }

    static List<String> filterAndSort(List<Employee> list) {
        return list.stream()
            .filter(e -> e.department().equals("Engineering"))
            .sorted(Comparator.comparingDouble(Employee::salary).reversed())
            .map(Employee::name)
            .toList();
    }

    static List<String> namesUppercase(List<Employee> list) {
        return list.stream()
            .map(e -> e.name().toUpperCase())
            .toList();
    }

    static Map<String, List<String>> groupByDepartment(List<Employee> list) {
        return list.stream()
            .collect(groupingBy(Employee::department, mapping(Employee::name, toList())));
    }

    static String salaryStatistics(List<Employee> list) {
        double total = list.stream().mapToDouble(Employee::salary).sum();
        Map<String, Double> avgByDept = list.stream()
            .collect(groupingBy(Employee::department, averagingDouble(Employee::salary)));
        return "Total: $" + (int)total + ", Avg by dept: " + avgByDept;
    }

    static List<String> uniqueSkills(List<Employee> list) {
        return list.stream()
            .flatMap(e -> e.skills().stream())
            .distinct()
            .toList();
    }

    static Map<Boolean, List<String>> partitionBySalary(List<Employee> list) {
        return list.stream()
            .collect(partitioningBy(e -> e.salary() > 70000, mapping(Employee::name, toList())));
    }

    static int totalYearsOfService(List<Employee> list) {
        return list.stream()
            .map(Employee::yearsOfService)
            .reduce(0, Integer::sum);
    }

    static List<String> departmentsAvgSalaryAbove(List<Employee> list, double threshold) {
        return list.stream()
            .collect(groupingBy(Employee::department, averagingDouble(Employee::salary)))
            .entrySet().stream()
            .filter(e -> e.getValue() > threshold)
            .map(Map.Entry::getKey)
            .toList();
    }
}
