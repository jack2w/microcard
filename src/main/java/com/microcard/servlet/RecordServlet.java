package com.microcard.servlet;

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
 * Servlet implementation class RecordServlet
 */
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			Long userId = Long.valueOf(request.getParameter("userId")); 
			Logger.getOperLogger().info("userId : " + userId);
			HibernateUtil.instance().beginTransaction();
			User user = DAOFactory.createUserDAO().getUserByID(userId);
			String userName = user.getName();
			response.setCharacterEncoding("utf-8");  
		    PrintWriter out = response.getWriter();  
	        //将数据拼接成JSON格式  
	        out.print("{\"userName\":\""+userName+"\"}");  
	        out.flush();  
	        out.close();  
		} catch (HibernateException e) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("查询会员ID失败");
		} catch(Exception ex){
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("查询会员ID失败");
		} finally{
			HibernateUtil.instance().closeSession();
		}
	}

}
