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
public class User {

	/**
	 * 微信中的用户id，从微信中获取，在某一个公共账号下用户id唯一，为主建，建表时除了这个字段不可为空外，其它字段都可以为空
	 */
	private String openId;
	
	/**
	 * 用户昵称，从微信中获取
	 */
	private String nickName;
	
	/**
	 * 用户真实姓名，由用户输入
	 */
	private String name;
	
	/**
	 * 用户联系地址,由用户输入
	 */
	private String address;
	
	/**
	 * 用户联系方式1，由用户输入
	 */
	private String phone1;
	
	/**
	 * 用户联系方式2，由用户输入
	 */
	private String phone2;
	
	/**
	 * 用户联系方式3，由用户输入
	 */
	private String phone3;
	
	/**
	 * 用户性别，从微信中获取
	 */
	private Sex sex;
	
	/**
	 * 用户所在国家，从微信中获取
	 */
	private String country;
	
	/**
	 * 用户所在省，从微信中获取
	 */
	private String province;
	
	/**
	 * 用户所在城市，从微信中获取
	 */
	private String city;
	
	/**
	 * 用户头像的url地址，从微信中获取
	 */
	private String headImgUrl;
	
	/**
	 * 是否已取消订阅
	 */
	private boolean delete_flag;

	/**
	 * 用户订阅时间，从微信中获取
	 */
	private Timestamp subscribeTime;
	
	/**
	 * 用户所属会员商铺，与Shop具有多对多关系，延迟加载
	 */
	private Set<Shop> shops;
	
	/**
	 * 用户的购买记录，与Record具有1对多关系，延迟加载
	 */
	private Set<Record> records;
	

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
		User other = (User) obj;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
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

	public Set<Shop> getShops() {
		return shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Set<Record> getRecords() {
		return records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}
	
	public boolean isDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	
}
