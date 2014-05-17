package com.microcard.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.ShopDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class ShopDAOImpl  implements ShopDAO{

	private Logger log = Logger.getMsgLogger();
	
	@Override
	public List getShops() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + User.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "fail get  shopes.");
			throw ex;
		}
	}

	@Override
	/**
	 * 删除商铺
	 * 如果商铺包含商品，营销方式信息，关联删除
	 * 如果商铺包含用户信息，不删除用户信息
	 * @param id
	 * @throws HibernateException
	 */
	public void deleteShop(Shop shop) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			session.delete(shop);
		} catch(HibernateException ex){
			log.error(ex, "fail delete  shope.");
			throw ex;
		}
		
	}

	@Override
	public Shop getShopByID(long id) throws HibernateException {
		Shop s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (Shop)session.load(Shop.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "fail get shope by id.");
			throw ex;
		}
		return s;
	}
	

	@Override
	public User[] getUsersByShop(Shop shop, int start, int length) throws HibernateException{

		Session session = HibernateUtil.instance().currentSession();
		try{
			Query query = session.createQuery("from Shop s where s.openId =:openid ");
			query.setParameter("openid", shop.getOpenId());
			shop.setUsers((Set)query.list());
			
		} catch(HibernateException e){
			log.error(e, "fail get uses by shopid.");
			throw e;
		} 
		
		User[] users =( User[] )shop.getUsers().toArray();
        if(users.length < start){
        	return null;
        }
        else if(users.length > (start + length)){
        	return Arrays.copyOfRange(users, start,  start + length);
        } else{
        	return Arrays.copyOfRange(users, start,  users.length);
        }
       
	}
	
	@Override
	public void updateShop(Shop shop) throws HibernateException {

		this.saveOrUpdate(shop);
		
	}

	@Override
	public void addShop(Shop shop) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.save(shop);
		}catch(HibernateException e){
			log.error(e, "fail add  commodities.");
			throw e;
		}
	}

	@Override
	public void addCommodity(Shop shop, Commodity... commodities)  throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Commodity c : commodities){
				c.setShop(shop);
				session.save(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail add  commodities.");
			throw e;
		}
	}

	@Override
	public void addSales(Shop shop, Sales... saleses)  throws HibernateException {
		
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Sales s : saleses){
				s.setShop(shop);
				session.save(s);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail add  sales.");
			throw e;
		}
		
	}
	
	public void saveOrUpdate(Shop s) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(s);
		}catch(HibernateException e){
			log.error(e, "fail save or update a shop.");
			throw e;
		}
	}

	@Override
	public void updateCommodity(Shop shop, Commodity... commodities)  throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Commodity c : commodities){
				c.setShop(shop);
				session.update(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail update  commodities.");
			throw e;
		}
		
	}

	@Override
	public void delteCommoditity(Shop shop, Commodity... commodities)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			if(commodities == null){
				commodities = shop.getCommodities().toArray(new Commodity[shop.getCommodities().size()]) ;
			}
			for(Commodity c : commodities){
				session.delete(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail delete  commodities.");
			throw e;
		}
		
	}

	@Override
	public void deleteSales(Shop shop, Sales... saleses)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			if(saleses == null){
				saleses = shop.getSales().toArray(new Sales[shop.getSales().size()]) ;
			}
			for(Sales c : saleses){
				session.delete(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail delete  sales.");
			throw e;
		}
		
	}

	@Override
	public void updateSales(Shop shop, Sales... saleses)
			throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Sales c : saleses){
				session.update(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail update sales.");
			throw e;
		}
		
	}

}
