/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.msg.Msg;
import com.microcard.msg.TxtMsg;

/**
 * @author jack
 *
 */
public class TextMsgProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.ilive.weixin.msg.IMsgProcessor#proccess(com.ilive.weixin.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) {
		if(msg instanceof TxtMsg) {
			((TxtMsg) msg).setContent("Weclome to Mobile Life. (message sent from wechatdm.cloudapp.net) --吴伟");
			return Msg.msgToXml(msg);
		}
		return null;
	}

}
