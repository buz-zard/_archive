package lt.solutioni.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.domain.Relationship;
import lt.solutioni.core.service.RelationshipService;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.service.PersonRepositoryService;
import lt.solutioni.web.WebConfiguration;
import lt.solutioni.web.domain.RelatedPerson;
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

    @Autowired
    private PersonRepositoryService repositoryService;

    @Autowired
    private RelationshipService relationshipService;

    private static final String URL_ALL = "/all.json";
    private static final String URL_GET = "/get/{personId}.json";
    private static final String URL_SAVE = "/save.json";
    private static final String URL_DELETE = "/delete/{personId}.json";
    private static final String URL_UPDATE = "/update.json";
    private static final String URL_RELATIONSHIPS = "/relationships/{personId}.json";

    public static final String MSG_PERSON_FIND_FAILED = "No person record with such id found.";
    public static final String MSG_PERSON_SAVED = "Person record successfully created.";
    public static final String MSG_PERSON_SAVE_FAILED = "Failed to save person record.";
    public static final String MSG_PERSON_DELETED = "Person record successfully deleted.";
    public static final String MSG_PERSON_DELETE_FAILED = "Failed to delete person record with such id.";
    public static final String MSG_PERSON_UPDATED = "Person record successfully updated.";
    public static final String MSG_PERSON_UPDATE_FAILED = "Failed to update person record.";

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
    public @ResponseBody RestResponse getPerson(@PathVariable(value = "personId") Long id) {
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
    public @ResponseBody RestResponse deletePerson(@PathVariable(value = "personId") Long id) {
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

    /**
     * Get all related people to a {@link Person} by id.
     */
    @RequestMapping(value = URL_RELATIONSHIPS, method = RequestMethod.GET)
    public @ResponseBody RestResponse relationships(@PathVariable(value = "personId") Long personId) {
        Person person = repositoryService.findOne(personId);
        if (person != null) {
            List<RelatedPerson> relatedPeople = new ArrayList<RelatedPerson>();
            Map<Person, Relationship> relationships = relationshipService.getRelationships(person,
                    repositoryService.findAll());
            for (Person relatedPerson : relationships.keySet()) {
                if (relatedPerson.getId() != person.getId()) {
                    relatedPeople.add(new RelatedPerson(relationships.get(relatedPerson),
                            relatedPerson));
                }
            }
            return RestResponse.ok(relatedPeople);
        } else {
            return RestResponse.error(MSG_PERSON_FIND_FAILED);
        }
    }

}
