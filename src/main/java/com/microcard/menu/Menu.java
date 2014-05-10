/**
 * 
 */
package com.microcard.menu;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @author jack
 *
 */
public class Menu {
	
	public static final String MENU_Key_RECORD = "Menu_Key_Record";
	
	public static final String MENU_Key_MEMBER = "Menu_Key_Member";
	
	public static final String MENU_Key_SHOP = "Menu_Key_Shop";
	
	public static final String Menu_Key_COMMODITY     = "Menu_Key_Commodity";
	
	public static final String Menu_Key_Code     = "Menu_Key_Code";
	
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	public Menu() {
		
	}
	
	public void addItem(MenuItem item) {
		
		menuItems.add(item);
		
	}
	
	public String toJSON() {
		
	   JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		for(MenuItem it : menuItems) {
			
			array.add(toJsonObject(it));
		}
		if(array.size() > 0) {
			jsonObject.element("button", array);
		}
		return jsonObject.toString();
	}
	
	private JSONObject toJsonObject(MenuItem item) {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("name", item.getName());
		
		if(item instanceof MenuClickItem) {
			jsonObject.element("type", "click");
			jsonObject.element("key", ((MenuClickItem) item).getKey());
		}else if(item instanceof MenuViewItem){
			jsonObject.element("type", "view");
			jsonObject.element("url", ((MenuViewItem) item).getUrl());
		}
		
		JSONArray array = new JSONArray();
		for(MenuItem it : item.getSubMenuItem()) {
			
			array.add(toJsonObject(it));
		}
		if(array.size() > 0) {
			jsonObject.element("sub_button", array);
		}
		return jsonObject;
	}
	
	public static Menu parseJSONToBean(String json) {
		
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( json );  
		
		JSONArray array = jsonObject.getJSONArray("button");
		if(array == null)
			return null;
		
		Menu menu = new Menu();
		
		for(Object o : array.toArray() ) {
			if(o instanceof JSONObject) { 
				MenuItem tmpItem =  toBean((JSONObject)o);
				if(tmpItem != null)
					menu.addItem(tmpItem);
			}
		}
		
		return menu;
	}
	
	private static MenuItem toBean(JSONObject obj) {
		
		String name = obj.getString("name");
		
		if(obj.has("sub_button")) {
			
			JSONArray array = obj.getJSONArray("sub_button");
			if(array != null && array.size() > 0) {
				MenuItem item = new MenuItem(name);
				for(Object o : array.toArray() ) {
					if(o instanceof JSONObject) { 
						
						MenuItem tmpItem =  toBean((JSONObject)o);
						if(tmpItem != null)
						  item.addSubMenuItem(tmpItem);
					}
				}
				return item;
			}
			
			return null;
		} else {
			String type = obj.getString("type");
			if("click".equals(type)) {
				
				String key = obj.getString("key");
				MenuClickItem item = new MenuClickItem(name,key);
				
				return item;
				
			}else if("view".equals(type)) {
				
				String url = obj.getString("url");
				MenuViewItem item = new MenuViewItem(name,url);
				
				return item;
				
			}else {
				return null;
			}
		}
	}
	
	public static void main(String[] arg) {
		
		Menu menu = MicroCardMenuBuilder.buildMenu();
		String txt = menu.toJSON();
		System.out.println(txt);
		Menu menu2 = Menu.parseJSONToBean(txt);
		System.out.println(menu2.toJSON());
		
	}

}
