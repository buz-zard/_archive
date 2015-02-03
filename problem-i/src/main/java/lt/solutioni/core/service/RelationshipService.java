package lt.solutioni.core.service;

import java.util.List;
import java.util.Map;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;

/**
 * 
 * @author buzzard
 *
 */
public interface RelationshipService {

    boolean areSurnamesRelated(String surname1, String surname2);

    Relationship getRelationship(Person basePerson, Person person);

    Map<Person, Relationship> getRelationships(Person basePerson,
            List<Person> people);

}
