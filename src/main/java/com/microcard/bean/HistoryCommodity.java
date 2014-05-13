/**
 * 
 */
package com.microcard.bean;

/**
 * @author jack
 *
 */
public class HistoryCommodity {
	/**
	 * 商品所属商铺，与商铺openid外建
	 */
	private Shop shop;
	
	/**
	 * 商品id，与Commodity中的id值一致，在用户购买时把Commodity的信息拷贝到HistoryCommodity中
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
		HistoryCommodity other = (HistoryCommodity) obj;
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
	
	
}
