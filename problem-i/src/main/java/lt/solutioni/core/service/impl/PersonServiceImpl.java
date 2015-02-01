package lt.solutioni.core.service.impl;

import lombok.Setter;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author buzzard
 *
 */
public class PersonServiceImpl implements PersonService {

    private String ltLetterRegex = "[a-ząčęėįšųūž]";
    private String nameRegex = "^(" + ltLetterRegex + "+|" + ltLetterRegex
            + "+[ ]" + ltLetterRegex + "+)$";
    private String surnameRegex = "^(" + ltLetterRegex + "+[sė]|"
            + ltLetterRegex + "+[sė][-]" + ltLetterRegex + "+[sė])$";

    @Autowired
    @Setter
    private DateService dateService;

    /**
     * Validate person.
     */
    @Override
    public boolean isPersonValid(Person person) {
        return isNameValid(person) && isSurnameValid(person)
                && isGenderValid(person) && isAgeValid(person);
    }

    /**
     * Validate person's name.
     */
    @Override
    public boolean isNameValid(Person person) {
        if (isNameOrSurnameLengthValid(person.getName())
                && person.getName().toLowerCase().matches(nameRegex)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate person's name.
     */
    @Override
    public boolean isSurnameValid(Person person) {
        if (isNameOrSurnameLengthValid(person.getSurname())
                && person.getSurname().toLowerCase().matches(surnameRegex)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate person's age.
     */
    @Override
    public boolean isAgeValid(Person person) {
        if (person.getDateOfBirth() != null
                && dateService.getAge(person.getDateOfBirth()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameOrSurnameLengthValid(String value) {
        if (value != null && value.length() >= 2 && value.length() <= 50) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate person's gender.
     */
    @Override
    public boolean isGenderValid(Person person) {
        return person.getGender() != null;
    }

    @Override
    public void setGender(Person person) {
        person.setGender(null);
        if (isSurnameValid(person)) {
            String surname = person.getSurname().toLowerCase();
            if (surname.endsWith("s")) {
                person.setGender(Gender.MALE);
            } else if (surname.endsWith("ė")) {
                person.setGender(Gender.FEMALE);
            }
        }
    }

}
