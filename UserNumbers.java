import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserNumbers {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Map<String, String> users = new HashMap<>();

        // Load existing data from file if it exists
        File file = new File("userNumbers.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    users.put(parts[0], parts[1]);
                }
            }
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("------------------------------------------");
            System.out.println("Enter 1 to assign names to numbers");
            System.out.println("Enter 2 to search for a name by numbers");
            System.out.println("Enter 3 to check in");
            System.out.println("Enter 4 to search for numbers by name");
            System.out.println("Enter 5 to exit");
            System.out.println("------------------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter user name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter string of numbers:");
                    String numbers = scanner.nextLine();
                    users.put(numbers, name);
                    saveToFile(users, file);
                    break;
                case 2:
                    System.out.println("Enter string of numbers to search:");
                    String searchNumbers = scanner.nextLine();
                    if (users.containsKey(searchNumbers)) {
                        String searchName = users.get(searchNumbers);
                        System.out.println("User name: " + searchName);
                        saveToFile(searchNumbers + "," + searchName, new File("searchResults.txt"));
                    } else {
                        System.out.println("No user found for the given numbers");
                    }
                    break;
                case 3:
                System.out.println("------------------------------------------");
                    System.out.println("Enter string of numbers to check in:");
                    System.out.println("------------------------------------------");
                    String checkinNumbers = scanner.nextLine();
                    while (!checkinNumbers.equalsIgnoreCase("exit")) {
                        if (users.containsKey(checkinNumbers)) {
                            String checkinName = users.get(checkinNumbers);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timestamp = sdf.format(new Date());
                            String checkinLine = checkinNumbers + "," + checkinName + "," + timestamp;
                            saveToFile(checkinLine, new File("checkin.txt"));
                            System.out.println(checkinName + " checked in successfully");
                        } else {
                            System.out.println("No user found for the given numbers");
                        }
                        System.out.println("------------------------------------------");
                        System.out.println(
                                "Enter another string of numbers to check in, or enter 'exit' to return to the main menu:");
                        System.out.println("------------------------------------------");
                        checkinNumbers = scanner.nextLine();
                    }
                    break;

                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, try again");
                    break;
                    
                    case 4:
    System.out.println("Enter user name to search:");
    String searchName = scanner.nextLine();
    boolean found = false;
    for (Map.Entry<String, String> entry : users.entrySet()) {
        if (entry.getValue().equals(searchName)) {
            System.out.println("User name: " + searchName + ", String of numbers: " + entry.getKey());
            found = true;
        }
    }
    if (!found) {
        System.out.println("No user found with the given name");
    }
    break;

                    
                    
            }
            
        }

        scanner.close();

    }

    private static void saveToFile(Map<String, String> users, File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String numbers : users.keySet()) {
                String name = users.get(numbers);
                bw.write(numbers + "," + name);
                bw.newLine();
            }
        }
    }

    private static void saveToFile(String line, File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(line);
            bw.newLine();
        }
    }

}
