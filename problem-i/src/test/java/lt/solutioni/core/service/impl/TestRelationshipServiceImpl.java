package lt.solutioni.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.solutioni.core.CoreTestCase;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.service.RelationshipService;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link RelationshipServiceImpl}
 * 
 * @author buzzard
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

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}
     */
    @Test
    public void testWifeHusbandRelationship() {
        Person p1 = mockPerson("Jonas", "Butkus-Vandenis", "1980-07-13");
        Person p2 = mockPerson("Toma", "Lašytė-Vandenienė", "1985-07-13");

        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        p2.setSurname("Vandenienė");
        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        p1.setSurname("Vandenis");
        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        p2.setSurname("Vandenytė-Lašienė");
        assertFalse(service.getRelationship(p2, p1) == Relationship.WIFE);
        assertFalse(service.getRelationship(p2, p1) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.WIFE);

        p2.setSurname("Vandenytė");
        assertFalse(service.getRelationship(p2, p1) == Relationship.WIFE);
        assertFalse(service.getRelationship(p2, p1) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.WIFE);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}
     */
    @Test
    public void testSonDaughterFatherMotherRelationship() {
        Person p1 = mockPerson("Jonas", "Purvas-Vandenis", "1970-07-13");
        Person p2 = mockPerson("Toma", "Lašytė-Vandenienė", "1970-07-13");
        Person p3 = mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        Person p4 = mockPerson("Tomas", "Lašas-Vandenis", "1990-07-13");

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.SON, service.getRelationship(p1, p4));
        assertEquals(Relationship.FATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.FATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.SON, service.getRelationship(p2, p4));
        assertEquals(Relationship.MOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.MOTHER, service.getRelationship(p4, p2));

        p1.setSurname("Vandenis");
        p2.setSurname("Vandenienė");
        p3.setSurname("Vandenytė");
        p4.setSurname("Vandenis");
        assertEquals(Relationship.DAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.SON, service.getRelationship(p1, p4));
        assertEquals(Relationship.FATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.FATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.SON, service.getRelationship(p2, p4));
        assertEquals(Relationship.MOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.MOTHER, service.getRelationship(p4, p2));

        p1.setSurname("Lašas");
        p2.setSurname("Lašienė");
        p3.setSurname("Vandenytė-Lašienė");
        p4.setSurname("Lašas-Vandenis");
        assertFalse(service.getRelationship(p1, p3) == Relationship.DAUGHTER);
        assertFalse(service.getRelationship(p1, p4) == Relationship.SON);
        assertFalse(service.getRelationship(p3, p1) == Relationship.FATHER);
        assertFalse(service.getRelationship(p4, p1) == Relationship.FATHER);

        assertFalse(service.getRelationship(p2, p3) == Relationship.DAUGHTER);
        assertFalse(service.getRelationship(p2, p4) == Relationship.SON);
        assertFalse(service.getRelationship(p3, p2) == Relationship.MOTHER);
        assertFalse(service.getRelationship(p4, p2) == Relationship.FATHER);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}
     */
    @Test
    public void testGrandSonDaughterFatherMotherRelationship() {
        Person p1 = mockPerson("Jonas", "Purvas-Vandenis", "1950-07-13");
        Person p2 = mockPerson("Toma", "Lašytė-Vandenienė", "1950-07-13");
        Person p3 = mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        Person p4 = mockPerson("Tomas", "Lašas-Vandenis", "1991-07-13");

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p1, p4));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p2, p4));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p4, p2));

        p1.setSurname("Vandenis");
        p2.setSurname("Vandenienė");
        p3.setSurname("Vandenytė");
        p4.setSurname("Vandenis");
        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p1, p4));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p2, p4));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p4, p2));

        p1.setSurname("Lašas");
        p2.setSurname("Lašienė");
        p3.setSurname("Vandenytė-Lašienė");
        p4.setSurname("Lašas-Vandenis");
        assertFalse(service.getRelationship(p1, p3) == Relationship.GRANDAUGHTER);
        assertFalse(service.getRelationship(p1, p4) == Relationship.GRANDSON);
        assertFalse(service.getRelationship(p3, p1) == Relationship.GRANDFATHER);
        assertFalse(service.getRelationship(p4, p1) == Relationship.GRANDFATHER);

        assertFalse(service.getRelationship(p2, p3) == Relationship.GRANDAUGHTER);
        assertFalse(service.getRelationship(p2, p4) == Relationship.GRANDSON);
        assertFalse(service.getRelationship(p3, p2) == Relationship.GRANDMOTHER);
        assertFalse(service.getRelationship(p4, p2) == Relationship.GRANDFATHER);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationships(Person, List)}
     */
    @Test
    public void testGetRelationships1() {
        Person p1 = mockPerson("Jonas", "Purvas-Vandenis", "1950-07-13");
        Person p2 = mockPerson("Toma", "Lašytė-Vandenienė", "1950-07-13");
        Person p3 = mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        Person p4 = mockPerson("Tomas", "Lašas-Vandenis", "1991-07-13");
        List<Person> people = new ArrayList<Person>();
        people.add(p2);
        people.add(p3);
        people.add(p4);

        Map<Person, Relationship> expectedResult = new HashMap<Person, Relationship>();
        expectedResult.put(p2, Relationship.WIFE);
        expectedResult.put(p3, Relationship.GRANDAUGHTER);
        expectedResult.put(p4, Relationship.GRANDSON);
        assertEquals(expectedResult, service.getRelationships(p1, people));
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationships(Person, List)}
     */
    @Test
    public void testGetRelationships2() {
        Person p1 = mockPerson("Jonas", "Purvas-Vandenis", "1970-07-13");
        Person p2 = mockPerson("Toma", "Lašytė-Vandenienė", "1970-07-13");
        Person p3 = mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        Person p4 = mockPerson("Tomas", "Lašas-Vandenis", "1990-07-13");
        List<Person> people = new ArrayList<Person>();
        people.add(p2);
        people.add(p3);
        people.add(p4);

        Map<Person, Relationship> expectedResult = new HashMap<Person, Relationship>();
        expectedResult.put(p2, Relationship.WIFE);
        expectedResult.put(p3, Relationship.DAUGHTER);
        expectedResult.put(p4, Relationship.SON);
        assertEquals(expectedResult, service.getRelationships(p1, people));
    }

    private Person mockPerson(String name, String surname, String date) {
        Person p1 = new Person(name, surname);
        p1.setGender(personService.getGender(p1));
        p1.setDateOfBirth(dateService.getDate(date));
        return p1;
    }

}
