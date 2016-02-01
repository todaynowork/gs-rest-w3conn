package hello;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
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

class RecordPathResourceResolver extends PathResourceResolver{

	@Override
	protected boolean checkResource(Resource arg0, Resource arg1) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("checkResource");
		System.out.println(arg0.getURL().toString());
		System.out.println(arg1.getURL().toString());
		return super.checkResource(arg0, arg1);
	}

	@Override
	protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {
		// TODO Auto-generated method stub
		Resource ret =  super.resolveResourceInternal(request, requestPath, locations, chain);
		System.out.println("resolveResourceInternal");
		System.out.println(ret.getFilename());
		System.out.println(requestPath);
		return ret;
	}

	@Override
	protected String resolveUrlPathInternal(String resourcePath, List<? extends Resource> locations,
			ResourceResolverChain chain) {
		// TODO Auto-generated method stub
		String url = super.resolveUrlPathInternal(resourcePath, locations, chain);
		
		System.out.println("resolveUrlPathInternal");
		System.out.println(url);
		return url;
	}

	@Override
	protected Resource getResource(String resourcePath, Resource location) throws IOException {
		// TODO Auto-generated method stub
		
		Resource rs = super.getResource(resourcePath, location);
		System.out.println("getResource");
		System.out.println(rs.getURI().toString());
		System.out.println(resourcePath);
		
		return rs;
	}
	
}
