package lt.solutioni.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rest service HTTP response wrapper.
 * 
 * @author buzzard
 *
 */
@AllArgsConstructor
public class RestResponse {

    @Getter
    private Object data;

    @Getter
    private ResponseType status;

    /**
     * Successful response.
     */
    public static RestResponse ok(Object data) {
        return new RestResponse(data, ResponseType.OK);
    }

    /**
     * Error response.
     */
    public static RestResponse error(Object data) {
        return new RestResponse(data, ResponseType.ERROR);
    }

}
