package lt.solutioni.core.utils;

import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.BaseTest;

import org.junit.Test;

/**
 * Tests for {@link DateUtils}.
 * 
 * @author buzzard
 *
 */
public class TestDateUtils extends BaseTest {

    /**
     * Test for {@link DateUtils#getDate(String)}.
     */
    @Test
    public void testGetDate() {
        Date date1 = DateUtils.getDate("1990-07-13");
        Calendar c = Calendar.getInstance();
        c.set(1990, 6, 13, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date expectedDate = c.getTime();
        Date date2 = DateUtils.getDate("1990-15-45");

        assertEquals(expectedDate, date1);
        assertNull(date2);
        assertNull(DateUtils.getDate(""));
        assertNull(DateUtils.getDate(null));
    }

    /**
     * Test for {@link DateUtils#formatDate(Date)}.
     */
    @Test
    public void testFormatDate() {
        assertEquals("1990-07-13", DateUtils.formatDate(DateUtils.getDate("1990-07-13")));
        assertEquals("1950-07-05", DateUtils.formatDate(DateUtils.getDate("1950-07-05")));
        assertEquals(null, DateUtils.formatDate(null));
    }

    /**
     * Test for {@link DateUtils#resetTime(Calendar)} and
     * {@link DateUtils#resetTime(Date)}.
     */
    @Test
    public void testResetTime() {
        Calendar c0 = null;
        DateUtils.resetTime(c0);
        assertTrue(true);

        Date date1 = null;
        DateUtils.resetTime(date1);
        assertTrue(true);

        Calendar c2 = Calendar.getInstance();
        Date expectedDate2 = DateUtils.getDate(DateUtils.formatDate(Calendar.getInstance()
                .getTime()));
        assertFalse(expectedDate2.equals(c2.getTime()));
        DateUtils.resetTime(c2);
        assertEquals(expectedDate2, c2.getTime());

        Calendar c3 = Calendar.getInstance();
        c3.set(Calendar.YEAR, 1990);
        c3.set(Calendar.MONTH, 6);
        c3.set(Calendar.DAY_OF_MONTH, 13);
        Date date3 = c3.getTime();
        assertFalse(DateUtils.getDate("1990-07-13").equals(date3));
        date3 = DateUtils.resetTime(date3);
        assertEquals(DateUtils.getDate("1990-07-13"), date3);
    }

}
