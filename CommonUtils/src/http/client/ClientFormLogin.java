package http.client;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class ClientFormLogin {

	private static final Logger logger = LoggerFactory.getLogger(ClientFormLogin.class);

	public static void main(String[] args) throws Exception {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {
			
			// create the parser
		    CommandLineParser parser = new DefaultParser();
		    Options options = new Options();
		    CommandLine line = null ;
		    options.addOption("usr", true, "user");
		    options.addOption("pwd", true, "password");
		    options.addOption("level", true, "level");
		    try {
		        // parse the command line arguments
		        line = parser.parse( options, args );
		        
		        if(!(line.hasOption("usr") && line.hasOption("pwd") && line.hasOption("level"))){
		        	throw new Exception("Argument is not enogh");
		        }

		    }
		    catch( ParseException exp ) {
		        // oops, something went wrong
		        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		    }

			String userName = line.getOptionValue("usr");
			String password = line.getOptionValue("pwd");
			String level = line.getOptionValue("level");
			// HashMap user = users.get(userName);
			HttpUriRequest login = ClientFormLoginHelper.createLogonReq(userName, password, level);

			ClientFormLoginHelper.executeRequest(httpclient, login);
			HttpUriRequest list = RequestBuilder.get().setUri("http://180.131.58.137/wx_toSale.do")
					.build();

			while (true) {
				CloseableHttpResponse response1 = httpclient.execute(list);
				try {
					HttpEntity entity = response1.getEntity();
					if(logger.isDebugEnabled()){
						for (Header head : response1.getAllHeaders()) {
							logger.debug(new StringBuilder(head.getName()).append(":").append(head.getValue()).toString());
						}						
					}

					String responseText = EntityUtils.toString(response1.getEntity());

					logger.debug("------------------------- ");
					logger.debug("Json get: " + response1.getStatusLine());
					logger.debug("Json get: " + responseText);
					EntityUtils.consume(entity);

					if (!responseText.isEmpty()) {

						Element ulList = null;
						if (responseText.indexOf("欢迎加入东澳娱乐") >= 0) {
							ClientFormLoginHelper.executeRequest(httpclient, login);
							continue;
						} else if (responseText.indexOf("404页面不存在") >= 0) {
							// logon(cookieStore,httpclient);
							continue;
						} else if (responseText.indexOf("交易大厅") < 0) {
							ClientFormLoginHelper.executeRequest(httpclient, login);
							continue;
						} else if ((ulList = ClientFormLoginHelper.fetchRecordsEelement(responseText)) != null) {
							logger.debug("list data =======================================================");
							logger.debug(ulList.html());
							ClientFormLoginHelper.createSale(ulList, httpclient);
							continue;
						}
					}

					// if(responseText.indexOf("交易大厅") < 0){
					// ClientFormLoginHelper.logon(cookieStore,httpclient);
					// continue;
					// }
					//
					// if(responseText.indexOf("<div
					// class=\"mui-pull-bottom-tips\">没有数据</div>") < 0)
					// break;

					EntityUtils.consume(entity);
//
//					logger.debug("logon set of cookies:");
//					List<Cookie> cookies = cookieStore.getCookies();
//					if (cookies.isEmpty()) {
//						System.out.println("None");
//					} else {
//						for (int i = 0; i < cookies.size(); i++) {
//							logger.debug("- " + cookies.get(i).toString());
//						}
//					}
				} catch (IOException ioEx) {
					logger.error("IOException", ioEx);
					continue;
				} finally {
					response1.close();
				}

			}

		} finally {
			httpclient.close();
		}
	}

}