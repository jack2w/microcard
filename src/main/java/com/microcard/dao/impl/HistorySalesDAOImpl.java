package com.microcard.dao.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.microcard.bean.HistorySales;
import com.microcard.dao.HistorySalesDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class HistorySalesDAOImpl implements HistorySalesDAO {

	private Logger log = Logger.getMsgLogger();
	
	@Override
	public List getAllHistorySales() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + HistorySales.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all historysales.");
			throw ex;
		}
	}

	@Override
	public void deleteHistorySales(HistorySales... historySales)
			throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(HistorySales s : historySales){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete historysales.");
			throw ex;
		}

	}

	@Override
	public HistorySales getHistorySalesByID(long id)
			throws HibernateException {
		HistorySales s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (HistorySales)session.load(HistorySales.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get historysales by id.");
			throw ex;
		}
		return s;
	}

	@Override
	public void updateHistorySales(HistorySales... historySales)
			throws HibernateException {
		for(HistorySales hs : historySales){
			this.saveOrUpdate(hs);		
		}
	}

	@Override
	public void saveHistorySales(HistorySales... historySales)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(HistorySales hs : historySales)
				session.save(hs);
		}catch(HibernateException e){
			log.error(e, "failed add  historysales.");
			throw e;
		}	

	}

	@Override
	public void saveOrUpdate(HistorySales historySales)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(historySales);
		}catch(HibernateException e){
			log.error(e, "failed save or update a historysales.");
			throw e;
		}		
		
	}

}
