    package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderTimeUserId {

    public static void main(String[] args) {

    	String logFilePath = "/ecommers.log";

        String targetTime = "10:05:15";
        String targetUserId="13579";

        try {
            List<String> logEntries = readLogEntriesForSpecificTime(logFilePath, targetTime,targetUserId);
            System.out.println("Log entries for time: " + targetTime+" User Id: "+targetUserId);
            logEntries.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }

    public static List<String> readLogEntriesForSpecificTime(String logFilePath, String targetTime,String targetUserId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Extract time portion from the log line
                String logTime = extractTimeFromLog(line);
                String logUserId=extractUserIdFromLog(line);
                if ((logTime != null && logTime.equals(targetTime))&&(logUserId != null && logUserId.equals(targetUserId))) {
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


// output:
// Log entries for time: 10:05:15 User Id: 13579
// 2023-08-25 10:05:15.123 - INFO - User [ID: 13579] logged in from IP: 192.168.1.4
// 2023-08-25 10:05:15.234 - INFO - User [ID: 13579] searched for "Smart Watches"
// 2023-08-25 10:05:15.345 - INFO - Product [ID: 54321] viewed by User [ID: 13579]
// 2023-08-25 10:05:15.456 - INFO - User [ID: 13579] added Product [ID: 54321] to cart
// 2023-08-25 10:05:15.567 - INFO - User [ID: 13579] removed Product [ID: 54321] from cart
// 2023-08-25 10:05:15.678 - INFO - User [ID: 13579] searched for "Smart Watches"
// 2023-08-25 10:05:15.789 - INFO - Product [ID: 67890] viewed by User [ID: 13579]
// 2023-08-25 10:05:15.890 - INFO - User [ID: 13579] added Product [ID: 67890] to cart
// 2023-08-25 10:05:15.901 - INFO - User [ID: 13579] started checkout process
// 2023-08-25 10:05:15.912 - INFO - Payment initiated by User [ID: 13579] using PayPal
// 2023-08-25 10:05:15.923 - ERROR - Payment failed for User [ID: 13579], Order [ID: 55555] due to payment gateway timeout
// 2023-08-25 10:05:15.934 - INFO - User [ID: 13579] attempted a retry for Order [ID: 55555]
// 2023-08-25 10:05:15.945 - INFO - Payment successful for User [ID: 13579], Order [ID: 55555]
// 2023-08-25 10:05:15.956 - INFO - User [ID: 13579] logged out
