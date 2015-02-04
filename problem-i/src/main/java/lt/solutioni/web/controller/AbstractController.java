package lt.solutioni.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lt.solutioni.web.domain.RestResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;

/**
 * Base abstract controller class.
 * 
 * @author buzzard
 *
 */
public abstract class AbstractController {

    /**
     * Returns exceptions details as valid JSON response.
     */
    @ExceptionHandler(Exception.class)
    protected @ResponseBody RestResponse onError(HttpServletRequest request,
            Exception exception) {
        return errorResponse(request, exception);
    }

    protected RestResponse errorResponse(HttpServletRequest request,
            Exception exception) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("URL", request.getRequestURL().toString());
        response.put("Controller", this.getClass().getCanonicalName());
        response.put("Exception", Throwables.getRootCause(exception).toString());
        return RestResponse.error(response);
    }

}
