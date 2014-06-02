package com.microcard.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.microcard.bean.Record;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.UserDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class UserDAOImpl implements UserDAO{

	private Logger log = Logger.getOperLogger();
	
	@Override
	public List<User> getUsers() throws HibernateException {
		
		try{
			Session session = HibernateUtil.instance().currentSession();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("deleteFlag", false));
			return c.list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all users.");
			throw ex;
		}
		
	}

	/**
	 * 删除用户，并删除和商铺之间的关系
	 */
	@Override
	public void deletePhsycalUser(User... users) throws HibernateException {
		
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(User u : users){
				if(u.getShops() != null){
					for ( Shop s : u.getShops()){
						s.getUsers().remove(u);
						session.update(s);
					}
				}
				session.delete(u);
			}			
		} catch(HibernateException ex){
			log.error(ex, "failed delete phsycally user.");
			throw ex;
		}
	}

	@Override
	public User getUserByID(String id) throws HibernateException {
		User u = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	       u = (User)session.get(User.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get user by id.");
			throw ex;
		}
		return u;
	}

	@Override
	public void updateUser(User... users) throws HibernateException {
			this.saveOrUpdate(users);		
	}

	@Override
	public void saveUser(User... users) throws HibernateException {		
		try{
			Session session = HibernateUtil.instance().currentSession();
				for(User u : users){
					u.setDeleteFlag(false);
					session.saveOrUpdate(u);
				}
		}catch(HibernateException e){
			log.error(e, "fail save or update a user.");
			throw e;
		}
	}

	@Override
	public void saveOrUpdate(User... users) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(User u : users){
				session.saveOrUpdate(u);
			}
		}catch(HibernateException e){
			log.error(e, "failed save or update a user.");
			throw e;
		}
		
	}

	@Override
	public void addRecords(User u, Record... records) throws HibernateException{
		if( this.getUserByOpenID(u.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot add user's records since the user is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Record r : records){
				r.setUser(u);
				session.save(r);
			}
			this.saveOrUpdate(u);
		}catch(HibernateException e){
			log.error(e, "failed add  records to user.");
			throw e;
		}
		
	}

	@Override
	public void updateRecords(User u, Record... records) throws HibernateException{
		if( this.getUserByOpenID(u.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot update user's records since the user is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Record r : records){
				r.setUser(u);
				session.update(r);				
			}
			this.saveOrUpdate(u);
		}catch(HibernateException e){
			log.error(e, "failed update records to user.");
			throw e;
		}
		
	}

	@Override
	public void deleteRecords(User u, Record... records) throws HibernateException {
		if( this.getUserByOpenID(u.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot delete user's records since the user is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(records == null){
				records = u.getRecords().toArray(new Record[u.getRecords().size()]) ;
			}
			for(Record r : records){
				session.delete(r);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete records from user.");
			throw e;
		}
		
	}

	@Override
	public void addShops(User u, Shop... shops) throws HibernateException {
		if( u.isDeleteFlag() == true ){
			throw new HibernateException("cannot add user's shops since the user is deleted.");
		}	
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Shop s : shops){
				if(s.getUsers() == null){
					s.setUsers(new HashSet<User>());
				}
				s.getUsers().add(u);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){ 
			log.error(e, "failed add shop.");
			throw e;
		}
		
	}

	@Override
	public void deleteShop(User u, Shop... shops) throws HibernateException {
		if( this.getUserByOpenID(u.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot delete user's shops since the user is deleted.");
		}			
		try{
			Session session = HibernateUtil.instance().currentSession();
			if(shops == null){
				shops = u.getShops().toArray(new Shop[u.getShops().size()]) ;
			}
			for(Shop s : shops){
				if(s.getUsers() == null){
					continue;
				}
				s.getUsers().remove(u);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete shop from user.");
			throw e;
		}
		
	}

	@Override
	public void deleteLogicalUser(User... users) throws HibernateException {
		try{
			for(User u : users){
				u.setDeleteFlag(true);
				this.saveOrUpdate(u);
			}
		}catch(HibernateException e){
			log.error(e, "failed delete logically user.");
			throw e;
		}	
	}
	
	@Override
	public User getUserByOpenID(String opendid) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("openId", opendid));
			List<User> list = c.list();
			//因为openid已经限定唯一性，查到的结果最多为一条
			return list.size() > 0 ? list.get(0) : null;

		}catch(HibernateException e){
			log.error(e, "failed get user by openid.");
			throw e;
		}
	}
	
	@Override
	public List<Shop> getShopsByUser(User u, int start, int length) throws HibernateException{
		if( this.getUserByOpenID(u.getOpenId()).isDeleteFlag()){
			throw new HibernateException("cannot get user's shop since the user is deleted.");
		}
		List<Shop> result =  new ArrayList<Shop>();		
		try{	
			Session session = HibernateUtil.instance().currentSession();	
			String hql = "from Shop as s inner join s.users as u where u.openId=? and s.deleteFlag=false";
			Query query = session.createQuery(hql).setString(0, u.getOpenId());
			query.setFirstResult(start);
			query.setMaxResults(length);
			List<Object> temp = query.list();
			for(Object obj : temp){
				result.add((Shop)((Object[])obj)[0]);
			}
			return result;
			
		}catch(HibernateException e){
			log.error(e, "failed get shops from user.");
			throw e;
		}
       
	}

	@Override
	public User getUserByID(long id) throws HibernateException {
		User u = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        u = (User)session.get(User.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get user by id.");
			throw ex;
		}
		return u;
	}

	@Override
	public List<Record> getRecordsByUserShop(User user, Shop shop, int start,
			int length) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			String hql = "from Record r where r.user = ? and r.shop = ? and r.deleteFlag=false order by time desc";
			Query query = session.createQuery(hql);
			query.setParameter(0, user);
			query.setParameter(1, shop);
			query.setFirstResult(start);
			query.setMaxResults(length);
			return query.list();
		} catch(HibernateException ex){
			log.error(ex, "failed get records by shop and user.");
		}
		return null;
	}

}
