package lt.solutioni.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.CoreConfiguration;

/**
 * Common {@link Date} manipulation methods collection.
 * 
 * @author buzzard
 *
 */
public class DateUtils {

    private static DateFormat format;
    static {
        format = new SimpleDateFormat(CoreConfiguration.DATE_FORMAT);
        format.setLenient(false);
    }

    /**
     * Parse date from string object in {@link CoreConfiguration#DATE_FORMAT}
     * format.
     */
    public static Date getDate(String date) {
        if (date != null) {
            try {
                return format.parse(date);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    /**
     * Format {@link Date} object to {@link CoreConfiguration#DATE_FORMAT}
     * format.
     */
    public static String formatDate(Date date) {
        if (date != null) {
            return format.format(date);
        }
        return null;
    }

    /**
     * Reset hours, minutes, seconds and milliseconds in {@link Calendar}
     * instance.
     */
    public static void resetTime(Calendar c) {
        if (c != null) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        }
    }

    /**
     * Reset hours, minutes, seconds and milliseconds in {@link Date} instance.
     */
    public static Date resetTime(Date date) {
        if (date != null) {
            date = org.apache.commons.lang3.time.DateUtils.setHours(date, 0);
            date = org.apache.commons.lang3.time.DateUtils.setMinutes(date, 0);
            date = org.apache.commons.lang3.time.DateUtils.setSeconds(date, 0);
            date = org.apache.commons.lang3.time.DateUtils.setMilliseconds(date, 0);
        }
        return date;
    }

}
