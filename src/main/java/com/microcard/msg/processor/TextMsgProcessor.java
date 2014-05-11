/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.msg.Msg;
import com.microcard.msg.MsgFactory;
import com.microcard.msg.ReceivedTxtMsg;
import com.microcard.msg.ResponseTxtMsg;

/**
 * @author jack
 *
 */
public class TextMsgProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.ilive.weixin.msg.IMsgProcessor#proccess(com.ilive.weixin.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		if(msg instanceof ReceivedTxtMsg) {
			
			ResponseTxtMsg responseMsg = new ResponseTxtMsg();
			responseMsg.setReceivedMsg(msg);
			responseMsg.setContent("Weclome to Mobile Life. (message sent from wechatdm.cloudapp.net) --吴伟");
			return MsgFactory.msgToXml(responseMsg);
		}
		return null;
	}

}
