package utilities;

import java.util.ArrayList;
import java.util.List;

public class PerformanceData {

    // Holds all performance logs for the test run
    public static List<String> elementTimings = new ArrayList<>();

    // Method to add entry
    public static void add(String message) {
        elementTimings.add(message);
    }

    // Optional: print everything at end of execution
    public static void printAll() {
        elementTimings.forEach(System.out::println);
    }
}