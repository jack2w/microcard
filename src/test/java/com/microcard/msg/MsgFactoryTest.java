package com.microcard.msg;

import junit.framework.TestCase;

import com.microcard.msg.processor.MenuClickProcessor;

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
			super.assertTrue(msg instanceof MenuClickMsg);
			super.assertTrue(msg.getMsgProcessor() instanceof MenuClickProcessor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
