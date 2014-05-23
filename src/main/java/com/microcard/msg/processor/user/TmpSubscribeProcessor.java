/**
 * 
 */
package com.microcard.msg.processor.user;

import org.hibernate.HibernateException;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.ShopDAO;
import com.microcard.dao.UserDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedSubscribeMsg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class TmpSubscribeProcessor implements IMsgProcessor {

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
				
			UserDAO userDao = DAOFactory.createUserDAO();
			
			User userD = userDao.getUserByOpenID(receivedsubscribemsg.getFromUserName());	
			User userW = WeixinClient.getUserInfo(receivedsubscribemsg.getFromUserName());
			if(userD != null) {
				userD.setAddress(userW.getAddress());
				userD.setCity(userW.getCity());
				userD.setCountry(userW.getCountry());
				userD.setHeadImgUrl(userW.getHeadImgUrl());
				userD.setNickName(userW.getNickName());
				userD.setSex(userW.getSex());
				userD.setProvince(userW.getProvince());
				userDao.saveUser(userD);
			}else {
				userDao.saveUser(userW);
			}
			
			ShopDAO shopDao = DAOFactory.createShopDAO();
			Shop shopD = shopDao.getShopByOpenID(receivedsubscribemsg.getFromUserName());
			Shop shopW = WeixinClient.getShopInfo(receivedsubscribemsg.getFromUserName());
			if(shopD != null) {
				shopD.setAddress(shopW.getAddress());
				shopD.setCity(shopW.getCity());
				shopD.setCountry(shopW.getCountry());
				shopD.setHeadImgUrl(shopW.getHeadImgUrl());
				shopD.setNickName(shopW.getNickName());
				shopD.setSex(shopW.getSex());
				shopD.setProvince(shopW.getProvince());
				shopDao.addShop(shopD);
			}else {
				shopDao.addShop(shopW);
			}
				
			String eventkey = receivedsubscribemsg.getEventKey();
			if(eventkey != null && eventkey.length() != 0){
				String shopid = eventkey.substring(eventkey.lastIndexOf("_") + 1, eventkey.length());
				Shop s = DAOFactory.createShopDAO().getShopByID(Long.parseLong(shopid));
				if(s == null){
					Logger.getOperLogger().warn("用户订阅事件获取的shop信息不存在。");
				}else {
					DAOFactory.createUserDAO().addShops(userW, s);	
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
