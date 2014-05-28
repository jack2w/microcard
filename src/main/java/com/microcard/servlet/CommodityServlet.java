package com.microcard.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
    private static Logger log = Logger.getOperLogger();
    private static final String errorMsg = "操作商品信息失败";
	
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			out = response.getWriter();	
			
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String price = request.getParameter("price");
			if(price.contains(".")){
				price = price.substring(0, price.indexOf("."));
			}
			String openid = request.getParameter("openid");
			String oper = request.getParameter("oper");
			log.info("name : " + name);
			log.info("id : " + id);
			log.info("price : " +price);
			log.info("openid: " + openid);
			log.info("oper: " + oper);

			if(oper == null || oper.equals("") || openid == null || openid.equals("")){
				throw new Exception("oper or openid is null");
			}
			HibernateUtil.instance().beginTransaction();
			Shop s = DAOFactory.createShopDAO().getShopByOpenID(openid);

			Commodity obj = new Commodity();
			obj.setName(name);
			obj.setPrice(Long.parseLong(price));
			obj.setShop(s);

			if (id != null && id.length() != 0) {
				obj.setId(Long.parseLong(id));
				if(oper.equals("delete")){
					DAOFactory.createShopDAO().delteCommoditity(s, obj);
				} else{
					DAOFactory.createShopDAO().updateCommodity(s, obj);
				}			
			} else {
				DAOFactory.createShopDAO().addCommodity(s, obj);
			}
			HibernateUtil.instance().commitTransaction();
			out.write("{\"result\":\"" + "操作成功" + "\"}");
		} catch (Exception ex) {
			HibernateUtil.instance().rollbackTransaction();
			log.error(ex, errorMsg);
			out.write(errorMsg + ex.getMessage());
		} finally {
			HibernateUtil.instance().closeSession();
		}

	}
}
