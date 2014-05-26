/**
 * 
 */
package com.microcard.bean;

import java.sql.Timestamp;
import java.util.Set;

/**
 * 用户购买记录
 * @author jack
 *
 */
public class Record {

	/**
	 * 主建，自动生成
	 */
	private long id;
	
	/**
	 * 该购买记录所属用户
	 */
	private User user;
	
	/**
	 * 商铺名称
	 */
	private Shop shop;
	
	/**
	 * 购买价格
	 */
	private double price;
	
	/**
	 * 奖励
	 */
	private double bonus;
	
	/**
	 * 是否用掉奖励
	 */
	private boolean bonusUsed;
	
	/**
	 * 是否删除
	 */
	private boolean deleteFlag;
	
	/**
	 * 购买发生时间
	 */
	private Timestamp time;
	
	/**
	 * 该营销参加的商品，具有1对多关系,需要延迟加载
	 */
	private Set<Commodity> commodities;
	
	/**
	 * 购买商品的折扣
	 */
	private Sales sales;
	
	

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
		Record other = (Record) obj;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the shop
	 */
	public Shop getShop() {
		return shop;
	}

	/**
	 * @param shop the shop to set
	 */
	public void setShop(Shop shop) {
		this.shop = shop;
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
	 * @return the bonusUsed
	 */
	public boolean isBonusUsed() {
		return bonusUsed;
	}

	/**
	 * @param bonusUsed the bonusUsed to set
	 */
	public void setBonusUsed(boolean bonusUsed) {
		this.bonusUsed = bonusUsed;
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

	/**
	 * @return the commodities
	 */
	public Set<Commodity> getCommodities() {
		return commodities;
	}

	/**
	 * @param commodities the commodities to set
	 */
	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	/**
	 * @return the sales
	 */
	public Sales getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
	
}
