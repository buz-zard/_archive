package lt.solutioni.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lt.solutioni.core.domain.Person;
import lt.solutioni.core.service.PersonService;
import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PeopleRepository;
import lt.solutioni.web.domain.ActionResponse;
import lt.solutioni.web.domain.ResponseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author buzzard
 *
 */
@RestController
@RequestMapping("/people")
public class PeopleController {

	@Autowired
	private PeopleRepository repository;

	@Autowired
	private PersonService service;

	@ExceptionHandler(Exception.class)
	public @ResponseBody ActionResponse<Map<String, String>> handleError(
			HttpServletRequest req, Exception exception) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("URL", req.getRequestURL().toString());
		response.put("Exception", exception.toString());
		return new ActionResponse<Map<String, String>>(response,
				ResponseType.BAD);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody ActionResponse<List<PersonDAO>> allPeople() {
		return new ActionResponse<List<PersonDAO>>(repository.findAll(),
				ResponseType.GOOD);
	}

	@RequestMapping(value = "/get/{personId}", method = RequestMethod.GET)
	public @ResponseBody ActionResponse<PersonDAO> getPerson(
			@PathVariable(value = "personId") Long id) {
		return new ActionResponse<PersonDAO>(repository.getOne(id),
				ResponseType.GOOD);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ActionResponse<String> savePerson(
			@RequestBody Person person) {
		person.setGender(service.getGender(person));
		if (service.isPersonValid(person)) {
			PersonDAO personDAO = repository.save(PersonDAO.fromPerson(person));
			return new ActionResponse<String>(personDAO.toString(),
					ResponseType.GOOD);
		} else {
			return new ActionResponse<String>("" + person.toString(),
					ResponseType.BAD);
		}
	}

	@RequestMapping(value = { "", "/" })
	public @ResponseBody List<String> people() {
		return new ArrayList<String>() {
			{
				add("Jonas");
				add("Tomas");
				add("Makaronas");
			}
		};
	}

}
