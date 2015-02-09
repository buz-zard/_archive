package lt.solutioni.core.utils;

import lt.solutioni.core.BaseTest;

import org.junit.Test;

/**
 * Test for {@link StringUtils}.
 * 
 * @author buzzard
 * 
 */
public class StringUtilsTest extends BaseTest {

    /**
     * Test for {@link StringUtils#commonPrefix(String, String)}.
     */
    @Test
    public void testCommonPrefix() {
        String vandenis = "Vandenis";
        assertEquals(null, StringUtils.commonPrefix(null, ""));
        assertEquals(null, StringUtils.commonPrefix("", null));
        assertEquals(null, StringUtils.commonPrefix(null, null));
        assertEquals(null, StringUtils.commonPrefix("", ""));
        assertEquals(null, StringUtils.commonPrefix("", vandenis));
        assertEquals(null, StringUtils.commonPrefix("Pavandenis", vandenis));
        assertEquals(null, StringUtils.commonPrefix("aVandenis", vandenis));
        assertEquals("vandeni", StringUtils.commonPrefix("vandenis", "Vandenienė"));
        assertEquals("Vandenis", vandenis);
    }

    /**
     * Test for {@link StringUtils#endsWithRegex(String, String)}.
     */
    @Test
    public void testEndsWithRegex() {
        String regex = "(as|is)";
        String s1 = "";
        String s2 = "aska";
        String s3 = "Jonas";
        String s4 = "Aspirinas";

        assertFalse(StringUtils.endsWithRegex(null, regex));
        assertFalse(StringUtils.endsWithRegex(s1, regex));
        assertFalse(StringUtils.endsWithRegex(s2, regex));
        assertTrue(StringUtils.endsWithRegex(s3, regex));
        assertTrue(StringUtils.endsWithRegex(s4, regex));
    }

    /**
     * Test for {@link StringUtils#lenght(String)}.
     */
    @Test
    public void testLenght() {
        assertEquals(-1, StringUtils.lenght(null));
        assertEquals(0, StringUtils.lenght(""));
        assertEquals(1, StringUtils.lenght(" "));
        assertEquals(3, StringUtils.lenght("abc"));
    }

}
