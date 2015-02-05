package lt.solutioni.core.service.impl;

import static org.mockito.Mockito.when;

import java.util.Date;

import lt.solutioni.core.BaseTest;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.utils.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Test for {@link DateServiceImpl}.
 * 
 * @author buzzard
 * 
 */
public class TestDateServiceImpl extends BaseTest {

    @InjectMocks
    private DateService service;

    @Mock
    private PersonService personServiceMock;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new DateServiceImpl();
        super.finishSetup();
    }

    /**
     * Test for {@link DateServiceImpl#getAge(Date)}.
     */
    @Test
    public void testGetAge() {
        Date now = DateUtils.getDate("2015-02-15");

        assertAge(now, "2013-02-13", 2);
        assertAge(now, "2014-02-15", 1);
        assertAge(now, "2014-02-16", 0);
        assertAge(now, "2015-02-16", -1);
        assertAge(now, "2016-09-25", -1);
    }

    private void assertAge(Date now, String fromDate, int value) {
        assertEquals(value, service.getAge(DateUtils.getDate(fromDate), now));
    }

    /**
     * Test for {@link DateServiceImpl#getAgeDifference(Person, Person)}.
     */
    @Test
    public void testGetAgeDiffrence() {
        Person p1 = new Person("", "", "1990-07-13");
        Person p2 = new Person("", "", "1990-01-13");
        Person p3 = new Person("", "", "1985-07-13");
        Person p4 = new Person("", "");

        when(personServiceMock.isAgeValid(p1)).thenReturn(true);
        when(personServiceMock.isAgeValid(p2)).thenReturn(true);
        when(personServiceMock.isAgeValid(p3)).thenReturn(true);
        when(personServiceMock.isAgeValid(p4)).thenReturn(false);
        assertSame(0, service.getAgeDifference(p1, p1));
        assertSame(0, service.getAgeDifference(p1, p2));
        assertSame(5, service.getAgeDifference(p1, p3));
        assertSame(5, service.getAgeDifference(p3, p1));
        assertSame(4, service.getAgeDifference(p2, p3));
        assertSame(4, service.getAgeDifference(p3, p2));
        assertEquals(null, service.getAgeDifference(p1, p4));
        assertEquals(null, service.getAgeDifference(p4, p1));
        assertEquals(null, service.getAgeDifference(p4, p4));
    }

}
