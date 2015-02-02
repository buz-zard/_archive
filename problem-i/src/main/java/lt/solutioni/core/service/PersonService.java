package lt.solutioni.core.service;

import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;

/**
 * 
 * @author buzzard
 *
 */
public interface PersonService {

    boolean isPersonValid(Person person);

    boolean isNameValid(Person person);

    boolean isSurnameValid(Person person);

    boolean isAgeValid(Person person);

    boolean isGenderValid(Person person);

    Gender getGender(Person person);

    String getFirstSurname(Person person);

    String getLastSurname(Person person);

}
