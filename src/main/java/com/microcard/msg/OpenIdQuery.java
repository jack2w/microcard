/**
 * 
 */
package com.microcard.msg;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 *
 */
public class OpenIdQuery {

	private String nextOpenId;
	
	private int total;
	
	private int count;
	
	private Set<String> openIdSet = new HashSet<String>();

	/**
	 * @return the nextOpenId
	 */
	public String getNextOpenId() {
		return nextOpenId;
	}

	/**
	 * @param nextOpenId the nextOpenId to set
	 */
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the openIdList
	 */
	public Set<String> getOpenIdList() {
		return openIdSet;
	}

	/**
	 * @param openIdList the openIdList to set
	 */
	public void setOpenIdList(Set<String> openIdList) {
		this.openIdSet = openIdList;
	}
	
	public void addOpenId(String openId) {
		this.openIdSet.add(openId);
	}
	
	public void removeOpenId(String openId) {
		this.openIdSet.remove(openId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.openIdSet.toString();
	}
	
	
}
