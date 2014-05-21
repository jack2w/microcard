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
import com.microcard.msg.ReceivedUnsubscribeMsg;

/**
 * @author jiyaguang
 *
 */
public class UnSubscribeProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	/**
	 * 成功返回空字符串，失败返回空，一般不会失败
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		
		if(!(msg instanceof ReceivedUnsubscribeMsg)) {
			
			throw new Exception("is not Subscribe msg" + msg.toString());
		}
		try{
				HibernateUtil.instance().beginTransaction();
				
				ReceivedUnsubscribeMsg subscribeMsg = (ReceivedUnsubscribeMsg)msg;
				//根据订阅消息获得的openid,向微信获取该用户的信息
				Shop shop = WeixinClient.getShopInfo(subscribeMsg.getFromUserName());
				
				Shop s = DAOFactory.createShopDAO().getShopByOpenID(shop.getOpenId());
				if(s != null){
					DAOFactory.createShopDAO().deleteLogicalShop(s);
				} else{
					throw new Exception("cant delete the shop from database");
				}
				HibernateUtil.instance().commitTransaction();
			
			} catch(HibernateException e){
				Logger.getOperLogger().error(e, "unsubscribe message process failed,  database error.");
				HibernateUtil.instance().rollbackTransaction();
			}  catch(Exception ex){
				Logger.getOperLogger().error(ex, "unsubscribe message process failed, unkonwn error.");
				HibernateUtil.instance().rollbackTransaction();
			}finally{
				HibernateUtil.instance().closeSession();
			}
		return null;
		
		
	}

}
