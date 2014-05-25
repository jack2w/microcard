/**
 * 
 */
package com.microcard.dao.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.microcard.bean.HistoryCommodity;
import com.microcard.dao.HistoryCommodityDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * @author jiyaguang
 *
 */
public class HistoryCommodityDAOImpl implements HistoryCommodityDAO {

	private Logger log = Logger.getMsgLogger();
	/* (non-Javadoc)
	 * @see com.microcard.dao.HistoryCommodityDAO#getHistoryCommodity()
	 */
	@Override
	public List getAllHistoryCommodity() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + HistoryCommodity.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all historycommodities.");
			throw ex;
		}
	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.HistoryCommodityDAO#deleteHistoryCommodity(com.microcard.bean.HistoryCommodity[])
	 */
	@Override
	public void deleteHistoryCommodity(HistoryCommodity... historyCommoditys)
			throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(HistoryCommodity s : historyCommoditys){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete historycommodities.");
			throw ex;
		}

	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.HistoryCommodityDAO#getHistoryCommodityByID(java.lang.String)
	 */
	@Override
	public HistoryCommodity getHistoryCommodityByID(long id)
			throws HibernateException {
		HistoryCommodity s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (HistoryCommodity)session.load(HistoryCommodity.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get historycommodity by id.");
			throw ex;
		}
		return s;
	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.HistoryCommodityDAO#updateHistoryCommodity(com.microcard.bean.HistoryCommodity[])
	 */
	@Override
	public void updateHistoryCommodity(HistoryCommodity... historyCommoditys)
			throws HibernateException {
		for(HistoryCommodity hc : historyCommoditys){
			this.saveOrUpdate(hc);		
		}

	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.HistoryCommodityDAO#saveHistoryCommodity(com.microcard.bean.HistoryCommodity[])
	 */
	@Override
	public void saveHistoryCommodity(HistoryCommodity... historyCommoditys)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(HistoryCommodity hc : historyCommoditys)
				session.save(hc);
		}catch(HibernateException e){
			log.error(e, "failed add  historycommodities.");
			throw e;
		}	

	}

	@Override
	public void saveOrUpdate(HistoryCommodity historyCommodity)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(historyCommodity);
		}catch(HibernateException e){
			log.error(e, "failed save or update a historycommodities.");
			throw e;
		}
		
	}

}
