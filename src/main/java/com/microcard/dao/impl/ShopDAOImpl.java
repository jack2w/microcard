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
			c.add(Restrictions.eq("deleteFlag", false));
			return c.list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all shops.");
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
			log.error(ex, "failed delete physically shop.");
			throw ex;
		}
		
	}
	
	@Override
	public void deleteLogicalShop(Shop... shops) throws HibernateException {
		try{
			for(Shop s : shops){
				s.setDeleteFlag(true);
				this.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete logically shop.");
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
			log.error(ex, "failed get shop by id.");
			throw ex;
		}
		return s;
	}
	

	@Override
	public List<User> getUsersByShop(Shop shop, int start, int length) throws HibernateException{
		if(shop == null) return null;
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot get shop's users since the shop is deleted.");
		}
		List<User> result =  new ArrayList<User>();		
		try{	
			Session session = HibernateUtil.instance().currentSession();	
			String hql = "from User as u inner join u.shops as s where s.openId=? and u.deleteFlag=false";
			Query query = session.createQuery(hql).setString(0, shop.getOpenId());
			query.setFirstResult(start);
			query.setMaxResults(length);
			List<Object> temp = query.list();
			for(Object obj : temp){
				result.add((User)((Object[])obj)[0]);
			}
			return result;
			
		}catch(HibernateException e){
			log.error(e, "failed get users from shop.");
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
				s.setDeleteFlag(false);
				this.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "failed add shop.");
			throw e;
		}
	}

	@Override
	public void addCommodity(Shop shop, Commodity... commodities)  throws HibernateException{
		Shop s = this.getShopByOpenID(shop.getOpenId());
		if(s == null || s.isDeleteFlag() ){
			throw new HibernateException("cannot add shop's commodity since the shop is deleted.");
		}
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Commodity c : commodities){
				c.setShop(shop);
				session.save(c);
			}
		}catch(HibernateException e){
			log.error(e, "failed add commodities to shop.");
			throw e;
		}
	}

	@Override
	public void addSales(Shop shop, Sales... saleses)  throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
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
			log.error(e, "failed add sales to shop.");
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
			log.error(e, "failed save or update shop.");
			throw e;
		}
	}

	@Override
	public void updateCommodity(Shop shop, Commodity... commodities)  throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
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
			log.error(e, "failed update commodities to shop.");
			throw e;
		}
		
	}

	@Override
	public void delteCommoditity(Shop shop, Commodity... commodities)
			throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot delete shop's commodity since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(commodities == null){
				commodities = shop.getCommodities().toArray(new Commodity[shop.getCommodities().size()]) ;
			}
			for(Commodity c : commodities){
				c.setDeleteFlag(true);
				session.saveOrUpdate(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "failed delete commodities from shop.");
			throw e;
		}
		
	}

	@Override
	public void deleteSales(Shop shop, Sales... saleses)
			throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot delete shop's sales since the shop is deleted.");
		}
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(saleses == null){
				saleses = shop.getSales().toArray(new Sales[shop.getSales().size()]) ;
			}
			for(Sales c : saleses){
				c.setDeleteFlag(true);
				session.saveOrUpdate(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "failed delete sales from shop.");
			throw e;
		}
		
	}

	@Override
	public void updateSales(Shop shop, Sales... saleses)
			throws HibernateException {
		if( this.getShopByOpenID(shop.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot update shop's sales since the shop is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Sales c : saleses){
				session.update(c);
			}
			this.saveOrUpdate(shop);
		}catch(HibernateException e){
			log.error(e, "fail update sales to shop.");
			throw e;
		}
		
	}

	@Override
	public Shop getShopByOpenID(String opendid) throws HibernateException {	
		try{
			Session session = HibernateUtil.instance().currentSession();
			Criteria c = session.createCriteria(Shop.class);
			c.add(Restrictions.eq("openId", opendid));
			List<Shop> list = c.list();
			//因为openid已经限定唯一性，查到的结果最多为一条
			if(list.size() > 0){
				return list.get(0);
			}
			return null;
		}catch(HibernateException e){
			log.error(e, "failed get shop by openid.");
			throw e;
		}
	}

	@Override
	public List<Commodity> getCommodity(String openid, int start, int length) {
			Shop s = this.getShopByOpenID(openid);
			if( s == null ||   s.isDeleteFlag()){
				throw new HibernateException("cannot get shop's commodity since the shop is not exist.");
			}	
		List<Commodity> result =  new ArrayList<Commodity>();		
		try{
			Session session = HibernateUtil.instance().currentSession();	
			String hql = "from Commodity c where c.deleteFlag=false";
			Query query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(length);
			List<Object> temp = query.list();
			for(Object obj : temp){
				result.add((Commodity)((Object[])obj)[0]);
			}
			return result;
		}catch(HibernateException e){
			log.error(e, "failed get commdities by shop openid.");
			throw e;
		}
	}

}
