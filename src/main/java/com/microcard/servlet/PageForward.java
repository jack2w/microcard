package com.microcard.servlet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.client.HttpDefaultClient;
import com.microcard.log.Logger;
import com.microcard.menu.Menu;
import com.microcard.token.TokenManager;

/**
 * Servlet implementation class PageForward
 */
public class PageForward extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public static final String STATE = "microcard";
	
	private static Logger log = Logger.getOperLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageForward() { 
        super();
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
			response.getWriter().print("错误的连接！");
			log.error("received state is " + state + " ,not match required STATE " + STATE);
			return;
		}else {
			
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" 
			             + TokenManager.AppId+ "&secret="+TokenManager.AppSecret
			             +"&code="+code+"&grant_type=authorization_code";
			log.debug("send to url : " + url);
			try {
				HttpDefaultClient client = new HttpDefaultClient(url);
				String result = client .doGet();
				
				String access_token = null;
				int expires_in;
				String refresh_token = null;
				String openid = null;
				String scope = null;
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
				
				
				if(jsonObject.has("access_token")) {
					
					access_token = jsonObject.getString("access_token");
					expires_in = jsonObject.getInt("expires_in");
					refresh_token = jsonObject.getString("refresh_token");
					openid = jsonObject.getString("openid");
					scope = jsonObject.getString("scope");
					log.debug("access_token:" + access_token);
					log.debug("expires_in:" + expires_in);
					log.debug("refresh_token:" + refresh_token);
					log.debug("openid:" + openid);
					log.debug("scope:" + scope);
					
					request.setAttribute("OPENID", openid);
					forward(page, request, response);
					
				}else if(jsonObject.has("errcode")) {
					
					log.error("received error msg " + jsonObject.getInt("errcode") + " ," + jsonObject.getString("errmsg"));
					response.getWriter().println("received error msg " + jsonObject.getInt("errcode") + " ,"
					                                    + jsonObject.getString("errmsg") + " from url " + url);
					return;
				}else {
					
					response.getWriter().println("received error msg from url " + url);
					return;
				}
				
			} catch (KeyManagementException | NoSuchAlgorithmException
					| URISyntaxException e) {
				response.getOutputStream().println("forward page error " + e.getMessage());
			}	
		}
	}
	 
	private void forward(String page , HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		switch(page){
		  
		case Menu.Menu_Key_COMMODITY:
			response.sendRedirect(path+"/commodity/commodity.html");
			break;
		case Menu.MENU_Key_MEMBER:
			response.sendRedirect(path+"/member/member.html");
			break;
		case Menu.MENU_Key_RECORD:
			response.sendRedirect(path+"/record/record.html");
			break;
		case Menu.MENU_Key_SHOP:
			response.sendRedirect(path+"/shop/shop.html");
			break;
		case Menu.Menu_Key_Code:
			response.sendRedirect(path+"/commodity/commodity.html");
			break;
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
