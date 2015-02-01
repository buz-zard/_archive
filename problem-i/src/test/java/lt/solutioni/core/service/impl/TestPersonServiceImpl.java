package lt.solutioni.core.service.impl;

import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.CoreTestCase;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;

import org.junit.Before;

/**
 * 
 * @author buzzard
 * 
 *         Test for @link PersonServiceImpl
 *
 */
public class TestPersonServiceImpl extends CoreTestCase {

    private PersonService service;
    private DateService dateService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new PersonServiceImpl();
        dateService = context.getBean(DateService.class);
        ((PersonServiceImpl) service).setDateService(dateService);
    }

    /**
     * Test for {@link PersonServiceImpl#isNameValid(Person)}
     */
    public void testNameValidation() {
        Person p1 = new Person("J", "");
        Person p2 = new Person("Jonas", "");
        Person p3 = new Person(
                "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonas", "");
        Person p4 = new Person(
                "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonasA", "");
        Person p5 = new Person("Jonas Antanas", "");
        Person p6 = new Person("J0nas", "");
        Person p7 = new Person("Jonas-Antanas", "");
        Person p8 = new Person("Žygimantas", "");
        Person p9 = new Person("Kęstutis", "");
        Person p10 = new Person(null, "");

        assertFalse(service.isNameValid(p1));
        assertTrue(service.isNameValid(p2));
        assertTrue(service.isNameValid(p3));
        assertFalse(service.isNameValid(p4));
        assertTrue(service.isNameValid(p5));
        assertFalse(service.isNameValid(p6));
        assertFalse(service.isNameValid(p7));
        assertTrue(service.isNameValid(p8));
        assertTrue(service.isNameValid(p9));
        assertFalse(service.isNameValid(p10));
    }

    /**
     * Test for {@link PersonServiceImpl#isSurnameValid(Person)}
     */
    public void testSurnameValidation() {
        Person p1 = new Person("", "K");
        Person p2 = new Person("", "Žiežirbinis");
        Person p3 = new Person("",
                "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonas");
        Person p4 = new Person("",
                "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonass");
        Person p5 = new Person("", "Jonas-Antanas");
        Person p6 = new Person("", "J0nas");
        Person p7 = new Person("", "Jonas Antanas");
        Person p8 = new Person("", "awdawd");
        Person p9 = new Person("", "Pavardenytė");
        Person p10 = new Person("", null);

        assertFalse(service.isSurnameValid(p1));
        assertTrue(service.isSurnameValid(p2));
        assertTrue(service.isSurnameValid(p3));
        assertFalse(service.isSurnameValid(p4));
        assertTrue(service.isSurnameValid(p5));
        assertFalse(service.isSurnameValid(p6));
        assertFalse(service.isSurnameValid(p7));
        assertFalse(service.isSurnameValid(p8));
        assertTrue(service.isSurnameValid(p9));
        assertFalse(service.isSurnameValid(p10));
    }

    /**
     * Test for {@link PersonServiceImpl#isAgeValid(Person)}
     */
    public void testAgeValidation() {
        Person p1 = new Person("", "");
        p1.setDateOfBirth(dateService.getDate("1990-07-13"));
        Person p2 = new Person("", "");
        p2.setDateOfBirth(new Date());
        Person p3 = new Person("", "");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        p3.setDateOfBirth(c.getTime());
        Person p4 = new Person("", "");

        assertTrue(service.isAgeValid(p1));
        assertTrue(service.isAgeValid(p2));
        assertFalse(service.isAgeValid(p3));
        assertFalse(service.isAgeValid(p4));
    }

    /**
     * Test for {@link PersonServiceImpl#isGenderValid(Person)}
     */
    public void testGenderValidation() {
        Person p1 = new Person("", "");
        Person p2 = new Person("", "");
        p2.setGender(Gender.MALE);
        Person p3 = new Person("", "");
        p3.setGender(Gender.FEMALE);

        assertFalse(service.isGenderValid(p1));
        assertTrue(service.isGenderValid(p2));
        assertTrue(service.isGenderValid(p3));
    }

    /**
     * Test for {@link PersonServiceImpl#setGender(Person)}
     */
    public void testSetGender() {
        Person p1 = new Person("", "qwerty");
        service.setGender(p1);
        Person p2 = new Person("", "Pavardenis");
        service.setGender(p2);
        Person p3 = new Person("", "Pavardenytė");
        service.setGender(p3);

        assertNull(p1.getGender());
        assertEquals(Gender.MALE, p2.getGender());
        assertEquals(Gender.FEMALE, p3.getGender());
    }

}
