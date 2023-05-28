import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Display the menu options
    private static void displayMenu() {
        System.out.println("File Manager Menu");
        System.out.println("1. Display contents of a directory");
        System.out.println("2. Copy a file");
        System.out.println("3. Move a file");
        System.out.println("4. Delete a file");
        System.out.println("5. Create a directory");
        System.out.println("6. Delete a directory");
        System.out.println("7. Search for files");
        System.out.println("8. Exit");
        System.out.println();
    }

    // Display the contents of a directory
    private static void displayDirectoryContents(String path) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path filePath : directoryStream) {
                BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                String fileName = filePath.getFileName().toString();
                long fileSize = attributes.size();
                String lastModified = dateFormat.format(new Date(attributes.lastModifiedTime().toMillis()));
                System.out.println("File Name: " + fileName);
                System.out.println("File Size: " + fileSize + " bytes");
                System.out.println("Last Modified: " + lastModified);
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error displaying the directory contents: " + e.getMessage());
        }
    }

    // Copy a file
    private static void copyFile(String sourcePath, String destinationPath) {
        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("Error coping the file: " + e.getMessage());
        }
    }

    // Move a file
    private static void moveFile(String sourcePath, String destinationPath) {
        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved successfully.");
        } catch (IOException e) {
            System.out.println("Error moving the file: " + e.getMessage());
        }
    }

    // Delete a file
    private static void deleteFile(String filePath) {
        try {
            Files.delete(Paths.get(filePath));
            System.out.println("File deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting the files: " + e.getMessage());
        }
    }

    // Create a directory
    private static void createDirectory(String directoryPath) {
        try {
            Files.createDirectory(Paths.get(directoryPath));
            System.out.println("Directory created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating the directory: " + e.getMessage());
        }
    }

    // Delete a directory
    private static void deleteDirectory(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);
            Files.delete(path);
            System.out.println("The directory has been deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting the directory: " + e.getMessage());
        }
    }


    // Search for files
    private static void searchFiles(String directoryPath, String searchQuery) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path filePath : directoryStream) {
                String fileName = filePath.getFileName().toString();
                if (fileName.contains(searchQuery)) {
                    System.out.println("Matching File: " + fileName);
                    System.out.println("File Path: " + filePath.toString());
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Error searching the files: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            while (true) {
                displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter the directory path to display contents: ");
                        String directoryPath = scanner.nextLine();
                        displayDirectoryContents(directoryPath);
                        break;
                    case 2:
                        System.out.print("Enter the source file path: ");
                        String sourcePath = scanner.nextLine();
                        System.out.print("Enter the destination file path: ");
                        String destinationPath = scanner.nextLine();
                        copyFile(sourcePath, destinationPath);
                        break;
                    case 3:
                        System.out.print("Enter the source file path: ");
                        sourcePath = scanner.nextLine();
                        System.out.print("Enter the destination file path: ");
                        destinationPath = scanner.nextLine();
                        moveFile(sourcePath, destinationPath);
                        break;
                    case 4:
                        System.out.print("Enter the file path to delete: ");
                        String filePath = scanner.nextLine();
                        deleteFile(filePath);
                        break;
                    case 5:
                        System.out.print("Enter the directory path to create: ");
                        String newDirectoryPath = scanner.nextLine();
                        createDirectory(newDirectoryPath);
                        break;
                    case 6:
                        System.out.print("Enter the directory path to delete: ");
                        String deleteDirectoryPath = scanner.nextLine();
                        deleteDirectory(deleteDirectoryPath);
                        break;
                    case 7:
                        System.out.print("Enter the directory path to search: ");
                        String searchDirectoryPath = scanner.nextLine();
                        System.out.print("Enter the search query: ");
                        String searchQuery = scanner.nextLine();
                        searchFiles(searchDirectoryPath, searchQuery);
                        break;
                    case 8:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } finally {
            scanner.close();
        }
    }
}
