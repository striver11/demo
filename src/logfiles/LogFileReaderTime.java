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


// output:
// Log entries for time: 10:16:15
// 2023-08-25 10:16:15.123 - INFO - User [ID: 90123] logged in from IP: 192.168.1.19
// 2023-08-25 10:16:15.234 - INFO - User [ID: 90123] searched for "Smart Home Devices"
// 2023-08-25 10:16:15.345 - INFO - Product [ID: 34567] viewed by User [ID: 90123]
// 2023-08-25 10:16:15.456 - INFO - User [ID: 90123] added Product [ID: 34567] to cart
// 2023-08-25 10:16:15.567 - INFO - User [ID: 10112] logged in from IP: 192.168.1.20
// 2023-08-25 10:16:15.678 - INFO - User [ID: 10112] searched for "Kitchen Appliances"
// 2023-08-25 10:16:15.789 - INFO - Product [ID: 45678] viewed by User [ID: 10112]
// 2023-08-25 10:16:15.890 - INFO - User [ID: 10112] added Product [ID: 45678] to cart
// 2023-08-25 10:16:15.901 - INFO - User [ID: 90123] removed Product [ID: 34567] from cart
// 2023-08-25 10:16:15.912 - INFO - User [ID: 10112] started checkout process
// 2023-08-25 10:16:15.923 - ERROR - Payment failed for User [ID: 10112], Order [ID: 34569] due to insufficient funds
// 2023-08-25 10:16:15.934 - INFO - User [ID: 10112] attempted a retry for Order [ID: 34569]
// 2023-08-25 10:16:15.945 - INFO - Payment successful for User [ID: 10112], Order [ID: 34569]
// 2023-08-25 10:16:15.956 - INFO - User [ID: 10112] logged out
