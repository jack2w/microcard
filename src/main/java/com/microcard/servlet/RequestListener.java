package com.microcard.servlet;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.microcard.dao.hibernate.HibernateUtil;

public class RequestListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
			HibernateUtil.instance().closeSession();
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {

	}

}
