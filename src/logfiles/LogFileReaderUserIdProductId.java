package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderUserIdProductId {

    public static void main(String[] args) {
    	
    	String logFilePath = "/ecommers.log";

        String targetUserId = "13579";
        String targetProductId="54321";

        try {
            List<String> logEntries = readLogEntriesForUserIdProductId(logFilePath, targetUserId,targetProductId);
            if (logEntries.isEmpty()) {
                System.out.println("No log entries found for User ID: " + targetUserId+" Product Id: "+targetProductId);
            } else {
                System.out.println("Log entries for User ID: " + targetUserId +" Product Id :"+targetProductId);
                logEntries.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }

    public static List<String> readLogEntriesForUserIdProductId(String logFilePath, String targetUserId,String targetProductId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String logUserId = extractUserIdFromLog(line);
                String logProductId=extractProductIdFromLog(line);
//                System.out.println("Processing line: " + line); 
//                System.out.println("Extracted User ID: " + logUserId); 
                if ((logUserId != null && logUserId.equals(targetUserId))&&(logProductId != null && logProductId.equals(targetProductId))) {
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
    
    private static String extractProductIdFromLog(String logEntry) {
        try {
          
        	 int startIndex = logEntry.indexOf("Product [ID: ") + 13; 
            int endIndex = logEntry.indexOf("]", startIndex);
            if (startIndex != -1 && endIndex != -1) {
                return logEntry.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.err.println("Error extracting product ID from log entry: " + e.getMessage()); 
        }
        return null;
    }
}


// output:
// Log entries for User ID: 13579 Product Id :54321
// 2023-08-25 10:05:15.345 - INFO - Product [ID: 54321] viewed by User [ID: 13579]
// 2023-08-25 10:05:15.456 - INFO - User [ID: 13579] added Product [ID: 54321] to cart
// 2023-08-25 10:05:15.567 - INFO - User [ID: 13579] removed Product [ID: 54321] from cart
// 2023-08-25 10:12:50.345 - INFO - Product [ID: 54321] viewed by User [ID: 13579]
// 2023-08-25 10:12:50.456 - INFO - User [ID: 13579] added Product [ID: 54321] to cart
// 2023-08-25 10:12:50.901 - INFO - User [ID: 13579] removed Product [ID: 54321] from cart
