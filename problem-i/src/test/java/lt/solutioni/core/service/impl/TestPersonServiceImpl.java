package lt.solutioni.core.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import lt.solutioni.core.BaseTest;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.utils.DateUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Test for {@link PersonServiceImpl}.
 * 
 * @author buzzard
 * 
 */
public class TestPersonServiceImpl extends BaseTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private DateService dateServiceMock;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new PersonServiceImpl();
        super.finishSetup();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        verifyNoMoreInteractions(dateServiceMock);
    }

    /**
     * Test for {@link PersonServiceImpl#isNameValid(Person)}.
     */
    @Test
    public void testNameValidation() {
        Person p1 = new Person("J", "");
        Person p2 = new Person("Jonas", "");
        Person p3 = new Person("JonasJonasJonasJonasJonasJonasJonasJonasJonasJonas", "");
        Person p4 = new Person("JonasJonasJonasJonasJonasJonasJonasJonasJonasJonasA", "");
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
     * Test for {@link PersonServiceImpl#isSurnameValid(Person)}.
     */
    @Test
    public void testSurnameValidation() {
        Person p1 = new Person("", "K");
        Person p2 = new Person("", "Žiežirbinis");
        Person p3 = new Person("", "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonas");
        Person p4 = new Person("", "JonasJonasJonasJonasJonasJonasJonasJonasJonasJonass");
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
     * Test for {@link PersonServiceImpl#isAgeValid(Person)}.
     */
    @Test
    public void testAgeValidation() {
        Person p1 = new Person("", "", "1990-07-13");
        Person p2 = new Person("", "", "2000-07-13");

        when(dateServiceMock.getAge(p1.getDateOfBirth())).thenReturn(99);
        when(dateServiceMock.getAge(p2.getDateOfBirth())).thenReturn(-1);
        assertTrue(service.isAgeValid(p1));
        assertFalse(service.isAgeValid(p2));
        verify(dateServiceMock, times(1)).getAge(DateUtils.getDate("1990-07-13"));
        verify(dateServiceMock, times(1)).getAge(DateUtils.getDate("2000-07-13"));
    }

    /**
     * Test for {@link PersonServiceImpl#isGenderValid(Person)}.
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
     * Test for {@link PersonServiceImpl#getGender(Person)}.
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
     * Test for {@link PersonServiceImpl#getFirstSurname(Person)}.
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
     * Test for {@link PersonServiceImpl#getLastSurname(Person)}.
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

    /**
     * Test for {@link PersonServiceImpl#createPerson(String, String, String)}.
     */
    @Test
    public void testCreatePerson() {
        assertNull(service.createPerson("", "", ""));
        assertNull(service.createPerson("", null, ""));
        assertNull(service.createPerson("", "", null));
        assertNull(service.createPerson("Jonas", "", ""));
        assertNull(service.createPerson("", "Pavardenis", ""));
        assertNull(service.createPerson("", "", "1990-07-13"));
        assertNull(service.createPerson("Jonas", "Vandenis", ""));
        assertNull(service.createPerson("Jonas", "asdasdasd", "1990-07-13"));

        Person p1 = new Person("Jonas", "Vandenis", "1990-07-13");
        p1.setGender(Gender.MALE);
        Person p2 = new Person("Janina", "Vandenienė", "1990-07-13");
        p2.setGender(Gender.FEMALE);
        Person p3 = new Person("Jonas", "Vandenis", "1990-07-13");

        assertEquals(p1, service.createPerson("Jonas", "Vandenis", "1990-07-13"));
        assertEquals(p2, service.createPerson("Janina", "Vandenienė", "1990-07-13"));
        assertFalse(p3.equals(service.createPerson("Jonas", "Vandenis", "1990-07-13")));
        verify(dateServiceMock, times(3)).getAge(DateUtils.getDate("1990-07-13"));
    }
}
