package com.microcard.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.microcard.bean.Record;
import com.microcard.dao.RecordDAO;
import com.microcard.dao.hibernate.HibernateUtil;
import com.microcard.log.Logger;

public class RecordDAOImpl implements RecordDAO {

	private Logger log = Logger.getMsgLogger();
	
	@Override
	public List getRecords() throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			return session.createQuery("from " + Record.class.getName()).list();
		} catch(HibernateException ex){
			log.error(ex, "fail get  Record.");
			throw ex;
		}
	}

	@Override
	public void deleteRecord(Record... records) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Record s : records){
				session.delete(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "fail delete  Record.");
			throw ex;
		}

	}

	@Override
	public Record getRecordByID(String id) throws HibernateException {
		Record s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (Record)session.load(Record.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "fail get record by id.");
			throw ex;
		}
		return s;
	}

	@Override
	public void updateRecord(Record... records) throws HibernateException {
		for(Record r : records){
			this.saveOrUpdate(r);		
		}

	}

	@Override
	public void saveRecord(Record... records) throws HibernateException {
		Session session = HibernateUtil.instance().currentSession();
		try{
				for(Record r : records)
				session.save(r);
		}catch(HibernateException e){
			log.error(e, "fail add  Record.");
			throw e;
		}	

	}
	
	public void saveOrUpdate(Record r) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(r);
		}catch(HibernateException e){
			log.error(e, "fail save or update a Sales.");
			throw e;
		}
	}

}
