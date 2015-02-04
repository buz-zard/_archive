package lt.solutioni.persistence.repository;

import lt.solutioni.persistence.domain.PersonDAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author buzzard
 *
 */
@Repository
public interface PeopleRepository extends JpaRepository<PersonDAO, Long> {

}
