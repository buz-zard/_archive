package lt.solutioni.web.controller;

import java.util.ArrayList;
import java.util.List;

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
