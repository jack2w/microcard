/**
 * 
 */
package com.microcard.msg;

import com.microcard.msg.processor.MsgProcessorAnnotation;
import com.microcard.msg.processor.TextMsgProcessor;


/**
 * @author jack
 *
 */
@MsgProcessorAnnotation(ShopMsgClass=TextMsgProcessor.class)
@MsgTypeAnnotation(msg=MsgType.text)
public class ReceivedTxtMsg extends ReceivedMsg {

	@MsgFieldAnnotation("Content")
	private String content;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
