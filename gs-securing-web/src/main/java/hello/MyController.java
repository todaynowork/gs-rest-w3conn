package hello;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MyController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    
//     
//    @RequestMapping("/json_auth")
//    public HttpHeaders jsonAuth(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
//    	HttpHeaders header = new HttpHeaders();
//    	
//    	header.add("myKey", "test from controller");
//        return header;
//    }

    @RequestMapping("/json_auth")
    public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity, @CookieValue("at") String cookie) throws UnsupportedEncodingException {
        List<String> cookies = requestEntity.getHeaders().get("Set-Cookie");
        String authCookieValue = "";
//        for (String cookie : cookies){
//        	System.out.println(cookie);
//        	authCookieValue = cookie;
//        }

        // do something with request header and body

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        responseHeaders.set("myKey", cookie);
        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
    }
    
}
