/**
 * 
 */
package com.microcard.bean;

import java.sql.Timestamp;

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
	private HistoryShop shop;
	
	/**
	 * 购买发生时间
	 */
	private Timestamp time;
	
	/**
	 * 购买的商品
	 */
	private HistoryCommodity commodity;
	
	/**
	 * 购买商品的折扣
	 */
	private HistorySales sales;

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

	public HistoryShop getShop() {
		return shop;
	}

	public void setShop(HistoryShop shop) {
		this.shop = shop;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public HistoryCommodity getCommodity() {
		return commodity;
	}

	public void setCommodity(HistoryCommodity commodity) {
		this.commodity = commodity;
	}

	public HistorySales getSales() {
		return sales;
	}

	public void setSales(HistorySales sales) {
		this.sales = sales;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
