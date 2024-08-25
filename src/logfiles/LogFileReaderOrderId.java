package logfiles;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LogFileReaderOrderId {

    public static void main(String[] args) {
    	   String logFilePath = "/ecommers.log";
        String targetOrderId = "12345";
        try {
            List<String> logEntries = readLogEntriesForOrderId(logFilePath, targetOrderId);
            if (logEntries.isEmpty()) {
                System.out.println("No log entries found for Order ID: " + targetOrderId);
            } else {
                System.out.println("Log entries for Order ID: " + targetOrderId);
                logEntries.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.err.println("Error reading the log file: " + e.getMessage());
        }
    }


    public static List<String> readLogEntriesForOrderId(String logFilePath, String targetOrderId) throws IOException {
        List<String> matchingEntries = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
         
                String logOrderId = extractOrderIdFromLog(line);
//                System.out.println("Processing line: " + line); // Debug output
//                System.out.println("Extracted Order ID: " + logOrderId); // Debug output
                if (logOrderId != null && logOrderId.equals(targetOrderId)) {
                    matchingEntries.add(line);
                }
            }
        }

        return matchingEntries;
    }

    private static String extractOrderIdFromLog(String logEntry) {
        try {
         
            int startIndex = logEntry.indexOf("Order [ID: ") + 11; // Skip "ID: "
//            System.out.println("start Index"+startIndex);
            int endIndex = logEntry.indexOf("]", startIndex); // Find the end of the user ID
//            System.out.println("end index"+endIndex);
            if (startIndex != -1 && endIndex != -1) {
//            	System.out.println("++++++++++++++++++++++++"+logEntry.substring(startIndex, endIndex));
                return logEntry.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.err.println("Error extracting user ID from log entry: " + e.getMessage()); 
        }
        return null;
    }
}
