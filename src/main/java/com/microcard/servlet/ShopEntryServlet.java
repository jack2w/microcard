package com.microcard.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microcard.log.Logger;
import com.microcard.msg.Msg;
import com.microcard.msg.MsgFactory;
import com.microcard.util.Utils;

/**
 * Servlet implementation class WeixinDev
 */
public class ShopEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getOperLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopEntryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String echostr = request.getParameter("echostr") == null ? "" : request.getParameter("echostr") ;
		if(echostr.length() > 0) {
			String signature = request.getParameter("signature") == null ? "" : request.getParameter("signature") ;
			String timestamp = request.getParameter("timestamp") == null ? "" : request.getParameter("timestamp") ;
			String nonce = request.getParameter("nonce") == null ? "" : request.getParameter("nonce") ;
			
			log.debug("signature:"+signature);
			log.debug("timestamp:"+timestamp);
			log.debug("nonce:"+nonce);
			log.debug("echostr:"+echostr);
			
			String mySignature = buildSignature(timestamp,nonce);
		    
		    if(signature.equalsIgnoreCase(mySignature)) {
				PrintWriter out = response.getWriter();
				
				out.print(echostr);	    	
		    }else {
		    	log.error("mySignature is " + mySignature + " is not equal to weixin signature:" + signature);
		    	return;
		    }	
			return;
		}
	
	}
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String encoding = request.getCharacterEncoding();
		if(encoding == null) {
			encoding = "UTF-8";
			request.setCharacterEncoding(encoding);
		}
			
		String signature = request.getParameter("signature") == null ? "" : request.getParameter("signature") ;
		String timestamp = request.getParameter("timestamp") == null ? "" : request.getParameter("timestamp") ;
		String nonce = request.getParameter("nonce") == null ? "" : request.getParameter("nonce") ;
		//auth url is from weixin
		if(signature.length() > 0 && timestamp.length() > 0 && nonce.length() > 0) {
			
			String mySignature = buildSignature(timestamp,nonce);
			if(!signature.equalsIgnoreCase(mySignature.toLowerCase())) {
				
				log.error("mySignature is " + mySignature + " is not equal to weixin signature:" + signature);
				return;
			}
		} else {
			
			log.error("there is no signature to auth from weixin!");
			return;
		}
		
		ServletInputStream input = request.getInputStream();
		byte[] buffer = new byte[request.getContentLength()];
//		ByteBuffer buffer = ByteBuffer.allocate(request.getContentLength());
//		int s;
//		while((s = input.read()) > -1) {
//			buffer.put((byte)s);
//		}
		input.read(buffer, 0, buffer.length);
		String content = new String(buffer,encoding);
		log.debug("recevied msg: " + content);
		
		Msg msg = null;
		try {
			msg = MsgFactory.createMsg(content);
			String responseMsg = msg.getShopMsgProcessor().proccess(msg);
			
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			if(responseMsg != null)
				out.print(responseMsg);	
		} catch (Exception e) {
			log.error(e.getMessage());
			return;
		}
		

	}
	
	private String buildSignature(String timestamp,String nonce) {
		String token = "503";
		ArrayList<String> list = new ArrayList<String>();
		list.add(token);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);
		
		StringBuffer strBuf = new StringBuffer();
		for(String s : list) {
			strBuf.append(s);
		}
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        log.error(e, "get SHA-1 error!");
	    } 		
	    md.reset();
	    md.update(strBuf.toString().getBytes());
	    String mySignature = Utils.convertToHex(md.digest());
	    
	    return mySignature;
	}
}
