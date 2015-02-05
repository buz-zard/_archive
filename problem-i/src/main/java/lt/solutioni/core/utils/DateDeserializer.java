package lt.solutioni.core.utils;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * String to {@link Date} deserializer.
 * 
 * @author buzzard
 *
 */
public class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext arg1) throws IOException,
            JsonProcessingException {
        return DateUtils.getDate(parser.getText());
    }

}
