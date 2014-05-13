package com.microcard.dao;

import com.microcard.dao.impl.UserDAOImpl;

public class HibernateDAOFactory {

	public static UserDAO createUserDAO(){
		return new UserDAOImpl();
	}
}
