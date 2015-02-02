package lt.solutioni.core.service.impl;

import lt.solutioni.core.CoreTestCase;
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

}
