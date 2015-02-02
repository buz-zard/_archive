package lt.solutioni.core;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 
 * @author buzzard
 *
 */
public abstract class CoreTestCase extends TestCase {

    protected AnnotationConfigApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(
                CoreConfiguration.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    /**
     * Get bean from application context.
     */
    protected <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
