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

public class UserScanMsgProcessorTest {

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
		User u = DAOFactory.createUserDAO().getUserByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
		Shop s = DAOFactory.createShopDAO().getShopByID(Long.parseLong("1"));

		if(u == null){
			// u 从微信服务器获得用户信息
			u = WeixinClient.getUserInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
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
	}

}
