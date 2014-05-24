package com.microcard.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Commodity;
import com.microcard.bean.Shop;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * Servlet implementation class CommodityServlet
 */
public class CommodityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommodityServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Logger.getOperLogger().info("name" + request.getParameter("name"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String price = request.getParameter("price");
			String openid = request.getParameter("openid");
			Logger.getOperLogger().info("name : " + name);
			Logger.getOperLogger().info("id : " + id);
			Logger.getOperLogger().info("price : " + price);
			Logger.getOperLogger().info("openid: " + openid);

			HibernateUtil.instance().beginTransaction();
			Shop s = DAOFactory.createShopDAO().getShopByOpenID(openid);

			Commodity obj = new Commodity();
			obj.setName(name);
			obj.setPrice(Long.parseLong(price));
			obj.setShop(s);

			if (id != null && id.length() != 0) {
				obj.setId(Long.parseLong(id));
				DAOFactory.createShopDAO().updateCommodity(s, obj);
			} else {
				DAOFactory.createShopDAO().addCommodity(s, obj);
			}
			HibernateUtil.instance().commitTransaction();
			response.getWriter().write("成功");
		} catch (HibernateException e) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("添加或更新商品记录失败");
			response.getWriter().write("添加或更新商品记录失败");
		} catch(Exception ex){
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("添加或更新商品记录失败");
			response.getWriter().write("添加或更新商品记录失败");
		} finally{
			HibernateUtil.instance().closeSession();
		}

	}
}
