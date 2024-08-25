package logfiles;


import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderTime {

    public static void main(String[] args) {

    	String logFilePath = "/ecommers.log";

        String targetTime = "10:16:15";

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
                // Extract time portion from the log line
                String logTime = extractTimeFromLog(line);
                if (logTime != null && logTime.equals(targetTime)) {
                    matchingEntries.add(line);
                }
            }
        }

        return matchingEntries;
    }

    private static String extractTimeFromLog(String logEntry) {
        try {
            int startIndex = logEntry.indexOf(" ") + 1; 
            int endIndex = logEntry.indexOf(".", startIndex); 
            if (startIndex != -1 && endIndex != -1) {
                return logEntry.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
          
        	e.printStackTrace();
        }
        return null;
    }
}
