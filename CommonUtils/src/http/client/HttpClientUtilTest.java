package http.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;



public class HttpClientUtilTest {

	@Test
	public void testPost() {
		Map<String, String> queries = new HashMap<String, String>();
		Map<String, String> params = new HashMap<String, String>();
		queries.put("version", "2016-02-11");
		String url = "https://gateway.watsonplatform.net/tone-analyzer-beta/api/v3/tone";

		try {
			String response = HttpClientUtil.post(url, queries, params);
			
//			CloseableHttpResponse response = httpclient.execute(httpget);
			
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//fail("Not yet implemented");
	}
	

//	@Test
//	public void testTone() {
//		ToneAnalyzer service = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_02_11);
//		service.setUsernameAndPassword("bf01f54b-cef2-4ecf-80af-f853f8d62222", "VcL70SYDiPFc");
//
//
//		String text = "I know the times are difficult! Our sales have been "
//		    + "disappointing for the past three quarters for our data analytics "
//		    + "product suite. We have a competitive data analytics product "
//		    + "suite in the industry. But we need to do our job selling it! "
//		    + "We need to acknowledge and fix our sales challenges.";
//
//
//		ToneAnalysis tone1 = service.getTone(text);
//		System.out.println(tone1);    
//	}

}
