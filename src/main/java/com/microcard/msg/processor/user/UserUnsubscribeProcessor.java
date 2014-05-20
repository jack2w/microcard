/**
 * 
 */
package com.microcard.msg.processor.user;

import com.microcard.msg.Msg;
import com.microcard.msg.processor.IMsgProcessor;

/**
 * @author jack
 *
 */
public class UserUnsubscribeProcessor  implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		// TODO 根据ReceivedUnscribeMsg中fromUserName对应User的openid找到User
		// TODO 如果存在这个User，然后把这个User设置逻辑删除，如果不存在不处理，日志中记录warn日志，说明取消订阅不应该存在找不到的用户
		return null;
	}

}
