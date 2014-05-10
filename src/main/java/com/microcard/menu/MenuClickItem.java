/**
 * 
 */
package com.microcard.menu;

/**
 * @author jack
 *
 */
public class MenuClickItem extends MenuItem {
	
	private String key;

	public MenuClickItem(String name, String key) {
		
		super(name);
		
		this.key = key;
	}
	
	public String getKey() {
		
		return this.key;
	}

}
