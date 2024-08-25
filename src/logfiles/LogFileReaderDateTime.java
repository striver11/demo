package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderDateTime {

    public static void main(String[] args) {

        String logFilePath = "/ecommers.log";
        String targetTime = "2023-08-25 10:16:15";
        try {
            List<String> logEntries = readLogEntriesForSpecificTime(logFilePath, targetTime);
            System.out.println("Log entries for time: " + targetTime);
            logEntries.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }

    public static List<String> readLogEntriesForSpecificTime(String logFilePath, String targetTime) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
              
                if (line.startsWith(targetTime)) {
                    matchingEntries.add(line);
                }
            }
        }

        return matchingEntries;
    }
}
