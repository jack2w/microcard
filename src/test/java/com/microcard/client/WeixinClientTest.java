/**
 * 
 */
package com.microcard.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class WeixinClientTest {

	public WeixinClientTest() {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		HibernateUtil.instance().beginTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		try{
			HibernateUtil.instance().commitTransaction();
		}
		catch(HibernateException e){
			HibernateUtil.instance().rollbackTransaction();
		}
	}

	/**
	 * Test method for {@link com.microcard.client.WeixinClient#getShopInfo(java.lang.String)}.
	 */
	@Test
	public void testGetShopInfo() {
		try{
			Shop shop = WeixinClient.getShopInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
			System.out.println(shop.getNickName());
			assertNotNull(shop);
			User user = WeixinClient.getUserInfo("o2gmduEx55FVt10DoRwMcHC7H5w8");
			System.out.println(shop.getNickName());
			assertNotNull(user);
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.microcard.client.WeixinClient#getShopTicket(java.lang.String, int)}.
	 */
	@Test
	public void testGetShopTicket() {
		try{
			String token = TokenManager.getShopToken();
			String ticket = WeixinClient.getShopTicket(token, 123);
			System.out.println(ticket);
			assertNotNull(ticket);
		}catch(Exception e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.microcard.client.WeixinClient#downloadCodePic(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDownloadCodePic() {
		fail("Not yet implemented");
	}

}
