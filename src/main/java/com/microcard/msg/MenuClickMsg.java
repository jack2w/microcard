/**
 * 
 */
package com.microcard.msg;

import com.microcard.msg.processor.MenuClickProcessor;
import com.microcard.msg.processor.MsgProcessorAnnotation;

/**
 * @author jack
 *
 */
@MsgProcessorAnnotation(MsgClass=MenuClickProcessor.class)
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.CLICK)
public class MenuClickMsg extends EventMsg {
	
	@MsgFieldAnnotation("EventKey")
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
