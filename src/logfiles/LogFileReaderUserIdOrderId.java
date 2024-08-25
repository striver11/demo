package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderUserIdOrderId {

    public static void main(String[] args) {

    	String logFilePath = "/ecommers.log";

        String targetUserId = "45678";
        String targetOrderId="12345";

        try {
            List<String> logEntries = readLogEntriesForUserId(logFilePath, targetUserId,targetOrderId);
            if (logEntries.isEmpty()) {
                System.out.println("No log entries found for User ID: " + targetUserId+" Order id: "+targetOrderId);
            } else {
                System.out.println("Log entries for User ID: " + targetUserId+" Order id: "+targetOrderId);
                logEntries.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }

    public static List<String> readLogEntriesForUserId(String logFilePath, String targetUserId,String targetOrderId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String logUserId = extractUserIdFromLog(line);
                String logOrderId=extractOrderIdFromLog(line);
//                System.out.println("Processing line: " + line);
//                System.out.println("Extracted User ID: " + logUserId); 
                if ((logUserId != null && logUserId.equals(targetUserId))&&(logOrderId != null && logOrderId.equals(targetOrderId))) {
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
    
    private static String extractOrderIdFromLog(String logEntry) {
        try {
            
        	  int startIndex = logEntry.indexOf("Order [ID: ") + 11; 
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
