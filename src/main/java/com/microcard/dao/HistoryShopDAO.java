package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.HistoryShop;

public interface HistoryShopDAO {

	public List getHistoryShop() throws HibernateException;

	public void deleteHistoryShop(HistoryShop...  historyShops)
			throws HibernateException;

	public HistoryShop getHistoryShopByID(String id) throws HibernateException;

	public void updateHistoryShop(HistoryShop... historyShops)
			throws HibernateException;

	public void saveHistoryShop(HistoryShop... historyShops)
			throws HibernateException;
}
