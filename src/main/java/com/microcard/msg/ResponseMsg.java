/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
public class ResponseMsg extends Msg {

	@Override
	public String getCreateTime() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	public void setReceivedMsg(Msg msg) {
		
		this.setToUserName(msg.getFromUserName());
		this.setFromUserName(msg.getToUserName());
		
	}

	
}
