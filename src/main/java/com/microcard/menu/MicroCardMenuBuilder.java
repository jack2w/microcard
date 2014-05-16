/**
 * 
 */
package com.microcard.menu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.microcard.client.HttpDefaultClient;
import com.microcard.log.Logger;
import com.microcard.token.TokenManager;

/**
 * @author jack
 *
 */
public class MicroCardMenuBuilder {

	
	public static Menu buildMenu() {
		
		
		Menu menu = new Menu();
		
		//记一笔
		
		MenuViewItem record = new MenuViewItem("记一笔",buildUrl(Menu.MENU_Key_RECORD));
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
		MenuViewItem commodity = new MenuViewItem("商品",buildUrl(Menu.Menu_Key_COMMODITY));
		setting.addSubMenuItem(commodity);
		//设置->二维码
		MenuViewItem code = new MenuViewItem("二维码",buildUrl(Menu.Menu_Key_Code));
		setting.addSubMenuItem(code);
		
		return menu;
	}
	
	@SuppressWarnings("deprecation")
	private static String buildUrl(String page) {
		String url = "http://wechatdm.cloudapp.net/microcard/pageforward";
		String encodeUrl = URLEncoder.encode(url);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenManager.AppId+"&redirect_uri="+encodeUrl+"&response_type=code&scope=snsapi_base&state=microcard#wechat_redirect";
	}
	
	public static void main(String[] arg) {
		
		try {
			Menu menu = MicroCardMenuBuilder.buildMenu();
			String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ejlx2ZiwKbYL86tG570p5o5856I1Gxk2fETfrJmVtyJLx0JarOezGdi4MNB1lABk"; //+ TokenManager.getToken();
			//HttpNettyClient client = new HttpNettyClient(url);
			//client.doPost(new DefaultHttpHandler(), menu.toJSON());
			HttpDefaultClient client = new HttpDefaultClient(url);
			String result = client.doPost(menu.toJSON());
			System.out.println(result);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
