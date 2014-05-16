package com.microcard.dao;

import com.microcard.dao.impl.ShopDAOImpl;
import com.microcard.dao.impl.UserDAOImpl;

public class HibernateDAOFactory {

	public static UserDAO createUserDAO(){
		return new UserDAOImpl();
	}
	

	public static ShopDAO createShopDAO(){
		return new ShopDAOImpl();
	}
}
