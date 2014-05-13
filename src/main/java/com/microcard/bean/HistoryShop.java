/**
 * 
 */
package com.microcard.bean;

/**
 * @author jack
 *
 */
public class HistoryShop {

	/**
	 * 商铺openId，与Shop中的openId值一致，在用户购买时把Shop的信息拷贝到HistoryShop中
	 */
	private String openId;
	
	/**
	 * 商铺名称,由商铺用户输入
	 */
	private String name;

	/**
	 * 商铺联系电话,由商铺用户输入
	 */
	private String phone;
	
	/**
	 * 商铺地址，由商铺用户输入
	 */
	private String address;
	
	/**
	 * 商铺简介，由商铺用户输入
	 */
	private String memo;
	
	/**
	 * 商铺图片，由商铺用户输入
	 */
	private String imgUrl;
	
	/**
	 * 商铺账号昵称，从微信中获取
	 */
	private String nickName;
	
	/**
	 * 商铺账号性别，从微信中获取
	 */
	private Sex sex;
	
	/**
	 * 商铺账号所在国家，从微信中获取
	 */
	private String country;
	
	/**
	 * 商铺账号所在省，从微信中获取
	 */
	private String province;
	
	/**
	 * 商铺账号所在城市，从微信中获取
	 */
	private String city;
	
	/**
	 * 商铺账号头像的url地址，从微信中获取
	 */
	private String headImgUrl;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((openId == null) ? 0 : openId.hashCode());
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
		HistoryShop other = (HistoryShop) obj;
		if (openId == null) {
			if (other.openId != null)
				return false;
		} else if (!openId.equals(other.openId))
			return false;
		return true;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
	
}
