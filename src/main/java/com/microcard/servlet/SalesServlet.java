package com.microcard.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Sales;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * Servlet implementation class SalesServlet
 */
public class SalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesServlet() {
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
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String saleId = request.getParameter("saleId");
		Logger.getOperLogger().info("saleId : " + saleId);
		try {
			// 获取Sales对象
			Sales sales = DAOFactory.createSalesDAO().getSalesByID(Long.valueOf(saleId));
			DAOFactory.createSalesDAO().deleteSales(sales);
			HibernateUtil.instance().commitTransaction();
			out.write("{\"result\":\"" + "操作成功" + "\"}");
		} catch (HibernateException exception) {
			HibernateUtil.instance().rollbackTransaction();
			out.write("删除营销失败：" + exception.getMessage());
			Logger.getOperLogger().warn("删除营销失败" + exception.getMessage());
		} catch (Exception exception) {
			HibernateUtil.instance().rollbackTransaction();
			out.write("删除营销失败：" + exception.getMessage());
			Logger.getOperLogger().warn("删除营销失败" + exception.getMessage());
		} finally {
			HibernateUtil.instance().closeSession();
			out.flush();
			out.close();
		}
	}

}
