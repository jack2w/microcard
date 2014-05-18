package com.microcard.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;

public class ShopDAOImplTest {

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

	
	public void testGetUsersByShop() {
		Shop s = DAOFactory.createShopDAO().getShopByID(2);
		User[] us = DAOFactory.createShopDAO().getUsersByShop(s, 5, 10);
		for(User  u : us){
			System.out.println(u.getOpenId());
		}
	}

	@Test
	public void testUpdateShop() {
		fail("Not yet implemented");
	}

	
	public void testAddShop() {
	Shop s1 = new Shop();
	s1.setOpenId("100001");
	Shop s2 = new Shop();
	s2.setOpenId("100002");
	DAOFactory.createShopDAO().addShop(s1,s2);

	}

	@Test
	public void testAddCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelteCommoditity() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testgetShopByOpenID() {
	Shop s1 = DAOFactory.createShopDAO().getShopByOpenID("100001");
	System.out.println(s1.getOpenId());
	}
	
	@Test
	public void testUpdateSales() {
		fail("Not yet implemented");
	}

}
