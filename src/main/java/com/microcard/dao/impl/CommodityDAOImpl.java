/**
 * 
 */
package com.microcard.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
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
			return session.createQuery("from " + Commodity.class.getName() +" c where c.deleteFlag=false").list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all commodities.");
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
				s.setDeleteFlag(true);
				session.saveOrUpdate(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete commodity.");
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
	        s = (Commodity)session.get(Commodity.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get commodity by id.");
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
			log.error(e, "failed add commodity.");
			throw e;
		}	

	}

	@Override
	public void saveOrUpdate(Commodity commodity) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(commodity);
		}catch(HibernateException e){
			log.error(e, "failed save or update a commodity.");
			throw e;
		}
		
	}

	@Override
	public void addSales(Commodity commodity, Sales... sales)
			throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(commodity.getSales() == null){
				commodity.setSales(new HashSet<Sales>());
			}
			for(Sales s : sales){
				if(s.getCommodities() == null){
					s.setCommodities(new HashSet<Commodity>());
				}
				s.getCommodities().add(commodity);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){ 
			log.error(e, "failed add sales on commodity.");
			throw e;
		}
		
	}

	@Override
	public void deleteSales(Commodity commodity, Sales... sales)
			throws HibernateException {
		if( this.getCommodityByID(commodity.getId()).isDeleteFlag()){
			throw new HibernateException("cannot delete commodity's sales since the commodity is deleted.");
		}			
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(sales == null){
				sales = commodity.getSales().toArray(new Sales[commodity.getSales().size()]) ;
			}
			for(Sales s : sales){
				if(s.getCommodities() == null){
					continue;
				}
				s.getCommodities().remove(commodity);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete sales from commodity.");
			throw e;
		}
		
	}

	@Override
	public List<Sales> getSalesByCommodity(Commodity commodity, int start, int length)
			throws HibernateException {		
		if(this.getCommodityByID(commodity.getId()).isDeleteFlag()){
			throw new HibernateException("cannot get commodity's sales since the commodity is deleted.");
		}
		List<Sales> result =  new ArrayList<Sales>();		
		try{	
			Session session = HibernateUtil.instance().currentSession();	
			String hql = "from Sales as s inner join s.commodities as c where c=? and s.deleteFlag=false";
			Query query = session.createQuery(hql).setParameter(0, commodity);
			query.setFirstResult(start);
			query.setMaxResults(length);
			List<Object> temp = query.list();
			for(Object obj : temp){
				result.add((Sales)((Object[])obj)[0]);
			}
			return result;
			
		}catch(HibernateException e){
			log.error(e, "failed get sales from commodity.");
			throw e;
		}
		
	}

}
