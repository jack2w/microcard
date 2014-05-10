/**
 * 
 */
package com.microcard.exception;

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
}
