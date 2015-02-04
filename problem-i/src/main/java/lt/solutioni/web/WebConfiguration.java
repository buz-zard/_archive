package lt.solutioni.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web layer related configuration.
 * 
 * @author buzzard
 *
 */
@Configuration
@ComponentScan("lt.solutioni.web")
public class WebConfiguration {

    public static final String PERSON_URL = "/person";

}
