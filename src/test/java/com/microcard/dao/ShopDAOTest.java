package com.microcard.dao;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;

public class ShopDAOTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransactionAndColoseSession();
	}

	@Test
	public void testGetShops() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddShop() {
		Shop s = new Shop();
		s.setOpenId("8888888");
		HibernateDAOFactory.createShopDAO().addShop(s);
	}

	@Test
	public void testGetUsersByShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCommodity() {
//		Shop s = HibernateDAOFactory.createShopDAO().getShopByID(1);
//		HibernateDAOFactory.createShopDAO().addCommodity(s, new Commodity());
	}

	@Test
	public void testAddSales() {
//		Shop s = HibernateDAOFactory.createShopDAO().getShopByID(1);
//		HibernateDAOFactory.createShopDAO().addSales(s, new Sales());
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testaddUer() {
		User u = new User();
		u.setOpenId("66666666");
		HibernateDAOFactory.createUserDAO().saveUser(u);
	}
	
	@Test
	public void testUserAndShop(){
      User u = HibernateDAOFactory.createUserDAO().getUserByID("66666666");
      Shop  s = HibernateDAOFactory.createShopDAO().getShopByID(1);
      Set ss =  new HashSet();
//      测试时发现这里不能用，但在tomcat里可用
      s.setUsers(new HashSet());
      s.getUsers().add(u);
      
      HibernateDAOFactory.createUserDAO().saveOrUpdate(u);
      HibernateDAOFactory.createShopDAO().saveOrUpdate(s);
      
	}

}
