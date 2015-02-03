package lt.solutioni.core.service.impl;

import lt.solutioni.core.CoreTestCase;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.service.RelationshipService;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author buzzard
 * 
 *         Test for @link RelationshipServiceImpl
 *
 */
public class TestRelationshipServiceImpl extends CoreTestCase {

    private RelationshipService service;
    private DateService dateService;
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new RelationshipServiceImpl();
        dateService = getBean(DateService.class);
        personService = getBean(PersonService.class);
        ((RelationshipServiceImpl) service).setDateService(dateService);
        ((RelationshipServiceImpl) service).setPersonService(personService);
    }

    /**
     * Test for
     * {@link RelationshipServiceImpl#areSurnamesRelated(String, String)}
     */
    @Test
    public void testAreSurnamesRelated() {
        String p1 = "Pavardenis";
        String p2 = "Pavardenienė";
        String p3 = "Pavardenytė";
        String p4 = "Pavargdenis";
        String p5 = "Kebabinskas";

        assertTrue(service.areSurnamesRelated(p1, p1));
        assertTrue(service.areSurnamesRelated(p2, p2));
        assertTrue(service.areSurnamesRelated(p3, p3));
        assertTrue(service.areSurnamesRelated(p4, p4));
        assertTrue(service.areSurnamesRelated(p5, p5));

        assertTrue(service.areSurnamesRelated(p1, p2));
        assertTrue(service.areSurnamesRelated(p1, p3));
        assertTrue(service.areSurnamesRelated(p2, p3));

        assertFalse(service.areSurnamesRelated(null, null));
        assertFalse(service.areSurnamesRelated(null, ""));
        assertFalse(service.areSurnamesRelated("", null));
        assertFalse(service.areSurnamesRelated("", ""));

        assertFalse(service.areSurnamesRelated(p1, p4));
        assertFalse(service.areSurnamesRelated(p2, p4));
        assertFalse(service.areSurnamesRelated(p3, p4));
        assertFalse(service.areSurnamesRelated(p1, p5));
        assertFalse(service.areSurnamesRelated(p2, p5));
        assertFalse(service.areSurnamesRelated(p3, p5));
        assertFalse(service.areSurnamesRelated(p4, p5));
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}
     */
    @Test
    public void testBrotherSisterRelationship() {
        Person p1 = mockPerson("Jonas", "Vandenis", "1990-07-13");
        Person p2 = mockPerson("Toma", "Vandenytė", "1990-07-13");

        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        p1.setDateOfBirth(dateService.getDate("1974-07-14"));
        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        p1.setDateOfBirth(dateService.getDate("1974-07-12"));
        assertFalse(service.getRelationship(p2, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p2, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.SISTER);

        p1.setSurname("Vandenis-Kebabinskas");
        p2.setSurname("Vandeniūtė-Sausumienė");
        p1.setDateOfBirth(dateService.getDate("1990-07-13"));

        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        p1.setDateOfBirth(dateService.getDate("1974-07-14"));
        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        p1.setDateOfBirth(dateService.getDate("1974-07-12"));
        assertFalse(service.getRelationship(p2, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p2, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.SISTER);

        p1.setDateOfBirth(dateService.getDate("1990-07-13"));
        Person p3 = mockPerson("Toma", "Kebabinskyė", "1990-07-13");
        assertFalse(service.getRelationship(p3, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p3, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p3) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p3) == Relationship.SISTER);
    }

    private Person mockPerson(String name, String surname, String date) {
        Person p1 = new Person(name, surname);
        p1.setGender(personService.getGender(p1));
        p1.setDateOfBirth(dateService.getDate(date));
        return p1;
    }

}
