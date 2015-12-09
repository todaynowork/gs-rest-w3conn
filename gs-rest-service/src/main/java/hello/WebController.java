package hello;

import hello.service.W3ConnService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	 @Autowired
	 W3ConnService w3srv;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam Map<String,String> allRequestParam, Model model) {
        model.addAttribute("name", allRequestParam.get("intranetId"));
        return "greeting";
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
