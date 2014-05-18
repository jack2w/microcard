/**
 * 
 */
package com.microcard.dao.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.microcard.bean.Commodity;
import com.microcard.dao.CommodityDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

/**
 * @author jiyaguang
 *
 */
public class CommodityDAOImpl implements CommodityDAO {

	private Logger log = Logger.getMsgLogger();
	/* (non-Javadoc)
	 * @see com.microcard.dao.CommodityDAO#getCommodity()
	 */
	@Override
	public List<Commodity> getAllCommodity() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + Commodity.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "fail get  Commodity.");
			throw ex;
		}
	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.CommodityDAO#deleteCommodity(com.microcard.bean.Commodity[])
	 */
	@Override
	public void deleteCommodity(Commodity... commoditys)
			throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Commodity s : commoditys){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "fail delete  Commodity.");
			throw ex;
		}

	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.CommodityDAO#getCommodityByID(java.lang.String)
	 */
	@Override
	public Commodity getCommodityByID(long id) throws HibernateException {
		Commodity s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (Commodity)session.load(Commodity.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "fail get commodity by id.");
			throw ex;
		}
		return s;
	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.CommodityDAO#updateCommodity(com.microcard.bean.Commodity[])
	 */
	@Override
	public void updateCommodity(Commodity... commoditys)
			throws HibernateException {
		for(Commodity c : commoditys){
			this.saveOrUpdate(c);		
		}
	}

	/* (non-Javadoc)
	 * @see com.microcard.dao.CommodityDAO#saveCommodity(com.microcard.bean.Commodity[])
	 */
	@Override
	public void saveCommodity(Commodity... commoditys)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(Commodity hc : commoditys)
				session.save(hc);
		}catch(HibernateException e){
			log.error(e, "fail add  commodity.");
			throw e;
		}	

	}

	@Override
	public void saveOrUpdate(Commodity commodity) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(commodity);
		}catch(HibernateException e){
			log.error(e, "fail save or update a commodity.");
			throw e;
		}
		
	}

}
