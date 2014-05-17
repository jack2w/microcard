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
		User u = new User();
		u.setOpenId("100001");
		DAOFactory.createUserDAO().saveUser(u);
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddRecords() {
		fail("Not yet implemented");
	}

	
	public void testUpdateRecords() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().addRecords(u, new Record());
	}

	@Test
	public void testDeleteRecords() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().deleteRecords(u, null);
	}

	
	public void testAddShops() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		Shop s = DAOFactory.createShopDAO().getShopByID(2);
		DAOFactory.createUserDAO().addShops(u, s);
	}

	
	public void testDeleteShop() {
		User u = DAOFactory.createUserDAO().getUserByID("100001");
		DAOFactory.createUserDAO().deleteUser(u);
	}

}
