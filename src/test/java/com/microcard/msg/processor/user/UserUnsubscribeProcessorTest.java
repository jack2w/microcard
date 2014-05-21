package com.microcard.msg.processor.user;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.User;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.msg.ReceivedUnsubscribeMsg;

public class UserUnsubscribeProcessorTest {

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
			
			//根据订阅消息获得的openid,向微信获取该用户的信息
			
			User u = DAOFactory.createUserDAO().getUserByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
			
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

	}

}
