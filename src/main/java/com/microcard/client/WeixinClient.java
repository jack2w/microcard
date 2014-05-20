/**
 * 
 */
package com.microcard.client;

import java.sql.Timestamp;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.bean.Sex;
import com.microcard.bean.Shop;
import com.microcard.exception.WeixinException;
import com.microcard.log.Logger;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class WeixinClient {

	//private static Object tokenAvailable = new Object();
	private static Logger log = Logger.getOperLogger();
	private WeixinClient() {
		
	}
	
	/**
	 * 根据openId获取该openId的微信用户信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public static Shop getShopInfo(String openId) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+TokenManager.getShopToken()+"&openid="+openId+"&lang=zh_CN";
		log.debug("begin getShopInfo from url " + url);
		HttpDefaultClient client = new HttpDefaultClient(url);
		String result = client.doGet();
		//如果微信返回的是错误信息则抛出异常
		WeixinException exception = WeixinException.parseException(result);
		if(exception != null) {
			throw exception;
		}
		//根据微信返回的结果解析成shop类
		
		Shop shop = parseShop(result);
		log.debug("end getShopInfo, shop operid is " + shop.getOpenId());
		return shop;
	}
	
	/**
	 * 获取商铺的二维码信息
	 * @param token
	 * @param codeId 二维码附加信息
	 * @return
	 * @throws Exception
	 */
	public static String getShopTicket(String token,int codeId) throws Exception {
		
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		log.debug("begin getShopCode from url: " + url);
		
		String ticket = null;

		HttpDefaultClient client = new HttpDefaultClient(url);
		String result = client.doPost("{\"expire_seconds\": 600, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+codeId+"}}}");
		
		WeixinException exception = WeixinException.parseException(result);
		if(exception != null) {
			throw exception;
		}
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
	     ticket = jsonObject.getString("ticket");
		jsonObject.getInt("expire_seconds");
		log.debug("end getShopCode, ticket is " + ticket);
		return ticket;
		
	}
	
	/**
	 * 根据微信传过来的验证码查找属于那个用户
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getOAuthUser(String code) throws Exception{
		
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" 
	             + TokenManager.ShopAppId+ "&secret="+TokenManager.ShopAppSecret
	             +"&code="+code+"&grant_type=authorization_code";
		log.debug("begin getOAuthUser from url: " + url);
		HttpDefaultClient client = new HttpDefaultClient(url);
		String result = client .doGet();
		log.debug("received result: " + result);
		String access_token = null;
		int expires_in;
		String refresh_token = null;
		String openid = null;
		String scope = null;
		
		Exception exception = WeixinException.parseException(result);
		if(exception != null)
			throw exception;
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
		
		
		access_token = jsonObject.getString("access_token");
		expires_in = jsonObject.getInt("expires_in");
		refresh_token = jsonObject.getString("refresh_token");
		openid = jsonObject.getString("openid");
		scope = jsonObject.getString("scope");
		log.debug("access_token:" + access_token);
		log.debug("expires_in:" + expires_in);
		log.debug("refresh_token:" + refresh_token);
		log.debug("openid:" + openid);
		log.debug("scope:" + scope);
		log.debug("end getOAuthUser, openid is " + openid);
		return openid;
	}
	
	public static String downloadCodePic(String localFile,String ticket) throws Exception {
		
		//获取二维码的图片
		
		//两种方式显示二维码图片
		//1. 通过向微信把图片取到本地
//		File codeFile = new File(filename);
//		if(!codeFile.exists()) {
//			url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
//			HttpDownloadClient client = new HttpDownloadClient(url,filename);
//			client.doGet();			
//		}
//		response.setContentType("text/html");
//		response.getWriter().println(getCodePage(CODEPATH + "/" + openId + ".jpg"));
		
		//2. 直接在html的IMG中显示微信的图片连接
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
		return url;
	}
	
	public static Shop parseShop(String msg) {
		
		Shop shop = new Shop();
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( msg );
		shop.setOpenId(jsonObject.getString("openid"));
		shop.setNickName(jsonObject.getString("nickname"));
		shop.setSex(Sex.valueOf(jsonObject.getInt("sex")));
		shop.setCity(jsonObject.getString("city"));
		shop.setProvince(jsonObject.getString("province"));
		shop.setCountry(jsonObject.getString("country"));
		shop.setHeadImgUrl(jsonObject.getString("headimgurl"));
		long createTime = jsonObject.getLong("subscribe_time") * 1000L;
		Timestamp timestamp = new Timestamp(createTime);
		shop.setSubscribeTime(timestamp);
		return shop;
	}
}