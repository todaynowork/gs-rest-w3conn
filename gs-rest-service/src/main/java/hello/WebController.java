package hello;

import hello.model.RecommendInfor;
import hello.service.W3ConnService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rometools.rome.feed.synd.SyndPerson;

@Controller
public class WebController {
	
	 @Autowired
	 W3ConnService w3srv;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam Map<String,String> allRequestParam, Model model) {
        model.addAttribute("name", allRequestParam.get("intranetId"));
        return "greeting";
    }
    
    @RequestMapping(value="/listperson", method=RequestMethod.GET)
    public String listPersonform(@RequestParam Map<String,String> allRequestParam, Model model) {
       return "listperson";
    }
    
    @RequestMapping(value="/listperson/{entryId}")
    public String listPersonSubmit(HttpServletRequest request ,@PathVariable String entryId, @RequestParam Map<String,String> allRequestParam, Model model) {
    	String[] credentials = GetUserBasicCredentials(request);
    	
    	if (credentials!=null){
//        	String username = allRequestParam.get("intranetId");
//        	String password = allRequestParam.get("password");
    		
    	   	String username = credentials[0];
        	
        	String password = credentials[1];
        	List<SyndPerson> persons = null;
        	try {
        		String url = "https://w3-connections.ibm.com/blogs/beba6c62-ff28-40bf-8b3c-b86f1520bfd7/api/recommend/entries/" + entryId.replace("urn:lsid:ibm.com:blogs:entry-", "");
        		persons = w3srv.listContributors(username, password, url);
    		} 
        	catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        	model.addAttribute("persons", persons);    		
    	}
    	return "listperson";
    	
    }
    
//    @RequestMapping(value="/status", method=RequestMethod.GET)
//    public String viewstatusform(@RequestParam Map<String,String> allRequestParam, Model model) {
//       return "status";
//    }
    
    @RequestMapping(value="/status")
    public String viewstatusSubmit(HttpServletRequest request ,@RequestParam Map<String,String> allRequestParam, Model model) {
    	
    	
    	String[] credentials = GetUserBasicCredentials(request);
    	
    	if (credentials!=null){
//    	   	String username = allRequestParam.get("intranetId");
//        	
//        	String password = allRequestParam.get("password");
        	
    	   	String username = credentials[0];
        	
        	String password = credentials[1];
        	
        	List<RecommendInfor> recommends = null;
        	try {
    			recommends = w3srv.voteStatus(username, password);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	//create a comparator object using a Lambda expression
        	Comparator<RecommendInfor> compareDouble = (d1, d2) ->new Integer(d1.getTotalResult()).compareTo( new Integer(d2.getTotalResult())); 
        	
        	Collections.sort(recommends, Collections.reverseOrder( compareDouble));
        	model.addAttribute("blogRecommends", recommends);
    	}
 
    	return "status";
    	
    }

	private String[] GetUserBasicCredentials(HttpServletRequest request) {
		final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));
            // credentials = username:password
            final String[] values = credentials.split(":",2);
            return values;
        }
        return null;
	}
    
    @RequestMapping(value="/querystatus", method=RequestMethod.GET)
    public String querystatusform(@RequestParam Map<String,String> allRequestParam, Model model) {
       
    	
    	return "querystatus";
    }
    
    @RequestMapping(value="/querystatus", method=RequestMethod.POST)
    public String querystatusSubmit(@RequestParam Map<String,String> allRequestParam, Model model) {
    	String username = allRequestParam.get("intranetId");
    	String password = allRequestParam.get("password");
    	String name = allRequestParam.get("email2verify");
    	
    	boolean isVoted =false;
    	try {
    		isVoted = w3srv.checkIfVoted(username, password, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
    	
    	if (isVoted){
    		model.addAttribute("name", "voted !");
    	} else{
    		model.addAttribute("name", "un voted !");
    	}
    	return "greeting";
    }

}
