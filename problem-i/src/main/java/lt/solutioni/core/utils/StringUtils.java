package lt.solutioni.core.utils;

/**
 * Common {@link String} manipulation methods collection.
 * 
 * @author buzzard
 *
 */
public class StringUtils {

    /**
     * Find longest common prefix (case insensitive) between 2 strings.
     */
    public static String commonPrefix(String val1, String val2) {
        if (lenght(val1) > 0 && lenght(val2) > 0) {
            String s1 = val1.toLowerCase();
            String s2 = val2.toLowerCase();
            while (s1.length() > 0) {
                if (s2.startsWith(s1)) {
                    return s1;
                }
                s1 = s1.substring(0, s1.length() - 1);
            }
        }
        return null;
    }

    /**
     * Returns true if given string ends with given regex value.
     */
    public static boolean endsWithRegex(String s, String regex) {
        if (lenght(s) > 0) {
            return s.matches(".*" + regex + "$");
        }
        return false;
    }

    /**
     * Return length of the string. In case of null value return -1.
     */
    public static int lenght(String s) {
        if (s != null) {
            return s.length();
        }
        return -1;
    }

}
