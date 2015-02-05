package lt.solutioni.core;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base abstract test class.
 * 
 * @author buzzard
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public abstract class BaseTest extends TestCase {

    protected void finishSetup() {
        MockitoAnnotations.initMocks(this);
    }

}
