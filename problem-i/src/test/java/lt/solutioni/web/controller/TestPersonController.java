package lt.solutioni.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.web.WebTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link PersonController}
 * 
 * @author buzzard
 *
 */
public class TestPersonController extends WebTest {

    @Autowired
    private PersonService personService;

    private List<Person> mockedPeople;

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
        mockedPeople = new ArrayList<Person>();
        mockPerson("Karolis", "Šarapnickis", "1990-07-13");
        mockPerson("Jonas", "Vandenis", "1970-07-13");
        mockPerson("Toma", "Lašytė-Vandenienė", "1970-07-13");
        mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        mockPerson("Tomas", "Lašas-Vandenis", "1990-07-13");
    }

    private Person mockPerson(String name, String surname, String date) {
        Person person = personService.createPerson(name, surname, date);
        mockedPeople.add(person);
        return person;
    }

    /**
     * Test for {@link PersonController#allPeople()}
     */
    @Test
    public void testPeople() throws Exception {
        mockMvc.perform(get("/person/all")).andExpect(status().isOk())
                .andExpect(content().contentType(JSON_UTF8));
    }

}
