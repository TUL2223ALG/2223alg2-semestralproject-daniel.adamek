package cz.tul.alg2.semestral.errorhandle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class ErrorLogger {

    private final String logFileName;

    /**
     * The ErrorLogger function takes a string as an argument and writes it to the log file.
     *
     * @param logFileName Specify the name of the log file
     */
    public ErrorLogger(String logFileName) {
        this.logFileName = logFileName;
    }

    /**
     * The logError function takes a message and an optional Throwable object,
     * and writes the message to the log file with a timestamp. If there is a
     * Throwable object, it also prints out its stack trace.

     *
     * @param message Print the error message
     * @param throwable Print the stack trace of an exception
     */
    public void logError(String message, Throwable throwable) {
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("[%s] ERROR: %s%n", now, message);

        try (FileWriter fileWriter = new FileWriter(logFileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("[%s] ERROR: %s%n", now, message);
            if (throwable != null) {
                throwable.printStackTrace(printWriter);
            }
        } catch (IOException e) {
            System.err.println("Nebylo možné zapsat chybu do logu: " + e.getMessage());
        }
    }
}