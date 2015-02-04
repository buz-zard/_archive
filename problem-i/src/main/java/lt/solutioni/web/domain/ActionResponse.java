package lt.solutioni.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author buzzard
 *
 */
@AllArgsConstructor
public class ActionResponse<T> {

    @Getter
    private T data;

    @Getter
    private ResponseType status;

}
