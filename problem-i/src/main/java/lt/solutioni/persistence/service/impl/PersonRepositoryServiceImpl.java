package lt.solutioni.persistence.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PersonRepository;
import lt.solutioni.persistence.service.PersonRepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * 
 * @author buzzard
 *
 */
public class PersonRepositoryServiceImpl implements PersonRepositoryService {

    @Autowired
    private PersonService service;

    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        for (PersonDAO person : repository.findAll()) {
            people.add(person.toPerson());
        }
        return people;
    }

    @Override
    public Person findOne(long id) {
        try {
            return repository.findOne(id).toPerson();
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    @Override
    public boolean save(Person person) {
        if (person != null) {
            person.setGender(service.getGender(person));
            if (service.isPersonValid(person)) {
                repository.save(PersonDAO.fromPerson(person));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        try {
            repository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Person person) {
        if (person != null && findOne(person.getId()) != null) {
            person.setGender(service.getGender(person));
            if (service.isPersonValid(person)) {
                repository.save(PersonDAO.fromPerson(person));
                return true;
            }
        }
        return false;
    }

}
