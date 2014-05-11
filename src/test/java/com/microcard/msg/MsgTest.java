package com.microcard.msg;

import junit.framework.TestCase;

public class MsgTest extends TestCase {

	public MsgTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetMsgType() {
		Msg msg = new SubscribeEventMsg();
		super.assertEquals(msg.getMsgType(), MsgType.event);
		
		
	}

}
