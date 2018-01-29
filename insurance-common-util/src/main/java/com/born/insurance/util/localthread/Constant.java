package com.born.insurance.util.localthread;

/**
 * 
 * 
 * @Filename Constant.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author yhl
 * 
 * @Email yhailong@yiji.com
 * 
 * @History <li>Author: yhl</li> <li>Date: 2013-6-25</li> <li>Version: 1.0</li>
 * <li>Content: create</li> 常量
 */
public class Constant {
	/**
	 * 超时时间， 若小于等于0就永远不会超时，单位毫秒
	 */
	public static long timeout = 1800000;
	
	/**
	 * 远程session服务器主机
	 */
	public static String host = "127.0.0.1";
	/**
	 * 远程session服务器端口
	 */
	public static int port = 8088;
	/**
	 * 远程session服务器上的session服务存根名称
	 */
	public static String name = "remoteSessionService";
	/**
	 * 扫描的包
	 */
	public static String[] packages = new String[] { "com.yjf" };
	/**
	 * 将session id与线程绑定
	 */
	private static ThreadLocal<String> sessionLocal = new ThreadLocal<String>();
	
	private static ThreadLocal<String> loginUserUrlLocal = new ThreadLocal<String>();
	
	/**
	 * 保存session id
	 * @param sessionId
	 */
	public static void setSessionId(String sessionId) {
		sessionLocal.set(sessionId);
	}
	
	/**
	 * 获取session id
	 * @return
	 */
	public static String getSessionId() {
		return sessionLocal.get();
	}
	
	/**
	 * 保存session id
	 * @param sessionId
	 */
	public static void setLoginUserUrl(String url) {
		loginUserUrlLocal.set(url);
	}
	
	/**
	 * 获取session id
	 * @return
	 */
	public static String getLoginUserUrl() {
		return loginUserUrlLocal.get();
	}
}
