package hello;

import hello.model.RecommendInfor;
import hello.service.W3ConnService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;


@RestController
public class W3ConnRestController {

	
	 @Autowired
	 W3ConnService w3srv;
	 
    private static final Logger log = LoggerFactory.getLogger(W3ConnRestController.class);
    
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
    
    @RequestMapping("/isvoted")
    public String isvoted(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password,@RequestParam(value="name", defaultValue="") String name) throws ClientProtocolException, Exception {
    	
    	
    	

    	String url = "https://w3-connections.ibm.com/blogs/beba6c62-ff28-40bf-8b3c-b86f1520bfd7/api/recommend/entries/c66a7291-a529-4ec4-8d62-932b6699edd6";

		HttpHost host = new HttpHost("w3-connections.ibm.com");
		final AuthHttpComponentsClientHttpRequestFactory requestFactory =
    		    new AuthHttpComponentsClientHttpRequestFactory(
    		                 host, username, password);
    	final RestTemplate restTemplate = new RestTemplate(requestFactory);
    	
    	Feed quote =  (Feed) restTemplate.getForObject(url, Feed.class);
    	

    	
    	//restTemplate.
//    	restTemplate.
//    	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
    	List entities = quote.getEntries();
//    	
//    	quote.getEntries();
    	String retValue ="";

    	for (Object obj :entities){
    		
    		Entry entity = (Entry)obj;
    		log.info(entity.getContributors().get(0).getEmail());
    		log.info(entity.getTitle());
    	}
//    	quote.getEntries().
    	

   	
//    	System.out.print(nl.getLength());
    	//log.info(String.valueOf(nl.getLength()));    
//    	log.info(doc.toString());    		
		return retValue;
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
