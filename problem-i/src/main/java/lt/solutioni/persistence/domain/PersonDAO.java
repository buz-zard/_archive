package lt.solutioni.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
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
import lt.solutioni.core.utils.DateDeserializer;
import lt.solutioni.core.utils.DateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Persistence object representation of {@link Person}.
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    /**
     * Convert to {@link Person} instance.
     */
    public Person toPerson() {
        return new Person(id, name, surname, dateOfBirth, gender);
    }

    /**
     * Create object from {@link Person} instance.
     */
    public static PersonDAO fromPerson(Person person) {
        return new PersonDAO(person.getId(), person.getName(), person.getSurname(),
                person.getDateOfBirth(), person.getGender());
    }

}
