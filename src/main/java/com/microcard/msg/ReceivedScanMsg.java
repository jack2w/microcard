/**
 * 
 */
package com.microcard.msg;

/**
 * @author jack
 *
 */
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.SCAN)
public class ReceivedScanMsg extends ReceivedEventMsg {
	//事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	@MsgFieldAnnotation("EventKey")
	private String eventKey;
	
	//二维码的ticket，可用来换取二维码图片
	@MsgFieldAnnotation("Ticket")
	private String ticket;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
