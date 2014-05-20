/**
 * 
 */
package com.microcard.msg.processor.user;

import com.microcard.msg.Msg;
import com.microcard.msg.ReceivedScanMsg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class UserScanMsgProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		if(!(msg instanceof ReceivedScanMsg)){
			return null;
		}
		//TODO 根据ReceivedScanMsg的fromUserName对应User的openid找到User
		//TODO 根据ReceivedScanMsg的eventKey对应Shop的id，找到Shop
		//TODO 如果该User不存在则添加一条User记录，存在则检查该Shop是否已经跟这个User建立关系
		//TODO 如果这个User和Shop没有关系，则建立他们之间的关系
		return null;
	}

}
