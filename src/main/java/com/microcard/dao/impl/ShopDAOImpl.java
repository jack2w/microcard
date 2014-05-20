package com.microcard.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
	public List<Shop> getShops() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			Criteria c = session.createCriteria(Shop.class);
			c.add(Restrictions.eq("delete_flag", false));
			return c.list();
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
	public void deletePhysicalShop(Shop... shops) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Shop s : shops){
				session.delete(s);
			}
		} catch(HibernateException ex){
			log.error(ex, "fail delete  shope.");
			throw ex;
		}
		
	}
	
	@Override
	public void deleteLogicalShop(Shop... shops) throws HibernateException {
		try{
			for(Shop s : shops){
				s.setDelete_flag(true);
				this.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "fail get shop by openid.");
			throw e;
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
	public List<User> getUsersByShop(Shop shop, int start, int length) throws HibernateException{
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot get shop's users since the shop is deleted.");
		}
		List<User> result =  new ArrayList<User>();		
		try{	
			Session session = HibernateUtil.instance().currentSession();	
			String hql = "from User as u inner join u.shops as s where s.openId=? and u.delete_flag=false";
			Query query = session.createQuery(hql).setString(0, shop.getOpenId());
			query.setFirstResult(start);
			query.setMaxResults(length);
			List<Object> temp = query.list();
			for(Object obj : temp){
				result.add((User)((Object[])obj)[0]);
			}
			return result;
			
		}catch(HibernateException e){
			log.error(e, "fail add  commodities.");
			throw e;
		}
       
	}
	
	@Override
	public void updateShop(Shop... shops) throws HibernateException {

		this.saveOrUpdate(shops);
		
	}

	@Override
	public void addShop(Shop... shopes) throws HibernateException {
		try{
			for (Shop s : shopes){
				s.setDelete_flag(false);
				this.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "fail add  commodities.");
			throw e;
		}
	}

	@Override
	public void addCommodity(Shop shop, Commodity... commodities)  throws HibernateException{
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot add shop's commodity since the shop is deleted.");
		}
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
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot add shop's sales since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
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
	
	public void saveOrUpdate(Shop... shops) throws HibernateException{	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Shop s : shops){
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "fail save or update a shop.");
			throw e;
		}
	}

	@Override
	public void updateCommodity(Shop shop, Commodity... commodities)  throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot update shop's commodity since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
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
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot delete shop's commodity since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
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
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot delete shop's sales since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
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
		if( this.getShopByOpenID(shop.getOpenId()).isDelete_flag()){
			throw new HibernateException("cannot update shop's sales since the shop is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Sales c : saleses){
				session.update(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail update sales.");
			throw e;
		}
		
	}

	@Override
	public Shop getShopByOpenID(String opendid) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			Criteria c = session.createCriteria(Shop.class);
			c.add(Restrictions.eq("openId", opendid));
			List<Shop> list = c.list();
			//因为openid已经限定唯一性，查到的结果最多为一条
			if(list.size() > 0){
				return list.get(0);
			}
			return null;
		}catch(HibernateException e){
			log.error(e, "fail get shop by openid.");
			throw e;
		}
	}



}
