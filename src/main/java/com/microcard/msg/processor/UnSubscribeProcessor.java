/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.bean.Shop;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
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
		
		String result = "";
			if(!(msg instanceof ReceivedUnsubscribeMsg)){
				return null;
			}
			HibernateUtil.instance().beginTransaction();
			Shop s = DAOFactory.createShopDAO().getShopByOpenID(msg.getFromUserName());
			if(s != null){
				DAOFactory.createShopDAO().deleteLogicalShop(s);
			}
			try{
				HibernateUtil.instance().commitTransactionAndColoseSession();
			} catch(Exception e){
				Logger.getOperLogger().error(e, "unscribe event save to database failed.");
				HibernateUtil.instance().rollbackTransaction();
				result = null;
			} finally{
				HibernateUtil.instance().closeSession();
			}
		return result;
		
		
	}

}
