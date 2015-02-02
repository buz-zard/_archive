package lt.solutioni.core.service;

import java.util.Date;

import lt.solutioni.core.domain.Person;

/**
 * 
 * @author buzzard
 *
 */
public interface DateService {

    Date getDate(String date);

    int getAge(Date fromDate);

    int getAge(Date fromDate, Date toDate);

    Integer getAgeDifference(Person person1, Person person2);

}
