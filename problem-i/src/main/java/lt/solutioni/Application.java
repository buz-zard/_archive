package lt.solutioni;

import lt.solutioni.core.CoreConfiguration;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.persistence.PersistenceConfiguration;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PersonRepository;
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
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        // TODO: remove mock data.
        PersonRepository repository = ctx.getBean(PersonRepository.class);
        PersonService personService = ctx.getBean(PersonService.class);
        DateService dateService = ctx.getBean(DateService.class);
        savePerson(repository, personService, dateService, "Karolis", "Šarapnickis", "1990-07-13");
        savePerson(repository, personService, dateService, "Jonas", "Vandenis", "1970-07-13");
        savePerson(repository, personService, dateService, "Toma", "Lašytė-Vandenienė",
                "1970-07-13");
        savePerson(repository, personService, dateService, "Kamilė", "Vandenytė-Butkienė",
                "1993-07-13");
        savePerson(repository, personService, dateService, "Tomas", "Lašas-Vandenis", "1990-07-13");
    }

    // TODO: remove
    private static void savePerson(PersonRepository repository, PersonService personService,
            DateService dateService, String name, String surname, String dateOfBirth) {
        Person p = new Person(name, surname, dateOfBirth);
        p.setGender(personService.getGender(p));
        repository.save(PersonDAO.fromPerson(p));
    }

}
