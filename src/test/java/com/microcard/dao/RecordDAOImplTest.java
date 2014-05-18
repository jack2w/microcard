package com.microcard.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Record;
import com.microcard.dao.hibernate.HibernateUtil;

public class RecordDAOImplTest {

	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransactionAndColoseSession();
	}

	@Test
	public void testGetRecords() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRecord() {
		Record r = DAOFactory.createRecordDAO().getRecordByID(1);
		 DAOFactory.createRecordDAO().deleteRecord(r);
	}

	@Test
	public void testGetRecordByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateRecord() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveRecord() {
//	 DAOFactory.createRecordDAO().saveRecord(new Record());
	}

	@Test
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

}
