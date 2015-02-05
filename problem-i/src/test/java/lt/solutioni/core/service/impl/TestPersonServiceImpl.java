package lt.solutioni.core.service.impl;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for {@link PersonServiceImpl}
 * 
 * @author buzzard
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreConfiguration.class)
public class TestPersonServiceImpl extends TestCase {

    private PersonService service;

    @Autowired
    private DateService dateService;

    @Before
    public void setUp() throws Exception {
        service = new PersonServiceImpl();
        ((PersonServiceImpl) service).setDateService(dateService);
    }

    /**
     * Test for {@link PersonServiceImpl#isNameValid(Person)}
     */
    @Test
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
    @Test
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
    @Test
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
     * Test for {@link PersonServiceImpl#getGender(Person)}
     */
    @Test
    public void testGetGender() {
        Person p1 = new Person("", "qwerty");
        Person p2 = new Person("", "Pavardenis");
        Person p3 = new Person("", "Pavardenytė");

        assertNull(service.getGender(p1));
        assertEquals(Gender.MALE, service.getGender(p2));
        assertEquals(Gender.FEMALE, service.getGender(p3));
    }

    /**
     * Test for {@link PersonServiceImpl#getFirstSurname(Person)}
     */
    public void testGetFirstSurname() {
        Person p1 = new Person("", "qwerty");
        Person p2 = new Person("", "Pavardenis");
        Person p3 = new Person("", "pavardenis-Bumblauskas");
        Person p4 = new Person("", "Pavardenytė");
        Person p5 = new Person("", "pavardenytė-Bumblauskienė");

        assertNull(service.getFirstSurname(p1));
        assertEquals("Pavardenis", service.getFirstSurname(p2));
        assertEquals("Pavardenis", service.getFirstSurname(p3));
        assertEquals("Pavardenytė", service.getFirstSurname(p4));
        assertEquals("Pavardenytė", service.getFirstSurname(p5));
    }

    /**
     * Test for {@link PersonServiceImpl#getLastSurname(Person)}
     */
    public void testGetLastSurname() {
        Person p1 = new Person("", "qwerty");
        Person p2 = new Person("", "Pavardenis");
        Person p3 = new Person("", "pavardenis-Bumblauskas");
        Person p4 = new Person("", "Pavardenytė");
        Person p5 = new Person("", "pavardenytė-bumblauskienė");

        assertNull(service.getLastSurname(p1));
        assertEquals("Pavardenis", service.getLastSurname(p2));
        assertEquals("Bumblauskas", service.getLastSurname(p3));
        assertEquals("Pavardenytė", service.getLastSurname(p4));
        assertEquals("Bumblauskienė", service.getLastSurname(p5));
    }

}
