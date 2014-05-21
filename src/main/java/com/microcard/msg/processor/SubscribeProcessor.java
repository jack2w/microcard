/**
 * 
 */
package com.microcard.msg.processor;

import org.hibernate.HibernateException;

import com.microcard.bean.Shop;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedSubscribeMsg;

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
			
			throw new Exception("is not Subscribe msg" + msg.toString());
		}
		
		ReceivedSubscribeMsg subscribeMsg = (ReceivedSubscribeMsg)msg;
		//根据订阅消息获得的openid,向微信获取该用户的信息
		Shop shop = WeixinClient.getShopInfo(subscribeMsg.getFromUserName());
		//TODO 1. 如果该Shop在shop表中不存在，增加一条记录
		//TODO 2. 如果该Shop在shop表中存在，则修改shop信息，并且把delete_flag置否
		try{
			HibernateUtil.instance().beginTransaction();
			Shop s = DAOFactory.createShopDAO().getShopByOpenID(shop.getOpenId());
			if(s != null){
				DAOFactory.createShopDAO().addShop(s);
			} else{
				DAOFactory.createShopDAO().addShop(shop);
			}
			HibernateUtil.instance().commitTransaction();
		} catch(HibernateException e){
			Logger.getOperLogger().error(e, "subscribe message process failed,  database error.");
			HibernateUtil.instance().rollbackTransaction();
		} catch(Exception ex ){
			Logger.getOperLogger().error(ex, "subscribe message process failed, unkonwn error.");
			HibernateUtil.instance().rollbackTransaction();
		}finally{
			HibernateUtil.instance().closeSession();
		}

		return null;
	}

}
