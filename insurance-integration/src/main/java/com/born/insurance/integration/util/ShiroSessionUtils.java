package com.born.insurance.integration.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ShiroSessionUtils {
	protected static final String SESSION_LOCAL_KEY = "sessionLocal";
	protected static final String SESSION_LOCAL_KEY_BAK = "sessionLocalBak";
	protected final static Logger logger = LoggerFactory.getLogger(ShiroSessionUtils.class);
	
	public static final Object getSessionValue(String key) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					return shiroSession.getAttribute(key);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final Object removeSessionValue(String key) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				return shiroSession.removeAttribute(key);
			}
		}
		return null;
	}
	
	public static final void setSessionValue(String key, Object object) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					shiroSession.setAttribute(key, object);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
	}
	
	public static final SessionLocal getSessionLocal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject != null) {
				Session shiroSession = subject.getSession();
				if (shiroSession != null) {
					return (SessionLocal) shiroSession.getAttribute(SESSION_LOCAL_KEY_BAK);
				}
			}
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final void setSessionLocal(SessionLocal local) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				SessionLocal sessionLocal = new SessionLocal();
				BeanCopier.staticCopy(local, sessionLocal);
				shiroSession.setAttribute(SESSION_LOCAL_KEY, local);
				shiroSession.setAttribute(SESSION_LOCAL_KEY_BAK, sessionLocal);
			}
			
		}
	}
	
	public static final void clear() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			if (shiroSession != null) {
				
				shiroSession.removeAttribute(SESSION_LOCAL_KEY);
				shiroSession.removeAttribute(SESSION_LOCAL_KEY_BAK);
			}
			
		}
	}
	
	public static final void commitSessionLocalModify() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Session shiroSession = subject.getSession();
			SessionLocal newSessionLocal = (SessionLocal) shiroSession
				.getAttribute(SESSION_LOCAL_KEY_BAK);
			SessionLocal sessionLocal = new SessionLocal();
			BeanCopier.staticCopy(newSessionLocal, sessionLocal);
			shiroSession.setAttribute(SESSION_LOCAL_KEY, sessionLocal);
		}
	}
}
