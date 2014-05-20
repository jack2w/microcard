package com.microcard.dao;

import static org.junit.Assert.*;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.client.WeixinClient;
import com.microcard.dao.hibernate.HibernateUtil;

public class ShopDAOImplTest {

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
	public void testGetShops() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePhysicalShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLogicalShop() {
		fail("Not yet implemented");
	}

	
	public void testGetShopByID() {
		Shop s = DAOFactory.createShopDAO().getShopByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
		System.out.println(s.getOpenId());
	}

	@Test
	public void testGetUsersByShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddShop() {
		try {
			Shop shop = WeixinClient.getShopInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
			DAOFactory.createShopDAO().addShop(shop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void testUpdateSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopByOpenID() {
		fail("Not yet implemented");
	}

}
