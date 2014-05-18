package com.microcard.dao;

import java.util.List;
import org.hibernate.HibernateException;
import com.microcard.bean.Record;

public interface RecordDAO {
	
	/**
	 * 获得所有购买纪录
	 * @return
	 * @throws HibernateException
	 */
	public List<Record> getRecords() throws HibernateException;

	/**
	 * 删除购买记录
	 * @param records
	 * @throws HibernateException
	 */
	public void deleteRecord(Record... records)
			throws HibernateException;

	/**
	 * 根据id获得购买记录
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public Record getRecordByID(long id) throws HibernateException;

	/**
	 * 更新
	 * @param records
	 * @throws HibernateException
	 */
	public void updateRecord(Record... records)
			throws HibernateException;

	/**
	 * 添加
	 * @param records
	 * @throws HibernateException
	 */
	public void saveRecord(Record... records)
			throws HibernateException;
	
	/**
	 * 该方法更新的仅是非集合属性信息，涉及到集合的属性需要调用相关方法
	 * @param record
	 * @throws HibernateException
	 */
	public void saveOrUpdate(Record record) throws HibernateException;
}
