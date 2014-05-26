package com.microcard.dao;

import com.microcard.dao.impl.CommodityDAOImpl;
import com.microcard.dao.impl.RecordDAOImpl;
import com.microcard.dao.impl.SalesDAOImpl;
import com.microcard.dao.impl.ShopDAOImpl;
import com.microcard.dao.impl.UserDAOImpl;

public class DAOFactory {

	public static UserDAO createUserDAO(){
		return new UserDAOImpl();
	}
	
	public static ShopDAO createShopDAO(){
		return new ShopDAOImpl();
	}
	
	public static SalesDAO createSalesDAO(){
		return new SalesDAOImpl();
	}
	
	public static CommodityDAO createCommodityDAO(){
		return new CommodityDAOImpl();
	}
	
	public static RecordDAO createRecordDAO(){
		return new RecordDAOImpl();
	}
	
}
