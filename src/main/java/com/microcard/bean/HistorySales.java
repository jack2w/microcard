/**
 * 
 */
package com.microcard.bean;

/**
 * @author jack
 *
 */
public class HistorySales {
	/**
	 * 营销id，自动生成，主建
	 */
	private long id;
	
	/**
	 * 营销名称
	 */
	private String name;

	/**
	 * 营销简介
	 */
	private String memo;
	
	/**
	 * 营销所属商铺
	 */
	private Shop shop;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistorySales other = (HistorySales) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
}
