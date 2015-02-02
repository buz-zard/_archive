package lt.solutioni.web;

import lt.solutioni.core.utils.StringUtils;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author buzzard
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);
        String womenSurnameEndingsRegex = "(ytė|aitė|[i]{0,1}ūtė)";
        String s1 = "Kuytėzminskai";

        System.out.println(StringUtils.endsWithRegex(s1,
                womenSurnameEndingsRegex));
    }
}
