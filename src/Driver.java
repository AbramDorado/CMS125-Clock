import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {

    // Constants for menu options
    private static final int ALARM_OPTION = 1;
    private static final int TIMER_OPTION = 2;
    private static final int STOPWATCH_OPTION = 3;
    private static final int EXIT_OPTION = 4;

    // Use try-with-resources to automatically close the Scanner
    public static void startMenu() {
        try (Scanner userInput = new Scanner(System.in)) {
            int option;

            do {
                printMenu(); // Call the method to print the menu
                System.out.print("Choice: ");
                option = userInput.nextInt();
                userInput.nextLine(); // Consume the newline character

                switch (option) {
                    case ALARM_OPTION -> setAlarm(userInput); // Call setAlarm method for option 1
                    case TIMER_OPTION -> setTimer(userInput); // Call setTimer method for option 2
                    case STOPWATCH_OPTION -> setStopwatch(userInput); // Call setStopwatch method for option 3
                    case EXIT_OPTION -> {
                        System.out.println("Exiting clock app");
                        System.exit(0);
                    }
                    default -> System.out.println("\nInvalid input, Please try again!");
                }
            } while (true);
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input, Please try again!");
            startMenu();
        }
    }

    // Method to print the menu options
    private static void printMenu() {
        System.out.println("\nMenu:");
        String menuOptions = """
                Alarm
                Timer
                Stopwatch
                Exit Program
                """;
        String[] options = menuOptions.split("\n");
        for (int i = 0; i < options.length; i++) {
            System.out.println("(" + (i + 1) + ")" + " " + options[i].trim());
        }
    }

    // Method to set the alarm
    static void setAlarm(Scanner scanner) {
        System.out.println("\nALARM");
        System.out.print("Alarm Time (HH:mm): ");
        String time = scanner.nextLine();

        // Use a dedicated method for creating and starting the alarm thread
        createAndStartThread(() -> {
            try {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.setLenient(false);
                Date date = dateFormat.parse(time);

                System.out.print("Message: ");
                String message = scanner.nextLine();
                Clock clock1 = new Clock(date, message);
                clock1.setAlarm(date, message);
            } catch (Exception e) {
                System.out.println("\nInvalid input");
            }
        });
    }

    // Method to set the timer
    static void setTimer(Scanner scanner) {
        System.out.println("\nTIMER");
        System.out.print("Timer (mm:ss): ");
        String timer = scanner.nextLine();

        // Use a dedicated method for creating and starting the timer thread
        createAndStartThread(() -> {
            try {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                dateFormat.setLenient(false);
                dateFormat.parse(timer);

                Clock clock2 = new Clock(timer);
                clock2.setTimer(timer);
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        });
    }

    // Method to set the stopwatch
    static void setStopwatch(Scanner scanner) {
        System.out.println("\nSTOPWATCH");
        System.out.println();

        // Use a dedicated method for creating and starting the stopwatch thread
        createAndStartThread(() -> {
            try {
                Clock clock3 = new Clock();
                Thread stopwatchThread = new Thread(clock3::setStopwatch);
                stopwatchThread.start();
                scanner.nextLine(); // Wait for user input
                clock3.stop();
                Thread.sleep(3000); // Wait for 3 seconds
                stopwatchThread.join();
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        });
    }

    // Extracted method for creating and starting a thread
    private static void createAndStartThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("\nWELCOME TO CLOCK APP");
        startMenu();
    }
}
