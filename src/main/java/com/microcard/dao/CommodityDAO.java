/**
 * 
 */
package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Commodity;
import com.microcard.bean.Sales;

/**
 * @author jiyaguang
 *
 */
public interface CommodityDAO {

	/**
	 * 获得所有商品信息
	 * @return
	 * @throws HibernateException
	 */
	public List<Commodity> getAllCommodity() throws HibernateException;
	
	/**
	 * 删除商品信息
	 * @param commoditys
	 * @throws HibernateException
	 */
	public void deleteCommodity(Commodity... commoditys) throws HibernateException;
	
	/**
	 * 通过id获得商品信息
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public Commodity getCommodityByID(long id) throws HibernateException;
	
	/**
	 * 更新
	 * @param commoditys
	 * @throws HibernateException
	 */
	public void updateCommodity(Commodity... commoditys) throws HibernateException;
	
	/**
	 * 添加一条商品记录
	 * @param commoditys
	 * @throws HibernateException
	 */
	public void saveCommodity(Commodity...  commoditys) throws HibernateException;
	
	/**
	 * 该方法更新的仅是非集合属性信息，涉及到集合的属性需要调用相关方法
	 * @param commodity
	 * @throws HibernateException
	 */
	public void saveOrUpdate(Commodity commodity ) throws HibernateException;
	
	/**
	 * 添加营销
	 * @param commodity
	 * @param sales
	 * @throws HibernateException
	 */
	public void addSales(Commodity commodity, Sales... sales) throws HibernateException;
	
	/**
	 * 删除营销
	 * @param commodity
	 * @param sales
	 * @throws HibernateException
	 */
	public void deleteSales(Commodity commodity, Sales... sales) throws HibernateException;
	
	/**
	 * 获得商品的促销信息
	 * @param commodity
	 * @param start
	 * @param length
	 * @return
	 * @throws HibernateException
	 */
	public List<Sales> getSalesByCommodity(Commodity commodity, int start, int length)throws HibernateException;
}
