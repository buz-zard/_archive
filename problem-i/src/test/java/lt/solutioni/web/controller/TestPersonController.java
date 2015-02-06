package lt.solutioni.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.solutioni.core.BaseTest;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.RelationshipService;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.domain.RelatedPerson;
import lt.solutioni.web.domain.ResponseType;
import lt.solutioni.web.domain.RestResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Test for {@link PersonController}.
 * 
 * @author buzzard
 *
 */
public class TestPersonController extends BaseTest {

    @InjectMocks
    private PersonController controller;

    @Mock
    private PersonRepositoryService repositoryServiceMock;

    @Mock
    private RelationshipService relationshipServiceMock;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        controller = new PersonController();
        finishSetup();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        verifyNoMoreInteractions(repositoryServiceMock);
        verifyNoMoreInteractions(relationshipServiceMock);
    }

    /**
     * Test for {@link PersonController#allPeople()}.
     */
    @Test
    public void testAllPeople() {
        List<Person> people = new ArrayList<Person>();
        when(repositoryServiceMock.findAll()).thenReturn(people);
        RestResponse response = controller.allPeople();
        assertSame(people, response.getData());
        assertEquals(ResponseType.OK, response.getStatus());
        verify(repositoryServiceMock, times(1)).findAll();
    }

    /**
     * Test for {@link PersonController#getPerson(Long)}.
     */
    @Test
    public void testGetPerson() {
        when(repositoryServiceMock.findOne(1)).thenReturn(null);
        RestResponse response1 = controller.getPerson(1l);
        assertSame(PersonController.MSG_PERSON_FIND_FAILED, response1.getData());
        assertEquals(ResponseType.ERROR, response1.getStatus());
        verify(repositoryServiceMock, times(1)).findOne(1);

        Person p2 = new Person("Karolis", "Šarapnickis");
        when(repositoryServiceMock.findOne(2)).thenReturn(p2);
        RestResponse response2 = controller.getPerson(2l);
        assertSame(p2, response2.getData());
        assertEquals(ResponseType.OK, response2.getStatus());
        verify(repositoryServiceMock, times(1)).findOne(2);
    }

    /**
     * Test for {@link PersonController#savePerson(Person)}.
     */
    @Test
    public void testSavePerson() {
        Person p1 = new Person("", "");
        when(repositoryServiceMock.save(p1)).thenReturn(false);
        RestResponse response1 = controller.savePerson(p1);
        assertSame(PersonController.MSG_PERSON_SAVE_FAILED, response1.getData());
        assertEquals(ResponseType.ERROR, response1.getStatus());
        verify(repositoryServiceMock, times(1)).save(p1);

        Person p2 = new Person("Karolis", "Šarapnickis");
        when(repositoryServiceMock.save(p2)).thenReturn(true);
        RestResponse response2 = controller.savePerson(p2);
        assertSame(PersonController.MSG_PERSON_SAVED, response2.getData());
        assertEquals(ResponseType.OK, response2.getStatus());
        verify(repositoryServiceMock, times(1)).save(p2);
    }

    /**
     * Test for {@link PersonController#deletePerson(Long)}.
     */
    @Test
    public void testDeletePerson() {
        when(repositoryServiceMock.delete(1)).thenReturn(false);
        RestResponse response1 = controller.deletePerson(1l);
        assertSame(PersonController.MSG_PERSON_DELETE_FAILED, response1.getData());
        assertEquals(ResponseType.ERROR, response1.getStatus());
        verify(repositoryServiceMock, times(1)).delete(1);

        when(repositoryServiceMock.delete(2)).thenReturn(true);
        RestResponse response2 = controller.deletePerson(2l);
        assertSame(PersonController.MSG_PERSON_DELETED, response2.getData());
        assertEquals(ResponseType.OK, response2.getStatus());
        verify(repositoryServiceMock, times(1)).delete(2);
    }

    /**
     * Test for {@link PersonController#updatePerson(Person)}.
     */
    @Test
    public void testUpdatePerson() {
        Person p1 = new Person("", "");
        when(repositoryServiceMock.update(p1)).thenReturn(false);
        RestResponse response1 = controller.updatePerson(p1);
        assertSame(PersonController.MSG_PERSON_UPDATE_FAILED, response1.getData());
        assertEquals(ResponseType.ERROR, response1.getStatus());
        verify(repositoryServiceMock, times(1)).update(p1);

        Person p2 = new Person("Karolis", "Šarapnickis");
        when(repositoryServiceMock.update(p2)).thenReturn(true);
        RestResponse response2 = controller.updatePerson(p2);
        assertSame(PersonController.MSG_PERSON_UPDATED, response2.getData());
        assertEquals(ResponseType.OK, response2.getStatus());
        verify(repositoryServiceMock, times(1)).update(p2);
    }

    /**
     * Test for {@link PersonController#relationships(Long)}.
     */
    @Test
    public void testRelationships() {
        when(repositoryServiceMock.findOne(-1)).thenReturn(null);
        RestResponse response1 = controller.relationships(-1l);
        assertSame(PersonController.MSG_PERSON_FIND_FAILED, response1.getData());
        assertEquals(ResponseType.ERROR, response1.getStatus());
        verify(repositoryServiceMock, times(1)).findOne(-1);

        Person p0 = new Person();
        List<Person> noRelatedPeopleList = new ArrayList<Person>();
        Map<Person, Relationship> noRelatedPeople = new HashMap<Person, Relationship>();
        List<RelatedPerson> noRelatedPeopleReponse = new ArrayList<RelatedPerson>();
        when(repositoryServiceMock.findOne(0)).thenReturn(p0);
        when(repositoryServiceMock.findAll()).thenReturn(noRelatedPeopleList);
        when(relationshipServiceMock.getRelationships(p0, noRelatedPeopleList)).thenReturn(
                noRelatedPeople);
        RestResponse response2 = controller.relationships(0l);
        assertEquals(noRelatedPeopleReponse, response2.getData());
        assertEquals(ResponseType.OK, response2.getStatus());
        verify(repositoryServiceMock, times(1)).findOne(0);
        verify(relationshipServiceMock, times(1)).getRelationships(p0, noRelatedPeopleList);

        Person p1 = new Person("Jonas", "Vandenis");
        p1.setId(1);
        Person p2 = new Person("Toma", "Lašytė-Vandenienė");
        p2.setId(2);
        Person p3 = new Person("Kamilė", "Vandenytė");
        p3.setId(3);
        List<Person> relatedPeopleList = new ArrayList<Person>();
        Map<Person, Relationship> relatedPeople = new HashMap<Person, Relationship>();
        relatedPeople.put(p2, Relationship.WIFE);
        relatedPeople.put(p3, Relationship.DAUGHTER);
        List<RelatedPerson> relatedPeopleReponse = new ArrayList<RelatedPerson>();
        relatedPeopleReponse.add(new RelatedPerson(Relationship.WIFE, p2));
        relatedPeopleReponse.add(new RelatedPerson(Relationship.DAUGHTER, p3));
        when(repositoryServiceMock.findOne(1)).thenReturn(p1);
        when(repositoryServiceMock.findAll()).thenReturn(relatedPeopleList);
        when(relationshipServiceMock.getRelationships(p1, relatedPeopleList)).thenReturn(
                relatedPeople);
        RestResponse response3 = controller.relationships(1l);
        assertEquals(relatedPeopleReponse, response3.getData());
        assertEquals(ResponseType.OK, response3.getStatus());
        verify(repositoryServiceMock, times(1)).findOne(1);
        verify(repositoryServiceMock, times(2)).findAll();
        verify(relationshipServiceMock, times(1)).getRelationships(p1, relatedPeopleList);
    }

}
