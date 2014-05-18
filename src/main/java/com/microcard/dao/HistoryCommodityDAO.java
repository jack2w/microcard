/**
 * 
 */
package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.HistoryCommodity;

/**
 * @author jiyaguang
 *
 */
public interface HistoryCommodityDAO {
	
	/**
	 * 获得所有购买商品xinxi
	 * @return
	 * @throws HibernateException
	 */
	public List<HistoryCommodity> getAllHistoryCommodity() throws HibernateException;
	
	/**
	 * 删除已购商品
	 * @param historyCommoditys
	 * @throws HibernateException
	 */
	public void deleteHistoryCommodity(HistoryCommodity...  historyCommoditys) throws HibernateException;
	
	/**
	 * 根据id获得已购商品
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public HistoryCommodity getHistoryCommodityByID(long id) throws HibernateException;
	
	/**
	 * 更新
	 * @param historyCommoditys
	 * @throws HibernateException
	 */
	public void updateHistoryCommodity(HistoryCommodity... historyCommoditys) throws HibernateException;
	
	/**
	 * 添加已购商品
	 * @param historyCommoditys
	 * @throws HibernateException
	 */
	public void saveHistoryCommodity(HistoryCommodity...  historyCommoditys) throws HibernateException;
	
	/**
	 * 该方法更新的仅是非集合属性信息，涉及到集合的属性需要调用相关方法
	 * @param historyCommodity
	 * @throws HibernateException
	 */
	public void saveOrUpdate(HistoryCommodity historyCommodity ) throws HibernateException;
}
