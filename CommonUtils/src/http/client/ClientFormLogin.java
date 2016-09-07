package http.client;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
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
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        try {
            HttpGet httpget = new HttpGet("http://180.131.58.137/wx_toSale.do");
            CloseableHttpResponse response1 = httpclient.execute(httpget);
            try {
                HttpEntity entity = response1.getEntity();

                logger.debug("Login form get: " + response1.getStatusLine());
                logger.debug("Login form get: " + EntityUtils.toString(response1.getEntity()));
                EntityUtils.consume(entity);

                logger.debug("Initial set of cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                    	logger.debug("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response1.close();
            }

            ClientFormLoginHelper.logon(cookieStore, httpclient);
            //wx_validatePay.do  {'password':password};  
//            HttpUriRequest validate = RequestBuilder.get().setUri("http://180.131.58.137/wx_validatePay.do").
//            		addParameter("password", "v").build();
            
            
            //wx_buy.do  {"saleid":buysaleid,"state":1}; sale.sale_id
            //String jsonData= "{\"datas\":[{\"amount\":1000,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 00:07:06\",\"buy_person\":124,\"create_date\":\"2016-09-07 00:07:01\",\"curr_amount\":3489.050048828125,\"orderno\":\"Y201609070007001\",\"pay_date\":\"2016-09-07 00:07:29\",\"sale_amount\":1050,\"sale_id\":6665,\"sale_person\":739,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 00:23:15\",\"buy_person\":124,\"create_date\":\"2016-09-07 00:23:08\",\"curr_amount\":323.8999938964844,\"orderno\":\"Y201609070023001\",\"pay_date\":\"2016-09-07 00:53:48\",\"sale_amount\":105,\"sale_id\":6667,\"sale_person\":1099,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 08:21:41\",\"buy_person\":107,\"create_date\":\"2016-09-07 08:21:37\",\"curr_amount\":2.140000104904175,\"orderno\":\"Y201609070821002\",\"pay_date\":\"2016-09-07 08:21:57\",\"sale_amount\":105,\"sale_id\":6675,\"sale_person\":1380,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":500,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:05:08\",\"buy_person\":160,\"create_date\":\"2016-09-07 10:04:59\",\"curr_amount\":75.2300033569336,\"orderno\":\"Y201609071004002\",\"pay_date\":\"2016-09-07 10:14:46\",\"sale_amount\":525,\"sale_id\":6690,\"sale_person\":930,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":4500,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:55:58\",\"buy_person\":101,\"create_date\":\"2016-09-07 10:55:53\",\"curr_amount\":454.3599853515625,\"orderno\":\"Y201609071055001\",\"pay_date\":\"2016-09-07 10:56:19\",\"sale_amount\":4725,\"sale_id\":6722,\"sale_person\":1384,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":2000,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:39:41\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:39:37\",\"curr_amount\":2542.1298828125,\"orderno\":\"Y201609071039001\",\"sale_amount\":2100,\"sale_id\":6716,\"sale_person\":130,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":300,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:49:43\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:49:38\",\"curr_amount\":426.0899963378906,\"orderno\":\"Y201609071049002\",\"sale_amount\":315,\"sale_id\":6721,\"sale_person\":546,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:56:25\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:56:20\",\"curr_amount\":6.039999961853027,\"orderno\":\"Y201609071056001\",\"sale_amount\":105,\"sale_id\":6724,\"sale_person\":1033,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-09 08:57:49\",\"create_date\":\"2016-07-09 08:43:04\",\"curr_amount\":0,\"orderno\":\"Y20160709000001\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":2,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-09 10:06:13\",\"create_date\":\"2016-07-09 09:45:23\",\"curr_amount\":0,\"orderno\":\"Y20160709000004\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":5,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":200,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-09 10:09:19\",\"create_date\":\"2016-07-09 10:07:14\",\"curr_amount\":0,\"orderno\":\"Y20160709000005\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":6,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":200,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-09 11:02:33\",\"create_date\":\"2016-07-09 10:18:15\",\"curr_amount\":0,\"orderno\":\"Y20160709000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":9,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-11 02:43:59\",\"create_date\":\"2016-07-11 02:32:30\",\"curr_amount\":0,\"orderno\":\"Y20160711000003\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":14,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-11 10:45:57\",\"create_date\":\"2016-07-11 10:20:04\",\"curr_amount\":0,\"orderno\":\"Y20160711000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":19,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-12 07:05:41\",\"create_date\":\"2016-07-12 07:05:34\",\"curr_amount\":0,\"orderno\":\"Y20160712000003\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":29,\"sale_person\":168,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-13 10:13:54\",\"create_date\":\"2016-07-13 02:20:00\",\"curr_amount\":0,\"orderno\":\"Y20160713000001\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":36,\"sale_person\":153,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":1000,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-13 10:44:31\",\"create_date\":\"2016-07-13 10:27:44\",\"curr_amount\":0,\"orderno\":\"Y20160713000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":43,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 06:27:14\",\"create_date\":\"2016-07-14 06:12:09\",\"curr_amount\":0,\"orderno\":\"Y20160714000002\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":55,\"sale_person\":153,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 09:50:22\",\"create_date\":\"2016-07-14 08:52:22\",\"curr_amount\":0,\"orderno\":\"Y20160714000007\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":60,\"sale_person\":212,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 20:51:19\",\"create_date\":\"2016-07-14 16:37:59\",\"curr_amount\":0,\"orderno\":\"Y20160714000013\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":66,\"sale_person\":186,\"sale_state\":4,\"sale_state_name\":\"已关闭\"}],\"page\":1,\"pagecount\":337}";
            //ClientFormLoginHelper.buy(jsonData);
            
            //{"datas":[{"amount":1000,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 00:07:06","buy_person":124,"create_date":"2016-09-07 00:07:01","curr_amount":3489.050048828125,"orderno":"Y201609070007001","pay_date":"2016-09-07 00:07:29","sale_amount":1050,"sale_id":6665,"sale_person":739,"sale_state":2,"sale_state_name":"已付款"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 00:23:15","buy_person":124,"create_date":"2016-09-07 00:23:08","curr_amount":323.8999938964844,"orderno":"Y201609070023001","pay_date":"2016-09-07 00:53:48","sale_amount":105,"sale_id":6667,"sale_person":1099,"sale_state":2,"sale_state_name":"已付款"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 08:21:41","buy_person":107,"create_date":"2016-09-07 08:21:37","curr_amount":2.140000104904175,"orderno":"Y201609070821002","pay_date":"2016-09-07 08:21:57","sale_amount":105,"sale_id":6675,"sale_person":1380,"sale_state":2,"sale_state_name":"已付款"},{"amount":500,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 10:05:08","buy_person":160,"create_date":"2016-09-07 10:04:59","curr_amount":75.2300033569336,"orderno":"Y201609071004002","pay_date":"2016-09-07 10:14:46","sale_amount":525,"sale_id":6690,"sale_person":930,"sale_state":2,"sale_state_name":"已付款"},{"amount":4500,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 10:55:58","buy_person":101,"create_date":"2016-09-07 10:55:53","curr_amount":454.3599853515625,"orderno":"Y201609071055001","pay_date":"2016-09-07 10:56:19","sale_amount":4725,"sale_id":6722,"sale_person":1384,"sale_state":2,"sale_state_name":"已付款"},{"amount":2000,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 10:39:41","buy_person":222,"create_date":"2016-09-07 10:39:37","curr_amount":2542.1298828125,"orderno":"Y201609071039001","sale_amount":2100,"sale_id":6716,"sale_person":130,"sale_state":1,"sale_state_name":"等待付款"},{"amount":300,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 10:49:43","buy_person":222,"create_date":"2016-09-07 10:49:38","curr_amount":426.0899963378906,"orderno":"Y201609071049002","sale_amount":315,"sale_id":6721,"sale_person":546,"sale_state":1,"sale_state_name":"等待付款"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_date":"2016-09-07 10:56:25","buy_person":222,"create_date":"2016-09-07 10:56:20","curr_amount":6.039999961853027,"orderno":"Y201609071056001","sale_amount":105,"sale_id":6724,"sale_person":1033,"sale_state":1,"sale_state_name":"等待付款"},{"amount":800,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-09 08:57:49","create_date":"2016-07-09 08:43:04","curr_amount":0,"orderno":"Y20160709000001","remark":"卖家关闭","sale_amount":0,"sale_id":2,"sale_person":108,"sale_state":4,"sale_state_name":"已关闭"},{"amount":800,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-09 10:06:13","create_date":"2016-07-09 09:45:23","curr_amount":0,"orderno":"Y20160709000004","remark":"卖家关闭","sale_amount":0,"sale_id":5,"sale_person":106,"sale_state":4,"sale_state_name":"已关闭"},{"amount":200,"amount_type":1,"amount_type_name":"贝壳","buy_person":0,"close_date":"2016-07-09 10:09:19","create_date":"2016-07-09 10:07:14","curr_amount":0,"orderno":"Y20160709000005","remark":"卖家关闭","sale_amount":0,"sale_id":6,"sale_person":106,"sale_state":4,"sale_state_name":"已关闭"},{"amount":200,"amount_type":1,"amount_type_name":"贝壳","buy_person":0,"close_date":"2016-07-09 11:02:33","create_date":"2016-07-09 10:18:15","curr_amount":0,"orderno":"Y20160709000008","remark":"卖家关闭","sale_amount":0,"sale_id":9,"sale_person":106,"sale_state":4,"sale_state_name":"已关闭"},{"amount":800,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-11 02:43:59","create_date":"2016-07-11 02:32:30","curr_amount":0,"orderno":"Y20160711000003","remark":"卖家关闭","sale_amount":0,"sale_id":14,"sale_person":108,"sale_state":4,"sale_state_name":"已关闭"},{"amount":800,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-11 10:45:57","create_date":"2016-07-11 10:20:04","curr_amount":0,"orderno":"Y20160711000008","remark":"卖家关闭","sale_amount":0,"sale_id":19,"sale_person":108,"sale_state":4,"sale_state_name":"已关闭"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-12 07:05:41","create_date":"2016-07-12 07:05:34","curr_amount":0,"orderno":"Y20160712000003","remark":"卖家关闭","sale_amount":0,"sale_id":29,"sale_person":168,"sale_state":4,"sale_state_name":"已关闭"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-13 10:13:54","create_date":"2016-07-13 02:20:00","curr_amount":0,"orderno":"Y20160713000001","remark":"卖家关闭","sale_amount":0,"sale_id":36,"sale_person":153,"sale_state":4,"sale_state_name":"已关闭"},{"amount":1000,"amount_type":1,"amount_type_name":"贝壳","buy_person":0,"close_date":"2016-07-13 10:44:31","create_date":"2016-07-13 10:27:44","curr_amount":0,"orderno":"Y20160713000008","remark":"卖家关闭","sale_amount":0,"sale_id":43,"sale_person":108,"sale_state":4,"sale_state_name":"已关闭"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-14 06:27:14","create_date":"2016-07-14 06:12:09","curr_amount":0,"orderno":"Y20160714000002","remark":"卖家关闭","sale_amount":0,"sale_id":55,"sale_person":153,"sale_state":4,"sale_state_name":"已关闭"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-14 09:50:22","create_date":"2016-07-14 08:52:22","curr_amount":0,"orderno":"Y20160714000007","remark":"卖家关闭","sale_amount":0,"sale_id":60,"sale_person":212,"sale_state":4,"sale_state_name":"已关闭"},{"amount":100,"amount_type":2,"amount_type_name":"铜板","buy_person":0,"close_date":"2016-07-14 20:51:19","create_date":"2016-07-14 16:37:59","curr_amount":0,"orderno":"Y20160714000013","remark":"卖家关闭","sale_amount":0,"sale_id":66,"sale_person":186,"sale_state":4,"sale_state_name":"已关闭"}],"page":1,"pagecount":337}

            
             //httpget = new HttpGet("http://180.131.58.137/wx_toSaleList.do");
             HttpUriRequest list = RequestBuilder.get().setUri("http://180.131.58.137/wx_toSale.do")
//            		 .addParameter("p", "p")
//            		 .addParameter("date", new Date().toString())
            		 //.addParameter("sale.state","0")
            		 .build();
             
             while(true){
            	 response1 = httpclient.execute(list);
                 try {
                     HttpEntity entity = response1.getEntity();
                     for (Header head : response1.getAllHeaders()){
                    	 logger.debug(new StringBuilder(head.getName()).append( ":").append(head.getValue()).toString());
                     }
                     String responseText =  EntityUtils.toString(response1.getEntity());
                     

                     logger.debug("------------------------- ");
                     logger.debug("Json get: " + response1.getStatusLine());
                     logger.debug("Json get: " + responseText);
                     EntityUtils.consume(entity);
                     
                     if(!responseText.isEmpty()){
                    	 
                         Element ulList=null;
						if(responseText.indexOf("欢迎加入东澳娱乐") >= 0){
                        	 ClientFormLoginHelper.logon(cookieStore,httpclient);
                        	 continue;
                         }else if (responseText.indexOf("404页面不存在") >= 0){
                        	 //logon(cookieStore,httpclient);
                        	 continue;
//                         }else if (responseText.equals("2")){
//                        	 //logon(cookieStore,httpclient);
//                        	 logger.debug("");
//                        	 continue;
                         }else if(responseText.indexOf("交易大厅") < 0){
	                    	 ClientFormLoginHelper.logon(cookieStore,httpclient);
	                    	 continue;
	                     }else if(( ulList =ClientFormLoginHelper.fetchRecordsEelement(responseText))!=null){
	                    	 logger.debug("list data =======================================================");
	                    	 logger.debug(ulList.html());
	                    	 ClientFormLoginHelper.createSale(ulList,httpclient);
	                    	 continue;
	                     }

                         
                         //ClientFormLoginHelper.buy(responseText, httpclient);
                         
                    	 //break;
                     }
                     
//                     if(responseText.indexOf("交易大厅") < 0){
//                    	 ClientFormLoginHelper.logon(cookieStore,httpclient);
//                    	 continue;
//                     }
//                     
//                     if(responseText.indexOf("<div class=\"mui-pull-bottom-tips\">没有数据</div>") < 0)
//                    	 break;
                     
                     EntityUtils.consume(entity);

                     logger.debug("logon set of cookies:");
                     List<Cookie> cookies = cookieStore.getCookies();
                     if (cookies.isEmpty()) {
                         System.out.println("None");
                     } else {
                         for (int i = 0; i < cookies.size(); i++) {
                        	 logger.debug("- " + cookies.get(i).toString());
                         }
                     }
                 }catch(IOException ioEx){
                	 logger.error("IOException", ioEx);
                	 continue;
                 }
                 finally {
                     response1.close();
                 }

             }
             
        } finally {
            httpclient.close();
        }
    }



    
    

}