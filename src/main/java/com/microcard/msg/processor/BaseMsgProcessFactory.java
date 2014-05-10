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
public class BaseMsgProcessFactory extends AbstractMsgProcessFactory {

	
	
	public BaseMsgProcessFactory(Msg msg) {
		
		super(msg);
	}

	/* (non-Javadoc)
	 * @see com.ilive.weixin.msg.IMsgProcessorFactory#getMsgProcessor()
	 */
	@Override
	public IMsgProcessor getMsgProcessor() {
		if(msg.getMsgType() == MsgType.text)
			return new TextMsgProcessor();
		
		return null;
	}


}
