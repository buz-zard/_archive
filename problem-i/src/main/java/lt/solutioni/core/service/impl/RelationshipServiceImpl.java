package lt.solutioni.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lt.solutioni.core.domain.Gender;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.service.RelationshipService;
import lt.solutioni.core.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author buzzard
 *
 */
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    @Setter
    private DateService dateService;

    @Autowired
    @Setter
    private PersonService personService;

    private String menSurnameEndingsRegex = "(is|as|[i]{0,1}us)";
    private String marriedWomenSurnameEndingsRegex = "(ienė)";
    private String womenSurnameEndingsRegex = "(ytė|aitė|[i]{0,1}ūtė)";

    /**
     * Determine if given string surnames are related by examining their root.
     */
    @Override
    public boolean areSurnamesRelated(String surname1, String surname2) {
        if (surname1 != null && surname1.length() > 0 && surname2 != null
                && surname2.length() > 0) {
            surname1 = surname1.toLowerCase();
            surname2 = surname2.toLowerCase();
            if (surname1.equals(surname2)) {
                return true;
            }
            String surnameRoot = StringUtils.commonPrefix(surname1, surname2);
            if (surnameRoot != null) {
                if (surnameRoot.endsWith("i")) {
                    surnameRoot = surnameRoot.substring(0,
                            surnameRoot.length() - 1);
                }
                String regex = surnameRoot + "(" + menSurnameEndingsRegex + "|"
                        + marriedWomenSurnameEndingsRegex + "|"
                        + womenSurnameEndingsRegex + ")";
                System.out.println(regex);
                return surname1.matches(regex) && surname2.matches(regex);
            }
        }
        return false;
    }

    @Override
    public Relationship getRelationship(Person fromPerson, Person toPerson) {
        if (personService.isPersonValid(fromPerson)
                && personService.isPersonValid(toPerson)) {
            Gender fromGender = personService.getGender(fromPerson);
            String fromFirstSurname = personService.getFirstSurname(fromPerson);
            String fromLastSurname = personService.getLastSurname(fromPerson);

            Gender toGender = personService.getGender(fromPerson);
            String toFirstSurname = personService.getFirstSurname(toPerson);
            String toLastSurname = personService.getLastSurname(toPerson);

            int ageDiff = dateService.getAgeDifference(fromPerson, toPerson);
            if (ageDiff >= 0 && ageDiff <= 15) {
                if (areSurnamesRelated(fromFirstSurname, toFirstSurname)) {
                    if (toGender == Gender.MALE) {
                        return Relationship.BROTHER;
                    } else if (StringUtils.endsWithRegex(toFirstSurname,
                            womenSurnameEndingsRegex)) {
                        return Relationship.SISTER;
                    }
                }
                if (areSurnamesRelated(fromLastSurname, toLastSurname)) {
                    if (fromGender == Gender.MALE
                            && toGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(toLastSurname,
                                    marriedWomenSurnameEndingsRegex)) {
                        return Relationship.WIFE;
                    } else if (toGender == Gender.MALE
                            && fromGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(fromLastSurname,
                                    marriedWomenSurnameEndingsRegex)) {
                        return Relationship.HUSBAND;
                    }
                }
            } else if (ageDiff >= 16 && ageDiff <= 40) {

            } else if (ageDiff >= 41) {

            }
        }
        return Relationship.NONE;
    }

    @Override
    public Map<Person, Relationship> getRelationships(Person fromPerson,
            List<Person> toPeople) {
        Map<Person, Relationship> items = new HashMap<>();
        for (Person person : toPeople) {
            items.put(person, getRelationship(fromPerson, person));
        }
        return items;
    }

}
