package lt.solutioni.core.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author buzzard
 *
 */
@Data
@AllArgsConstructor
public class Person {

    private long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
