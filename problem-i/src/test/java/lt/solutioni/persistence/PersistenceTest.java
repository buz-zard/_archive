package lt.solutioni.persistence;

import lt.solutioni.core.CoreTest;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringApplicationConfiguration(classes = PersistenceConfiguration.class)
public class PersistenceTest extends CoreTest {

}
