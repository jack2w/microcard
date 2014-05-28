package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Record;
import com.microcard.bean.Shop;
import com.microcard.bean.User;

public interface UserDAO {

	public List<User> getUsers() throws HibernateException;
	
	/**
	 * 物理删除用户以及该用户和商铺及购买记录的相关信息
	 * @param u
	 * @throws HibernateException
	 */
	public void deletePhsycalUser(User... user) throws HibernateException;
	
	/**
	 * 逻辑删除用户
	 * @param u
	 * @throws HibernateException
	 */
	public void deleteLogicalUser(User... user) throws HibernateException;
	
	public User getUserByID(String id) throws HibernateException;
	
	/**
	 * 获得指定openid的用户
	 */
	public User getUserByOpenID(String opendid) throws HibernateException;
	
	/**
	 * 获得指定id的用户
	 * 
	 * @return 
	 * @throws HibernateException
	 */
	public User getUserByID(long id) throws HibernateException;
	
	/**
	 * 该方法修改的仅是用户信息，涉及到集合的属性比如购买记录和商铺信息需要调用相关方法
	 * @param user
	 * @throws HibernateException
	 */
	public void updateUser(User... users) throws HibernateException;
	
	/**
	 * 该方法保存的只有不涉及集合属性的用户信息，涉及到集合属性的信息需要调用相关方法
	 * @param user
	 * @throws HibernateException
	 */
	public void saveUser(User... users) throws HibernateException;
	
	/**
	 * 该方法更新的仅是用户信息，涉及到集合的属性比如购买记录和商铺信息需要调用相关方法
	 * @param user
	 * @throws HibernateException
	 */
	public void saveOrUpdate(User... users ) throws HibernateException;
	
	/**
	 * 增加购买记录
	 */
	public void addRecords(User shop, Record... records) throws HibernateException;
	
	/**
	 * 修改购买纪录
	 * @param shop
	 * @param records
	 */
	public void updateRecords(User shop, Record... records) throws HibernateException;
	
	/**
	 * 删除纪录,该方法将删除相关购买记录
	 */
	public void deleteRecords(User shop, Record... records) throws HibernateException;
	
	/**
	 * 添加商铺
	 */
	public void addShops(User u, Shop... shops) throws HibernateException;
	
	/**
	 * 删除商铺，如果shops为null，删除所有该用户关联的商铺信息
	 */
	public void deleteShop(User u, Shop... shops) throws HibernateException;

	/**
	 * 分页获得用户关注的shops
	 * @param u
	 * @param start
	 * @param length
	 * @return
	 * @throws HibernateException
	 */
	List<Shop> getShopsByUser(User u, int start, int length)
			throws HibernateException;
	
	/**
	 * 根据用户和商铺获得所有购买记录
	 * @param u
	 * @param s
	 * @param start
	 * @param length
	 * @return
	 * @throws HibernateException
	 */
	List<Record> getRecordsByUser(User u, Shop s,  int start, int length) throws HibernateException;
	
}
