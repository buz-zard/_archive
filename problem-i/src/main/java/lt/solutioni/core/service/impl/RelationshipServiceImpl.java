package lt.solutioni.core.service.impl;

import static lt.solutioni.core.domain.Gender.isFemale;
import static lt.solutioni.core.domain.Gender.isMale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private DateService dateService;

    @Autowired
    private PersonService personService;

    private String menSurnameEndings = "(is|as|[i]{0,1}us)";
    private String marriedWomenSurnameEndings = "(ienė)";
    private String unmarriedWomenSurnameEndings = "(ytė|aitė|[i]{0,1}ūtė)";

    @Override
    public boolean areSurnamesRelated(String surname1, String surname2) {
        if (surname1 != null && surname1.length() > 0 && surname2 != null && surname2.length() > 0) {
            String s1 = surname1.toLowerCase();
            String s2 = surname2.toLowerCase();
            if (s1.equals(s2)) {
                return true;
            }
            String surnameRoot = StringUtils.commonPrefix(s1, s2);
            if (surnameRoot != null) {
                if (surnameRoot.endsWith("i")) {
                    surnameRoot = surnameRoot.substring(0, surnameRoot.length() - 1);
                }
                String regex = surnameRoot + "(" + menSurnameEndings + "|"
                        + marriedWomenSurnameEndings + "|" + unmarriedWomenSurnameEndings + ")";
                return s1.matches(regex) && s2.matches(regex);
            }
        }
        return false;
    }

    @Override
    public Relationship getRelationship(Person basePerson, Person otherPerson) {
        if (personService.isPersonValid(basePerson) && personService.isPersonValid(otherPerson)) {
            String base1stSurname = personService.getFirstSurname(basePerson);
            String base2ndSurname = personService.getLastSurname(basePerson);
            String other1stSurname = personService.getFirstSurname(otherPerson);
            String other2ndSurname = personService.getLastSurname(otherPerson);
            int ageDifference = dateService.getAgeDifference(basePerson, otherPerson);
            if (ageDifference >= 0 && ageDifference <= 15) {
                if (isMale(basePerson)) {
                    if (isMale(otherPerson)) {
                        if (areSurnamesRelated(base2ndSurname, other2ndSurname)) {
                            return Relationship.BROTHER;
                        }
                    } else {
                        if (areSurnamesRelated(base2ndSurname, other1stSurname)
                                && isWomansFirstSurnameUnamrried(otherPerson)) {
                            return Relationship.SISTER;
                        }
                    }
                } else {
                    if (isMale(otherPerson)) {
                        if (areSurnamesRelated(base1stSurname, other2ndSurname)
                                && isWomansFirstSurnameUnamrried(basePerson)) {
                            return Relationship.BROTHER;
                        }
                    } else {
                        if (areSurnamesRelated(base1stSurname, other1stSurname)
                                && isWomansFirstSurnameUnamrried(basePerson)
                                && isWomansFirstSurnameUnamrried(otherPerson)) {
                            return Relationship.SISTER;
                        }
                    }
                }
                if (areSurnamesRelated(base2ndSurname, other2ndSurname)) {
                    if (isMale(basePerson) && isFemale(otherPerson) && isWomanMarried(otherPerson)) {
                        return Relationship.WIFE;
                    } else if (isMale(otherPerson) && isFemale(basePerson)
                            && isWomanMarried(basePerson)) {
                        return Relationship.HUSBAND;
                    }
                }
            } else if (ageDifference >= 16) {
                if (dateService.getAge(otherPerson.getDateOfBirth()) < dateService
                        .getAge(basePerson.getDateOfBirth())) {
                    if (isFemale(otherPerson) && areSurnamesRelated(other1stSurname, base2ndSurname)
                            && isWomansFirstSurnameUnamrried(otherPerson)) {
                        return ageDifference >= 41 ? Relationship.GRANDAUGHTER : Relationship.DAUGHTER;
                    } else if (isMale(otherPerson)
                            && areSurnamesRelated(other2ndSurname, base2ndSurname)) {
                        return ageDifference >= 41 ? Relationship.GRANDSON : Relationship.SON;
                    }
                } else {
                    if (isFemale(otherPerson) && isWomanMarried(otherPerson)) {
                        if (isMale(basePerson) && areSurnamesRelated(base2ndSurname, other2ndSurname)) {
                            return ageDifference >= 41 ? Relationship.GRANDMOTHER : Relationship.MOTHER;

                        } else if (isFemale(basePerson)
                                && areSurnamesRelated(base1stSurname, other2ndSurname)) {
                            return ageDifference >= 41 ? Relationship.GRANDMOTHER : Relationship.MOTHER;
                        }
                    } else if (isMale(otherPerson)) {
                        if (isMale(basePerson) && areSurnamesRelated(base2ndSurname, other2ndSurname)) {
                            return ageDifference >= 41 ? Relationship.GRANDFATHER : Relationship.FATHER;
                        } else if (isFemale(basePerson)
                                && areSurnamesRelated(base1stSurname, other2ndSurname)) {
                            return ageDifference >= 41 ? Relationship.GRANDFATHER : Relationship.FATHER;
                        }
                    }
                }
            }
        }
        return Relationship.NONE;
    }

    @Override
    public Map<Person, Relationship> getRelationships(Person basePerson, List<Person> people) {
        Map<Person, Relationship> items = new HashMap<Person, Relationship>();
        for (Person person : people) {
            Relationship relationship = getRelationship(basePerson, person);
            if (relationship != Relationship.NONE) {
                items.put(person, relationship);
            }
        }
        return items;
    }

    private boolean isWomanMarried(Person person) {
        if (person.getGender() == Gender.FEMALE) {
            return StringUtils.endsWithRegex(personService.getLastSurname(person),
                    marriedWomenSurnameEndings);
        }
        throw new IllegalArgumentException(
                "Method isWomanMarried person parameter doesn't have WOMAN gender.");
    }

    private boolean isWomansFirstSurnameUnamrried(Person person) {
        if (person.getGender() == Gender.FEMALE) {
            return StringUtils.endsWithRegex(personService.getFirstSurname(person),
                    unmarriedWomenSurnameEndings);
        }
        throw new IllegalArgumentException(
                "Method isWomansFNUnamrried person parameter doesn't have WOMAN gender.");
    }

}
