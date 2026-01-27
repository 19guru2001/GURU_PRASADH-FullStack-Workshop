package com.example.taskmanager.ui;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface.
 *
 * NEW IN STAGE 3:
 * - @Component marks this as a Spring-managed bean
 * - @Autowired injects TaskService automatically
 */
@Component
public class ConsoleUI {

    private final TaskService taskService;
    private final Scanner scanner;

    @Autowired
    public ConsoleUI(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
        System.out.println("ConsoleUI initialized by Spring");
    }

    public void run() {
        System.out.println("\n=== Task Manager ===");
        System.out.println("Spring DI is managing all dependencies!\n");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> createTask();
                    case "2" -> listAllTasks();
                    case "3" -> viewTaskById();
                    case "4" -> updateTaskStatus();
                    case "5" -> updateTask();
                    case "6" -> deleteTask();
                    case "7" -> listTasksByStatus();
                    case "8" -> startTask();
                    case "9" -> completeTask();
                    case "0" -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Create Task");
        System.out.println("2. List All Tasks");
        System.out.println("3. View Task by ID");
        System.out.println("4. Update Task Status");
        System.out.println("5. Update Task Details");
        System.out.println("6. Delete Task");
        System.out.println("7. List Tasks by Status");
        System.out.println("8. Start Task");
        System.out.println("9. Complete Task");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void createTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        Task task = taskService.createTask(title, description);
        System.out.println("Task created: " + task);
    }

    private void listAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("\n=== All Tasks ===");
            tasks.forEach(System.out::println);
        }
    }

    private void viewTaskById() {
        System.out.print("Enter task ID: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        taskService.getTaskById(id)
                .ifPresentOrElse(
                        task -> System.out.println("Task found: " + task),
                        () -> System.out.println("Task not found with ID: " + id)
                );
    }

    private void updateTaskStatus() {
        System.out.print("Enter task ID: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.println("Available statuses:");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println("  - " + status);
        }
        System.out.print("Enter new status: ");
        String statusStr = scanner.nextLine().trim().toUpperCase();

        TaskStatus newStatus = TaskStatus.valueOf(statusStr);
        Task updatedTask = taskService.updateTaskStatus(id, newStatus);
        System.out.println("Task updated: " + updatedTask);
    }

    private void updateTask() {
        System.out.print("Enter task ID: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Enter new title (or press Enter to skip): ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter new description (or press Enter to skip): ");
        String description = scanner.nextLine().trim();

        Task updatedTask = taskService.updateTask(id,
                title.isEmpty() ? null : title,
                description.isEmpty() ? null : description);
        System.out.println("Task updated: " + updatedTask);
    }

    private void deleteTask() {
        System.out.print("Enter task ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        taskService.deleteTask(id);
        System.out.println("Task deleted successfully.");
    }

    private void listTasksByStatus() {
        System.out.println("Available statuses:");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println("  - " + status);
        }
        System.out.print("Enter status: ");
        String statusStr = scanner.nextLine().trim().toUpperCase();

        TaskStatus status = TaskStatus.valueOf(statusStr);
        List<Task> tasks = taskService.getTasksByStatus(status);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found with status: " + status);
        } else {
            System.out.println("\n=== Tasks with status " + status + " ===");
            tasks.forEach(System.out::println);
        }
    }

    private void startTask() {
        System.out.print("Enter task ID to start: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        Task task = taskService.startTask(id);
        System.out.println("Task started: " + task);
    }

    private void completeTask() {
        System.out.print("Enter task ID to complete: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        Task task = taskService.completeTask(id);
        System.out.println("Task completed: " + task);
    }
}
