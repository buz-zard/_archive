package lt.solutioni.core.service.impl;

import java.util.Calendar;
import java.util.Date;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.core.utils.DateUtils;

import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author buzzard
 *
 */
public class DateServiceImpl implements DateService {

    @Autowired
    private PersonService personService;

    @Override
    public int getAge(Date fromDate) {
        Calendar now = Calendar.getInstance();
        DateUtils.resetTime(now);
        return getAge(DateUtils.resetTime(fromDate), now.getTime());
    }

    @Override
    public int getAge(Date fromDate, Date toDate) {
        Period period = new Period(DateUtils.resetTime(fromDate).getTime(), DateUtils.resetTime(
                toDate).getTime());
        if (period.getDays() < 0) {
            return -1;
        } else {
            return period.getYears();
        }
    }

    @Override
    public Integer getAgeDifference(Person person1, Person person2) {
        if (personService.isAgeValid(person1) && personService.isAgeValid(person2)) {
            Period period = new Period(person1.getDateOfBirth().getTime(), person2.getDateOfBirth()
                    .getTime());
            int diff = period.getYears();
            return diff < 0 ? -diff : diff;
        }
        return null;
    }

}
