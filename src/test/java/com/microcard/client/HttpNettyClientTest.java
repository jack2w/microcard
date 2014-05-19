/**
 * 
 */
package com.microcard.client;

import junit.framework.TestCase;

import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class HttpNettyClientTest extends TestCase {

	/**
	 * @param name
	 */
	public HttpNettyClientTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetToken()  {
		try {
			String token = TokenManager.getShopToken();
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
			HttpDefaultClient client = new HttpDefaultClient(url);
			
			String result = client.doPost("{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
			System.out.println(result);//gQFT8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2NFUEZVZFBsNXhxZXBnQTE1MjhlAAIEXpNzUwMEAAAAAA==
		} catch (Exception e) {
			super.fail(e.getMessage());
		}
	}
}
