package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.domain.User;

public interface UserDAO {

	public List getUsers() throws HibernateException;
	
	public void deleteUser(String id) throws HibernateException;
	
	public User getUserByID(String id) throws HibernateException;
	
	public void updateUser(User user) throws HibernateException;
	
	public void saveUser(User user) throws HibernateException;
}
