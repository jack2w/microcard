/**
 * 
 */
package com.microcard.bean;

/**
 * 商品类，某个商铺具有哪些商品
 * @author jack
 *
 */
public class Commodity {

	/**
	 * 商品所属商铺，与商铺openid外建
	 */
	private Shop shop;
	
	/**
	 * 商品id，自动生成，主建
	 */
	private long id;
	
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 商品简介
	 */
	private String memo;
	
	/**
	 * 商品图片
	 */
	private String imgUrl;
	
	/**
	 * 商品价格
	 */
	private double price;
	
	/**
	 * 商品折扣
	 */
	private double discount;
	
	/**
	 * 该商品的营销类型
	 */
	private Sales sales;
	
	/**
	 * 是否删除
	 */
	private boolean deleteFlag;

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
		Commodity other = (Commodity) obj;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
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
