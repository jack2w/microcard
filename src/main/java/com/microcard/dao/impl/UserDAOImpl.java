package com.microcard.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.microcard.dao.UserDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;
import com.microcard.bean.Record;
import com.microcard.bean.Sales;
import com.microcard.bean.Shop;
import com.microcard.bean.User;

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

	@Override
	public void deleteUser(User u) throws HibernateException {
		
		try{
			Session session = HibernateUtil.instance().currentSession();
			session.delete(u);
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
	public void updateUser(User user) throws HibernateException {
		this.saveOrUpdate(user);
		
	}

	@Override
	public void saveUser(User user) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.save(user);
		}catch(HibernateException e){
			log.error(e, "fail save or update a user.");
			throw e;
		}
	}

	@Override
	public void saveOrUpdate(User u) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(u);
		}catch(HibernateException e){
			log.error(e, "fail save or update a user.");
			throw e;
		}
		
	}

	@Override
	public void addRecords(User u, Record... records) {
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
	public void updateRecords(User u, Record... records) {
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
	public void deleteRecords(User u, Record... records) {
		Session session = HibernateUtil.instance().currentSession();
		try{
			for(Record r : records){
				session.delete(r);
			}
		}catch(HibernateException e){
			log.error(e, "fail add  records.");
			throw e;
		}
		
	}


}
