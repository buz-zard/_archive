package lt.solutioni.core.utils;

/**
 * 
 * @author buzzard
 *
 */
public class StringUtils {

    /**
     * Find longest common prefix between 2 strings.
     */
    public static String commonPrefix(String val1, String val2) {
        if (val1 != null && val2 != null) {
            val1 = val1.toLowerCase();
            val2 = val2.toLowerCase();
            while (val1.length() > 0) {
                if (val2.startsWith(val1)) {
                    return val1;
                }
                val1 = val1.substring(0, val1.length() - 1);
            }
        }
        return null;
    }

}
