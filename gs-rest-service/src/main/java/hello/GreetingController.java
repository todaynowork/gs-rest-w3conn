package hello;

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
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);
    
    @RequestMapping("/votes")
    public String votes(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password,@RequestParam(value="name", defaultValue="") String name) throws ClientProtocolException, Exception {
		HttpHost targetHost = new HttpHost("w3-connections.ibm.com", 80, "https");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, 
		  new UsernamePasswordCredentials(username, password));
		 
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());
		 
		// Add AuthCache to the execution context
		 HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		HttpClient client = HttpClientBuilder.create().build();
   	
    	String url = "https://w3-connections.ibm.com/blogs/beba6c62-ff28-40bf-8b3c-b86f1520bfd7/api/recommend/entries/c66a7291-a529-4ec4-8d62-932b6699edd6";
    	
    	String retValue ="";

    	HttpResponse response = client.execute(new HttpGet(url), context);
    	 
    	int statusCode = response.getStatusLine().getStatusCode();
    	NodeList nl = null;
    	Document doc = null;
    	
    	//assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    	if (statusCode  == HttpStatus.SC_OK){
        	doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response.getEntity().getContent());
        	
        	XPath xpath = XPathFactory.newInstance().newXPath();
        	
        	nl = (NodeList) xpath.evaluate("//entry[contributor/email=\"" + name +"\"]", doc,XPathConstants.NODESET);
        	
        	if (nl.getLength()>0) {
        		retValue = "Voted !";
        	}else{
        		retValue = "un Vote,go ahead!<br/>" + name;
        	}
    	}else{
    		retValue = "Error occur, retry";
    	}
		return retValue;
    }
    
    @RequestMapping("/votestatus")
    public String voteStatus(@RequestParam(value="username", defaultValue="") String username,@RequestParam(value="password", defaultValue="") String password) throws ClientProtocolException, Exception {
    	

	   	HttpHost targetHost = new HttpHost("w3-connections.ibm.com", 80, "https");
    	CredentialsProvider credsProvider = new BasicCredentialsProvider();
    	credsProvider.setCredentials(AuthScope.ANY, 
    	  new UsernamePasswordCredentials(username, password));
    	 
    	AuthCache authCache = new BasicAuthCache();
    	authCache.put(targetHost, new BasicScheme());
    	 
    	// Add AuthCache to the execution context
    	 HttpClientContext context = HttpClientContext.create();
    	context.setCredentialsProvider(credsProvider);
    	context.setAuthCache(authCache);
    	
    	HttpClient client = HttpClientBuilder.create().build();
    	
    	String url = "https://w3-connections.ibm.com/blogs/beba6c62-ff28-40bf-8b3c-b86f1520bfd7/feed/entries/atom?since=2015-11-01T00:00:00.000Z";

    	
    	String retValue ="<table border=\"1\">";

    	HttpResponse response = client.execute(new HttpGet(url), context);
    	 
    	int statusCode = response.getStatusLine().getStatusCode();
    	NodeList nl = null;
    	Document doc = null;
    	
    	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    	//assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    	if (statusCode  == HttpStatus.SC_OK){
        	doc = db.parse(response.getEntity().getContent());
        	
        	XPath xpath = XPathFactory.newInstance().newXPath();
        	
        	nl = (NodeList) xpath.evaluate("//entry", doc,XPathConstants.NODESET);
        	
        	NodeList linkList = (NodeList) xpath.evaluate("//entry/link[@rel=\"http://www.ibm.com/xmlns/prod/sn/recommendations\"]", doc,XPathConstants.NODESET);
        	
        
        	for(int i=0; i< nl.getLength();i ++){
        		retValue += "<tr><td>";
        		Element el = (Element)nl.item(i);
        		
        		String title = el.getElementsByTagName("title").item(0).getTextContent();
        		
        		retValue += title + "</td><td>";

        		Node recommendUrl = (Node) linkList.item(i);
        		String rUrl = (String) recommendUrl.getAttributes().getNamedItem("href").getNodeValue();
        		//String title = el.getElementsByTagName("title").item(0).getTextContent();
        		log.info(rUrl);
        		//String recommendUrl = el.getElementsByTagName("title").item(0).getTextContent();
        		
        		log.info(title);
        		log.info(rUrl);
        		
        		response = client.execute(new HttpGet(rUrl), context);
        		statusCode = response.getStatusLine().getStatusCode();
        		if (statusCode  == HttpStatus.SC_OK){
        			Document recdoc = db.parse(response.getEntity().getContent());
        			
        			Node recommendC =  (Node) xpath.evaluate("/feed", recdoc,XPathConstants.NODE);
        			
        			NodeList child = recommendC.getChildNodes();
        			String count =  "";
        			for (int j=0; j< child.getLength();j++){
        				Node nd = child.item(j);
        				if(nd.getNodeName().equals("opensearch:totalResults")){
            				log.info(nd.getNodeName());
            				log.info(nd.getFirstChild().getNodeValue());
            				count = nd.getFirstChild().getNodeValue();
            				break;
        				}

        			}

        			
        			retValue += count + "</td></tr>";
        			log.info(count);
        		}
        	}
        	
        	retValue += "</table>";
    	}else{
    		retValue = "Error occur, retry";
    	}
    	log.info(retValue);

		return retValue;
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
//    	GreetingController  contrl= new GreetingController();
//    	
//    	try {
//			contrl.isvoted("duzhiguo@cn.ibm.com", "", "duzhiguo@cn.ibm.com");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    }
}
