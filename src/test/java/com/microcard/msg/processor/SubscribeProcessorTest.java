/**
 * 
 */
package com.microcard.msg.processor;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.client.HttpDefaultClient;
import com.microcard.client.WeixinClient;

/**
 * @author jack
 *
 */
public class SubscribeProcessorTest  extends TestCase {

	public SubscribeProcessorTest() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.microcard.msg.processor.SubscribeProcessor#proccess(com.microcard.msg.Msg)}.
	 */
	@Test
	public void testProccess() {
		
		try {
			HttpDefaultClient client = new HttpDefaultClient("https://api.weixin.qq.com/cgi-bin/user/info?access_token=PfA3rsPBNSbRCwQ2J8kDBDh-31WOqN-xVI8UKXTzZPNp7yMaLuxN9JQOhFk0-AYH&openid=o2gmduF6NicaYlrYc0OKsbYeYVE4&lang=zh_CN");
			String result = client.doGet();
			Shop shop = WeixinClient.parseShop(result);
			System.out.println(result);
			System.out.println(shop.getCity());
			super.assertEquals("阿富汗",shop.getCity());
			
		} catch (Exception e) {
			super.fail(e.getMessage());
		}
		
	}

}
