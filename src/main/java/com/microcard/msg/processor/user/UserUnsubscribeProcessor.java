/**
 * 
 */
package com.microcard.msg.processor.user;

import org.hibernate.HibernateException;

import com.microcard.bean.User;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedUnsubscribeMsg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class UserUnsubscribeProcessor  implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		// TODO 根据ReceivedUnscribeMsg中fromUserName对应User的openid找到User
		// TODO 如果存在这个User，然后把这个User设置逻辑删除，如果不存在不处理，日志中记录warn日志，说明取消订阅不应该存在找不到的用户
		if(!(msg instanceof ReceivedUnsubscribeMsg)){
			return null;
		}
		try{
			HibernateUtil.instance().beginTransaction();
			
			ReceivedUnsubscribeMsg unsubscribeMsg = (ReceivedUnsubscribeMsg)msg;
			//根据订阅消息获得的openid,向微信获取该用户的信息
			
			User u = DAOFactory.createUserDAO().getUserByOpenID(unsubscribeMsg.getFromUserName());
			
			if(u == null){
				Logger.getOperLogger().warn("取消订阅不应该存在不存在的用户。");
			} else{
				DAOFactory.createUserDAO().deleteLogicalUser(u);
			}

			HibernateUtil.instance().commitTransaction();
		
		} catch(HibernateException e){
			Logger.getOperLogger().error(e, "user unsubscribe message process failed,  database error.");
			HibernateUtil.instance().rollbackTransaction();
		}  catch(Exception ex){
			Logger.getOperLogger().error(ex, "user unsubscribe message process failed, unkonwn error.");
			HibernateUtil.instance().rollbackTransaction();
		}finally{
			HibernateUtil.instance().closeSession();
		}
		
		return null;
	}

}
