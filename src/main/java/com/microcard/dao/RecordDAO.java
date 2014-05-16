package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Record;

public interface RecordDAO {
	
	public List getRecord() throws HibernateException;

	public void deleteRecord(Record... records)
			throws HibernateException;

	public Record getRecordByID(String id) throws HibernateException;

	public void updateRecord(Record... records)
			throws HibernateException;

	public void saveRecord(Record... records)
			throws HibernateException;
	
}
