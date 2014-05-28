package com.microcard.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
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
		Shop s = DAOFactory.createShopDAO().getShopByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
		s.setAddress("beijing");
		DAOFactory.createShopDAO().updateShop(s);
	}

	@Test
	public void testAddShop() {
		try {
			Shop shop = WeixinClient.getShopInfo("o2gmduF6NicaYlrYc0OKsbYeYVE4");
			DAOFactory.createShopDAO().addShop(shop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testAddCommodity() {
		try{
		Shop shop =  DAOFactory.createShopDAO().getShopByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
		Commodity[] cs = new Commodity[30];
		for(int i = 0; i < 30; i++){
			Commodity c = new Commodity();
			c.setName("手表" + i);
			c.setPrice(10000 + (i * 1000));
			cs[i] = c;
		}
		DAOFactory.createShopDAO().addCommodity(shop, cs);
//		DAOFactory.createShopDAO().addCommodity(shop, new Commodity());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddSales() {
		try{
			Sales[] ss = new Sales[100];
			for(int i = 0; i < 100; i++){
				Sales s = new Sales();
				s.setName("123");
				ss[i] = s;
			}
			Shop s =  DAOFactory.createShopDAO().getShopByOpenID("o2gmduEx55FVt10DoRwMcHC7H5w8");
			 DAOFactory.createShopDAO().addSales(s, ss);
	//		List<Sales> sales = DAOFactory.createShopDAO().getSalesByShop("o2gmduEx55FVt10DoRwMcHC7H5w8", 0, 100);
	//		System.out.println(sales.size());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testgetSales(){
		List<Sales> sales = DAOFactory.createShopDAO().getSalesByShop("o2gmduEx55FVt10DoRwMcHC7H5w8", 0, 100);
		System.out.println(sales.size());
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
