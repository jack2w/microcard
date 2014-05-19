/**
 * 
 */
package com.microcard.exception;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author jack
 *
 */
public class WeixinException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6700336661569243965L;
	
	private int errorCode;
	private String errorMsg;

	public WeixinException(int error) {
		
	}
	
	public WeixinException(int error,String msg) {
		this.errorCode = error;
		this.errorMsg = msg;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
    
    public String getMessage() {
    	
    	return this.errorMsg;
    }
    
    public static WeixinException parseException(String msg) {
    	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( msg );
    	if(jsonObject.has("errcode")) {
    		 int errorCode = jsonObject.getInt("errcode");
    		 String errorMsg = jsonObject.getString("errmsg");
    		return new WeixinException(errorCode,errorMsg);
    	}
    	return null;
    }
}
