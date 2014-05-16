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
	public List getShops() throws HibernateException;
	
	/**
	 * 删除商铺
	 * 如果商铺包含商品，营销方式信息，关联删除
	 * 如果商铺包含用户信息，不删除用户信息
	 * @param id
	 * @throws HibernateException
	 */
	public void deleteShop(Shop shops) throws HibernateException;
	
	/**
	 * 获得指定id的商铺
	 * 
	 * @return 
	 * @throws HibernateException
	 */
	public Shop getShopByID(long id) throws HibernateException;
		
	/**
	 * 更新商铺
	 * 1.更新一个单一的Shop对象
	 * 2.商铺中包含了用户信息，商铺信息，营销方式等相关信息，可以做相关更新
	 * 3.商铺中已经包含了用户信息，但要删除某用户与该商铺的关系，此时不删除用户自身信息
	 * @param shop
	 * @throws HibernateException
	 */
	public void updateShop(Shop shop) throws HibernateException;
	
	
	/**
	 * 添加一条商铺记录，不包含其他关联数据
	 *添加一条商铺记录，包含用户信息，商铺信息，营销方式等相关信息
	 * @param shop 可以为一个单一的Shop对象，也可以包含其他关联对象
	 * @throws HibernateException
	 */
	public void addShop(Shop shop)throws HibernateException;
	
/**
 * 获得商铺的用户信息
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
	public void addCommodity(Shop shop, Commodity... commodities);
	
	/**
	 * 增加营销
	 */
	public void addSales(Shop shop, Sales... saleses);
	
	public void saveOrUpdate(Shop s);
	
}
