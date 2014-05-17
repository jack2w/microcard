package com.microcard.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.dao.hibernate.HibernateUtil;

public class SalesDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransactionAndColoseSession();
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
		fail("Not yet implemented");
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
	public void testDeleteCommodity() {
		fail("Not yet implemented");
	}

}
