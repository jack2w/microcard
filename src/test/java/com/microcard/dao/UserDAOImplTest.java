package com.microcard.dao;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Record;
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
			for(int i = 0; i < 20; i++){
				Shop s = new Shop();
				s.setName("Nike");
				s.setOpenId("abc" + i);
				DAOFactory.createUserDAO().addShops(u, s);
			}
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
		User u = DAOFactory.createUserDAO().getUserByID(1);
		Shop s = DAOFactory.createShopDAO().getShopByID(11);
		for(int i = 0; i < 20; i++){
			Record r = new Record();
			r.setPrice(1000);
			r.setBonus(200);
			r.setTime(new Timestamp(new Date().getTime() - i * 10000));
			r.setShop(s);
			DAOFactory.createUserDAO().addRecords(u, r);
		}
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
