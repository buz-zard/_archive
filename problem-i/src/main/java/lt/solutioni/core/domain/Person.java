package lt.solutioni.core.domain;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @author buzzard
 *
 */
@Data
public class Person {

    private String name;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
