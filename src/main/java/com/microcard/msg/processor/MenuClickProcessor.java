/**
 * 
 */
package com.microcard.msg.processor;

import com.microcard.menu.Menu;
import com.microcard.msg.MsgFactory;
import com.microcard.msg.ReceivedMenuClickMsg;
import com.microcard.msg.Msg;
import com.microcard.msg.ResponseTxtMsg;

/**
 * @author jack
 *
 */
public class MenuClickProcessor implements IMsgProcessor {

	/* (non-Javadoc)
	 * @see com.microcard.msg.processor.IMsgProcessor#proccess(com.microcard.msg.Msg)
	 */
	@Override
	public String proccess(Msg msg) throws Exception {
		if(!(msg instanceof ReceivedMenuClickMsg))
			return null;
		
		ReceivedMenuClickMsg menuMsg = (ReceivedMenuClickMsg)msg;
		
		ResponseTxtMsg responseMsg = new ResponseTxtMsg();
		responseMsg.setReceivedMsg(msg);
		String result = null;
		
		switch(menuMsg.getEventKey()){
		  
			case Menu.Menu_Key_COMMODITY:
				result = processCommodity(responseMsg);
				break;
			case Menu.MENU_Key_MEMBER:
				result = processMember(responseMsg);
				break;
			case Menu.MENU_Key_RECORD:
				result = processRecord(responseMsg);
				break;
			case Menu.MENU_Key_SHOP:
				result = processShop(responseMsg);
				break;
			case Menu.Menu_Key_Code:
				result = processKeyCode(msg.getFromUserName());
				break;
		}
		
	
		return result;
	}
	
	private String processMember(ResponseTxtMsg msg) throws Exception {
		msg.setContent("会员管理<br><a href=\"http://wechatdm.cloudapp.net/microcard/member/member.html\">点击进入会员管理</a> ");
		return MsgFactory.msgToXml(msg);
	}

	private String processRecord(ResponseTxtMsg msg) throws Exception {
		msg.setContent("记一笔<br><a href=\"http://wechatdm.cloudapp.net/microcard/record/record.html\">点击进入记一笔</a> ");
		return MsgFactory.msgToXml(msg);
	}
	
	private String processShop(ResponseTxtMsg msg) throws Exception {
		msg.setContent("店铺管理<br><a href=\"http://wechatdm.cloudapp.net/microcard/shop/shop.html\">点击进入店铺管理</a> ");
		return MsgFactory.msgToXml(msg);
	}
	
	private String processCommodity(ResponseTxtMsg msg) throws Exception {
		msg.setContent("商品管理<br><a href=\"http://wechatdm.cloudapp.net/microcard/commodity/commodity.html\">点击进入商品管理</a> ");
		return MsgFactory.msgToXml(msg);
	}
	
	private String processKeyCode(String userId) {
		
		return "";
	}
	
}
