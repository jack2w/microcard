package com.microcard.dao;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;

/**
 * Servlet implementation class TestDAO
 */
public class TestDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;

   private User u1; 
   private User u2; 
   private User u3; 
   
   private Shop s1;
   private Shop s2;
   
   private Commodity c1;
   private Commodity c2;
   
   private Sales sale1;
   private Sales sale2;
   
	
    public TestDAO() {
    	u1 = new User();
		u1.setOpenId("100001");
		u2 = new User();
		u2.setOpenId("100002");
		u3 = new User();
		u3.setOpenId("100003");
		s1 = new Shop();
		s2 = new Shop();
		
		sale1 = new Sales();
		sale2 = new Sales();
		
		c1 = new Commodity();
		c2 = new Commodity();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HibernateUtil.instance().beginTransaction();
		process();
		HibernateUtil.instance().commitTransactionAndColoseSession();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void process(){
		testAddUser();
	}
	
	private void testAddUser(){
		
		HibernateDAOFactory.createUserDAO().saveUser(u1);
		HibernateDAOFactory.createUserDAO().saveUser(u2);
		HibernateDAOFactory.createUserDAO().saveUser(u3);
		
		HibernateDAOFactory.createShopDAO().addShop(s1);
		HibernateDAOFactory.createShopDAO().addShop(s2);
		
		if(s1.getUsers() == null){
			s1.setUsers(new HashSet());
			s1.getUsers().add(u1);
			s1.getUsers().add(u2);
		}
		
		if(s1.getCommodities() == null){
			
//			s1.getCommodities().add(c1);
//			s1.getCommodities().add(c2);
			HashSet ss = new HashSet();
			ss.add(c1);
			ss.add(c2);
			s1.setCommodities(ss);
//			c2.setShop(s1);
//			c1.setShop(s1);
			
		}
		HibernateDAOFactory.createShopDAO().updateShop(s1);
		
		s1.getUsers().remove(u1);
		HibernateDAOFactory.createShopDAO().updateShop(s1);
		
	}

}
