package lt.solutioni.core.service.impl;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.solutioni.core.BaseTest;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.service.RelationshipService;
import lt.solutioni.core.utils.DateUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Test for {@link RelationshipServiceImpl}.
 * 
 * @author buzzard
 * 
 */
public class RelationshipServiceImplTest extends BaseTest {

    @InjectMocks
    private RelationshipService service;

    @Mock
    private DateService dateServiceMock;

    @Mock
    private PersonService personServiceMock;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        service = new RelationshipServiceImpl();
        super.finishSetup();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        verifyNoMoreInteractions(dateServiceMock);
        verifyNoMoreInteractions(personServiceMock);
    }

    /**
     * Test for
     * {@link RelationshipServiceImpl#areSurnamesRelated(String, String)}.
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
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}.
     */
    @Test
    public void testBrotherSisterRelationship() {
        Person p1 = newMale();
        Person p2 = newFemale();
        setSurnames(p1, "Vandenis");
        setSurnames(p2, "Vandenytė");
        setAgeBetween(p1, p2, 0);

        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        setAgeBetween(p1, p2, 15);
        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        setAgeBetween(p1, p2, 40);
        assertFalse(service.getRelationship(p2, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p2, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.SISTER);

        setSurnames(p1, "Kebabinskas", "Vandenis");
        setSurnames(p2, "Vandenytė", "Sausumienė");
        setAgeBetween(p1, p2, 0);
        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        setAgeBetween(p1, p2, 15);
        assertEquals(Relationship.SISTER, service.getRelationship(p1, p2));
        assertEquals(Relationship.BROTHER, service.getRelationship(p2, p1));

        setAgeBetween(p1, p2, 40);
        assertFalse(service.getRelationship(p2, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p2, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p2) == Relationship.SISTER);

        Person p3 = newFemale();
        setSurnames(p3, "Kebabinskyė");
        setAgeBetween(p1, p3, 0);
        assertFalse(service.getRelationship(p3, p2) == Relationship.SISTER);
        assertFalse(service.getRelationship(p3, p2) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p2, p3) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p2, p3) == Relationship.SISTER);

        assertFalse(service.getRelationship(p3, p1) == Relationship.SISTER);
        assertFalse(service.getRelationship(p3, p1) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p3) == Relationship.BROTHER);
        assertFalse(service.getRelationship(p1, p3) == Relationship.SISTER);

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verfyPersonExamination(p3);

        verifyAgeExamination(p1);
        verifyPersonAgeDifferenceExamination(p1, p2);
        verifyPersonAgeDifferenceExamination(p1, p3);

        verifyAgeExamination(p2);
        verifyPersonAgeDifferenceExamination(p2, p3);

        verifyAgeExamination(p3);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}.
     */
    @Test
    public void testWifeHusbandRelationship() {
        Person p1 = newMale();
        Person p2 = newFemale();
        setSurnames(p1, "Butkus", "Vandenis");
        setSurnames(p2, "Lašytė", "Vandenienė");

        setAgeBetween(p1, p2, 5);
        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        setSurnames(p2, "Vandenienė");
        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        setSurnames(p1, "Vandenis");
        assertEquals(Relationship.WIFE, service.getRelationship(p1, p2));
        assertEquals(Relationship.HUSBAND, service.getRelationship(p2, p1));

        setSurnames(p2, "Vandenytė", "Lašienė");
        assertFalse(service.getRelationship(p2, p1) == Relationship.WIFE);
        assertFalse(service.getRelationship(p2, p1) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.WIFE);

        setSurnames(p2, "Vandenytė");
        assertFalse(service.getRelationship(p2, p1) == Relationship.WIFE);
        assertFalse(service.getRelationship(p2, p1) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.HUSBAND);
        assertFalse(service.getRelationship(p1, p2) == Relationship.WIFE);

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verifyPersonAgeDifferenceExamination(p1, p2);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}.
     */
    @Test
    public void testSonDaughterFatherMotherRelationship() {
        Person p1 = newMale();
        Person p2 = newFemale();
        Person p3 = newFemale();
        Person p4 = newMale();
        setSurnames(p1, "Purvas", "Vandenis");
        setAge(p1, 45);
        setSurnames(p2, "Lašytė", "Vandenienė");
        setAge(p2, 45);
        setSurnames(p3, "Vandenytė", "Butkienė");
        setAge(p3, 22);
        setSurnames(p4, "Lašas", "Vandenis");
        setAge(p4, 25);

        setAgeBetween(p1, p2, 0);
        setAgeBetween(p1, p3, 23);
        setAgeBetween(p1, p4, 20);

        setAgeBetween(p2, p3, 23);
        setAgeBetween(p2, p4, 20);

        setAgeBetween(p3, p4, 3);

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.SON, service.getRelationship(p1, p4));
        assertEquals(Relationship.FATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.FATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.SON, service.getRelationship(p2, p4));
        assertEquals(Relationship.MOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.MOTHER, service.getRelationship(p4, p2));

        setSurnames(p1, "Vandenis");
        setSurnames(p2, "Vandenienė");
        setSurnames(p3, "Vandenytė");
        setSurnames(p4, "Vandenis");
        assertEquals(Relationship.DAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.SON, service.getRelationship(p1, p4));
        assertEquals(Relationship.FATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.FATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.DAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.SON, service.getRelationship(p2, p4));
        assertEquals(Relationship.MOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.MOTHER, service.getRelationship(p4, p2));

        setSurnames(p1, "Lašas");
        setSurnames(p2, "Lašienė");
        setSurnames(p3, "Vandenytė", "Lašienė");
        setSurnames(p4, "Lašas", "Vandenis");
        assertFalse(service.getRelationship(p1, p3) == Relationship.DAUGHTER);
        assertFalse(service.getRelationship(p1, p4) == Relationship.SON);
        assertFalse(service.getRelationship(p3, p1) == Relationship.FATHER);
        assertFalse(service.getRelationship(p4, p1) == Relationship.FATHER);

        assertFalse(service.getRelationship(p2, p3) == Relationship.DAUGHTER);
        assertFalse(service.getRelationship(p2, p4) == Relationship.SON);
        assertFalse(service.getRelationship(p3, p2) == Relationship.MOTHER);
        assertFalse(service.getRelationship(p4, p2) == Relationship.FATHER);

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verfyPersonExamination(p3);
        verfyPersonExamination(p4);

        verifyAgeExamination(p1);
        verifyPersonAgeDifferenceExamination(p1, p3);
        verifyPersonAgeDifferenceExamination(p1, p4);

        verifyAgeExamination(p2);
        verifyPersonAgeDifferenceExamination(p2, p3);
        verifyPersonAgeDifferenceExamination(p2, p4);

        verifyAgeExamination(p3);

        verifyAgeExamination(p4);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationship(Person, Person)}.
     */
    @Test
    public void testGrandSonDaughterFatherMotherRelationship() {
        Person p1 = newMale();
        Person p2 = newFemale();
        Person p3 = newFemale();
        Person p4 = newMale();
        setSurnames(p1, "Purvas", "Vandenis");
        setAge(p1, 65);
        setSurnames(p2, "Lašytė", "Vandenienė");
        setAge(p2, 65);
        setSurnames(p3, "Vandenytė", "Butkienė");
        setAge(p3, 22);
        setSurnames(p4, "Lašas", "Vandenis");
        setAge(p4, 25);

        setAgeBetween(p1, p2, 0);
        setAgeBetween(p1, p3, 43);
        setAgeBetween(p1, p4, 41);

        setAgeBetween(p2, p3, 43);
        setAgeBetween(p2, p4, 41);

        setAgeBetween(p3, p4, 3);

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p1, p4));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p2, p4));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p4, p2));

        setSurnames(p1, "Vandenis");
        setSurnames(p2, "Vandenienė");
        setSurnames(p3, "Vandenytė");
        setSurnames(p4, "Vandenis");
        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p1, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p1, p4));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p3, p1));
        assertEquals(Relationship.GRANDFATHER, service.getRelationship(p4, p1));

        assertEquals(Relationship.GRANDAUGHTER, service.getRelationship(p2, p3));
        assertEquals(Relationship.GRANDSON, service.getRelationship(p2, p4));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p3, p2));
        assertEquals(Relationship.GRANDMOTHER, service.getRelationship(p4, p2));

        setSurnames(p1, "Lašas");
        setSurnames(p2, "Lašienė");
        setSurnames(p3, "Vandenytė", "Lašienė");
        setSurnames(p4, "Lašas", "Vandenis");
        assertFalse(service.getRelationship(p1, p3) == Relationship.GRANDAUGHTER);
        assertFalse(service.getRelationship(p1, p4) == Relationship.GRANDSON);
        assertFalse(service.getRelationship(p3, p1) == Relationship.GRANDFATHER);
        assertFalse(service.getRelationship(p4, p1) == Relationship.GRANDFATHER);

        assertFalse(service.getRelationship(p2, p3) == Relationship.GRANDAUGHTER);
        assertFalse(service.getRelationship(p2, p4) == Relationship.GRANDSON);
        assertFalse(service.getRelationship(p3, p2) == Relationship.GRANDMOTHER);
        assertFalse(service.getRelationship(p4, p2) == Relationship.GRANDFATHER);

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verfyPersonExamination(p3);
        verfyPersonExamination(p4);

        verifyAgeExamination(p1);
        verifyPersonAgeDifferenceExamination(p1, p3);
        verifyPersonAgeDifferenceExamination(p1, p4);

        verifyAgeExamination(p2);
        verifyPersonAgeDifferenceExamination(p2, p3);
        verifyPersonAgeDifferenceExamination(p2, p4);

        verifyAgeExamination(p3);

        verifyAgeExamination(p4);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationships(Person, List)}.
     */
    @Test
    public void testGetRelationships1() {
        Person p1 = newMale();
        Person p2 = newFemale();
        Person p3 = newFemale();
        Person p4 = newMale();
        setSurnames(p1, "Purvas", "Vandenis");
        setAge(p1, 65);
        setSurnames(p2, "Lašytė", "Vandenienė");
        setAge(p2, 65);
        setSurnames(p3, "Vandenytė", "Butkienė");
        setAge(p3, 22);
        setSurnames(p4, "Lašas", "Vandenis");
        setAge(p4, 25);

        setAgeBetween(p1, p2, 0);
        setAgeBetween(p1, p3, 43);
        setAgeBetween(p1, p4, 41);

        setAgeBetween(p2, p3, 43);
        setAgeBetween(p2, p4, 41);

        setAgeBetween(p3, p4, 3);

        List<Person> people = new ArrayList<Person>();
        people.add(p2);
        people.add(p3);
        people.add(p4);

        Map<Person, Relationship> expectedResult = new HashMap<Person, Relationship>();
        expectedResult.put(p2, Relationship.WIFE);
        expectedResult.put(p3, Relationship.GRANDAUGHTER);
        expectedResult.put(p4, Relationship.GRANDSON);
        assertEquals(expectedResult, service.getRelationships(p1, people));

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verfyPersonExamination(p3);
        verfyPersonExamination(p4);

        verifyAgeExamination(p1);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p2);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p3);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p4);

        verifyAgeExamination(p2);

        verifyAgeExamination(p3);

        verifyAgeExamination(p4);
    }

    /**
     * Test for {@link RelationshipServiceImpl#getRelationships(Person, List)}.
     */
    @Test
    public void testGetRelationships2() {
        Person p1 = newMale();
        Person p2 = newFemale();
        Person p3 = newFemale();
        Person p4 = newMale();
        Person p5 = newMale();
        setSurnames(p1, "Purvas", "Vandenis");
        setAge(p1, 45);
        setSurnames(p2, "Lašytė", "Vandenienė");
        setAge(p2, 45);
        setSurnames(p3, "Vandenytė", "Butkienė");
        setAge(p3, 22);
        setSurnames(p4, "Lašas", "Vandenis");
        setAge(p4, 25);
        setSurnames(p5, "Kristijonas", "Pašalietis");
        setAge(p4, 35);

        setAgeBetween(p1, p2, 0);
        setAgeBetween(p1, p3, 23);
        setAgeBetween(p1, p4, 20);
        setAgeBetween(p1, p5, 10);

        setAgeBetween(p2, p3, 23);
        setAgeBetween(p2, p4, 20);
        setAgeBetween(p1, p5, 10);

        setAgeBetween(p3, p4, 3);
        setAgeBetween(p3, p5, 13);

        setAgeBetween(p4, p5, 10);

        List<Person> people = new ArrayList<Person>();
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);

        Map<Person, Relationship> expectedResult = new HashMap<Person, Relationship>();
        expectedResult.put(p2, Relationship.WIFE);
        expectedResult.put(p3, Relationship.DAUGHTER);
        expectedResult.put(p4, Relationship.SON);
        assertEquals(expectedResult, service.getRelationships(p1, people));

        verfyPersonExamination(p1);
        verfyPersonExamination(p2);
        verfyPersonExamination(p3);
        verfyPersonExamination(p4);
        verfyPersonExamination(p5);

        verifyAgeExamination(p1);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p2);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p3);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p4);
        verify(dateServiceMock, times(1)).getAgeDifference(p1, p5);

        verifyAgeExamination(p2);

        verifyAgeExamination(p3);

        verifyAgeExamination(p4);
    }

    /*
     * ==================================================================
     * HELPERS
     * ==================================================================
     */

    // assertion helpers

    private void verifyAgeExamination(Person person) {
        verify(dateServiceMock, atLeastOnce()).getAge(person.getDateOfBirth());
    }

    private void verifyPersonAgeDifferenceExamination(Person p1, Person p2) {
        verify(dateServiceMock, atLeastOnce()).getAgeDifference(p1, p2);
        verify(dateServiceMock, atLeastOnce()).getAgeDifference(p2, p1);
    }

    private void verfyPersonExamination(Person p1) {
        verify(personServiceMock, atLeastOnce()).isPersonValid(p1);
        verify(personServiceMock, atLeastOnce()).getFirstSurname(p1);
        verify(personServiceMock, atLeastOnce()).getLastSurname(p1);
    }

    // mock helpers

    private long nextId = 1;

    private Person newPerson() {
        Person person = new Person();
        person.setId(nextId++);
        when(personServiceMock.isPersonValid(person)).thenReturn(true);
        return person;
    }

    private Person newMale() {
        Person person = newPerson();
        person.setGender(Gender.MALE);
        return person;
    }

    private Person newFemale() {
        Person person = newPerson();
        person.setGender(Gender.FEMALE);
        return person;
    }

    private void setSurnames(Person p1, String s1) {
        setSurnames(p1, s1, s1);
    }

    private void setSurnames(Person p1, String s1, String s2) {
        when(personServiceMock.getFirstSurname(p1)).thenReturn(s1);
        when(personServiceMock.getLastSurname(p1)).thenReturn(s2);
    }

    private void setAge(Person p1, int age) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -age);
        DateUtils.resetTime(c);
        p1.setDateOfBirth(c.getTime());
        when(dateServiceMock.getAge(p1.getDateOfBirth())).thenReturn(age);
    }

    private void setAgeBetween(Person p1, Person p2, int age) {
        when(dateServiceMock.getAgeDifference(p1, p2)).thenReturn(age);
        when(dateServiceMock.getAgeDifference(p2, p1)).thenReturn(age);
    }

}
