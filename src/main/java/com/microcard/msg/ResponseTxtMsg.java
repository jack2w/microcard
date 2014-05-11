/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
@MsgTypeAnnotation(msg=MsgType.text)
public class ResponseTxtMsg extends ResponseMsg {
	
	
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
