package lt.solutioni.web.controller;

import lt.solutioni.core.domain.Person;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.WebConfiguration;
import lt.solutioni.web.domain.RestResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle {@link Person} related CRUD actions.
 * 
 * @author buzzard
 *
 */
@RestController
@RequestMapping(WebConfiguration.PERSON_URL)
public class PersonController extends AbstractController {

    private static final String URL_ALL = "/all";
    private static final String URL_GET = "/get/{personId}";
    private static final String URL_SAVE = "/save";
    private static final String URL_DELETE = "/delete/{personId}";
    private static final String URL_UPDATE = "/update";

    private static final String MSG_PERSON_FIND_FAILED = "No person record with such id found.";
    private static final String MSG_PERSON_SAVED = "Person record successfully created.";
    private static final String MSG_PERSON_SAVE_FAILED = "Failed to save person record.";
    private static final String MSG_PERSON_DELETED = "Person record successfully deleted.";
    private static final String MSG_PERSON_DELETE_FAILED = "Failed to delete person record.";
    private static final String MSG_PERSON_UPDATED = "Person record successfully updated.";
    private static final String MSG_PERSON_UPDATE_FAILED = "Failed to update person record.";

    @Autowired
    private PersonRepositoryService repositoryService;

    /**
     * Get a list of all {@link Person}.
     */
    @RequestMapping(value = URL_ALL, method = RequestMethod.GET)
    public @ResponseBody RestResponse allPeople() {
        return RestResponse.ok(repositoryService.findAll());
    }

    /**
     * Retrieve {@link Person} object by it's id.
     */
    @RequestMapping(value = URL_GET, method = RequestMethod.GET)
    public @ResponseBody RestResponse getPerson(
            @PathVariable(value = "personId") Long id) {
        Person person = repositoryService.findOne(id);
        if (person != null) {
            return RestResponse.ok(person);
        } else {
            return RestResponse.error(MSG_PERSON_FIND_FAILED);
        }
    }

    /**
     * Save a new {@link Person} instance to repository.
     */
    @RequestMapping(value = URL_SAVE, method = RequestMethod.POST)
    public @ResponseBody RestResponse savePerson(@RequestBody Person person) {
        if (repositoryService.save(person)) {
            return RestResponse.ok(MSG_PERSON_SAVED);
        } else {
            return RestResponse.error(MSG_PERSON_SAVE_FAILED);
        }
    }

    /**
     * Delete {@link PersonDAO} by it's id.
     */
    @RequestMapping(value = URL_DELETE, method = RequestMethod.POST)
    public @ResponseBody RestResponse deletePerson(
            @PathVariable(value = "personId") Long id) {
        if (repositoryService.delete(id)) {
            return RestResponse.ok(MSG_PERSON_DELETED);
        } else {
            return RestResponse.error(MSG_PERSON_DELETE_FAILED);
        }
    }

    /**
     * Update {@link PersonDAO} record in repository.
     */
    @RequestMapping(value = URL_UPDATE, method = RequestMethod.POST)
    public @ResponseBody RestResponse updatePerson(@RequestBody Person person) {
        if (repositoryService.update(person)) {
            return RestResponse.ok(MSG_PERSON_UPDATED);
        } else {
            return RestResponse.error(MSG_PERSON_UPDATE_FAILED);
        }
    }

}
