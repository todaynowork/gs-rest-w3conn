package hello;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.Header;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            response.addHeader("Access-Control-Allow-Origin", "*");
            
//            Header[] cookieHeader = response.getHeaders("Set-Cookie");
//            if (cookieHeader.length > 0) {
//                HttpUtil.PHPSESSID = cookieHeader[0].getValue();
//            }
//            
            
            Iterable<String> cookies = response.getHeaders("Set-Cookie");
            
            for (String cookie : cookies){
            	System.out.println(cookie);
            }
            
            response.addHeader("bbbbbb",
                    "bbbbbbbbbbbbbaa");
            
            if (request.getHeader("Access-Control-Request-Method") != null
                    && "OPTIONS".equals(request.getMethod())) {
                // CORS "pre-flight" request
                response.addHeader("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers",
                        "X-Requested-With,Origin,Content-Type, Accept");
                
                response.addHeader("bbbbbb",
                        "bbbbbbbbbbbbbaa");
                
            }
            filterChain.doFilter(request, response);
    }

}