/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.msg.Msg;

/**
 * @author jack
 *
 */
public class EventMsgProcessFactory extends AbstractMsgProcessFactory {

	public EventMsgProcessFactory(Msg msg) {
		super(msg);
	}

	/* (non-Javadoc)
	 * @see com.ilive.weixin.msg.IMsgProcessorFactory#getMsgProcessor()
	 */
	@Override
	public IMsgProcessor getMsgProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

}
