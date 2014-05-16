package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.HistoryCommodity;
import com.microcard.bean.HistorySales;


public interface HistorySalesDAO {
	
	public List getHistorySales() throws HibernateException;
	
	public void deleteHistorySales(HistorySales...  historySales) throws HibernateException;
	
	public HistoryCommodity getHistorySalesByID(String id) throws HibernateException;
	
	public void updateHistorySales(HistorySales... historySales) throws HibernateException;
	
	public void saveHistorySales(HistorySales...  historySales) throws HibernateException;
}
