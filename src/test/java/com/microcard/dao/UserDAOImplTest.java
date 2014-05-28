package com.microcard.dao;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.client.WeixinClient;
import com.microcard.dao.hibernate.HibernateUtil;

public class UserDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		try{
			HibernateUtil.instance().commitTransaction();
		}
		catch(HibernateException e){
			HibernateUtil.instance().rollbackTransaction();
		}
	}

	@Test
	public void testGetUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePhsycalUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveUser() {
		try {
			User u = WeixinClient.getUserInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
			DAOFactory.createUserDAO().saveUser(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
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
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRecords() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddShops() {
		try {
			User u2 = DAOFactory.createUserDAO().getUserByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w5");
			User u3 = DAOFactory.createUserDAO().getUserByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w4");
			User u4 = DAOFactory.createUserDAO().getUserByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w3");
			Shop s = DAOFactory.createShopDAO().getShopByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
			DAOFactory.createUserDAO().addShops(u2, s);
			DAOFactory.createUserDAO().addShops(u3, s);
			DAOFactory.createUserDAO().addShops(u4, s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLogicalUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByOpenID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopsByUser() {
		fail("Not yet implemented");
	}

}
