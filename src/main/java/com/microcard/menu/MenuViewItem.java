/**
 * 
 */
package com.microcard.menu;

/**
 * @author jack
 *
 */
public class MenuViewItem extends MenuItem {
	
	private String url;

	public MenuViewItem(String name,String url) {
		
		super(name);
		this.url = url;
	}
	
	public String getUrl() {
		
		return this.url;
	}

}
