/**
 * 
 */
package com.microcard.msg.processor;

import junit.framework.TestCase;

import com.microcard.menu.Menu;
import com.microcard.msg.ReceivedMenuClickMsg;

/**
 * @author jack
 *
 */
public class MenuClickProcessorTest extends TestCase {

	/**
	 * @param name
	 */
	public MenuClickProcessorTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.microcard.msg.processor.MenuClickProcessor#proccess(com.microcard.msg.Msg)}.
	 */
	public void testProccess() {
		
		ReceivedMenuClickMsg msg = new ReceivedMenuClickMsg();
		msg.setToUserName("wuwei");
		msg.setFromUserName("weixin");
		msg.setCreateTime("1395157159");
		msg.setMsgType("event");
		msg.setEvent("CLICK");
		msg.setEventKey(Menu.MENU_Key_Shop_SHOPINFO);
		
		MenuClickProcessor processor = new MenuClickProcessor();
		try {
			String result = processor.proccess(msg);
			System.out.println(result);
			super.assertNotNull(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		
		
	}

}
