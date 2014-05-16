package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Record;
import com.microcard.bean.User;

public interface UserDAO {

	public List getUsers() throws HibernateException;
	
	public void deleteUser(User u) throws HibernateException;
	
	public User getUserByID(String id) throws HibernateException;
	
	public void updateUser(User user) throws HibernateException;
	
	public void saveUser(User user) throws HibernateException;
	
	public void saveOrUpdate(User u ) throws HibernateException;
	
	/**
	 * 增加购买记录
	 */
	public void addRecords(User shop, Record... records);
	
	/**
	 * 修改纪录
	 * @param shop
	 * @param records
	 */
	public void updateRecords(User shop, Record... records);
	
	/**
	 * 删除纪录
	 */
	public void deleteRecords(User shop, Record... records);
	
}
