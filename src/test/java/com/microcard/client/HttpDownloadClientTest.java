/**
 * 
 */
package com.microcard.client;

import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.exception.WeixinException;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class HttpDownloadClientTest extends TestCase {

	/**
	 * @param name
	 */
	public HttpDownloadClientTest(String name) {
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

	/**
	 * Test method for {@link com.microcard.client.HttpDownloadClient#doGet()}.
	 */
	public void testDoGet() {
		try{
			System.setProperty("jsse.enableSNIExtension", "false");
			String token = TokenManager.getShopToken();
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
			HttpDefaultClient client = new HttpDefaultClient(url);
			String result = client.doPost("{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
			
			WeixinException exception = WeixinException.parseException(result);
			if(exception != null) {
				fail(exception.getMessage());
				return;
			}
			
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
		    String ticket = jsonObject.getString("ticket");
			jsonObject.getInt("expire_seconds");
			
			url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		    client = new HttpDownloadClient(url,"/Users/jack/Downloads/openid.jpg");
			client.doGet();			
		}catch(Exception e) {
			super.fail(e.getMessage());
		}

	}

//	/**
//	 * Test method for {@link com.microcard.client.HttpDownloadClient#doPost(java.lang.String)}.
//	 */
//	public void testDoPost() {
//		fail("Not yet implemented");
//	}

}
