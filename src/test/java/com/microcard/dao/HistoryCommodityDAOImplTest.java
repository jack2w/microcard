package com.microcard.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Commodity;
import com.microcard.bean.HistoryCommodity;
import com.microcard.dao.hibernate.HibernateUtil;

public class HistoryCommodityDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransaction();
	}

	@Test
	public void testGetAllHistoryCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteHistoryCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHistoryCommodityByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateHistoryCommodity() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveHistoryCommodity() {
		HistoryCommodity hc =new HistoryCommodity();
		hc.setId(2);
		DAOFactory.createHistoryCommodityDAO().saveHistoryCommodity(hc);
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

}
