package lt.solutioni.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import lt.solutioni.Application;
import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.DateService;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.domain.RestResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test for {@link PersonController}
 * 
 * @author buzzard
 *
 */
@SuppressWarnings("rawtypes")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PersonControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PersonRepositoryService repositoryService;

    @Autowired
    private DateService dateService;

    @Autowired
    private PersonService personService;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                httpMessageConverter = converter;
                break;
            }
        }
        assertNotNull(httpMessageConverter);
    }

    private HttpMessageConverter httpMessageConverter;

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;
    private List<Person> mockedPeople;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockedPeople = new ArrayList<Person>();
        Person p1 = mockPerson("Karolis", "Šarapnickis", "1990-07-13");
        mockedPeople.add(p1);
        repositoryService.save(p1);
        mockedPeople = new ArrayList<Person>();
        Person p2 = mockPerson("Jonas", "Vandenis", "1970-07-13");
        mockedPeople.add(p2);
        repositoryService.save(p2);
        mockedPeople = new ArrayList<Person>();
        Person p3 = mockPerson("Toma", "Lašytė-Vandenienė", "1970-07-13");
        mockedPeople.add(p3);
        repositoryService.save(p3);
        mockedPeople = new ArrayList<Person>();
        Person p4 = mockPerson("Kamilė", "Vandenytė-Butkienė", "1993-07-13");
        mockedPeople.add(p4);
        repositoryService.save(p4);
        mockedPeople = new ArrayList<Person>();
        Person p5 = mockPerson("Tomas", "Lašas-Vandenis", "1990-07-13");
        mockedPeople.add(p5);
        repositoryService.save(p5);
    }

    private Person mockPerson(String name, String surname, String date) {
        Person p1 = new Person(name, surname, dateService.getDate(date));
        p1.setGender(personService.getGender(p1));
        return p1;
    }

    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        httpMessageConverter.write(o, MediaType.APPLICATION_JSON,
                mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    /**
     * Test for {@link PersonController#allPeople()}
     * 
     */
    @Test
    public void testPeople() throws Exception {
        mockMvc.perform(get("/person/all").content(
                json(RestResponse.ok(mockedPeople))).contentType(contentType));
    }

}
