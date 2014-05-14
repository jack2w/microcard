package com.microcard.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.dao.UserDAO;
import com.microcard.dao.hibernate.HibernateSupport;
import com.microcard.domain.User;

public class UserDAOImpl extends HibernateSupport implements UserDAO{

	@Override
	public List getUsers() throws HibernateException {
		
		return super.queryUsers(User.class);
		
	}

	@Override
	public void deleteUser(String id) throws HibernateException {
		super.delete(User.class, id);
		
	}

	@Override
	public User getUserByID(String id) throws HibernateException {
		return super.getobjByID(User.class, id);
	}

	@Override
	public void updateUser(User user) throws HibernateException {
		super.update(user);
		
	}

	@Override
	public void saveUser(User user) throws HibernateException {
		super.save(user);	
	}



}
