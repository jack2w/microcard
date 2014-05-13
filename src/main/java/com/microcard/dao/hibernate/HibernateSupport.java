package com.microcard.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateSupport {

	ThreadLocal<Session> currentSession = new ThreadLocal<Session>();
	
	protected Session getSession(){
		Session session = HibernateUtil.instance().currentSession();
		return session;
	}
	
	protected <T> T save(T obj) throws HibernateException{
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			session.save(obj);
			transaction.commit();
		} catch(HibernateException e){
			throw e;
		} finally{
			session.close();
		}
		return obj;
		
	}
	
	protected <T> T update(T obj) throws HibernateException{
		Session session = getSession();
		try{
			Transaction transaction = session.beginTransaction();
			session.update(obj);
			transaction.commit();
		} catch(HibernateException e){
			throw e;
		} finally{
			session.close();
		}
		return obj;		
	}
	
	protected <T> T getobjByID(Class<T> className, String id) throws HibernateException{
		Session session = getSession();
		T obj = null;
		try{
			Transaction transaction = session.beginTransaction();
			obj = (T)session.load(className, id);
			transaction.commit();
		} catch(HibernateException e){
			throw e;
		} finally{
			session.close();
		}
		return obj;		
	}
	
	protected <T> T delete(Class<T> className, String id) throws HibernateException{
		Session session = getSession();
		T obj = null;
		try{
			Transaction transaction = session.beginTransaction();
			obj = (T)session.load(className, id);
			session.delete(obj);
			transaction.commit();
		} catch(HibernateException e){
			throw e;
		} finally{
			session.close();
		}
		return obj;		
	}
	
	protected <T> List<T>  queryUsers(Class<T> className) throws HibernateException{
		Session session = getSession();
		List<T> list = null;
		try{
			Transaction transaction = session.beginTransaction();
			list = session.createQuery("from " + className.getName()).list();
			transaction.commit();
		} catch(HibernateException e){
			throw e;
		} finally{
			session.close();
		}
		return list;		
	}
}
