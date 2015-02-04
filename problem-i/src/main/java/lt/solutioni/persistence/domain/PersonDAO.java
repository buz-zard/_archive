package lt.solutioni.persistence.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;

/**
 * Persistence representation of {@link lt.solutioni.core.domain.Person}
 * 
 * @author buzzard
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String surname;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    /**
     * Convert to {@link Person} instance.
     */
    public Person toPerson() {
        return new Person(id, name, surname, dateOfBirth, gender);
    }

    public static PersonDAO fromPerson(Person person) {
        return new PersonDAO(person.getId(), person.getName(),
                person.getSurname(), person.getDateOfBirth(),
                person.getGender());
    }

}
