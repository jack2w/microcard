/**
 * 
 */
package com.microcard.bean;

import java.util.Set;

/**
 * 商铺的营销类，具有折扣，现金返还等各种营销方式
 * @author jack
 *
 */
public class Sales {

	/**
	 * 该商铺的营销，与商铺openid外建，与商铺类有1对多关系
	 */
	private Shop shop;
	
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
	 * 营销价格
	 */
	private double price;
	
	/**
	 * 奖励
	 */
	private double bonus;
	
	/**
	 * 是否删除
	 */
	private boolean deleteFlag;
	
	/**
	 * 该营销参加的商品，具有1对多关系,需要延迟加载
	 */
	private Set<Commodity> commodities;

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
		Sales other = (Sales) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
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

	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the bonus
	 */
	public double getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the deleteFlag
	 */
	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
