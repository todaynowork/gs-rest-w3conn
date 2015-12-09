package hello.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hello.W3ConnRestController;
import hello.model.RecommendInfor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
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
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service ("w3ConnService")
public class W3ConnService {

	
	private static final Logger log = LoggerFactory.getLogger(W3ConnService.class);
	
	/**
	 * if voted, return true, if un voted return false
	 * @param username intranet id
	 * @param password intranet id pass word
	 * @param name  the intranet id to be checked
	 * @return if voted, return true, if un voted return false
	 * @throws Exception
	 */
	public boolean checkIfVoted(String username, String password, String name) throws Exception{
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
    	
    	boolean retValue =false;

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
        		retValue = true;
        	}
    	}
		return retValue;
	}
	
	
	/**
	 * check vote status for a specific period
	 * @param username 
	 * @param password
	 * @throws Exception
	 * @throws IOException
	 */
	public List<RecommendInfor> voteStatus(String username, String password) throws Exception, IOException{
 		List<RecommendInfor> retValue = null;
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

    	HttpResponse response = client.execute(new HttpGet(url), context);
    	 
    	int statusCode = response.getStatusLine().getStatusCode();
    	NodeList nl = null;
    	Document doc = null;
    	
    	DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    	if (statusCode  == HttpStatus.SC_OK){
        	doc = db.parse(response.getEntity().getContent());
        	
        	XPath xpath = XPathFactory.newInstance().newXPath();
        	
        	nl = (NodeList) xpath.evaluate("//entry", doc,XPathConstants.NODESET);
        	
        	NodeList linkList = (NodeList) xpath.evaluate("//entry/link[@rel=\"http://www.ibm.com/xmlns/prod/sn/recommendations\"]", doc,XPathConstants.NODESET);
        	
        	if(nl.getLength()>0)
        		retValue = new ArrayList<RecommendInfor>();
        
        	for(int i=0; i< nl.getLength();i ++){
        		
        		RecommendInfor recommendInfo = new RecommendInfor();
        		Element el = (Element)nl.item(i);
        		
        		String title = el.getElementsByTagName("title").item(0).getTextContent();
        		
        		recommendInfo.setTitle(title);

        		Node recommendUrl = (Node) linkList.item(i);
        		String rUrl = (String) recommendUrl.getAttributes().getNamedItem("href").getNodeValue();
        		log.info(rUrl);
        		log.info(title);
        		response = client.execute(new HttpGet(rUrl), context);
        		statusCode = response.getStatusLine().getStatusCode();
        		if (statusCode  == HttpStatus.SC_OK){
        			Document recdoc = db.parse(response.getEntity().getContent());
        			
        			Node recommendTotalResult =  (Node) xpath.evaluate("/feed/totalResults", recdoc,XPathConstants.NODE);
        			String count =  "";
        			
        			if(recommendTotalResult!=null && recommendTotalResult.getFirstChild() != null){
        				log.info(recommendTotalResult.getFirstChild().getNodeValue());
        				count = recommendTotalResult.getFirstChild().getNodeValue();
        				recommendInfo.setTotalResult(count);
        			}
        		}
        		
        		retValue.add(recommendInfo);
        	}
        	
    	}
    	return retValue;
	}
}
