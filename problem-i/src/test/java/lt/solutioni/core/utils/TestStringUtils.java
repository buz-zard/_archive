package lt.solutioni.core.utils;

import lt.solutioni.core.CoreTestCase;

import org.junit.Test;

/**
 * 
 * @author buzzard
 * 
 *         Test for @link StringUtils
 *
 */
public class TestStringUtils extends CoreTestCase {

    /**
     * Test for {@link StringUtils#commonPrefix(String, String)}
     */
    @Test
    public void testCommonPrefix() {
        assertEquals(null, StringUtils.commonPrefix(null, ""));
        assertEquals(null, StringUtils.commonPrefix("", null));
        assertEquals(null, StringUtils.commonPrefix(null, null));
        assertEquals(null, StringUtils.commonPrefix("", ""));
        assertEquals(null, StringUtils.commonPrefix("", "Vandenis"));
        assertEquals(null, StringUtils.commonPrefix("Pavandenis", "Vandenis"));
        assertEquals(null, StringUtils.commonPrefix("aVandenis", "Vandenis"));
        assertEquals("vandeni",
                StringUtils.commonPrefix("vandenis", "Vandenienė"));
    }

}
