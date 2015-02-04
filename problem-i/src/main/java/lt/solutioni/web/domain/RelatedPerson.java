package lt.solutioni.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;

/**
 * POJO object to help represent {@link Person} and {@link Relationship} in
 * JSON.
 * 
 * @author buzzard
 *
 */
@Data
@AllArgsConstructor
public class RelatedPerson {

    private Relationship relationship;

    private Person person;

}
