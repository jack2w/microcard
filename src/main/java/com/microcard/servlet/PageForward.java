package com.microcard.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microcard.client.WeixinClient;
import com.microcard.exception.WeixinException;
import com.microcard.log.Logger;
import com.microcard.menu.Menu;
import com.microcard.token.TokenManager;
import com.microcard.util.ErrorPage;

/**
 * Servlet implementation class PageForward
 */
public class PageForward extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public static final String STATE = "microcard";
	
	private static Logger log = Logger.getOperLogger();
 
	private static String WEBPATH = null;
	
	private static final String CODEPATH = "resources/code";
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public PageForward() { 
        super();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if(WEBPATH == null) {
			
			WEBPATH = config.getServletContext().getRealPath("/");
		}
		File tmpCodePath = new File(WEBPATH + CODEPATH);
		log.debug("CodePath: " + tmpCodePath);
		if(!tmpCodePath.exists()) tmpCodePath.mkdir();	
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		log.debug("begin page forward");
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String page = request.getParameter("page");
		
		log.debug("Servlet PageForward received parameter code: " + code) ;
		log.debug("Servlet PageForward received parameter state: " + state) ;
		log.debug("Servlet PageForward received parameter page: " + page) ;
		
		if(!STATE.equals(state)) {
			response.getWriter().print(ErrorPage.createPage("错误的连接！"));
			log.error("received state is " + state + " ,not match required STATE " + STATE);
			return;
		}else {
			try{
				
				String openid = WeixinClient.getOAuthUser(code);
				request.setAttribute("OPENID", openid);
				forward(page,openid, request, response);

				
			} catch (Exception e) {
				log.error(e, "forward page error " + e.getMessage());
				response.getOutputStream().println(ErrorPage.createPage("forward page error " + e.getMessage()));
			}	
		}
	}
	 
	private void forward(String page ,String openId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch(page){
		  
		case Menu.Menu_Key_Shop_COMMODITY:
			response.sendRedirect("commodity/commodity.html");
			break;
		case Menu.MENU_Key_Shop_MEMBER:
			response.sendRedirect("member/member.html");
			break;
		case Menu.MENU_Key_Shop_RECORD:
			response.sendRedirect("record/record.html");
			break;
		case Menu.MENU_Key_Shop_SALES:
			response.sendRedirect("sales/sales.html");
			break;
		case Menu.MENU_Key_Shop_SHOPINFO:
			response.sendRedirect("shop/shop.html");
			break;
		case Menu.Menu_Key_Shop_Code:
			processCode(openId,request,response);
			break;
		default:
			response.setContentType("text/html");
			response.getOutputStream().println(ErrorPage.createPage("could't find page by " + page ));
			break;
		}
	}
	
	private void processCode(String openId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = null;
		
		try {
			//获取账号得二维码
			token = TokenManager.getShopToken();
			
		} catch (Exception e) {

			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(ErrorPage.createPage("向微信获取Token发生异常: " + e.getMessage()));
			return;
		}
		
		
		//获取二维码的ticket
		String ticket = null;
		try {
			ticket = WeixinClient.getShopTicket(token,123);//TODO 暂用临时二维码进行测试
			
		} catch (Exception e) {
			
			if((e instanceof WeixinException) && ((WeixinException)e).getErrorCode()==40001) {
				//可能是token有问题，重新获取一次token
				try {
					token = TokenManager.refreshShopToken();
				} catch (Exception ex) {

					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println(ErrorPage.createPage("向微信获取Token发生异常: " + ex.getMessage()));
					return;
				}
				//获取新的token后再获取二维码信息
				try {
					ticket = WeixinClient.getShopTicket(token,123);//TODO 暂用临时二维码进行测试
				} catch (Exception e1) {
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println(ErrorPage.createPage("向微信获取Ticken发生异常: " + e1.getMessage()));
					return;	
				}
			} else {
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println(ErrorPage.createPage("向微信获取Ticken发生异常: " + e.getMessage()));
				return;				
			}
		}
		
		try {
			String filename = WEBPATH + CODEPATH + File.separator + openId + ".jpg";
			String codePicUrl = WeixinClient.downloadCodePic(filename, ticket);
			response.setContentType("text/html");
			response.getWriter().println(getCodePage(codePicUrl));		
			
		} catch (Exception e) {
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(ErrorPage.createPage("向微信获取二维码图片异常: " + e.getMessage()));
			return;
		}
		
	}
	
	private String getCodePage(String imgSrc) {
		
		StringBuffer out = new StringBuffer();
		out.append("<!DOCTYPE html>");
		out.append("<html>");
		out.append("<head>");
		out.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no\" />");
		out.append("<meta name=\"apple-touch-fullscreen\" content=\"yes\" />");
		out.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
		out.append("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />");
		out.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.append("<link href=\"resources/styles/code.css\" rel=\"stylesheet\" />");
		out.append("<title>二维码信息</title>");
		out.append("</head>");
		out.append("<body>");
		out.append("<div class=\"contentDiv\"><p class=\"content\">您商铺的二维码</p></div>");
		out.append("<div class=\"code\"><img class=\"img\" src=\""+imgSrc+"\"></img></div>");
		out.append("</body>");
		out.append("</html>");
		return out.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
