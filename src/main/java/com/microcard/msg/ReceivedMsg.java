/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
public class ReceivedMsg extends Msg {
	
	
	@MsgFieldAnnotation("MsgId")
	private String msgId;
	

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
