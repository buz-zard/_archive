package lt.solutioni.core.service.impl;

import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.utils.StringUtils;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author buzzard
 *
 */
public class PersonServiceImpl implements PersonService {

    private String nameRegex = "^[a-ž]+(|[ ][a-ž]+)$";
    private String surnameRegex = "^[a-ž]+[sė](|[-][a-ž]+[sė])$";

    @Autowired
    private DateService dateService;

    @Override
    public boolean isPersonValid(Person person) {
        return isNameValid(person) && isSurnameValid(person) && isGenderValid(person)
                && isAgeValid(person);
    }

    @Override
    public boolean isNameValid(Person person) {
        return isNameOrSurnameLengthValid(person.getName())
                && person.getName().toLowerCase().matches(nameRegex);
    }

    @Override
    public boolean isSurnameValid(Person person) {
        return isNameOrSurnameLengthValid(person.getSurname())
                && person.getSurname().toLowerCase().matches(surnameRegex);
    }

    private boolean isNameOrSurnameLengthValid(String value) {
        return StringUtils.lenght(value) >= 2 && StringUtils.lenght(value) <= 50;
    }

    @Override
    public boolean isAgeValid(Person person) {
        return person.getDateOfBirth() != null && dateService.getAge(person.getDateOfBirth()) >= 0;
    }

    @Override
    public boolean isGenderValid(Person person) {
        return person.getGender() != null;
    }

    @Override
    public Gender getGender(Person person) {
        if (isSurnameValid(person)) {
            String surname = person.getSurname().toLowerCase();
            if (surname.endsWith("s")) {
                return Gender.MALE;
            } else if (surname.endsWith("ė")) {
                return Gender.FEMALE;
            }
        }
        return null;
    }

    @Override
    public String getFirstSurname(Person person) {
        if (isSurnameValid(person)) {
            String surname = person.getSurname().toLowerCase();
            if (surname.indexOf("-") != -1) {
                surname = surname.substring(0, surname.indexOf("-"));
            }
            return WordUtils.capitalize(surname);
        }
        return null;
    }

    @Override
    public String getLastSurname(Person person) {
        if (isSurnameValid(person)) {
            String surname = person.getSurname().toLowerCase();
            if (surname.indexOf("-") != -1) {
                surname = surname.substring(surname.indexOf("-") + 1);
            }
            return WordUtils.capitalize(surname);
        }
        return null;
    }

    @Override
    public Person createPerson(String name, String surname, String dateOfBirth) {
        Person person = new Person(name, surname, dateOfBirth);
        person.setGender(getGender(person));
        return isPersonValid(person) ? person : null;
    }

}
