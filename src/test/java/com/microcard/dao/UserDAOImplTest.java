package com.microcard.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Record;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class UserDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransactionAndColoseSession();
	}

	@Test
	public void testGetUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	
	public void testGetUserByID() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		for(Record r : u.getRecords()){
			Logger.getOperLogger().info(String.valueOf(r.getId()));
		}
	}

	
	public void testUpdateUser() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		u.setCity("shanghai");
		DAOFactory.createUserDAO().saveUser(u);

	}


	public void testSaveUser() {
		User u1 = new User();
		u1.setOpenId("100008");
		User u2 = new User();
		u2.setOpenId("100002");
		User u3 = new User();
		u3.setOpenId("100003");
		User u4 = new User();
		u4.setOpenId("100004");
		User u5 = new User();
		u5.setOpenId("100005");
		User u6 = new User();
		u6.setOpenId("100006");
		User u7 = new User();
		u7.setOpenId("100007");
		
		
		DAOFactory.createUserDAO().saveUser(u1, u2, u3, u4, u5, u6, u7);
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddRecords() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateRecords() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().addRecords(u, new Record());
	}


	public void testDeleteRecords() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().deleteRecords(u, null);
	}

	@Test
	public void testAddShops() {
		User u1 = DAOFactory.createUserDAO().getUserByID("100001");
		User u2 = DAOFactory.createUserDAO().getUserByID("100002");
		User u3 = DAOFactory.createUserDAO().getUserByID("100003");
		User u4 = DAOFactory.createUserDAO().getUserByID("100004");
		User u5 = DAOFactory.createUserDAO().getUserByID("100005");
		User u6 = DAOFactory.createUserDAO().getUserByID("100006");
		User u7 = DAOFactory.createUserDAO().getUserByID("100007");
		Shop s = DAOFactory.createShopDAO().getShopByID(2);

		DAOFactory.createUserDAO().addShops(u1, s);
		DAOFactory.createUserDAO().addShops(u2, s);
		DAOFactory.createUserDAO().addShops(u3, s);
		DAOFactory.createUserDAO().addShops(u4, s);
		DAOFactory.createUserDAO().addShops(u5, s);
		DAOFactory.createUserDAO().addShops(u6, s);
		DAOFactory.createUserDAO().addShops(u7, s);
	}

	
	public void testDeleteShop() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().deleteUser(u);
	}

}
