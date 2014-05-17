package com.microcard.dao;

import java.util.List;
import org.hibernate.HibernateException;
import com.microcard.bean.HistoryShop;

public interface HistoryShopDAO {

	/**
	 * 获得所有HistoryShop
	 * @return
	 * @throws HibernateException
	 */
	public List<HistoryShop> getAllHistoryShop() throws HibernateException;

	/**
	 * 删除
	 * @param historyShops
	 * @throws HibernateException
	 */
	public void deleteHistoryShop(HistoryShop...  historyShops)
			throws HibernateException;

	/**
	 * 通过id获取
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public HistoryShop getHistoryShopByID(String id) throws HibernateException;

	/**
	 * 更新
	 * @param historyShops
	 * @throws HibernateException
	 */
	public void updateHistoryShop(HistoryShop... historyShops)
			throws HibernateException;

	/**
	 * 添加
	 * @param historyShops
	 * @throws HibernateException
	 */
	public void saveHistoryShop(HistoryShop... historyShops)
			throws HibernateException;
	
	/**
	 * 该方法更新的仅是非集合属性信息，涉及到集合的属性需要调用相关方法
	 * @param historyShop
	 * @throws HibernateException
	 */
	public void saveOrUpdate(HistoryShop historyShop) throws HibernateException;
}
