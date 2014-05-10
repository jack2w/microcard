/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.msg.Msg;
import com.microcard.msg.MsgType;


/**
 * @author jack
 *
 */
public abstract class AbstractMsgProcessFactory implements IMsgProcessorFactory {
	
	protected Msg msg = null;
	
	public AbstractMsgProcessFactory(Msg msg) {
		
		this.msg = msg;
	}

	public static AbstractMsgProcessFactory getProcessorFactory(Msg msg) {
		
		if(msg == null) return null;
		
		if(msg.getMsgType() == MsgType.event)
			return new EventMsgProcessFactory(msg);
		
		return new BaseMsgProcessFactory(msg);
	}

}
