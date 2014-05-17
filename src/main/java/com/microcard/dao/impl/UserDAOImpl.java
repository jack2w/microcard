package com.microcard.dao.impl;

import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.microcard.bean.Record;
import com.microcard.bean.Shop;
import com.microcard.bean.User;
import com.microcard.dao.UserDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class UserDAOImpl implements UserDAO{

	private Logger log = Logger.getMsgLogger();
	
	@Override
	public List getUsers() throws HibernateException {
		
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + User.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "fail get users.");
			throw ex;
		}
		
	}

	/**
	 * 删除用户，并删除和商铺之间的关系
	 */
	@Override
	public void deleteUser(User... users) throws HibernateException {
		
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
			log.error(ex, "fail delete user.");
			throw ex;
		}
	}

	@Override
	public User getUserByID(String id) throws HibernateException {
		User u = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	       u = (User)session.load(User.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "fail get user by id.");
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
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(User u : users){
					session.save(u);
				}
		}catch(HibernateException e){
			log.error(e, "fail save or update a user.");
			throw e;
		}
	}

	@Override
	public void saveOrUpdate(User... users) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(User u : users){
				session.saveOrUpdate(u);
			}
		}catch(HibernateException e){
			log.error(e, "fail save or update a user.");
			throw e;
		}
		
	}

	@Override
	public void addRecords(User u, Record... records) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Record r : records){
				r.setUser(u);
				session.save(r);
			}
			this.saveOrUpdate(u);
		}catch(HibernateException e){
			log.error(e, "fail add  records.");
			throw e;
		}
		
	}

	@Override
	public void updateRecords(User u, Record... records) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Record r : records){
				r.setUser(u);
				session.update(r);				
			}
			this.saveOrUpdate(u);
		}catch(HibernateException e){
			log.error(e, "fail add  records.");
			throw e;
		}
		
	}

	@Override
	public void deleteRecords(User u, Record... records) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			if(records == null){
				records = u.getRecords().toArray(new Record[u.getRecords().size()]) ;
			}
			for(Record r : records){
				session.delete(r);
			}
		}catch(HibernateException e){
			log.error(e, "fail add  records.");
			throw e;
		}
		
	}

	@Override
	public void addShops(User u, Shop... shops) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Shop s : shops){
				if(s.getUsers() == null){
					s.setUsers(new HashSet<User>());
				}
				s.getUsers().add(u);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "fail add  shops.");
			throw e;
		}
		
	}

	@Override
	public void deleteShop(User u, Shop... shops) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
			if(shops == null){
				shops = u.getShops().toArray(new Shop[u.getShops().size()]) ;
			}
			for(Shop s : shops){
				if(s.getUsers() == null){
					return;
				}
				s.getUsers().remove(u);
				session.saveOrUpdate(s);
			}
		}catch(HibernateException e){
			log.error(e, "fail delete  shops.");
			throw e;
		}
		
	}

}
