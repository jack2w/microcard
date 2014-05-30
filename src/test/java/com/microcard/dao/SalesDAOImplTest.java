package com.microcard.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.dao.hibernate.HibernateUtil;

public class SalesDAOImplTest {

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
	public void testGetSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSalesByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateSales() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveSales() {
		try {
			Shop shop = new Shop();
			shop.setOpenId("abc01");
			shop.setName("nike");
			shop.setAddress("shanghai");
			
			DAOFactory.createShopDAO().addShop(shop);
			
			for(int i =0 ; i< 20; i++){
				Commodity c = new Commodity();
				c.setName("shoe");
				c.setPrice(800);
				DAOFactory.createShopDAO().addCommodity(shop, c);
			}
			
			Sales sale = new Sales();
			sale.setName("duanwu");
			sale.setPrice(1000);
			sale.setBonus(200);
			DAOFactory.createShopDAO().addSales(shop, sale);
			
			Sales sale2 = new Sales();
			sale2.setName("qingren");
			sale2.setPrice(1000);
			sale2.setBonus(200);
			DAOFactory.createShopDAO().addSales(shop, sale2);
			
			List<Commodity> cs = DAOFactory.createShopDAO().getCommodity("abc01", 0, 20);
			
			//向营销增加商品
			DAOFactory.createSalesDAO().addCommodity(sale, cs.toArray(new Commodity[cs.size()]));
			
			Commodity c = DAOFactory.createCommodityDAO().getCommodityByID(2);
			Sales ss = DAOFactory.createSalesDAO().getSalesByID(2);
			//向商品增加营销
			DAOFactory.createCommodityDAO().addSales(c, ss);

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
	public void testAddCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCommodity() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetCommodityBySales(){
		Sales s =  DAOFactory.createSalesDAO().getSalesByID(1);
		List<Commodity> cs = DAOFactory.createSalesDAO().getCommodityBySales(s, 0, 20);
		System.out.print(cs.size());
	}

	@Test
	public void testDeleteCommodity() {
		Sales s =  DAOFactory.createSalesDAO().getSalesByID(1);
		Commodity c = DAOFactory.createCommodityDAO().getCommodityByID(1);
		DAOFactory.createSalesDAO().deleteCommodity(s, c);
	}

}
