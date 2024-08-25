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
