package com.microcard.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Commodity;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class CommodityDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransaction();
	}

	
	public void testGetAllCommodity() {
		List<Commodity> l = DAOFactory.createCommodityDAO().getAllCommodity();
		for(Commodity c : l){
			Logger.getOperLogger().info(String.valueOf(c.getId()));
		}
		
	}

	@Test
	public void testDeleteCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCommodityByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveCommodity() {
//		Commodity c1 = new Commodity();
//		c1.setName("手机");
//		DAOFactory.createCommodityDAO().saveCommodity(new Commodity());
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

}
