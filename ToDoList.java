import java.io.*;
import java.util.*;

public class ToDoList {
    // Task class to represent a task
    static class Task {
        String title;
        boolean isCompleted;

        Task(String title) {
            this.title = title;
            this.isCompleted = false;
        }

        void markAsCompleted() {
            isCompleted = true;
        }
    }

    // Main class methods
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "tasks.dat";

    public static void main(String[] args) {
        loadTasksFromFile();
        while (true) {
            System.out.println("\nTo-Do List Application");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskAsCompleted();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    saveTasksToFile();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // Add task method
    static void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        Task task = new Task(title);
        tasks.add(task);
        System.out.println("Task added.");
    }

    // View tasks method
    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks added yet.");
        } else {
            System.out.println("\nTasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". " + task.title + " - " + (task.isCompleted ? "Completed" : "Pending"));
            }
        }
    }

    // Mark task as completed method
    static void markTaskAsCompleted() {
        viewTasks();
        System.out.print("Enter task number to mark as completed: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.get(taskNumber - 1).markAsCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    // Delete task method
    static void deleteTask() {
        viewTasks();
        System.out.print("Enter task number to delete: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.remove(taskNumber - 1);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    // Load tasks from a file
    static void loadTasksFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tasks = (ArrayList<Task>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found, starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save tasks to a file
    static void saveTasksToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
