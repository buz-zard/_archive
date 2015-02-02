package lt.solutioni.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author buzzard
 *
 */
@RestController
public class BaseController {

    @RequestMapping("/")
    public String index() {
        return "Yep, it's Spring boot!";
    }

}
