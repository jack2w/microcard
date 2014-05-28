package com.microcard.servlet;

import java.io.IOException;

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
 * Servlet implementation class SalesDetailServlet
 */
public class SalesDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getOperLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SalesDetailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String salesId = request.getParameter("salesId");
		String salesName = request.getParameter("salesName");
		String salesPrice = request.getParameter("salesPrice");
		String salesBonus = request.getParameter("salesBonus");
		log.info("salesId : " + salesId);
		log.info("salesName : " + salesName);
		log.info("salesPrice : " +salesPrice);
		log.info("salesBonus: " + salesBonus);
		try {
			HibernateUtil.instance().beginTransaction();
			if (salesId == null || salesId.equals("")) {
				Sales sales = new Sales();
				sales.setName(salesName);
				sales.setPrice(Double.valueOf(salesPrice));
				sales.setBonus(Double.valueOf(salesBonus));
				DAOFactory.createSalesDAO().saveSales(sales);
			} else {
				Sales sales = DAOFactory.createSalesDAO().getSalesByID(
						Long.valueOf(salesId));
				sales.setName(salesName);
				sales.setPrice(Double.valueOf(salesPrice));
				sales.setBonus(Double.valueOf(salesBonus));
				DAOFactory.createSalesDAO().updateSales(sales);
			}
			HibernateUtil.instance().commitTransaction();
			response.sendRedirect("shop/sales.jsp");
		} catch (HibernateException e) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("操作促销信息失败");
			response.getWriter().write("操作促销息失败--" + e.getMessage());
			response.getWriter().write("操作促销息失败");
		} catch (Exception ex) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("操作促销信息失败");
			response.getWriter().write("操作促销信息失败--" + ex.getMessage());
		} finally {
			HibernateUtil.instance().closeSession();
		}
	}

}
