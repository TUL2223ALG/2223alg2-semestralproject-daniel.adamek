package cz.tul.alg2.semestral.errorhandle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ErrorLogger {

    private final String logFileName;

    public ErrorLogger(String logFileName) {
        this.logFileName = logFileName;
    }

    public void logError(String message, Throwable throwable) {
        try (FileWriter fileWriter = new FileWriter(logFileName, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            LocalDateTime now = LocalDateTime.now();
            printWriter.printf("[%s] ERROR: %s%n", now.toString(), message);
            if (throwable != null) {
                throwable.printStackTrace(printWriter);
            }
        } catch (IOException e) {
            System.err.println("Failed to write error log: " + e.getMessage());
        }
    }
}