package com.microcard.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.microcard.client.HttpDefaultClient;
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
			
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" 
			             + TokenManager.AppId+ "&secret="+TokenManager.AppSecret
			             +"&code="+code+"&grant_type=authorization_code";
			log.debug("send to url : " + url);
			try {
				HttpDefaultClient client = new HttpDefaultClient(url);
				String result = client .doGet();
				log.debug("received result: " + result);
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
					forward(page,openid, request, response);
					
				}else if(jsonObject.has("errcode")) {
					
					log.error("received error msg " + jsonObject.getInt("errcode") + " ," + jsonObject.getString("errmsg"));
					response.getWriter().println(ErrorPage.createPage("received error msg " + jsonObject.getInt("errcode") + " ,"
					                                    + jsonObject.getString("errmsg") + " from url " + url));
					return;
				}else {
					
					response.getWriter().println(ErrorPage.createPage("received error msg from url " + url));
					return;
				}
				
			} catch (Exception e) {
				response.getOutputStream().println(ErrorPage.createPage("forward page error " + e.getMessage()));
			}	
		}
	}
	 
	private void forward(String page ,String openId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			response.sendRedirect("shop/shop.html");
			break;
		case Menu.Menu_Key_Code:
			processCode(openId,request,response);
			break;
		}
	}
	
	private void processCode(String openId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = null;
		
		try {
			//获取账号得二维码
			token = TokenManager.getToken();
			
		} catch (Exception e) {

			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(ErrorPage.createPage("向微信获取Token发生异常: " + e.getMessage()));
			return;
		}
		
		//TODO 暂用临时二维码进行测试
		//获取二维码的ticket
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
		String ticket = null;
		try {
			
			HttpDefaultClient client = new HttpDefaultClient(url);
			String result = client.doPost("{\"expire_seconds\": 600, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
			
			WeixinException exception = WeixinException.paserException(result);
			if(exception != null) {
				
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println(ErrorPage.createPage("向微信获取Ticket发生异常: " + exception.getMessage()));
				return;
			}
			
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( result );
		     ticket = jsonObject.getString("ticket");
			jsonObject.getInt("expire_seconds");
			
		} catch (Exception e) {
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(ErrorPage.createPage("向微信获取Ticken发生异常: " + e.getMessage()));
			return;
		}
		
		try {
			//获取二维码的图片
			
			//两种方式显示二维码图片
			//1. 通过向微信把图片取到本地
//			String filename = WEBPATH + CODEPATH + File.separator + openId + ".jpg";
//			File codeFile = new File(filename);
//			if(!codeFile.exists()) {
//				url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
//				HttpDownloadClient client = new HttpDownloadClient(url,filename);
//				client.doGet();			
//			}
//			response.setContentType("text/html");
//			response.getWriter().println(getCodePage(CODEPATH + "/" + openId + ".jpg"));
			
			//2. 直接在html的IMG中显示微信的图片连接
			url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
			response.setContentType("text/html");
			response.getWriter().println(getCodePage(url));		
			
		} catch (Exception e) {
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println(ErrorPage.createPage("向微信获取二维码图片异常: " + e.getMessage()));
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
