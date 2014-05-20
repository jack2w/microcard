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
public class UserSubscribeProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		// TODO 当用户扫描一个带参数的二维码时，ReceivedSubscribeMsg的eventKey有值
		// TODO 当用户扫描一个不带参数的二维码时，ReceivedSubscribeMsg的eventKey无值
		// TODO eventKey的格式是qrscene_123123,qrscene是前缀，123123是Shop的id
		// TODO 如果eventKey无值，则检查该User是否存在，不存在则增加记录，如果存在则修改deleteFlag为false，并更新用户的信息
		// TODO 如果eventKey有值，做无值时的用户更新，并则根据qrscene_123123的Shop id找到Shop，建立与该Shop的关系，注意不要重复建立
		return null;
	}

}
