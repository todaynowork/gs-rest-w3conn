package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

		
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

	    registry.addResourceHandler("/dojo/**")
	    .addResourceLocations("/resources/dojo-release-1.10.3/")
	    .resourceChain(true)
	    .addResolver(recordPathResolver());
	}

	@Bean PathResourceResolver recordPathResolver(){
		return new RecordPathResourceResolver();
	}
}

