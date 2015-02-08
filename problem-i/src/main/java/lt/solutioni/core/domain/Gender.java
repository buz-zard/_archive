package lt.solutioni.core.domain;

/**
 * {@link Person} gender type.
 * 
 * @author buzzard
 *
 */
public enum Gender {

    MALE, FEMALE;

    public static boolean isMale(Person person) {
        return person.getGender() == MALE;
    }

    public static boolean isFemale(Person person) {
        return person.getGender() == FEMALE;
    }

}
