package com.microcard.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
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
		String shopId = request.getParameter("shopId");
		String salesId = request.getParameter("salesId");
		String salesName = request.getParameter("salesName");
		String salesPrice = request.getParameter("salesPrice");
		String salesBonus = request.getParameter("salesBonus");
		// 获取被选中的商品Id
		String[] commodityIds = request.getParameterValues("checkbox");
		log.info("shopId : " + shopId);
		log.info("salesId : " + salesId);
		log.info("salesName : " + salesName);
		log.info("salesPrice : " + salesPrice);
		log.info("salesBonus: " + salesBonus);
		log.info("commodityIds: " + commodityIds);
		Set<Commodity> commodities = new HashSet<Commodity>();
		try {
			HibernateUtil.instance().beginTransaction();
			// 根据商品Id获取商品对象存入商品集合中
			if (commodityIds != null) {
				for (String commodityId : commodityIds) {
					Commodity commodity = DAOFactory.createCommodityDAO()
							.getCommodityByID(Long.valueOf(commodityId));
					commodities.add(commodity);

				}
			}
			
			Shop shop = DAOFactory.createShopDAO().getShopByID(Long.valueOf(shopId));
			if (salesId == null || salesId.equals("")) {
				Sales sales = new Sales();
				sales.setShop(shop);
				sales.setName(salesName);
				sales.setPrice(Double.valueOf(salesPrice));
				sales.setBonus(Double.valueOf(salesBonus));
				sales.setCommodities(commodities);
				DAOFactory.createSalesDAO().saveSales(sales);
			} else {
				Sales sales = DAOFactory.createSalesDAO().getSalesByID(
						Long.valueOf(salesId));
				sales.setName(salesName);
				sales.setPrice(Double.valueOf(salesPrice));
				sales.setBonus(Double.valueOf(salesBonus));
				sales.setCommodities(commodities);
				DAOFactory.createSalesDAO().updateSales(sales);
			}
			HibernateUtil.instance().commitTransaction();
			response.sendRedirect("shop/sales.jsp?OPENID=" + shop.getOpenId());
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
