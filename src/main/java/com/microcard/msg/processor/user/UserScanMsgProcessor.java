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
import com.microcard.msg.ReceivedScanMsg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class UserScanMsgProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		if(!(msg instanceof ReceivedScanMsg)){
			return null;
		}
		//TODO 根据ReceivedScanMsg的fromUserName对应User的openid找到User
		//TODO 根据ReceivedScanMsg的eventKey对应Shop的id，找到Shop
		//TODO 如果该User不存在则添加一条User记录，存在则检查该Shop是否已经跟这个User建立关系
		//TODO 如果这个User和Shop没有关系，则建立他们之间的关系
		try{
			ReceivedScanMsg receivescanmsg = (ReceivedScanMsg)msg;
			User u = DAOFactory.createUserDAO().getUserByOpenID(receivescanmsg.getFromUserName());
			Shop s = DAOFactory.createShopDAO().getShopByID(Long.parseLong(receivescanmsg.getEventKey()));
	
			if(u == null){
				// u 从微信服务器获得用户信息
				u = WeixinClient.getUserInfo(receivescanmsg.getFromUserName());
				DAOFactory.createUserDAO().saveUser(u);
			} else if(u.isDeleteFlag()){
				DAOFactory.createUserDAO().saveUser(u);
			}
			
			DAOFactory.createUserDAO().addShops(u, s);
			
			HibernateUtil.instance().commitTransaction();
		} catch(HibernateException e){
			Logger.getOperLogger().error(e, "user scan message process failed,  database error.");
			HibernateUtil.instance().rollbackTransaction();
		}  catch(Exception ex){
			Logger.getOperLogger().error(ex, "user scan message process failed, unkonwn error.");
			HibernateUtil.instance().rollbackTransaction();
		}finally{
			HibernateUtil.instance().closeSession();
		}
		return null;
	}

}
