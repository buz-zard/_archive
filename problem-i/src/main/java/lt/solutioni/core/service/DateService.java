package lt.solutioni.core.service;

import java.util.Date;

/**
 * 
 * @author buzzard
 *
 */
public interface DateService {

    Date getDate(String date);

    int getAge(Date fromDate);

    int getAge(Date fromDate, Date toDate);

}
