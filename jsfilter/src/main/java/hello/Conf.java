package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Conf  extends WebMvcConfigurerAdapter {

	@Bean 
	public Recorder recorder(){
		return new ConsoleOutputRecord();
	}

}
