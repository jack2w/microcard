/**
 * 
 */
package com.microcard.msg.processor.user;

import org.hibernate.HibernateException;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedSubscribeMsg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class UserSubscribeProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		// TODO 当用户扫描一个带参数的二维码时，ReceivedSubscribeMsg的eventKey有值
		// TODO 当用户扫描一个不带参数的二维码时，ReceivedSubscribeMsg的eventKey无值
		// TODO eventKey的格式是qrscene_123123,qrscene是前缀，123123是Shop的id
		// TODO 如果eventKey无值，则检查该User是否存在，不存在则增加记录，如果存在则修改deleteFlag为false，并更新用户的信息
		// TODO 如果eventKey有值，做无值时的用户更新，并则根据qrscene_123123的Shop id找到Shop，建立与该Shop的关系，注意不要重复建立
		if(!(msg instanceof ReceivedSubscribeMsg)) {
			
			throw new Exception("is not Subscribe msg" + msg.toString());
		}
		try{
			
			HibernateUtil.instance().beginTransaction();
			ReceivedSubscribeMsg receivedsubscribemsg = (ReceivedSubscribeMsg)msg;
				
				//检查user是否存在，不存在增加记录，存在更新用户信息
//				User u = DAOFactory.createUserDAO().getUserByOpenID(receivedsubscribemsg.getFromUserName()) ;
//				if(u == null){
//					u = WeixinClient.getUserInfo(receivedsubscribemsg.getFromUserName());
//				}
//				if(u.isDeleteFlag()){
//					DAOFactory.createUserDAO().saveUser(u);
//				}
				//从微信服务器获得用户信息，如果数据库里存在会更新用户基本数据，不会影响关联信息，如果不存在会添加一条用户数据
			User u = WeixinClient.getUserInfo(receivedsubscribemsg.getFromUserName());
			DAOFactory.createUserDAO().saveUser(u);
				
			String eventkey = receivedsubscribemsg.getEventKey();
			if(eventkey != null && eventkey.length() != 0){
				String shopid = eventkey.substring(eventkey.lastIndexOf("_") + 1, eventkey.length());
				Shop s = DAOFactory.createShopDAO().getShopByID(Long.parseLong(shopid));
				if(s == null){
					Logger.getOperLogger().warn("用户订阅事件获取的shop信息不存在。");
				}else {
					DAOFactory.createUserDAO().addShops(u, s);	
				}
			}
			HibernateUtil.instance().commitTransaction();
		}  catch(HibernateException e){
				Logger.getOperLogger().error(e, "user subscribe message process failed,  database error.");
				HibernateUtil.instance().rollbackTransaction();
				throw e;
		}  catch(Exception ex){
				Logger.getOperLogger().error(ex, "user subscribe message process failed, unkonwn error.");
				HibernateUtil.instance().rollbackTransaction();
				throw ex;
		}finally{
				HibernateUtil.instance().closeSession();
		}
		
		return null;
	}

}
