package lt.solutioni.core.service;

import java.util.Date;

import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.core.domain.Person;

/**
 * Service interface for {@link Date} related functions.
 * 
 * @author buzzard
 *
 */
public interface DateService {

    /**
     * Parse date from string object in {@link CoreConfiguration#DATE_FORMAT}
     * format.
     */
    Date getDate(String date);

    /**
     * Get time difference in years from given date to now. Returns -1 if
     * fromDate is in the future.
     */
    int getAge(Date fromDate);

    /**
     * Get time difference in years from given dates. Returns -1 if fromDate
     * higher than toDate.
     */
    int getAge(Date fromDate, Date toDate);

    /**
     * Get age difference in years between two people.
     */
    Integer getAgeDifference(Person person1, Person person2);

}
