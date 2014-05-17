package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.HistorySales;

public interface HistorySalesDAO {
	
	/**
	 * 获得所有记录
	 * @return
	 * @throws HibernateException
	 */
	@SuppressWarnings("rawtypes")
	public List getAllHistorySales() throws HibernateException;
	
	/**
	 * 删除记录
	 * @param historySales
	 * @throws HibernateException
	 */
	public void deleteHistorySales(HistorySales...  historySales) throws HibernateException;
	
	/**
	 * 通过id获得记录
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public HistorySales getHistorySalesByID(String id) throws HibernateException;
	
	/**
	 * 更新记录
	 * @param historySales
	 * @throws HibernateException
	 */
	public void updateHistorySales(HistorySales... historySales) throws HibernateException;
	
	/**
	 * 添加记录
	 * @param historySales
	 * @throws HibernateException
	 */
	public void saveHistorySales(HistorySales...  historySales) throws HibernateException;
	
	/**
	 * 该方法更新的仅是非集合属性信息，涉及到集合的属性需要调用相关方法
	 * @param user
	 * @throws HibernateException
	 */
	public void saveOrUpdate(HistorySales historySales) throws HibernateException;
}
