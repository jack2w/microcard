/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
public class ReceivedEventMsg extends ReceivedMsg {
	
	
	@MsgFieldAnnotation("Event")
	private String event;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}
