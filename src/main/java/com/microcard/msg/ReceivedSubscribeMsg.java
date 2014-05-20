/**
 * 
 */
package com.microcard.msg;

import com.microcard.msg.processor.MsgProcessorAnnotation;
import com.microcard.msg.processor.SubscribeProcessor;
import com.microcard.msg.processor.user.UserSubscribeProcessor;

/**
 * @author jack
 *
 */
@MsgProcessorAnnotation(ShopMsgClass=SubscribeProcessor.class,UserMsgClass=UserSubscribeProcessor.class)
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.subscribe)
public class ReceivedSubscribeMsg extends ReceivedEventMsg {

	//EventKey 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	@MsgFieldAnnotation("EventKey")
	private String eventKey;
	
	//Ticket 二维码的ticket，可用来换取二维码图片
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
