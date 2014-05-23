/**
 * 
 */
package com.microcard.msg;

import com.microcard.msg.processor.MsgProcessorAnnotation;
import com.microcard.msg.processor.user.TmpUnsubscribeProcessor;
import com.microcard.msg.processor.user.UserSubscribeProcessor;

/**
 * @author jack
 *
 */
@MsgProcessorAnnotation(ShopMsgClass=TmpUnsubscribeProcessor.class,UserMsgClass=UserSubscribeProcessor.class)
@MsgTypeAnnotation(msg=MsgType.event,event=EventType.unsubscribe)
public class ReceivedUnsubscribeMsg extends ReceivedEventMsg {
	

	

}
