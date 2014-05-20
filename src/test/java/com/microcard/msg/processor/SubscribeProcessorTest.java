/**
 * 
 */
package com.microcard.msg.processor;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.client.HttpDefaultClient;
import com.microcard.client.WeixinClient;
import com.microcard.dao.DAOFactory;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class SubscribeProcessorTest  extends TestCase {

	public SubscribeProcessorTest() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.microcard.msg.processor.SubscribeProcessor#proccess(com.microcard.msg.Msg)}.
	 * @throws Exception 
	 */
	@Test
	public void testProccess() throws Exception {
		
//		try {
//			HttpDefaultClient client = new HttpDefaultClient("https://api.weixin.qq.com/cgi-bin/user/info?access_token=PfA3rsPBNSbRCwQ2J8kDBDh-31WOqN-xVI8UKXTzZPNp7yMaLuxN9JQOhFk0-AYH&openid=o2gmduF6NicaYlrYc0OKsbYeYVE4&lang=zh_CN");
//			String result = client.doGet();
//			Shop shop = WeixinClient.parseShop(result);
//			System.out.println(result);
//			System.out.println(shop.getCity());
//			super.assertEquals("阿富汗",shop.getCity());
//			
//		} catch (Exception e) {
//			super.fail(e.getMessage());
//		}
		
		Shop shop = WeixinClient.getShopInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
		//TODO 1. 如果该Shop在shop表中不存在，增加一条记录
		//TODO 2. 如果该Shop在shop表中存在，则修改shop信息，并且把delete_flag置否
		try{
			HibernateUtil.instance().beginTransaction();
			Shop s = DAOFactory.createShopDAO().getShopByOpenID(shop.getOpenId());
			if(s != null){
				DAOFactory.createShopDAO().addShop(s);
			} else{
				DAOFactory.createShopDAO().addShop(shop);
			}
			HibernateUtil.instance().commitTransaction();
		} catch(HibernateException e){
			Logger.getOperLogger().error(e, "scribe event save to database failed.");
			HibernateUtil.instance().rollbackTransaction();
			throw new Exception("scribe event save to database failed");
		} finally{
			HibernateUtil.instance().closeSession();
		}

	}

}
