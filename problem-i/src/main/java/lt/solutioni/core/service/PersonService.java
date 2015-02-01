package lt.solutioni.core.service;

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

    void setGender(Person person);

}
