/**
 * 
 */
package com.microcard.msg.processor;

import java.sql.Timestamp;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.bean.Sex;
import com.microcard.bean.Shop;
import com.microcard.client.HttpDefaultClient;
import com.microcard.exception.WeixinException;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedSubscribeMsg;
import com.microcard.token.TokenManager;

/**
 * 订阅消息处理类，
 * @author jack
 *
 */
public class SubscribeProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		//订阅消息，则接受到的Msg应该是ReceivedSubscribeMsg，否则就不处理
		if(!(msg instanceof ReceivedSubscribeMsg)) {
			
			throw new WeixinException("is not Subscribe msg" + msg.toString());
		}
		
		ReceivedSubscribeMsg subscribeMsg = (ReceivedSubscribeMsg)msg;
		//根据订阅消息获得的openid,向微信获取该用户的信息
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+TokenManager.getShopToken()+"&openid="+subscribeMsg.getFromUserName()+"&lang=zh_CN";
		
		HttpDefaultClient client = new HttpDefaultClient(url);
		String result = client.doGet();
		//如果微信返回的是错误信息则抛出异常
		WeixinException exception = WeixinException.paserException(result);
		if(exception != null) {
			throw exception;
		}
		//根据微信返回的结果解析成shop类
		
		Shop shop = parseShop(result);
		Logger.getOperLogger().debug("operid: " + shop.getOpenId());
		//TODO 1. 如果该Shop在shop表中不存在，则检查HistoryShop中是否存在,如果存在则先把HistoryShop，HistoryCommodity,HistorySales中该信息放到
		//TODO 2. 
		
		return null;
	}
	
	public Shop parseShop(String msg) {
		
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
