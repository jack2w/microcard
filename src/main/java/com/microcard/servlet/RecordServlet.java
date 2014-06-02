package com.microcard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import com.microcard.bean.Record;
import com.microcard.bean.Sales;
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
		String openId = request.getParameter("openId");
		String userId = request.getParameter("userId");
		String recordPrice = request.getParameter("recordPrice");
		String recordBonus = request.getParameter("recordBonus");
		String saleId = request.getParameter("saleId");
		String comfirm = request.getParameter("comfirm");
		Logger.getOperLogger().info("openId : " + openId);
		Logger.getOperLogger().info("userId : " + userId);
		Logger.getOperLogger().info("recordPrice : " + recordPrice);
		Logger.getOperLogger().info("recordBonus : " + recordBonus);
		Logger.getOperLogger().info("saleId : " + saleId);
		Logger.getOperLogger().info("comfirm : " + comfirm);
		try {
			
			Shop shop = DAOFactory.createShopDAO().getShopByOpenID(openId);
			// 查询会员ID
			if (comfirm == null || comfirm.equals("")) {
				HibernateUtil.instance().beginTransaction();
				List<User> users = DAOFactory.createShopDAO().getUsersByShop(shop,
						0, -1);
				HibernateUtil.instance().commitTransaction();
				String userName = "";
				boolean isUser = false;
				for (User user : users) {
					if (String.valueOf(user.getId()).equals(userId)) {
						userName = user.getName();
						if(userName == null || userName.length() < 1)
							userName = user.getNickName();
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
			// 提交记一笔信息
			} else {
				Record record = new Record();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = df.format(new Date());
				Timestamp ts = Timestamp.valueOf(time);
				record.setShop(shop);
				if(userId != null){
					User user = DAOFactory.createUserDAO().getUserByID(Long.valueOf(userId));
					if(user == null) {
						response.sendError(5001, "记一笔失败！" + "用户ID " + userId + " 不存在！");
						return;
					}
					record.setUser(user);
				}else {
					response.sendError(5001, "记一笔失败！" + "没有输入用户ID " + userId + "！");
					return;				
				}
				
				
				if(saleId != null) {
					Sales sales = DAOFactory.createSalesDAO().getSalesByID(Long.valueOf(saleId));
					if(sales != null)
						record.setSales(sales);
				}
				record.setPrice(Double.valueOf(recordPrice));
				record.setBonus(Double.valueOf(recordBonus));
				record.setTime(ts);
				HibernateUtil.instance().beginTransaction();
				DAOFactory.createRecordDAO().saveRecord(record);
				HibernateUtil.instance().commitTransaction();
			}

		} catch (HibernateException exception) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("操作记一笔失败：" + exception.getMessage());
			response.sendError(5001, "记一笔失败！" + exception.getMessage());
			return;			
		} catch (Exception exception) {
			HibernateUtil.instance().rollbackTransaction();
			Logger.getOperLogger().warn("操作记一笔失败：" + exception.getMessage());
			response.sendError(5001, "记一笔失败！" + exception.getMessage());
			return;			
		} finally {
			HibernateUtil.instance().closeSession();
			out.flush();
			out.close();
		}

	}

}
