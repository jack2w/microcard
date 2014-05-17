package com.microcard.dao;

import com.microcard.dao.impl.CommodityDAOImpl;
import com.microcard.dao.impl.HistoryCommodityDAOImpl;
import com.microcard.dao.impl.HistorySalesDAOImpl;
import com.microcard.dao.impl.HistoryShopDAOImpl;
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
	
	public static HistoryCommodityDAO createHistoryCommodityDAO(){
		return new  HistoryCommodityDAOImpl();
	}
	
	public static HistorySalesDAO createHistorySalesDAO(){
		return new HistorySalesDAOImpl();
	}
	
	public static HistoryShopDAO createHistoryShopDAO(){
		return new HistoryShopDAOImpl();
	}
	
	public static RecordDAO createRecordDAO(){
		return new RecordDAOImpl();
	}
	
}
