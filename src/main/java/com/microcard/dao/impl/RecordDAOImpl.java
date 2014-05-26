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
			return session.createQuery("from " + Record.class.getName() + " r where r.deleteFlag=false").list();
		} catch(HibernateException ex){
			log.error(ex, "failed get all records.");
			throw ex;
		}
	}

	@Override
	public void deleteRecord(Record... records) throws HibernateException {
		try{
			Session session = HibernateUtil.instance().currentSession();
			for(Record s : records){
				s.setDeleteFlag(true);
				session.saveOrUpdate(s);
			}		
		} catch(HibernateException ex){
			log.error(ex, "failed delete record.");
			throw ex;
		}

	}

	@Override
	public Record getRecordByID(long id) throws HibernateException {
		Record s = null;
		try{
			Session session = HibernateUtil.instance().currentSession();
	        s = (Record)session.load(Record.class.getName(), id);
		} catch(HibernateException ex){
			log.error(ex, "failed get record by id.");
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
			log.error(e, "failed add record.");
			throw e;
		}	

	}
	
	public void saveOrUpdate(Record r) throws HibernateException{
		Session session = HibernateUtil.instance().currentSession();
		try{
				session.saveOrUpdate(r);
		}catch(HibernateException e){
			log.error(e, "failed save or update a record.");
			throw e;
		}
	}

}
