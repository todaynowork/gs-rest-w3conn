package http.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Element;
import org.junit.Test;

import common.Utils;

public class ClientFormLoginHelperTest {

	@Test
	public void test() {
		String jsonData= "{\"datas\":[{\"amount\":1000,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 00:07:06\",\"buy_person\":124,\"create_date\":\"2016-09-07 00:07:01\",\"curr_amount\":3489.050048828125,\"orderno\":\"Y201609070007001\",\"pay_date\":\"2016-09-07 00:07:29\",\"sale_amount\":1050,\"sale_id\":6665,\"sale_person\":739,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 00:23:15\",\"buy_person\":124,\"create_date\":\"2016-09-07 00:23:08\",\"curr_amount\":323.8999938964844,\"orderno\":\"Y201609070023001\",\"pay_date\":\"2016-09-07 00:53:48\",\"sale_amount\":105,\"sale_id\":6667,\"sale_person\":1099,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 08:21:41\",\"buy_person\":107,\"create_date\":\"2016-09-07 08:21:37\",\"curr_amount\":2.140000104904175,\"orderno\":\"Y201609070821002\",\"pay_date\":\"2016-09-07 08:21:57\",\"sale_amount\":105,\"sale_id\":6675,\"sale_person\":1380,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":500,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:05:08\",\"buy_person\":160,\"create_date\":\"2016-09-07 10:04:59\",\"curr_amount\":75.2300033569336,\"orderno\":\"Y201609071004002\",\"pay_date\":\"2016-09-07 10:14:46\",\"sale_amount\":525,\"sale_id\":6690,\"sale_person\":930,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":4500,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:55:58\",\"buy_person\":101,\"create_date\":\"2016-09-07 10:55:53\",\"curr_amount\":454.3599853515625,\"orderno\":\"Y201609071055001\",\"pay_date\":\"2016-09-07 10:56:19\",\"sale_amount\":4725,\"sale_id\":6722,\"sale_person\":1384,\"sale_state\":2,\"sale_state_name\":\"已付款\"},{\"amount\":2000,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:39:41\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:39:37\",\"curr_amount\":2542.1298828125,\"orderno\":\"Y201609071039001\",\"sale_amount\":2100,\"sale_id\":6716,\"sale_person\":130,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":300,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:49:43\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:49:38\",\"curr_amount\":426.0899963378906,\"orderno\":\"Y201609071049002\",\"sale_amount\":315,\"sale_id\":6721,\"sale_person\":546,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_date\":\"2016-09-07 10:56:25\",\"buy_person\":222,\"create_date\":\"2016-09-07 10:56:20\",\"curr_amount\":6.039999961853027,\"orderno\":\"Y201609071056001\",\"sale_amount\":105,\"sale_id\":6724,\"sale_person\":1033,\"sale_state\":1,\"sale_state_name\":\"等待付款\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-09 08:57:49\",\"create_date\":\"2016-07-09 08:43:04\",\"curr_amount\":0,\"orderno\":\"Y20160709000001\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":2,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-09 10:06:13\",\"create_date\":\"2016-07-09 09:45:23\",\"curr_amount\":0,\"orderno\":\"Y20160709000004\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":5,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":200,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-09 10:09:19\",\"create_date\":\"2016-07-09 10:07:14\",\"curr_amount\":0,\"orderno\":\"Y20160709000005\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":6,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":200,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-09 11:02:33\",\"create_date\":\"2016-07-09 10:18:15\",\"curr_amount\":0,\"orderno\":\"Y20160709000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":9,\"sale_person\":106,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-11 02:43:59\",\"create_date\":\"2016-07-11 02:32:30\",\"curr_amount\":0,\"orderno\":\"Y20160711000003\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":14,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":800,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-11 10:45:57\",\"create_date\":\"2016-07-11 10:20:04\",\"curr_amount\":0,\"orderno\":\"Y20160711000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":19,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-12 07:05:41\",\"create_date\":\"2016-07-12 07:05:34\",\"curr_amount\":0,\"orderno\":\"Y20160712000003\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":29,\"sale_person\":168,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-13 10:13:54\",\"create_date\":\"2016-07-13 02:20:00\",\"curr_amount\":0,\"orderno\":\"Y20160713000001\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":36,\"sale_person\":153,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":1000,\"amount_type\":1,\"amount_type_name\":\"贝壳\",\"buy_person\":0,\"close_date\":\"2016-07-13 10:44:31\",\"create_date\":\"2016-07-13 10:27:44\",\"curr_amount\":0,\"orderno\":\"Y20160713000008\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":43,\"sale_person\":108,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 06:27:14\",\"create_date\":\"2016-07-14 06:12:09\",\"curr_amount\":0,\"orderno\":\"Y20160714000002\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":55,\"sale_person\":153,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 09:50:22\",\"create_date\":\"2016-07-14 08:52:22\",\"curr_amount\":0,\"orderno\":\"Y20160714000007\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":60,\"sale_person\":212,\"sale_state\":4,\"sale_state_name\":\"已关闭\"},{\"amount\":100,\"amount_type\":2,\"amount_type_name\":\"铜板\",\"buy_person\":0,\"close_date\":\"2016-07-14 20:51:19\",\"create_date\":\"2016-07-14 16:37:59\",\"curr_amount\":0,\"orderno\":\"Y20160714000013\",\"remark\":\"卖家关闭\",\"sale_amount\":0,\"sale_id\":66,\"sale_person\":186,\"sale_state\":4,\"sale_state_name\":\"已关闭\"}],\"page\":1,\"pagecount\":337}";

//		try {
//			ClientFormLoginHelper.buy(jsonData, null);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	public void testSale() {
		String html = Utils.readFile("/Users/zhiguodu/temp/console.html");
		
		Element sales = ClientFormLoginHelper.fetchRecordsEelement(html);
		
		
		try {
			ClientFormLoginHelper.createSale(sales, null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
