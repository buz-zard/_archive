package lt.solutioni.web;

import java.io.IOException;
import java.nio.charset.Charset;

import lt.solutioni.Application;
import lt.solutioni.core.CoreTest;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class WebTest extends CoreTest {

    /*
     * =========================================================================
     * 
     * Methods/fields used in child test classes.
     * 
     * =========================================================================
     */

    public static final MediaType JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    public String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        httpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    /*
     * =========================================================================
     * 
     * Inner methods.
     * 
     * =========================================================================
     */

    private HttpMessageConverter httpMessageConverter;

    @Autowired
    protected void setConverters(HttpMessageConverter<?>[] converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                httpMessageConverter = converter;
                break;
            }
        }
        assertNotNull(httpMessageConverter);
    }

    @Autowired
    private WebApplicationContext context;

}
