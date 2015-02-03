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
            String s1 = surname1.toLowerCase();
            String s2 = surname2.toLowerCase();
            if (s1.equals(s2)) {
                return true;
            }
            String surnameRoot = StringUtils.commonPrefix(s1, s2);
            if (surnameRoot != null) {
                if (surnameRoot.endsWith("i")) {
                    surnameRoot = surnameRoot.substring(0,
                            surnameRoot.length() - 1);
                }
                String regex = surnameRoot + "(" + menSurnameEndingsRegex + "|"
                        + marriedWomenSurnameEndingsRegex + "|"
                        + womenSurnameEndingsRegex + ")";
                return s1.matches(regex) && s2.matches(regex);
            }
        }
        return false;
    }

    /**
     * Determine relationship type: what person is to basePerson.
     */
    @Override
    public Relationship getRelationship(Person basePerson, Person person) {
        if (personService.isPersonValid(basePerson)
                && personService.isPersonValid(person)) {
            Gender baseGender = personService.getGender(basePerson);
            String baseFirstSurname = personService.getFirstSurname(basePerson);
            String baseLastSurname = personService.getLastSurname(basePerson);
            Gender otherGender = personService.getGender(person);
            String otherFirstSurname = personService.getFirstSurname(person);
            String otherLastSurname = personService.getLastSurname(person);
            int ageDiff = dateService.getAgeDifference(basePerson, person);
            if (ageDiff >= 0 && ageDiff <= 15) {
                if (areSurnamesRelated(baseFirstSurname, otherFirstSurname)) {
                    if (otherGender == Gender.MALE
                            && StringUtils.endsWithRegex(baseFirstSurname,
                                    womenSurnameEndingsRegex)) {
                        return Relationship.BROTHER;
                    } else if (StringUtils.endsWithRegex(otherFirstSurname,
                            womenSurnameEndingsRegex)) {
                        return Relationship.SISTER;
                    }
                }
                if (areSurnamesRelated(baseLastSurname, otherLastSurname)) {
                    if (baseGender == Gender.MALE
                            && otherGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(otherLastSurname,
                                    marriedWomenSurnameEndingsRegex)) {
                        return Relationship.WIFE;
                    } else if (otherGender == Gender.MALE
                            && baseGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(baseLastSurname,
                                    marriedWomenSurnameEndingsRegex)) {
                        return Relationship.HUSBAND;
                    }
                }
            } else if (ageDiff >= 16) {
                if (dateService.getAge(person.getDateOfBirth()) < dateService
                        .getAge(basePerson.getDateOfBirth())) {
                    if (otherGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(otherFirstSurname,
                                    womenSurnameEndingsRegex)
                            && areSurnamesRelated(otherFirstSurname,
                                    baseLastSurname)) {
                        return ageDiff >= 41 ? Relationship.GRANDAUGHTER
                                : Relationship.DAUGHTER;
                    } else if (otherGender == Gender.MALE
                            && areSurnamesRelated(otherLastSurname,
                                    baseLastSurname)) {
                        return ageDiff >= 41 ? Relationship.GRANDSON
                                : Relationship.SON;
                    }
                } else {
                    if (otherGender == Gender.FEMALE
                            && StringUtils.endsWithRegex(otherLastSurname,
                                    marriedWomenSurnameEndingsRegex)) {
                        if (baseGender == Gender.MALE
                                && areSurnamesRelated(baseLastSurname,
                                        otherLastSurname)) {
                            return ageDiff >= 41 ? Relationship.GRANDMOTHER
                                    : Relationship.MOTHER;

                        } else if (baseGender == Gender.FEMALE
                                && areSurnamesRelated(baseFirstSurname,
                                        otherLastSurname)) {
                            return ageDiff >= 41 ? Relationship.GRANDMOTHER
                                    : Relationship.MOTHER;
                        }
                    } else if (otherGender == Gender.MALE) {
                        if (baseGender == Gender.MALE
                                && areSurnamesRelated(baseLastSurname,
                                        otherLastSurname)) {
                            return ageDiff >= 41 ? Relationship.GRANDFATHER
                                    : Relationship.FATHER;
                        } else if (baseGender == Gender.FEMALE
                                && areSurnamesRelated(baseFirstSurname,
                                        otherLastSurname)) {
                            return ageDiff >= 41 ? Relationship.GRANDFATHER
                                    : Relationship.FATHER;
                        }
                    }
                }
            }
        }
        return Relationship.NONE;
    }

    /**
     * Determine given people list relationships to basePerson.
     */
    @Override
    public Map<Person, Relationship> getRelationships(Person basePerson,
            List<Person> people) {
        Map<Person, Relationship> items = new HashMap<>();
        for (Person person : people) {
            items.put(person, getRelationship(basePerson, person));
        }
        return items;
    }

}
