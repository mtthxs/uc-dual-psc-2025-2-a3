package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class providing common helper methods for date formatting and safe string handling.
 */
public class Utils {

    /**
     * Formats a {@link Date} object into a string with pattern "dd/MM/yyyy".
     * Returns "-" if the date is null.
     *
     * @param date the date to format
     * @return formatted date string or "-" if date is null
     */
    public static String formatDate(Date date) {
        if (date == null) return "-";
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    /**
     * Returns a non-null string.
     * If the input is null, returns an empty string.
     *
     * @param value the string to check
     * @return original string if not null, otherwise an empty string
     */
    public static String safeString(String value) {
        return value != null ? value : "";
    }
}
