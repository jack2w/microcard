package com.microcard.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.HistorySales;
import com.microcard.dao.hibernate.HibernateUtil;

public class HistorySalesDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransaction();
	}

	@Test
	public void testGetAllHistorySales() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteHistorySales() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHistorySalesByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateHistorySales() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveHistorySales() {
		
		DAOFactory.createHistorySalesDAO().saveHistorySales(new HistorySales());
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

}
