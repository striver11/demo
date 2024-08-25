package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderProductId {

    public static void main(String[] args) {
    	
    	   String logFilePath = "/ecommers.log";

    
        String targetProductId = "54321";

        try {
            List<String> logEntries = readLogEntriesForUserId(logFilePath, targetProductId);
            if (logEntries.isEmpty()) {
                System.out.println("No log entries found for Product ID: " + targetProductId);
            } else {
                System.out.println("Log entries for Product ID: " + targetProductId);
                logEntries.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }

    public static List<String> readLogEntriesForUserId(String logFilePath, String targetProductId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String logProductId = extractProductIdFromLog(line);

                if (logProductId != null && logProductId.equals(targetProductId)) {
                    matchingEntries.add(line);
                }
            }
        }

        return matchingEntries;
    }


    private static String extractProductIdFromLog(String logEntry) {
        try {
            int startIndex = logEntry.indexOf("Product [ID: ") + 13; 
            int endIndex = logEntry.indexOf("]", startIndex); 
            if (startIndex != -1 && endIndex != -1) {
                return logEntry.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.err.println("Error extracting user ID from log entry: " + e.getMessage()); 
        }
        return null;
    }
}
