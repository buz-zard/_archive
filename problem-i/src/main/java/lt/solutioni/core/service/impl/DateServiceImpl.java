package lt.solutioni.core.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Setter;
import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;

import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author buzzard
 *
 */
public class DateServiceImpl implements DateService {

	@Autowired
	@Setter
	private PersonService personService;

	private DateFormat dateFormat;

	private DateFormat getDateFormat() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(CoreConfiguration.DATE_FORMAT);
			dateFormat.setLenient(false);
		}
		return dateFormat;
	}

	/**
	 * Parse date from string object.
	 * 
	 * @param - date in "yyyy-MM-dd" format.
	 */
	@Override
	public Date getDate(String date) {
		try {
			return getDateFormat().parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Get time difference in years from given date to now. Returns -1 if
	 * fromDate is in the future.
	 */
	@Override
	public int getAge(Date fromDate) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return getAge(fromDate, now.getTime());
	}

	/**
	 * Get time difference in years from given dates. Returns -1 if fromDate
	 * higher than toDate.
	 */
	@Override
	public int getAge(Date fromDate, Date toDate) {
		Period period = new Period(fromDate.getTime(), toDate.getTime());
		if (period.getDays() < 0) {
			return -1;
		} else {
			return period.getYears();
		}
	}

	/**
	 * Get age difference in years between two people.
	 */
	@Override
	public Integer getAgeDifference(Person person1, Person person2) {
		if (personService.isAgeValid(person1)
				&& personService.isAgeValid(person2)) {
			Period period = new Period(person1.getDateOfBirth().getTime(),
					person2.getDateOfBirth().getTime());
			int diff = period.getYears();
			return diff < 0 ? -diff : diff;
		}
		return null;
	}

}
