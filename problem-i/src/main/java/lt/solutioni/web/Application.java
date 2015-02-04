package lt.solutioni.web;

import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.persistence.PersistenceConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author buzzard
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "lt.solutioni.persistence.repository" })
@ComponentScan("lt.solutioni")
@Import({ CoreConfiguration.class, PersistenceConfiguration.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
