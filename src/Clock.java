import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock implements Runnable {

    // Flag to control the clock execution
    private boolean stop;

    // Date object to store the current date and time
    private Date date;

    // Date and input variables for alarm and timer functionality
    private Date time;
    private String input;

    // Constructor for initializing a Clock without specific parameters
    public Clock() {
        stop = false; // Initialize the stop flag to false
    }

    // Constructor for initializing a Clock with time and input parameters for alarm functionality
    public Clock(Date time, String input) {
        date = new Date(); // Initialize the date object to the current date and time
        stop = false;
        this.time = time; // Set the provided time for the alarm
        this.input = input; // Set the provided input for the alarm
    }

    // Constructor for initializing a Clock with input parameter for timer functionality
    public Clock(String input) {
        date = new Date();
        stop = false;
        this.input = input; // Set the provided input for the timer
    }

    // Method to set an alarm
    void setAlarm(Date time, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Format for displaying time with seconds
        SimpleDateFormat hm = new SimpleDateFormat("HH:mm"); // Format for comparing hours and minutes
        Date endTime = new Date(time.getTime() + 60000); // Calculate the end time (60 seconds later)

        while (!stop) {
            System.out.println();
            date.setTime(System.currentTimeMillis()); // Update the current date and time
            if ((hm.format(date).equals(hm.format(time)))) {
                System.out.print("\r" + sdf.format(date) + " " + message); // Display the alarm message at the specified time
            } else if (hm.format(date).equals(hm.format(endTime))) {
                stop(); // Stop the clock when the end time is reached
            } else {
                System.out.print("\r" + sdf.format(date)); // Display the current time
            }
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to set a timer
    void setTimer(String input) {
        String[] bit = input.split(":"); // Split the input into minutes and seconds
        int mins = Integer.parseInt(bit[0]);
        int secs = Integer.parseInt(bit[1]);
        int totalSecs = mins * 60 + secs; // Convert minutes to seconds and add with seconds

        for (int i = totalSecs; i >= 0; i--) {
            int currentMins = i / 60; // Calculate current minutes
            int currentSecs = i % 60; // Calculate current seconds
            System.out.printf("%02d:%02d\n", currentMins, currentSecs); // Display the current time
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop(); // Stop the clock when the timer reaches zero
    }

    // Method to set a stopwatch
    void setStopwatch() {
        int mins = 0;
        int secs = 1;
        int hrs = 0;
        int totalSecs = hrs * 3600 + mins * 60 + secs; // Calculate total seconds
        int currentMins = mins;
        int currentSecs = secs;
        int currentHrs = hrs;

        while (!stop) {
            currentHrs = totalSecs / 3600; // Calculate current hours
            currentMins = (totalSecs / 60) % 60; // Calculate current minutes
            currentSecs = totalSecs % 60; // Calculate current seconds
            totalSecs++;

            System.out.printf("%02d:%02d\n", currentMins, currentSecs); // Display the current time
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("Elapsed time: ");
        System.out.printf("%02d:%02d:%02d\n", currentHrs, currentMins, currentSecs); // Display elapsed time
    }

    // Method to stop the clock
    public void stop() {
        stop = true; // Set the stop flag to true
    }

    // Override the run method from the Runnable interface
    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Format for displaying time with seconds
        while (!stop) {
            date.setTime(System.currentTimeMillis()); // Update the current date and time
            System.out.print("\r" + sdf.format(date)); // Display the current time
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
