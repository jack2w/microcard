package com.microcard.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microcard.dao.DataSourceUtil;
import com.microcard.dao.HibernateDAOFactory;
import com.microcard.dao.impl.UserDAOImpl;
import com.microcard.domain.User;
import com.microcard.log.Logger;


/**
 * Servlet implementation class TestDataSource
 */
public class TestDataSource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestDataSource() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.getMsgLogger().info("123213213");
		System.out.println(DataSourceUtil.isConnectedOK());
		List<User> users = HibernateDAOFactory.createUserDAO().getUsers();
//		List<User> users = new UserDAOImpl().getUsers();
		for(User u : users){
			System.out.println(u);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
