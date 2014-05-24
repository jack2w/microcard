/**
 * 
 */
package com.microcard.menu;

import java.net.URLEncoder;

import com.microcard.client.HttpDefaultClient;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class MicroCardMenuBuilder {

	
	public static Menu buildShopMenu() {
		
		
		Menu menu = new Menu();
		
		//记一笔
		MenuViewItem record = new MenuViewItem("记一笔",buildUrl(Menu.MENU_Key_Shop_RECORD));
		menu.addItem(record);	
		
		//会员
		MenuItem user = new MenuItem("会员");
		menu.addItem(user);		
		//会员->信息
		MenuViewItem userInfo = new MenuViewItem("信息",buildUrl(Menu.MENU_Key_User_USERINFO));
		user.addSubMenuItem(userInfo);		
		//会员->商铺
		MenuViewItem userShop = new MenuViewItem("商铺",buildUrl(Menu.MENU_Key_User_USERINFO));
		user.addSubMenuItem(userShop);		

		
		//商铺
		MenuItem shop = new MenuItem("商铺");
		menu.addItem(shop);
		//商铺->商铺信息
		MenuViewItem shopInfo = new MenuViewItem("信息",buildUrl(Menu.MENU_Key_Shop_SHOPINFO));
		shop.addSubMenuItem(shopInfo);
		//商铺->营销
		MenuViewItem salses = new MenuViewItem("营销",buildUrl(Menu.MENU_Key_Shop_SALES));
		shop.addSubMenuItem(salses);
		//商铺->商品
		MenuViewItem commodity = new MenuViewItem("商品",buildUrl(Menu.Menu_Key_Shop_COMMODITY));
		shop.addSubMenuItem(commodity);
		//商铺->会员
		MenuViewItem member = new MenuViewItem("会员",buildUrl(Menu.MENU_Key_Shop_MEMBER));
		shop.addSubMenuItem(member);
		//设置->二维码
		MenuViewItem code = new MenuViewItem("二维码",buildUrl(Menu.Menu_Key_Shop_Code));
		shop.addSubMenuItem(code);
		
		return menu;
	}
	
	@SuppressWarnings("deprecation")
	private static String buildUrl(String page) {
		String url = "http://wechatdm.cloudapp.net/microcard/pageforward?page=" + page;
		String encodeUrl = URLEncoder.encode(url);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenManager.ShopAppId+"&redirect_uri="+encodeUrl+"&response_type=code&scope=snsapi_base&state=microcard#wechat_redirect";
	}
	
	public static void main(String[] arg) {
		
		try {
			Menu menu = MicroCardMenuBuilder.buildShopMenu();
			String token = TokenManager.getShopToken();
			String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token; //+ ;
			//HttpNettyClient client = new HttpNettyClient(url);
			//client.doPost(new DefaultHttpHandler(), menu.toJSON());
			HttpDefaultClient client = new HttpDefaultClient(url);
			String result = client.doPost(menu.toJSON());
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
