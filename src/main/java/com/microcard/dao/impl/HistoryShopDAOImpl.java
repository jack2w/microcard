package com.microcard.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.microcard.bean.HistoryShop;
import com.microcard.dao.HistoryShopDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class HistoryShopDAOImpl implements HistoryShopDAO {

	private Logger log = Logger.getMsgLogger();
	@Override
	public List getAllHistoryShop() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + HistoryShop.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all historyshops.");
			throw ex;
		}
	}

	@Override
	public void deleteHistoryShop(HistoryShop... historyShops)
			throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(HistoryShop s : historyShops){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete historyshop.");
			throw ex;
		}

	}

	@Override
	public HistoryShop getHistoryShopByID(String id) throws HibernateException {
		HistoryShop s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (HistoryShop)session.load(HistoryShop.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get historyshop by id.");
			throw ex;
		}
		return s;
	}

	@Override
	public void updateHistoryShop(HistoryShop... historyShops)
			throws HibernateException {
		for(HistoryShop hs : historyShops){
			this.saveOrUpdate(hs);		
		}

	}

	@Override
	public void saveHistoryShop(HistoryShop... historyShops)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(HistoryShop hs : historyShops)
				session.save(hs);
		}catch(HibernateException e){
			log.error(e, "failed add historyshop.");
			throw e;
		}	

	}

	@Override
	public void saveOrUpdate(HistoryShop historyShop) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(historyShop);
		}catch(HibernateException e){
			log.error(e, "failed save or update a historyshop.");
			throw e;
		}		
	}

}
