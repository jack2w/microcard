/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.msg.Msg;

/**
 * @author jack
 *
 */
public class NullMsgProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		//do nothing
		return null;
	}

}
