package lt.solutioni.web;

import junit.framework.TestCase;
import lt.solutioni.Application;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

/**
 * Base class for integration test classes.
 * 
 * @author buzzard
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8888")
public abstract class ITest extends TestCase {

    @Override
    @Before
    public void setUp() {
        RestAssured.port = 8888;
    }

}
