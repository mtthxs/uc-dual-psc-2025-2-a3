package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple logging utility for printing messages with timestamps and severity levels.
 * Provides methods for INFO, WARN, and ERROR level logging.
 */
public class Logger {

    /**
     * Enumeration of log levels.
     */
    public enum Level {
        INFO, WARN, ERROR
    }

    /**
     * Formatter for the timestamp in log messages.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs a message with the given level and current timestamp.
     *
     * @param level   the severity level of the log message
     * @param message the message to log
     */
    public static void log(Level level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] [" + level + "] " + message);
    }

    /**
     * Logs an INFO-level message.
     *
     * @param message the message to log
     */
    public static void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Logs a WARN-level message.
     *
     * @param message the message to log
     */
    public static void warn(String message) {
        log(Level.WARN, message);
    }

    /**
     * Logs an ERROR-level message.
     *
     * @param message the message to log
     */
    public static void error(String message) {
        log(Level.ERROR, message);
    }
}
