package com.born.insurance.web.listener;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 *                       
 * @Filename SessionCookieServletContextListener.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author yhl
 *
 * @Email yhailong@yiji.com
 *       
 * @History
 *<li>Author: yhl<>
 *<li>Date: 2013-9-9<>
 *<li>Version: 1.0<>
 *<li>Content: create<>
 * 
 */
public class SessionCookieServletContextListener implements
		ServletContextListener {
	
	protected final static String SESSION_COOKIE_NAME_KEY = "sessionCookieName";
	
	protected static String SESSION_COOKIE_NAME = "jsessionId";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String t = servletContext.getInitParameter(SESSION_COOKIE_NAME_KEY);
		if(t != null && t.length() > 0){
			SESSION_COOKIE_NAME = t;
		}
		servletContext.getSessionCookieConfig().setName(SESSION_COOKIE_NAME);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
