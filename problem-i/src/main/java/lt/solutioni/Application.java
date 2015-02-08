package lt.solutioni;

import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.core.domain.Person;
import lt.solutioni.persistence.PersistenceConfiguration;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.WebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring-boot application.
 * 
 * @author buzzard
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({ CoreConfiguration.class, PersistenceConfiguration.class, WebConfiguration.class })
public class Application {

    /**
     * Application main method.
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        handleArgs(context, args);
    }

    private static void handleArgs(ConfigurableApplicationContext context, String[] args) {
        for (String arg : args) {
            if (arg.equals("test")) {
                PersonRepositoryService service = context.getBean(PersonRepositoryService.class);
                service.save(new Person("Karolis", "Šarapnickis", "1990-07-13"));
                service.save(new Person("Jonas", "Vandenis", "1970-07-13"));
                service.save(new Person("Toma", "Lašytė-Vandenienė", "1970-07-13"));
                service.save(new Person("Kamilė", "Vandenytė-Butkienė", "1993-07-13"));
                service.save(new Person("Tomas", "Lašas-Vandenis", "1990-07-13"));
            }
        }
    }

}
