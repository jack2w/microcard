/**
 * 
 */
package com.microcard.util;

/**
 * @author jack
 *
 */
public class ErrorPage {

	private static final StringBuffer page = new StringBuffer();
	
	private static final String end = "</body></html>";
	
	static {
		page.append("<!DOCTYPE html>");
		page.append("<html>");
		page.append("<head>");
		page.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no\" />");
		page.append("<meta name=\"apple-touch-fullscreen\" content=\"yes\" />");
		page.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
		page.append("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />");
		page.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		page.append("<link href=\"styles/error.css\" rel=\"stylesheet\" />");
		page.append("<title>二维码信息</title>");
		page.append("</head>");
		page.append("<body>");
		page.append("<div class=\"contentDiv\"><p class=\"content\">错误信息</p></div>");
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static String createPage(String msg) {
		StringBuffer buffer = new StringBuffer(page);
		buffer.append("<div class=\"msgDiv\"><p class=\"msg\">");
		buffer.append(msg);
		buffer.append("</p></div>");
		buffer.append(end);
		return buffer.toString();
		
	}
}
