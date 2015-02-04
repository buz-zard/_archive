package lt.solutioni.core.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.solutioni.core.utils.DateDeserializer;
import lt.solutioni.core.utils.DateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * @author buzzard
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private long id;

    private String name;
    private String surname;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dateOfBirth;
    private Gender gender;

    public Person(String name, String surname, Date dateOfBirth) {
        this(name, surname);
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
