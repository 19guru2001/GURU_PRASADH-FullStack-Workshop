import java.nio.file.Path;
import java.util.List;

public class CsvProcessorTest {
    public static void main(String[] args) {
        CsvFileProcessor processor = new CsvFileProcessor();

        System.out.println("=== Test 1: Read Valid CSV ===");
        testReadValidCsv(processor);

        System.out.println("\n=== Test 2: Read CSV with Errors ===");
        testReadCsvWithErrors(processor);

        System.out.println("\n=== Test 3: Filter High Earners (salary > 70000) ===");
        testFilterHighEarners(processor);

        System.out.println("\n=== Test 4: Generate Department Report ===");
        testGenerateDepartmentReport(processor);

        System.out.println("\n=== Test 5: Write and Read Employees ===");
        testWriteAndRead(processor);

        System.out.println("\n=== Test 6: Handle File Not Found ===");
        testFileNotFound(processor);
    }

    private static void testReadValidCsv(CsvFileProcessor processor) {
        try {
            List<Employee> employees = processor.readEmployees(Path.of("employees.csv"));
            System.out.println("Successfully read " + employees.size() + " employees");
            assert employees.size() == 5 : "Expected 5 employees";

            System.out.println("\nEmployee List:");
            employees.forEach(emp -> System.out.println("  " + emp.firstName() + " " +
                    emp.lastName() + " - " + emp.department() + " - $" + emp.salary()));

            System.out.println("\n✓ Test Passed");
        } catch (Exception e) {
            System.err.println("✗ Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testReadCsvWithErrors(CsvFileProcessor processor) {
        try {
            List<Employee> employees = processor.readEmployees(Path.of("employees-with-errors.csv"));
            System.out.println("Successfully read " + employees.size() + " valid employees (errors logged above)");

            System.out.println("\nValid Employees:");
            employees.forEach(emp -> System.out.println("  " + emp.firstName() + " " +
                    emp.lastName() + " - " + emp.department()));

            System.out.println("\n✓ Test Passed - Invalid rows were skipped");
        } catch (Exception e) {
            System.err.println("✗ Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testFilterHighEarners(CsvFileProcessor processor) {
        try {
            processor.filterAndWrite(
                    Path.of("employees.csv"),
                    Path.of("high-earners.csv"),
                    emp -> emp.salary() > 70000
            );

            List<Employee> highEarners = processor.readEmployees(Path.of("high-earners.csv"));
            System.out.println("Filtered " + highEarners.size() + " high earners (salary > $70,000)");

            System.out.println("\nHigh Earners:");
            highEarners.forEach(emp -> System.out.println("  " + emp.firstName() + " " +
                    emp.lastName() + " - $" + emp.salary()));

            assert highEarners.stream().allMatch(emp -> emp.salary() > 70000) :
                    "All employees should have salary > 70000";

            System.out.println("\n✓ Test Passed");
        } catch (Exception e) {
            System.err.println("✗ Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testGenerateDepartmentReport(CsvFileProcessor processor) {
        try {
            processor.generateDepartmentReport(
                    Path.of("employees.csv"),
                    Path.of("department-report.csv")
            );

            System.out.println("Department report generated successfully");
            System.out.println("\nReport contents:");

            List<String> reportLines = java.nio.file.Files.readAllLines(Path.of("department-report.csv"));
            reportLines.forEach(line -> System.out.println("  " + line));

            assert reportLines.size() == 4 : "Expected header + 3 departments";
            assert reportLines.get(0).equals("Department,EmployeeCount,TotalSalary,AverageSalary") :
                    "Invalid header";

            System.out.println("\n✓ Test Passed");
        } catch (Exception e) {
            System.err.println("✗ Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testWriteAndRead(CsvFileProcessor processor) {
        try {
            List<Employee> original = processor.readEmployees(Path.of("employees.csv"));
            processor.writeEmployees(Path.of("employees-copy.csv"), original);

            List<Employee> copy = processor.readEmployees(Path.of("employees-copy.csv"));

            System.out.println("Original size: " + original.size());
            System.out.println("Copy size: " + copy.size());

            assert original.size() == copy.size() : "Size mismatch";

            for (int i = 0; i < original.size(); i++) {
                Employee orig = original.get(i);
                Employee cp = copy.get(i);
                assert orig.id() == cp.id() : "ID mismatch at index " + i;
                assert orig.firstName().equals(cp.firstName()) : "First name mismatch";
                assert orig.salary() == cp.salary() : "Salary mismatch";
            }

            System.out.println("✓ Test Passed - Write and read successful");
        } catch (Exception e) {
            System.err.println("✗ Test Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testFileNotFound(CsvFileProcessor processor) {
        try {
            processor.readEmployees(Path.of("non-existent-file.csv"));
            System.err.println("✗ Test Failed - Should have thrown exception");
        } catch (CsvFileNotFoundException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
            System.out.println("✓ Test Passed");
        } catch (Exception e) {
            System.err.println("✗ Test Failed - Wrong exception type: " + e.getMessage());
        }
    }
}