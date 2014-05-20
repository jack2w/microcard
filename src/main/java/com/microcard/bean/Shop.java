/**
 * 
 */
package com.microcard.bean;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author jack
 *
 */
public class Shop {

	
	/**
	 * 微信中的用户id，从微信中获取，在某一个公共账号下用户id唯一，为主建，建表时除了这个字段不可为空外，其它字段都可以为空
	 */
	private String openId;
	
	/**
	 * 自动增长型，从1开始
	 */
	private long id;
	
	/**
	 * 商铺的二维码信息
	 */
	private String String;
	
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
	
	/**
	 * 商铺账号订阅时间，从微信中获取
	 */
	private Timestamp subscribeTime;
	
	/**
	 * 
	 */
	private String code;
	
	/**
	 * 是否已取消订阅
	 */
	private boolean deleteFlag;

	/**
	 * 商铺的会员用户，与User有多对多的关系，注意该字段需要延迟加载，数据库需要有多对多关系的中间表
	 */
	private Set<User> users;
	
	/**
	 * 商铺的商品，具有1对多关系，需要延迟加载
	 */
	private Set<Commodity> commodities;
	
	/**
	 * 商铺存在哪些营销行为，与Sales具有1对多关系，不需要延迟加载
	 */
	private Set<Sales> sales;

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
		Shop other = (Shop) obj;
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

	public String getString() {
		return String;
	}

	public void setString(String string) {
		String = string;
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

	public Timestamp getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Timestamp subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Sales> getSales() {
		return sales;
	}

	public void setSales(Set<Sales> sales) {
		this.sales = sales;
	}

	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


}
