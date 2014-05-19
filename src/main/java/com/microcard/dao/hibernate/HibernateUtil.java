package com.microcard.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.microcard.log.Logger;

public class HibernateUtil {

	static final String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	
	private static volatile SessionFactory sessionFactory;
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal tLocalsess = new ThreadLocal();
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal tLocaltx = new ThreadLocal();
	
	private static final Logger log = Logger.getMsgLogger();
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	private static HibernateUtil instance = new HibernateUtil();
	
	private static void initialize(){
		try{
			Configuration config = new Configuration().configure(CONFIG_FILE_LOCATION);
			
			@SuppressWarnings("deprecation")
			final ServiceRegistry serviceRegistry =  new ServiceRegistryBuilder().applySettings(config.getProperties()).build();
			
			config.setSessionFactoryObserver(new SessionFactoryObserver(){
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void sessionFactoryClosed(SessionFactory arg0) {
					( (StandardServiceRegistryImpl) arg0 ).destroy(); 				
				}
	
				@Override
				public void sessionFactoryCreated(SessionFactory arg0) {
					sessionFactory = arg0;				
				}
			
			});
			
			sessionFactory = config.buildSessionFactory(serviceRegistry);
		} catch(Exception e){
			log.error(e, "Can't initialize SessionFactory.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获得当前线程的session
	 * @return
	 * @throws HibernateException
	 */
	public Session currentSession() throws HibernateException{
		Session session = (Session)tLocalsess.get();
		
		try{
			if(session == null || !session.isOpen()){
				session = sessionFactory.openSession();
				tLocalsess.set(session);
			}
		} catch(HibernateException e){
			log.error("can't get currentSession.");
			throw new HibernateException("can't get currentSession.");
		}
		
		return session;
	}
	

	public void closeSession(){
		Session session = (Session)tLocalsess.get();
		tLocalsess.set(null);
		try{
			if(session != null || session.isOpen()){
				session.close();
			}
		} catch(HibernateException e){
			log.error("can't close currentSession.");
			throw new HibernateException("can't close currentSession.");
		}
	}
	
	/**
	 * 开始事务
	 */
	public void beginTransaction(){
		Transaction tx = (Transaction)tLocaltx.get();
		try{
			if(tx == null){
				tx = currentSession().beginTransaction();
				tLocaltx.set(tx);
			}
		} catch(HibernateException e){
			log.error("can't beigin a transaction.");
			throw new HibernateException("can't beigin a transaction.");
		}
	}
	
	/**
	 * 提交并关闭session
	 */
	public void commitTransactionAndColoseSession(){
		Transaction tx = (Transaction)tLocaltx.get();
		try{
			if(tx != null && !tx.wasCommitted() && !tx.wasRolledBack()){
				tx.commit();
				tLocaltx.set(null);
				closeSession();
			}
		} catch(HibernateException e){
			log.error("can't commit a transaction.");
			throw new HibernateException("can't commit a transaction.");
		}
	}
	
	/**
	 * 回退
	 */
	public void rollbackTransaction(){
		Transaction tx = (Transaction)tLocaltx.get();
		try{
			if(tx != null && !tx.wasCommitted() && !tx.wasRolledBack()){
				tx.rollback();
				tLocaltx.set(null);
			}
		} catch(HibernateException e){
			log.error("can't rollback a transaction.");
			throw new HibernateException("can't rollback a transaction.");
		}
	}
	public static HibernateUtil instance(){
		if(null == sessionFactory){
			initialize();
		}
		return instance;
	}
	
	
}
