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

	public WeixinException(int error) {
		
	}
	
	public WeixinException(int error,String msg) {
		
	}
	
	public WeixinException(String msg) {
		
	}
	
    public WeixinException(Throwable cause) {
        super(cause);
    }
    
    public String getMessage() {
    	
    	return super.getMessage();
    }
    
    public static WeixinException paserException(String msg) {
    	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( msg );
    	if(jsonObject.has("errcode")) {
    		int errorCode = jsonObject.getInt("errcode");
    		String errorMsg = jsonObject.getString("errmsg");
    		return new WeixinException(errorCode,errorMsg);
    	}
    	return null;
    }
}
