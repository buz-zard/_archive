package lt.solutioni.core.service;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;

/**
 * 
 * @author buzzard
 *
 */
public interface RelationshipService {

    Relationship getRelationship(Person fromPerson, Person toPerson);

}
