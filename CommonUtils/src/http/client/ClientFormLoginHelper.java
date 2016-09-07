package http.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientFormLoginHelper {
	private static final Logger logger = LoggerFactory.getLogger(ClientFormLogin.class); 
	
	
	static void buy(String jsonData, CloseableHttpClient httpclient) throws ClientProtocolException, IOException {
		JSONObject data = new JSONObject(jsonData);
		
		JSONArray sales = data.getJSONArray("datas");
		
		for (int i=0;i< sales.length();i++ ){
			JSONObject obj  = sales.getJSONObject(i);
			logger.debug(obj.toString());
			
            String saleId = obj.getString("sale_id");
            executeSale(httpclient, saleId);

		}
	}

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
	static void logon(BasicCookieStore cookieStore, CloseableHttpClient httpclient)
			throws URISyntaxException, IOException, ClientProtocolException {
		HttpUriRequest login = RequestBuilder.post()
		        .setUri(new URI("http://180.131.58.137/wx_login.do"))
		        .addParameter("person.login_area", "3")
//		        .addParameter("person.login_acount", "17080671147")
//		        .addParameter("person.password", "wang1211")
//		        .addParameter("person.login_acount", "15940926867")
//		        .addParameter("person.password", "15940926867")
		        .addParameter("person.login_acount", "15998530111")
		        .addParameter("person.password", "221328")
		        .build();
		CloseableHttpResponse response2 = httpclient.execute(login);
		try {
		    HttpEntity entity = response2.getEntity();

		    logger.debug("Login form get: " + response2.getStatusLine());
		    logger.debug("Login form get: " + EntityUtils.toString(response2.getEntity()));
		    EntityUtils.consume(entity);

		    logger.debug("Post logon cookies:");
		    List<Cookie> cookies = cookieStore.getCookies();
		    if (cookies.isEmpty()) {
		        System.out.println("None");
		    } else {
		        for (int i = 0; i < cookies.size(); i++) {
		        	logger.debug("- " + cookies.get(i).toString());
		        }
		    }
		} finally {
		    response2.close();
		}
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
			System.out
					.println("Title: " + doc.getElementsByTag("title").text());
			System.out.println("H1: " + doc.getElementsByTag("h1").text());
			Element table = null;
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
