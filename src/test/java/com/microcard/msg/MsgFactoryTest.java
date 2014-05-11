package com.microcard.msg;

import junit.framework.TestCase;

import com.microcard.msg.processor.MenuClickProcessor;
import com.microcard.msg.processor.TextMsgProcessor;

public class MsgFactoryTest extends TestCase {

	public MsgFactoryTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateMsg() {
		
		try {
			Msg msg = MsgFactory.createMsg("<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[EVENTKEY]]></EventKey></xml>");
			super.assertTrue(msg instanceof ReceivedMenuClickMsg);
			super.assertTrue(msg.getMsgProcessor() instanceof MenuClickProcessor);
			super.assertEquals(msg.getToUserName(), "toUser");
			super.assertEquals(msg.getFromUserName(), "FromUser");
			super.assertEquals(((ReceivedMenuClickMsg)msg).getEventKey(), "EVENTKEY");
			super.assertEquals(((ReceivedMenuClickMsg)msg).getMsgType().toString(), "event");
			super.assertEquals(((ReceivedMenuClickMsg)msg).getEvent(), "CLICK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testMsgToXml() {
		StringBuffer str = new StringBuffer();
		str.append("<xml><ToUserName><![CDATA[gh_1c25e0b75543]]></ToUserName>").append('\n');
		str.append("<FromUserName><![CDATA[oOrfwjk7S2-meQ6I51v92upnCGYM]]></FromUserName>").append('\n');
		str.append("<CreateTime>1395157159</CreateTime>").append('\n');
		str.append("<MsgType><![CDATA[text]]></MsgType>").append('\n');
		str.append("<Content><![CDATA[测试]]></Content>").append('\n');
		str.append("<MsgId>5992154370885374638</MsgId>").append('\n');
		str.append("</xml>");
		Msg msg;
		try {
			msg = MsgFactory.createMsg(str.toString());
			super.assertTrue(msg instanceof ReceivedTxtMsg);
			super.assertTrue(msg.getMsgProcessor() instanceof TextMsgProcessor);
			super.assertEquals(msg.getToUserName(), "gh_1c25e0b75543");
			super.assertEquals(msg.getFromUserName(), "oOrfwjk7S2-meQ6I51v92upnCGYM");	
			super.assertEquals(((ReceivedTxtMsg)msg).getMsgType().toString(), "text");
			
			String responseMsg = msg.getMsgProcessor().proccess(msg);
			System.out.println(responseMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
