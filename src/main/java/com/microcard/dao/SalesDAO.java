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
public interface SalesDAO {

	/**
	 * 获得所有促销信息
	 * @return
	 * @throws HibernateException
	 */
	public List<Sales> getSales() throws HibernateException;

	/**
	 * 删除sales，会删除所有相关的商品信息
	 * @param sales
	 * @throws HibernateException
	 */
	public void deleteSales(Sales... sales)
			throws HibernateException;

	/**
	 * 通过id获得sales
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public Sales getSalesByID(String id) throws HibernateException;

	/**
	 * 更新sales对象，不包括set属性信息
	 * @param sales
	 * @throws HibernateException
	 */
	public void updateSales(Sales... sales)
			throws HibernateException;

	/**
	 * 保存
	 * @param sales
	 * @throws HibernateException
	 */
	public void saveSales(Sales... sales)
			throws HibernateException;

	/**
	 * 增加促销商品
	 * @param sales
	 * @param commodities
	 * @throws HibernateException
	 */
   public void addCommodity(Sales sales, Commodity... commodities) throws HibernateException;
   
   /**
    * 修改促销商品
    * @param sales
    * @param commodities
    * @throws HibernateException
    */
   public void updateCommodity(Sales sales, Commodity... commodities) throws HibernateException;
   
   /**
    * 删除促销商品，如果commodities为null，删除所有该促销关联的商品
    * @param sales
    * @param commodities
    * @throws HibernateException
    */
   public void deleteCommodity(Sales sales, Commodity... commodities) throws HibernateException;
   
	/**
	 * 该方法更新的仅是促销信息，涉及到集合的属性需要调用相关方法
	 * @param sales
	 * @throws HibernateException
	 */
	public void saveOrUpdate(Sales sales ) throws HibernateException;
}
