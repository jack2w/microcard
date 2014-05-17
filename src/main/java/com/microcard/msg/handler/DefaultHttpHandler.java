/**
 * 
 */
package com.microcard.msg.handler;

import com.microcard.client.HttpClientTxtHandler;
import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class DefaultHttpHandler extends HttpClientTxtHandler {
	private static Logger msgLog = Logger.getMsgLogger();
	/* (non-Javadoc)
	 * @see com.microcard.client.HttpClientHandler#process(java.lang.String)
	 */
	@Override
	public void process(String msg) {
		
		msgLog.info("received msg is " + msg);

	}

}
