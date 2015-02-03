package lt.solutioni.web;

import lt.solutioni.core.CoreConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 
 * @author buzzard
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({ CoreConfiguration.class, WebConfiguration.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
