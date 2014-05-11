/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.VIEW)
public class MenuViewMsg extends EventMsg {
	
	
	@MsgFieldAnnotation("EventKey")
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
