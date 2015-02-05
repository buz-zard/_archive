package lt.solutioni.web.controller;

import javax.servlet.http.HttpServletRequest;

import lt.solutioni.web.domain.RestResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base controller to handle global errors @ /error.
 * 
 * @author buzzard
 *
 */
@RestController
public class BaseController extends AbstractController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public @ResponseBody RestResponse onGlobalError(HttpServletRequest request, Exception exception) {
        return super.errorResponse(request, exception);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
