/**
 * 
 */
package com.microcard.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.microcard.bean.Sales;

/**
 * @author jiyaguang
 *
 */
public interface SalesDAO {

	public List getSales() throws HibernateException;

	public void deleteSales(Sales... sales)
			throws HibernateException;

	public Sales getSalesByID(String id) throws HibernateException;

	public void updateSales(Sales... sales)
			throws HibernateException;

	public void saveSales(Sales... sales)
			throws HibernateException;
}
