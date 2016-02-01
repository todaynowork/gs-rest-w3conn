package hello;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

		
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

	    registry.addResourceHandler("/dojo/**")
	    .addResourceLocations("/resources/dojo-release-1.10.3/")
	    .resourceChain(true)
	    .addResolver(new RecordPathResourceResolver());
	}
	

	
}

