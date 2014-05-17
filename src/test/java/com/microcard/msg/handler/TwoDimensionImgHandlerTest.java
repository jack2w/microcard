/**
 * 
 */
package com.microcard.msg.handler;

import java.io.File;

import junit.framework.TestCase;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.client.HttpDefaultClient;
import com.microcard.client.HttpNettyClient;
import com.microcard.exception.WeixinException;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class TwoDimensionImgHandlerTest extends TestCase {

	/**
	 * @param name
	 */
	public TwoDimensionImgHandlerTest(String name) {
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

	public void testGetCodePic() throws Exception {
		System.setProperty("jsse.enableSNIExtension", "false");
		String token = TokenManager.getToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		HttpDefaultClient client = new HttpDefaultClient(url);
		String result = client.doPost("{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
		
		WeixinException exception = WeixinException.paserException(result);
		if(exception != null) {
			fail(exception.getMessage());
			return;
		}
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
	    String ticket = jsonObject.getString("ticket");
		jsonObject.getInt("expire_seconds");
		
		File file = new File("/Users/jack/Downloads/openid.jpg");
		url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		HttpNettyClient nettyClient = new HttpNettyClient(url);
		TwoDimensionImgHandler handler = new TwoDimensionImgHandler(file);	
		nettyClient.doGet(handler);
	}
}
