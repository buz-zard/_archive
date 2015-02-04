package lt.solutioni.persistence.service;

import java.util.List;

import lt.solutioni.core.domain.Person;
import lt.solutioni.persistence.domain.PersonDAO;

/**
 * {@link PersonRepository} service to interact with JPA repository using
 * {@link Person} objects.
 * 
 * @author buzzard
 *
 */
public interface PersonRepositoryService {

    /**
     * Retrieve all {@link PersonDAO} records as list of {@link Person}
     * instances.
     */
    public List<Person> findAll();

    /**
     * Find {@link PersonDAO} record by id and retrieve as {@link Person}
     * instance.
     */
    public Person findOne(long id);

    /**
     * Save {@link Person} instance as {@link PersonDAO} record.
     */
    public boolean save(Person person);

    /**
     * Delete {@link PersonDTO} record by id.
     */
    public boolean delete(long id);

    /**
     * Update {@link PersonDTO} record using {@link Person} instance.
     */
    public boolean update(Person person);

}
