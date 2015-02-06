package lt.solutioni.persistence.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import lt.solutioni.core.BaseTest;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PersonRepository;
import lt.solutioni.persistence.service.PersonRepositoryService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Test for {@link PersonRepositoryServiceImpl}.
 * 
 * @author buzzard
 *
 */
public class TestPersonRepositoryServiceImpl extends BaseTest {

    @InjectMocks
    private PersonRepositoryService service;

    @Mock
    private PersonService serviceMock;

    @Mock
    private PersonRepository repositoryMock;

    private List<Person> mockedPeople;
    private List<PersonDAO> mockedPeopleDAO;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new PersonRepositoryServiceImpl();
        mockedPeople = new ArrayList<Person>();
        mockedPeopleDAO = new ArrayList<PersonDAO>();
        super.finishSetup();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        verifyNoMoreInteractions(repositoryMock);
        verifyNoMoreInteractions(serviceMock);
    }

    /**
     * Test for {@link PersonRepositoryService#findAll()}.
     */
    @Test
    public void testFindAll() {
        assertEquals(new ArrayList<>(), service.findAll());
        createMockedPeople();
        assertEquals(mockedPeople, service.findAll());
        verify(repositoryMock, times(2)).findAll();
    }

    /**
     * Test for {@link PersonRepositoryService#findOne(long)}.
     */
    @Test
    public void testFindOne() {
        createMockedPeople();
        assertEquals(mockedPeople.get(0), service.findOne(1));
        assertEquals(mockedPeople.get(1), service.findOne(2));
        assertEquals(mockedPeople.get(2), service.findOne(3));
        assertEquals(mockedPeople.get(3), service.findOne(4));
        assertEquals(mockedPeople.get(4), service.findOne(5));
        verify(repositoryMock, times(1)).findOne(1l);
        verify(repositoryMock, times(1)).findOne(2l);
        verify(repositoryMock, times(1)).findOne(3l);
        verify(repositoryMock, times(1)).findOne(4l);
        verify(repositoryMock, times(1)).findOne(5l);
    }

    /**
     * Test for {@link PersonRepositoryService#findOne(long)}.
     */
    @Test
    public void testFindOneFail() {
        when(repositoryMock.findOne(1l)).thenThrow(new EntityNotFoundException());
        assertEquals(null, service.findOne(1));
        verify(repositoryMock, times(1)).findOne(1l);
    }

    /**
     * Test for {@link PersonRepositoryService#delete(long)}.
     */
    @Test
    public void testDelete() {
        doNothing().when(repositoryMock).delete(1l);
        assertTrue(service.delete(1));
        verify(repositoryMock, times(1)).delete(1l);
    }

    /**
     * Test for {@link PersonRepositoryService#delete(long)}.
     */
    @Test
    public void testDeleteFail() {
        doThrow(EmptyResultDataAccessException.class).when(repositoryMock).delete(1l);
        assertFalse(service.delete(1));
        verify(repositoryMock, times(1)).delete(1l);
    }

    /**
     * Test for {@link PersonRepositoryService#save(Person)}.
     */
    @Test
    public void testSave() {
        assertFalse(service.save(null));

        Person p1 = mockPerson(1, "Karolis", "awdawdawd", "1990-07-13", Gender.MALE);
        when(serviceMock.isPersonValid(p1)).thenReturn(false);
        assertFalse(service.save(p1));
        verify(serviceMock, times(1)).getGender(p1);
        verify(serviceMock, times(1)).isPersonValid(p1);

        Person p2 = mockPerson(1, "Karolis", "Šarapnickis", "1990-07-13", Gender.MALE);
        PersonDAO pDAO2 = PersonDAO.fromPerson(p2);
        when(serviceMock.isPersonValid(p2)).thenReturn(true);
        when(repositoryMock.save(pDAO2)).thenReturn(pDAO2);
        assertTrue(service.save(p2));
        verify(serviceMock, times(1)).getGender(p2);
        verify(serviceMock, times(1)).isPersonValid(p2);
        verify(repositoryMock, times(1)).save(pDAO2);
    }

    /**
     * Test for {@link PersonRepositoryService#update(Person)}.
     */
    @Test
    public void testUpdate() {
        assertFalse(service.update(null));

        Person p1 = mockPerson(1, "Karolis", "Šarapnickis", "1990-07-13", Gender.MALE);
        when(repositoryMock.findOne(1l)).thenThrow(new EntityNotFoundException());
        assertFalse(service.update(p1));
        verify(repositoryMock, times(1)).findOne(1l);

        Person p2 = mockPerson(2, "Karolis", "awdawdawd", "1990-07-13", Gender.MALE);
        when(serviceMock.isPersonValid(p2)).thenReturn(false);
        when(repositoryMock.findOne(2l)).thenReturn(PersonDAO.fromPerson(p2));
        assertFalse(service.update(p2));
        verify(repositoryMock, times(1)).findOne(2l);
        verify(serviceMock, times(1)).getGender(p2);
        verify(serviceMock, times(1)).isPersonValid(p2);

        Person p3 = mockPerson(3, "Karolis", "Šarapnickis", "1990-07-13", Gender.MALE);
        PersonDAO pDAO3 = PersonDAO.fromPerson(p3);
        when(serviceMock.isPersonValid(p3)).thenReturn(true);
        when(repositoryMock.findOne(3l)).thenReturn(pDAO3);
        assertTrue(service.update(p3));
        verify(repositoryMock, times(1)).findOne(3l);
        verify(serviceMock, times(1)).getGender(p3);
        verify(serviceMock, times(1)).isPersonValid(p3);
        verify(repositoryMock, times(1)).save(pDAO3);
    }

    /*
     * ==================================================================
     * HELPERS
     * ==================================================================
     */

    // mock helpers

    private void createMockedPeople() {
        mockPerson(1, "Karolis", "Šarapnickis", "1990-07-13", Gender.MALE);
        mockPerson(2, "Jonas", "Vandenis", "1970-07-13", Gender.MALE);
        mockPerson(3, "Toma", "Lašytė-Vandenienė", "1970-07-13", Gender.MALE);
        mockPerson(4, "Kamilė", "Vandenytė-Butkienė", "1993-07-13", Gender.MALE);
        mockPerson(5, "Tomas", "Lašas-Vandenis", "1990-07-13", Gender.MALE);
        when(repositoryMock.findAll()).thenReturn(mockedPeopleDAO);
    }

    private Person mockPerson(long id, String name, String surname, String date, Gender gender) {
        Person person = new Person(name, surname, date);
        person.setId(id);
        person.setGender(gender);
        when(serviceMock.getGender(person)).thenReturn(person.getGender());
        mockedPeople.add(person);
        mockPersonDAO(person);
        return person;
    }

    private PersonDAO mockPersonDAO(Person person) {
        PersonDAO personDAO = PersonDAO.fromPerson(person);
        mockedPeopleDAO.add(personDAO);
        when(repositoryMock.findOne(person.getId())).thenReturn(personDAO);
        return personDAO;
    }

}
