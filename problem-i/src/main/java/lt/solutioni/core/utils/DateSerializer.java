package lt.solutioni.core.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lt.solutioni.core.CoreConfiguration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * {@link Date} to JSON serializer.
 * 
 * @author buzzard
 *
 */
public class DateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator generator,
            SerializerProvider serializer) throws IOException,
            JsonProcessingException {
        DateFormat format = new SimpleDateFormat(CoreConfiguration.DATE_FORMAT);
        generator.writeString(format.format(date));
    }

}
