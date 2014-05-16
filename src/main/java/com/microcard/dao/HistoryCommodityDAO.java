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
	
	public List getHistoryCommodity() throws HibernateException;
	
	public void deleteHistoryCommodity(HistoryCommodity...  historyCommoditys) throws HibernateException;
	
	public HistoryCommodity getHistoryCommodityByID(String id) throws HibernateException;
	
	public void updateHistoryCommodityUser(HistoryCommodity... historyCommoditys) throws HibernateException;
	
	public void saveHistoryCommodity(HistoryCommodity...  historyCommoditys) throws HibernateException;
}
