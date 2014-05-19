/**
 * 
 */
package com.microcard.token;

import java.net.URI;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.client.HttpDefaultClient;
import com.microcard.exception.WeixinException;
import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class TokenManager {

	public static final String ShopAppId = "wxe6297940decb5dee";
	
	public static final String ShopAppSecret = "85beed8c47ee619028b34303045f3e42";
	
	private static String shopToken = null;
	
	private static Object shopTokenAvailable = new Object();
	
	private static int shop_expire_time = 7200 *1000;
	
	private static long shopLastTokenTime = 0;
	
	public static String getShopToken() throws Exception {
		long current = System.currentTimeMillis();
		if(shopToken !=null &&  (current - shopLastTokenTime) < shop_expire_time )
		  return shopToken;
		
		
		synchronized(shopTokenAvailable) {
			 current = System.currentTimeMillis();  //重新判断
			if(shopToken == null || (current - shopLastTokenTime) > shop_expire_time) {
				shopLastTokenTime =  System.currentTimeMillis();
				getShopTokenFromWeixin();
				return shopToken;
			}
			return shopToken;
		}
	}
	
	private static String getShopTokenFromWeixin() throws Exception {
		Logger.getOperLogger().debug("begin take token from weixin");
        URI uri = new URI("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ShopAppId+"&secret="+ShopAppSecret);
        HttpDefaultClient client = new HttpDefaultClient(uri);
        
        String result = client.doGet();
        
        WeixinException exception = WeixinException.parseException(result);
        if(exception != null) throw exception;
        
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );  				
		shopToken = jsonObject.getString("access_token");
		shop_expire_time = jsonObject.getInt("expires_in") * 1000;    
		
        return shopToken;
	}
	
	public static String refreshShopToken() throws Exception{
		synchronized(shopTokenAvailable) {
			getShopTokenFromWeixin();
			return shopToken;
		}
	}
}
