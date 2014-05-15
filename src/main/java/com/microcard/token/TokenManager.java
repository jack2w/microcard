/**
 * 
 */
package com.microcard.token;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.client.HttpClientHandler;
import com.microcard.client.HttpDefaultClient;
import com.microcard.client.HttpNettyClient;
import com.microcard.exception.WeixinException;
import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class TokenManager {

	public static final String AppId = "wxe6297940decb5dee";
	
	public static final String AppSecret = "85beed8c47ee619028b34303045f3e42";
	
	private static String token = null;
	
	private static Object tokenAvailable = new Object();
	
	private static int expire_time = 7200 *1000;
	
	private static long lastTokenTime = 0;
	
	private static WeixinException exception = null;
	
	public static String getToken() throws URISyntaxException, InterruptedException,WeixinException {
		long current = System.currentTimeMillis();
		if(token !=null &&  (current - lastTokenTime) < expire_time )
		  return token;
		
		
		synchronized(tokenAvailable) {
			 current = System.currentTimeMillis();  //重新判断
			if(token == null || (current - lastTokenTime) > expire_time) {
				lastTokenTime =  System.currentTimeMillis();
				token = getTokenFromWeixin();
				if(token == null || exception != null) {
					throw exception;
				}
				return token;
			}
			return token;
		}
	}
	
	private static String getTokenFromWeixin() throws URISyntaxException, InterruptedException {
		Logger.getOperLogger().debug("begin take token from weixin");
        URI uri = new URI("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId+"&secret="+AppSecret);
        new HttpNettyClient(uri).doGet(new HttpClientHandler(){

			@Override
			public void process(String msg) {
				exception = null;
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( msg );  				
				if(jsonObject.has("access_token")) {
					
					token = jsonObject.getString("access_token");
					expire_time = jsonObject.getInt("expires_in") * 1000;
					exception = null;
				}else if(jsonObject.has("errcode")) {
					
					token = null;
					exception = new WeixinException(jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
					
				}else {
					
					token = null;
					exception = new WeixinException("get token from weixin error!");
					
				}
				
			}});
        Logger.getOperLogger().debug("end take token from weixin successfully");
        return token;
	}
	
	public static void main(String[] arg) {
		
		try {
			String token = TokenManager.getToken();
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
			HttpDefaultClient client = new HttpDefaultClient(url);
			
			String result = client.doPost("{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
			System.out.println(result);//gQFT8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2NFUEZVZFBsNXhxZXBnQTE1MjhlAAIEXpNzUwMEAAAAAA==
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WeixinException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
