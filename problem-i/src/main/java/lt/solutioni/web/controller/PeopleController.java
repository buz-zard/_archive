package lt.solutioni.web.controller;

import java.util.ArrayList;
import java.util.List;

import lt.solutioni.persistence.domain.PersonDAO;
import lt.solutioni.persistence.repository.PeopleRepository;
import lt.solutioni.web.domain.ActionResponse;
import lt.solutioni.web.domain.ResponseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/all")
    public @ResponseBody ActionResponse<List<PersonDAO>> allPeople() {
        return new ActionResponse<List<PersonDAO>>(repository.findAll(),
                ResponseType.GOOD);
    }

}
