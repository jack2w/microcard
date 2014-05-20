package com.microcard.msg.processor;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.ReceivedSubscribeMsg;

public class UnSubscribeProcessorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProccess() throws Exception {

			try{
				HibernateUtil.instance().beginTransaction();
				
				//根据订阅消息获得的openid,向微信获取该用户的信息
				Shop shop = WeixinClient.getShopInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
				
				Shop s = DAOFactory.createShopDAO().getShopByOpenID(shop.getOpenId());
				if(s != null){
					DAOFactory.createShopDAO().deleteLogicalShop(s);
				} else{
					throw new Exception("cant delete the shop from database");
				}
				HibernateUtil.instance().commitTransaction();
			
			} catch(HibernateException e){
				Logger.getOperLogger().error(e, "unscribe event save to database failed.");
				HibernateUtil.instance().rollbackTransaction();
				throw new Exception("unscribe event save to database failed");
			} finally{
				HibernateUtil.instance().closeSession();
			}
	
		
		
	
	}

}
