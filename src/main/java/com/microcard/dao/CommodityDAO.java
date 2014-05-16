/**
 * 
 */
package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Commodity;

/**
 * @author jiyaguang
 *
 */
public interface CommodityDAO {

	public List getCommodity() throws HibernateException;
	
	public void deleteCommodity(Commodity... commoditys) throws HibernateException;
	
	public Commodity getCommodityByID(String id) throws HibernateException;
	
	public void updateCommodityUser(Commodity... commoditys) throws HibernateException;
	
	public void saveCommodity(Commodity...  commoditys) throws HibernateException;
}
