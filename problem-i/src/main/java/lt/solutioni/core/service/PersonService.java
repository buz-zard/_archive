package lt.solutioni.core.service;

import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;

/**
 * Service interface for {@link Person} manipulation.
 * 
 * @author buzzard
 *
 */
public interface PersonService {

    /**
     * Check if person is valid.
     */
    boolean isPersonValid(Person person);

    /**
     * Check if person's name is valid.
     */
    boolean isNameValid(Person person);

    /**
     * Check if person's surname is valid.
     */
    boolean isSurnameValid(Person person);

    /**
     * Check if person's age is valid.
     */
    boolean isAgeValid(Person person);

    /**
     * Check if person's gender is valid.
     */
    boolean isGenderValid(Person person);

    /**
     * Get person's gender.
     */
    Gender getGender(Person person);

    /**
     * Get person's first surname.
     */
    String getFirstSurname(Person person);

    /**
     * Get person's last surname.
     */
    String getLastSurname(Person person);

    /**
     * Create person instance from name, surname and dateOfBirth string values.
     */
    Person createPerson(String name, String surname, String dateOfBirth);

}
