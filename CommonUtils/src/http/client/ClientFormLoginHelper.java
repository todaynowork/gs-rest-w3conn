package http.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientFormLoginHelper {
	
	private static HashMap<String, HashMap<String, String>> users = new HashMap<String, HashMap<String, String>>();
	private static final Logger logger = LoggerFactory.getLogger(ClientFormLogin.class); 
	

	private static void executeSale(CloseableHttpClient httpclient, String saleId)
			throws ClientProtocolException, IOException {
		HttpUriRequest buy = RequestBuilder.get().setUri("http://180.131.58.137/wx_buy.do").
				addParameter("saleid", saleId)
				.addParameter("state","1")
				.build();
		executeRequest(httpclient,buy);
	}
	
	static void executeRequest(CloseableHttpClient httpclient, HttpUriRequest httpget) throws ClientProtocolException, IOException{
		CloseableHttpResponse response1 = httpclient.execute(httpget);
        try {
            HttpEntity entity = response1.getEntity();

            logger.debug("executeRequest get: " + response1.getStatusLine());
            logger.debug("executeRequest: " + EntityUtils.toString(response1.getEntity()));
            EntityUtils.consume(entity);

        } finally {
            response1.close();
        }
	}
	static void logon(CloseableHttpClient httpclient,String... credentials)
			throws URISyntaxException, IOException, ClientProtocolException {
		
		String userName = credentials.length >0 ? credentials[0] : null;
		String password = credentials.length >1 ? credentials[1] : null;
		String level = credentials.length >2 ? credentials[2] : null;
//		HashMap user = users.get(userName);
		HttpUriRequest login = createLogonReq(userName, password, level);
		executeRequest(httpclient,login);

	}

	static HttpUriRequest createLogonReq(String userName, String password, String level)
			throws URISyntaxException {
		HttpUriRequest login = RequestBuilder.post()
		        .setUri(new URI("http://180.131.58.137/wx_login.do"))
		        .addParameter("person.login_area", level)
		        .addParameter("person.login_acount", userName)
		        .addParameter("person.password", password)
		        .build();
		return login;
	}
	
//	static boolean verify(String responseText){
//		boolean retValue = true;
//        if(!responseText.isEmpty()){
//            if(responseText.indexOf("欢迎加入东澳娱乐") >= 0){
//           	 ClientFormLoginHelper.logon(cookieStore,httpclient);
//           	 continue;
//            }else if (responseText.indexOf("404页面不存在") >= 0){
//           	 //logon(cookieStore,httpclient);
//           	 continue;
//            }
//            
//            ClientFormLoginHelper.buy(responseText, httpclient);
//            
//       	 break;
//        }
//        return false;
//	}
	
	static Element fetchRecordsEelement(String HTML) {
		System.out.println("*** JSOUP ***");
		Element retValue = null;

		if (HTML != null && HTML.length() > 0) {
			Document doc = Jsoup.parse(HTML);
//			Element table = null;
			Element ul = null;

			if (doc != null) {
				ul = doc.select("#item1mobile div div ul").first();
			}
			String listText  = ul.html();
			if(!listText.isEmpty()){
				retValue = ul;
			}
			logger.debug("=======list=========text========================");
			logger.debug(listText);
		}
		return retValue;
	}
	
	
	static void createSale(Element element,CloseableHttpClient httpclient) throws ClientProtocolException, IOException {
		Elements buttons = element.getElementsByTag("button");
		Iterator<Element> it = buttons.iterator();
		while (it.hasNext()) {
			Element button = (Element) it.next();
			if(button!= null){
				String onclick = buttons.attr("onclick");
				String[] values = onclick.split("'");
				logger.debug(values[1]);
				executeSale(httpclient,values[1]);
			}
		}

	}
}
