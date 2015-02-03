package lt.solutioni.core.utils;


/**
 * 
 * @author buzzard
 *
 */
public class StringUtils {

    /**
     * Find longest common prefix (case insensitive) between 2 strings.
     */
    public static String commonPrefix(String val1, String val2) {
        if (val1 != null && val2 != null) {
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
        if (s != null) {
            return s.matches(".*" + regex + "$");
        }
        return false;
    }

}
