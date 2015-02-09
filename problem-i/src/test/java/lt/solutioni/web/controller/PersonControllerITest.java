package lt.solutioni.web.controller;

import java.util.ArrayList;

import lt.solutioni.core.domain.Person;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.ITest;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

/**
 * Integration test for {@link PersonController}.
 * 
 * @author buzzard
 *
 */
public class PersonControllerITest extends ITest {

    @Autowired
    private PersonRepositoryService service;

    @Before
    public void setUp() {
        super.setUp();
    }

    // @Test
    // public void testIndex() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(
    // MockMvcResultMatchers.status().isOk());
    // }

    @Test
    public void testFindAll() {
        assertTrue(service.findAll().size() == 0);
        assertTrue(true);
        RestAssured.when().get("/person/all.json").then().statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON).body("status", Matchers.is("OK"))
                .body("data", Matchers.equalTo(new ArrayList<>()));

        service.save(new Person("Karolis", "Šarapnickis", "1990-07-13"));
        RestAssured.when().get("/person/all.json").then().statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON).body("status", Matchers.is("OK"))
                .body("data[0].name", Matchers.equalTo("Karolis"))
                .body("data[0].surname", Matchers.equalTo("Šarapnickis"))
                .body("data[0].dateOfBirth", Matchers.equalTo("1990-07-13"))
                .body("data[0].gender", Matchers.equalTo("MALE"))
                .body("data[1]", Matchers.equalTo(null));
    }

}
