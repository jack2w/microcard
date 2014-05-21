package com.microcard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Shop;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * Servlet implementation class ShopServlet
 */
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getOperLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShopServlet() {
		super();
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
		String openId = request.getParameter("shopOpenId");
		String name = request.getParameter("shopName");
		String phone = request.getParameter("shopPhone");
		String address = request.getParameter("shopAddress");
		String memo = request.getParameter("shopMemo");

		log.debug("openId:" + openId);
		log.debug("name:" + name);
		log.debug("phone:" + phone);
		log.debug("address:" + address);
		log.debug("memo:" + memo);

		try {
			HibernateUtil.instance().beginTransaction();
			Shop shop = DAOFactory.createShopDAO().getShopByOpenID(openId);
			shop.setOpenId(openId);
			shop.setName(name);
			shop.setPhone(phone);
			shop.setAddress(address);
			shop.setMemo(memo);
			DAOFactory.createShopDAO().updateShop(shop);
			HibernateUtil.instance().commitTransaction();
			request.setAttribute("updateShop", shop);
		} catch (HibernateException exception) {
			log.error("update failed case:" + exception);
		}
		//request.getRequestDispatcher("shop/shop.jsp")
		//		.forward(request, response);
		response.sendRedirect("shop/shop.jsp?OPENID=" + openId);
	}

}
