/**
 * 
 */
package com.microcard.bean;

/**
 * @author jack
 *
 */
public enum Sex {

	male,
	female;
	
	public static Sex valueOf(int i) {
		if(i == 1) return Sex.male;
		
		return Sex.female;
	}
}
