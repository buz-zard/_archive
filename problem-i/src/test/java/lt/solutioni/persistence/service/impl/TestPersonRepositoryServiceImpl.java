package lt.solutioni.persistence.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.service.impl.DateServiceImpl;
import lt.solutioni.persistence.PersistenceTest;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PersonRepository;
import lt.solutioni.persistence.service.PersonRepositoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class TestPersonRepositoryServiceImpl extends PersistenceTest {

    private PersonRepositoryService service;

    @Mock
    private EntityManager em;

    @Autowired
    private DateService dateService = new DateServiceImpl();

    @Autowired
    private PersonService serviceMock;

    private List<Person> mockedPeople;
    private List<PersonDAO> mockedPeopleDAO;

    @Autowired
    private PersonRepository repositoryMock;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new PersonRepositoryServiceImpl();
        ((PersonRepositoryServiceImpl) service).setService(serviceMock);
        ((PersonRepositoryServiceImpl) service).setRepository(repositoryMock);

        mockedPeople = new ArrayList<Person>();
        mockedPeopleDAO = new ArrayList<PersonDAO>();
        Person p1 = mockPerson(1, "Karolis", "Šarapnickis", "1990-07-13", Gender.MALE);
        Person p2 = mockPerson(2, "Jonas", "Vandenis", "1970-07-13", Gender.MALE);
        Person p3 = mockPerson(3, "Toma", "Lašytė-Vandenienė", "1970-07-13", Gender.MALE);
        Person p4 = mockPerson(4, "Kamilė", "Vandenytė-Butkienė", "1993-07-13", Gender.MALE);
        Person p5 = mockPerson(5, "Tomas", "Lašas-Vandenis", "1990-07-13", Gender.MALE);
        when(repositoryMock.findAll()).thenReturn(mockedPeopleDAO);
    }

    private Person mockPerson(long id, String name, String surname, String date, Gender gender) {
        Person person = new Person(name, surname);
        person.setGender(gender);
        person.setDateOfBirth(dateService.getDate(date));
        mockedPeople.add(person);
        PersonDAO personDAO = PersonDAO.fromPerson(person);
        mockedPeopleDAO.add(personDAO);
        when(repositoryMock.findOne(person.getId())).thenReturn(personDAO);
        return person;
    }

    /**
     * Test for {@link PersonRepositoryService#findAll()}
     */
    @Test
    public void testFindAll() {
        assertEquals(mockedPeople, service.findAll());
        verify(repositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(repositoryMock);
    }

    /**
     * Test for {@link PersonRepositoryService#findOne(long)}
     */
    @Test
    public void testFindOne() {
        assertEquals(mockedPeople.get(0), service.findOne(1));
        assertEquals(mockedPeople.get(1), service.findOne(2));
        assertEquals(mockedPeople.get(2), service.findOne(3));
        assertEquals(mockedPeople.get(3), service.findOne(4));
        assertEquals(mockedPeople.get(4), service.findOne(5));
        verify(repositoryMock, times(1)).findOne(1L);
        verify(repositoryMock, times(1)).findOne(2L);
        verify(repositoryMock, times(1)).findOne(3L);
        verify(repositoryMock, times(1)).findOne(4L);
        verify(repositoryMock, times(1)).findOne(5L);
        verifyNoMoreInteractions(repositoryMock);
    }
}
