package com.microcard.msg.processor.user;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.ReceivedSubscribeMsg;

public class UserSubscribeProcessorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProccess() {
		try{
			HibernateUtil.instance().beginTransaction();
			//从微信服务器获得用户信息，如果数据库里存在会更新用户基本数据，不会影响关联信息，如果不存在会添加一条用户数据
			User u = WeixinClient.getUserInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
			DAOFactory.createUserDAO().saveUser(u);
			
			String eventkey = "qrscene_1";
			if(eventkey != null && eventkey.length() != 0){
				String shopid = eventkey.substring(eventkey.lastIndexOf("_")+1, eventkey.length());
				Shop s = DAOFactory.createShopDAO().getShopByID(Long.parseLong(shopid));
				if(s == null){
					Logger.getOperLogger().warn("用户订阅事件获取的shop信息不存在。");
					return;
				}
				DAOFactory.createUserDAO().addShops(u, s);
				HibernateUtil.instance().commitTransaction();
	} 
	}  catch(HibernateException e){
			Logger.getOperLogger().error(e, "user subscribe message process failed,  database error.");
			HibernateUtil.instance().rollbackTransaction();
	}  catch(Exception ex){
			Logger.getOperLogger().error(ex, "user subscribe message process failed, unkonwn error.");
			HibernateUtil.instance().rollbackTransaction();
	}finally{
			HibernateUtil.instance().closeSession();
	}
		}

}
