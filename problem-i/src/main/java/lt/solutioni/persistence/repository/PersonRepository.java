package lt.solutioni.persistence.repository;

import lt.solutioni.core.domain.Person;
import lt.solutioni.persistence.domain.PersonDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository interface to handle {@link Person} business objects in
 * persistence layer.
 * 
 * @author buzzard
 *
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonDAO, Long> {

}
