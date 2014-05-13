package com.microcard.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.microcard.log.Logger;

public class HibernateUtil {

	static final String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	
	private static volatile SessionFactory sessionFactory;
	
	private static final Logger log = Logger.getMsgLogger();
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	private static HibernateUtil instance = new HibernateUtil();
	
	private static void initialize(){
		try{
			Configuration config = new Configuration().configure(CONFIG_FILE_LOCATION);
			
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
			log.error("Can't initialize SessionFactory.");
		}
		
	}
	
	public Session currentSession() throws HibernateException{
		if(null != sessionFactory){
			return sessionFactory.openSession();
		} else{
			log.error("can't initialize SessionFactory.");
			throw new HibernateException("Session factory is not exsit.");
		}
	}
	
	public static HibernateUtil instance(){
		if(null == sessionFactory){
			initialize();
		}
		return instance;
	}
	
	
}
