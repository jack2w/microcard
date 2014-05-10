/**
 * 
 */
package com.microcard.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 *
 */
public class MenuItem {

	private String name;
	
	private ArrayList<MenuItem> subMenuItem = new ArrayList<MenuItem>();
	
	public MenuItem(String name) {
		
		this.name = name;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public void addSubMenuItem(MenuItem item) {
		
		subMenuItem.add(item);
	}
	
	public List<MenuItem> getSubMenuItem() {

		return this.subMenuItem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
