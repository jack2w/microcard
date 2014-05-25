package com.microcard.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.dao.SalesDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class SalesDAOImpl implements SalesDAO{

	private Logger log = Logger.getMsgLogger();
	
	@Override
	public List getSales() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + Sales.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all sales.");
			throw ex;
		}
	}

	@Override
	public void deleteSales(Sales... sales) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Sales s : sales){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete sales.");
			throw ex;
		}
		
	}

	@Override
	public Sales getSalesByID(long id) throws HibernateException {
		Sales s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (Sales)session.load(Sales.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get sales by id.");
			throw ex;
		}
		return s;
	}

	@Override
	public void updateSales(Sales... sales) throws HibernateException {
		for(Sales s : sales){
			this.saveOrUpdate(s);		
		}
	
	}

	@Override
	public void saveSales(Sales... sales) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(Sales s : sales)
				session.save(s);
		}catch(HibernateException e){
			log.error(e, "failed add sales.");
			throw e;
		}	
	}
	
	public void saveOrUpdate(Sales s) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(s);
		}catch(HibernateException e){
			log.error(e, "failed save or update sales.");
			throw e;
		}
	}

	@Override
	public void addCommodity(Sales sales, Commodity... commodities)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Commodity c : commodities){
				c.setSales(sales);
				session.save(c);
			}
			this.saveOrUpdate(sales);
		}catch(HibernateException e){
			log.error(e, "failed add commodities to sale.");
			throw e;
		}
		
	}

	@Override
	public void updateCommodity(Sales sales, Commodity... commodities)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Commodity c : commodities){
				c.setSales(sales);
				session.update(c);
			}
		}catch(HibernateException e){
			log.error(e, "failed update commodity to sale.");
			throw e;
		}
		
	}

	@Override
	public void deleteCommodity(Sales sales, Commodity... commodities)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			if(commodities == null){
				commodities = sales.getCommodities().toArray(new Commodity[sales.getCommodities().size()]) ;
			}
			for(Commodity c : commodities){
				session.update(c);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete commodity from sale.");
			throw e;
		}
		
	}
}
