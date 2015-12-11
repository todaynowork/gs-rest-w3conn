package hello;

import hello.model.RecommendInfor;
import hello.service.W3ConnService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;


@RestController
public class W3ConnRestController {

	
	 @Autowired
	 W3ConnService w3srv;
	 
    private static final Logger log = LoggerFactory.getLogger(W3ConnRestController.class);
    
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    
    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping("/greeting-javaconfig")
    public @ResponseBody Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== in greeting ====");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping("/votes")
    public String votes(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password,@RequestParam(value="name", defaultValue="") String name) throws ClientProtocolException, Exception {
    	
    	String retValue ="un voted !";
    	
    	boolean isVoted =false;
    	try {
    		isVoted = w3srv.checkIfVoted(username, password, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			retValue ="Error Occur, retry !";
		}
    	
    	if (isVoted){
    		retValue ="Voted !";
    	}
    	return retValue;
    }
    
    @RequestMapping("/votestatus")
    public List<RecommendInfor> voteStatus(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password) throws ClientProtocolException, Exception {
    	return w3srv.voteStatus(username, password);
    }
    
    @RequestMapping("/votedpersons")
    public List isvoted(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password,@RequestParam(value="name", defaultValue="") String name) throws ClientProtocolException, Exception {
    	
    	
    	

    	String url = "https://w3-connections.ibm.com/blogs/beba6c62-ff28-40bf-8b3c-b86f1520bfd7/api/recommend/entries/c66a7291-a529-4ec4-8d62-932b6699edd6";

    	return w3srv.listContributors(username, password, url);
    }
//    public static void main(String[] args){
//    	W3ConnRestController  contrl= new W3ConnRestController();
//    	
//    	try {
//			contrl.voteStatus("duzhiguo@cn.ibm.com", "");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    }
}
