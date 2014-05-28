package com.microcard.servlet.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.User;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
     private static Logger log = Logger.getOperLogger();
     private static final String errorMsg = "修改用户信息失败";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();	
			String userid = request.getParameter("userid");
			String name = request.getParameter("username");
			String phone = request.getParameter("userphone");
			String address = request.getParameter("useraddress");
			
			log.debug("userid : " + userid);
			log.debug("username : " + name);
			log.debug("userphone : " + phone);
			log.debug("useraddress : " + address);
			
			HibernateUtil.instance().beginTransaction();
			User user = DAOFactory.createUserDAO().getUserByID(Long.parseLong(userid));
			user.setName(name);
			user.setPhone1(phone);
			user.setAddress(address);
			DAOFactory.createUserDAO().updateUser(user);
			out.print("{\"name\":\"" + user.getName()
					+ "\",\"phone\":\"" + user.getPhone1()
					+ "\",\"address\":\"" + user.getAddress() + "\"}");
			
			HibernateUtil.instance().commitTransaction();
		} catch (Exception ex) {
			HibernateUtil.instance().rollbackTransaction();
			log.error(ex, errorMsg);
			out.write(errorMsg + ex.getMessage());
		} finally {
			out.close();
			HibernateUtil.instance().closeSession();
		}
		
		
	}

}
