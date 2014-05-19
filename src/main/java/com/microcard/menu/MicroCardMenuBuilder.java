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
		
		//商品
		
		MenuViewItem record = new MenuViewItem("商品",buildUrl(Menu.Menu_Key_COMMODITY));
		menu.addItem(record);

		//会员
		MenuViewItem member = new MenuViewItem("会员",buildUrl(Menu.MENU_Key_MEMBER));
		menu.addItem(member);
		
		//设置
		MenuItem setting = new MenuItem("设置");
		menu.addItem(setting);
		//设置->商铺
		MenuViewItem shop = new MenuViewItem("商铺",buildUrl(Menu.MENU_Key_SHOP));
		setting.addSubMenuItem(shop);
		//设置->商品
		MenuViewItem commodity = new MenuViewItem("营销",buildUrl(Menu.MENU_Key_SALES));
		setting.addSubMenuItem(commodity);
		//设置->二维码
		MenuViewItem code = new MenuViewItem("二维码",buildUrl(Menu.Menu_Key_Code));
		setting.addSubMenuItem(code);
		
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
