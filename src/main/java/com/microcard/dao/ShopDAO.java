package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;

public interface ShopDAO {

	/**
	 * 获得所有商铺信息
	 * @return
	 * @throws HibernateException
	 */
	public List<Shop> getShops() throws HibernateException;
	
	/**
	 * 删除商铺
	 * 如果商铺包含商品，营销方式信息，关联删除
	 * 如果商铺包含用户信息，不删除用户信息
	 * @param id
	 * @throws HibernateException
	 */
	public void deleteShop(Shop... shops) throws HibernateException;
	
	/**
	 * 获得指定id的商铺
	 * 
	 * @return 
	 * @throws HibernateException
	 */
	public Shop getShopByID(long id) throws HibernateException;
	
	/**
	 * 获得指定openid的商铺
	 */
	public Shop getShopByOpenID(String opendid) throws HibernateException;
		
	/**
	 * 更新商铺
	 * 1.仅更新一个单一的Shop对象，对其他关联信息的更新需要调用相关方法
	 * @param shop
	 * @throws HibernateException
	 */
	public void updateShop(Shop... shop) throws HibernateException;
	
	
	/**
	 * 添加一条商铺记录，不包含其他关联数据，其他关联信息的添加需要调用其他相关方法
	 * @param shop 可以为一个单一的Shop对象，不包含其他关联对象
	 * @throws HibernateException
	 */
	public void addShop(Shop... shop)throws HibernateException;
	
/**
 * 获得商铺的指定长度和起始位的用户信息
 * @param shop
 * @param start用户起始位置
 * @param length返回长度
 * @return
 * @throws HibernateException
 */
	public User[] getUsersByShop(Shop shop, int start, int length) throws HibernateException;
	
	/**
	 * 增加商品
	 */
	public void addCommodity(Shop shop, Commodity... commodities) throws HibernateException;
	
	/**
	 * 修改商品
	 */
	public void updateCommodity(Shop shop, Commodity... commodities) throws HibernateException;
	
	/**
	 * 删除商品,如果commodities为null，删除该shop关联的所有Commodity
	 */
	public void delteCommoditity(Shop shop, Commodity... commodities) throws HibernateException;
	
	/**
	 * 增加营销
	 */
	public void addSales(Shop shop, Sales... saleses) throws HibernateException;
	
	/**
	 * 删除营销，如果sales为null，删除shop关联的所有sales
	 * @param s
	 * @throws HibernateException
	 */
	public void deleteSales(Shop shop, Sales... saleses) throws HibernateException;
	
	/**
	 * 修改营销
	 * @param s
	 * @throws HibernateException
	 */
	public void updateSales(Shop shop, Sales... saleses) throws HibernateException;
	
	/**
	 * 该方法更新的仅是商铺信息，涉及到集合的属性需要调用相关方法
	 * @param s
	 * @throws HibernateException
	 */
	public void saveOrUpdate(Shop... shops) throws HibernateException;
	
}
