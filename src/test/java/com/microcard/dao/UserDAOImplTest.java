/**
 * 
 */
package com.microcard.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Record;
import com.microcard.bean.Sex;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;

/**
 * @author jiyaguang
 *
 */
public class UserDAOImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		HibernateUtil.instance().commitTransactionAndColoseSession();

	}

	@Test
	public void test() {
		testAddUser();
//		testgetUsers();
//		testdeleteUser();
//		getUserByID("100001");
//		testaddRecords();
	}
	
	public void testAddUser(){
	User u1 = new User();
	u1.setOpenId("100001");
	u1.setAddress("shanghai");
	u1.setSex(Sex.male);
	try{
		HibernateDAOFactory.createUserDAO().saveUser(u1);
	} catch(Exception e){
		e.printStackTrace();
	}
	}
	
	public void testgetUsers(){
		List l = HibernateDAOFactory.createUserDAO().getUsers();
		if(l.size() > 0){
			Assert.assertTrue(true);
		}
	}
	
	public void testdeleteUser() {
		List<User> l = HibernateDAOFactory.createUserDAO().getUsers();
		for(User  uu : l){
			HibernateDAOFactory.createUserDAO().deleteUser(uu);
		}
		Assert.assertTrue(HibernateDAOFactory.createUserDAO().getUsers().size() == 0);
	}
	
	public User getUserByID(String id) {
		User u = HibernateDAOFactory.createUserDAO().getUserByID(id);
		Assert.assertTrue(u!=null);
		return u;
	}

	public void testaddRecords(){
		User u = getUserByID("100001");

		 HibernateDAOFactory.createUserDAO().addRecords(u, new Record());
		Session s = HibernateUtil.instance().currentSession();
	}

}
