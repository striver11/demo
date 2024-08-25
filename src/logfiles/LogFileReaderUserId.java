package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderUserId {

    public static void main(String[] args) {
        
    	String logFilePath = "/ecommers.log";

        String targetUserId = "13579";

        try {
            List<String> logEntries = readLogEntriesForUserId(logFilePath, targetUserId);
            if (logEntries.isEmpty()) {
                System.out.println("No log entries found for User ID: " + targetUserId);
            } else {
                System.out.println("Log entries for User ID: " + targetUserId);
                logEntries.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }


    public static List<String> readLogEntriesForUserId(String logFilePath, String targetUserId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String logUserId = extractUserIdFromLog(line);
//                System.out.println("Processing line: " + line); 
//                System.out.println("Extracted User ID: " + logUserId); 
                if (logUserId != null && logUserId.equals(targetUserId)) {
                    matchingEntries.add(line);
                }
            }
        }

        return matchingEntries;
    }


    private static String extractUserIdFromLog(String logEntry) {
        try {
           
            int startIndex = logEntry.indexOf("User [ID: ") + 10; 
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
