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

/**
 * @author jack
 *
 */
public class TokenManager {

	private static final String AppId = "wxe6297940decb5dee";
	
	private static final String AppSecret = "85beed8c47ee619028b34303045f3e42";
	
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
        
        return token;
	}
	
	public static void main(String[] arg) {
		
		try {
			URI uri = new URI("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId+"&secret="+AppSecret);
			HttpDefaultClient client = new HttpDefaultClient(uri);
			String result = client.doGet();
			System.out.println(result);
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
		}
		
	}
}
