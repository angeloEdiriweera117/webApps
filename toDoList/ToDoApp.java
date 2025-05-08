import java.io.*;
import java.util.*;

class Task {
    String title;
    String description;
    String dueDate;
    boolean isCompleted;

    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String toString() {
        return String.format("[%s] %s (Due: %s)\nDescription: %s\n",
                isCompleted ? "✓" : " ", title, dueDate, description);
    }

    public String toFileString() {
        return title + "|" + description + "|" + dueDate + "|" + isCompleted;
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split("\\|");
        Task t = new Task(parts[0], parts[1], parts[2]);
        t.isCompleted = Boolean.parseBoolean(parts[3]);
        return t;
    }
}

public class ToDoApp {
    private static final String FILE_NAME = "tasks.txt";
    private static List<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasksFromFile();
        int choice;
        do {
            printMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    markTaskComplete();
                    break;
                case 5:
                    saveTasksToFile();
                    break;
                case 0:
                    saveTasksToFile();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n--- TO-DO LIST ---");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Delete Task");
        System.out.println("4. Mark Task as Complete");
        System.out.println("5. Save Tasks");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Due Date: ");
        String date = scanner.nextLine();
        tasks.add(new Task(title, desc, date));
        System.out.println("Task added.");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\nTask #" + (i + 1));
            System.out.println(tasks.get(i));
        }
    }

    private static void deleteTask() {
        viewTasks();
        System.out.print("Enter task number to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void markTaskComplete() {
        viewTasks();
        System.out.print("Enter task number to mark complete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).isCompleted = true;
            System.out.println("Task marked as complete.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
            System.out.println("Tasks saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
