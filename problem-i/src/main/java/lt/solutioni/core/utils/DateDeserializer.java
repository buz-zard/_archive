package lt.solutioni.core.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lt.solutioni.core.CoreConfiguration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String date = parser.getText();
		DateFormat format = new SimpleDateFormat(CoreConfiguration.DATE_FORMAT);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

}
