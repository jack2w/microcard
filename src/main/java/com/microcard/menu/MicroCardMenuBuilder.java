/**
 * 
 */
package com.microcard.menu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.microcard.client.HttpDefaultClient;

/**
 * @author jack
 *
 */
public class MicroCardMenuBuilder {

	
	public static Menu buildMenu() {
		Menu menu = new Menu();
		
		//记一笔
		MenuClickItem record = new MenuClickItem("记一笔",Menu.MENU_Key_RECORD);
		menu.addItem(record);

		//会员
		MenuClickItem member = new MenuClickItem("会员",Menu.MENU_Key_MEMBER);
		menu.addItem(member);
		
		//设置
		MenuItem setting = new MenuItem("设置");
		menu.addItem(setting);
		//设置->商铺
		MenuClickItem shop = new MenuClickItem("商铺",Menu.MENU_Key_SHOP);
		setting.addSubMenuItem(shop);
		//设置->商品
		MenuClickItem commodity = new MenuClickItem("商品",Menu.Menu_Key_COMMODITY);
		setting.addSubMenuItem(commodity);
		//设置->二维码
		MenuClickItem code = new MenuClickItem("二维码",Menu.Menu_Key_Code);
		setting.addSubMenuItem(code);
		
		return menu;
	}
	
	public static void main(String[] arg) {
		
		try {
			Menu menu = MicroCardMenuBuilder.buildMenu();
			String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=0Z2PDw6goM4MozIwZcsa26STP1UYzxzJKMGtq8DXlJHzYhMIeJJVCufY6YZ2BJG0"; //+ TokenManager.getToken();
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
