package lt.solutioni.core.service.impl;

import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.CoreTestCase;
import lt.solutioni.core.service.DateService;

import org.junit.Before;

/**
 * 
 * @author buzzard
 * 
 *         Test for @link DateServiceImpl
 *
 */
public class TestDateServiceImpl extends CoreTestCase {

    private DateService service;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new DateServiceImpl();
    }

    /**
     * Test for {@link DateServiceImpl#getDate(String)}
     */
    public void testGetDate() {
        Date date1 = service.getDate("1990-07-13");
        Calendar c = Calendar.getInstance();
        c.set(1990, 6, 13, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date expectedDate = c.getTime();
        Date date2 = service.getDate("1990-15-45");

        assertEquals(expectedDate, date1);
        assertNull(date2);
    }

    /**
     * Test for {@link DateServiceImpl#getAge(Date)}
     */
    public void testGetAge() {
        Date now = service.getDate("2015-02-15");

        assertAge(now, "2013-02-13", 2);
        assertAge(now, "2014-02-15", 1);
        assertAge(now, "2014-02-16", 0);
        assertAge(now, "2015-02-16", -1);
        assertAge(now, "2016-09-25", -1);
    }

    private void assertAge(Date now, String fromDate, int value) {
        assertEquals(value, service.getAge(service.getDate(fromDate), now));
    }

}
