package lt.solutioni.core.service;

import java.util.List;
import java.util.Map;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;

/**
 * Service interface for {@link Person} and {@link Relationship} manipulation.
 * 
 * @author buzzard
 *
 */
public interface RelationshipService {

    /**
     * Determine if given string surnames are related by examining their root.
     */
    boolean areSurnamesRelated(String surname1, String surname2);

    /**
     * Determine relationship type: what person is to basePerson.
     */
    Relationship getRelationship(Person basePerson, Person person);

    /**
     * Determine given people list relationships to basePerson.
     */
    Map<Person, Relationship> getRelationships(Person basePerson,
            List<Person> people);

}
