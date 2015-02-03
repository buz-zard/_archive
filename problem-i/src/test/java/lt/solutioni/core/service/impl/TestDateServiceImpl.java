package lt.solutioni.core.service.impl;

import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.CoreTestCase;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author buzzard
 * 
 *         Test for {@link DateServiceImpl}
 *
 */
public class TestDateServiceImpl extends CoreTestCase {

	private DateService service;
	private PersonService personService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		service = new DateServiceImpl();
		personService = getBean(PersonService.class);
		((DateServiceImpl) service).setPersonService(personService);
	}

	/**
	 * Test for {@link DateServiceImpl#getDate(String)}
	 */
	@Test
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
	@Test
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

	/**
	 * Test for {@link DateServiceImpl#getAgeDifference(Person, Person)}
	 */
	@Test
	public void testGetAgeDiffrence() {
		Person p1 = new Person("", "");
		p1.setDateOfBirth(service.getDate("1990-07-13"));
		Person p2 = new Person("", "");
		p2.setDateOfBirth(service.getDate("1990-01-13"));
		Person p3 = new Person("", "");
		p3.setDateOfBirth(service.getDate("1985-07-13"));

		assertSame(0, service.getAgeDifference(p1, p2));
		assertSame(5, service.getAgeDifference(p1, p3));
		assertSame(5, service.getAgeDifference(p3, p1));
		assertSame(4, service.getAgeDifference(p2, p3));
		assertSame(4, service.getAgeDifference(p3, p2));
	}

}
