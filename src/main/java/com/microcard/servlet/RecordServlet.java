package com.microcard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Shop;
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
		PrintWriter out = response.getWriter();
		try {
			// 获取Shop对象
			String openId = request.getParameter("openId");
			Shop shop = DAOFactory.createShopDAO().getShopByOpenID(openId);
			String userId = request.getParameter("userId");
			Logger.getOperLogger().info("userId : " + userId);
			HibernateUtil.instance().beginTransaction();
			List<User> users = DAOFactory.createShopDAO().getUsersByShop(shop,
					0, -1);
			HibernateUtil.instance().commitTransaction();
			String userName = "";
			boolean isUser = false;
			for (User user : users) {
				if (String.valueOf(user.getId()).equals(userId)) {
					userName = user.getName();
					// 将数据拼接成JSON格式
					out.print("{\"userName\":\"" + userName + "\"}");
					isUser = true;
					break;
				}
			}

			if (!isUser) {
				out.write("该会员不存在，请重新输入！");
				Logger.getOperLogger().warn("查询会员失败");
			}

		} catch (HibernateException exception) {
			HibernateUtil.instance().rollbackTransaction();
			out.write("查询会员ID失败：" + exception.getMessage());
			Logger.getOperLogger().warn("查询会员ID失败" + exception.getMessage());
		} catch (Exception exception) {
			HibernateUtil.instance().rollbackTransaction();
			out.write("查询会员ID失败：" + exception.getMessage());
			Logger.getOperLogger().warn("查询会员ID失败" + exception.getMessage());

		} finally {
			HibernateUtil.instance().closeSession();
			out.flush();
			out.close();
		}

	}

}
