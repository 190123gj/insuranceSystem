package com.born.insurance.integration.util;

import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.ws.info.bpm.UserDetailInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SessionLocal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 权限列表
	 */
	private List<MenuInfo> permissions = null;
	/**
	 * 用户ID
	 */
	private long userId;
	
	private String userName = null;
	private String nickname = null;
	private String userBaseId = null;
	
	private String rolesName;
	
	private String realName = null;
	private String remoteAddr = null;
	private Date lastDate = null;
	private Date loginDate = null;
	private String lastRemoteAddr;
	private String password = null;
	private SessionMobileSend sessionMobileSend = null;
	private Map<String, Object> attibuteMap = new HashMap<String, Object>();
	
	private Map<String, List<MenuInfo>> userMenuInfoTreeMap = new HashMap<String, List<MenuInfo>>();
	
	private List<MenuInfo> userMenuInfoList;
	UserInfo userInfo;
	
	/**
	 * 新版用户详细信息
	 */
	UserDetailInfo userDetailInfo;
	
	public SessionLocal() {
		
	}
	
	public List<MenuInfo> getUserMenuInfoTreeBySysAlias(String sysAlias) {
		return userMenuInfoTreeMap.get(sysAlias);
	}
	
	public void setUserMenuInfoTreeBySysAlias(String sysAlias, List<MenuInfo> MenuInfos) {
		userMenuInfoTreeMap.put(sysAlias, MenuInfos);
	}
	
	public void setUserMenuInfoTreeMap(Map<String, List<MenuInfo>> userMenuInfoTreeMap) {
		this.userMenuInfoTreeMap = userMenuInfoTreeMap;
	}
	
	public List<MenuInfo> getUserMenuInfoListBySysAlias(String sysAlias) {
		return this.userMenuInfoList;
	}
	
	public List<MenuInfo> getUserMenuInfoListMap() {
		return this.userMenuInfoList;
	}
	
	public void setUserMenuInfoListMap(List<MenuInfo> userMenuInfoList) {
		if (userMenuInfoList == null)
			return;
		this.userMenuInfoList = userMenuInfoList;
	}
	
	public SessionLocal(long userId, String userName, String realName) {
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		
	}
	
	public void addAttibute(String key, Object object) {
		attibuteMap.put(key, object);
	}
	
	public Object getAttibute(String key) {
		return attibuteMap.get(key);
	}
	
	public Object removeObject(String key) {
		return attibuteMap.remove(key);
	}
	
	public List<MenuInfo> getPermissions() {
		return this.permissions;
	}
	
	public void setPermissions(List<MenuInfo> permissions) {
		this.permissions = permissions;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getUserBaseId() {
		return this.userBaseId;
	}
	
	public void setUserBaseId(String userBaseId) {
		this.userBaseId = userBaseId;
	}
	
	public String getRolesName() {
		return this.rolesName;
	}
	
	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}
	
	public String getLastRemoteAddr() {
		return this.lastRemoteAddr;
	}
	
	public void setLastRemoteAddr(String lastRemoteAddr) {
		this.lastRemoteAddr = lastRemoteAddr;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public String getRemoteAddr() {
		return this.remoteAddr;
	}
	
	public Date getLastDate() {
		return this.lastDate;
	}
	
	public Date getLoginDate() {
		return this.loginDate;
	}
	
	public SessionMobileSend getSessionMobileSend() {
		return this.sessionMobileSend;
	}
	
	public Map<String, Object> getAttibuteMap() {
		return this.attibuteMap;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public void setSessionMobileSend(SessionMobileSend sessionMobileSend) {
		this.sessionMobileSend = sessionMobileSend;
	}
	
	public void setAttibuteMap(Map<String, Object> attibuteMap) {
		this.attibuteMap = attibuteMap;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public List<MenuInfo> getUserMenuInfoList() {
		return userMenuInfoList;
	}
	
	public void setUserMenuInfoList(List<MenuInfo> userMenuInfoList) {
		this.userMenuInfoList = userMenuInfoList;
	}
	
	public UserDetailInfo getUserDetailInfo() {
		return userDetailInfo;
	}
	
	public void setUserDetailInfo(UserDetailInfo userDetailInfo) {
		this.userDetailInfo = userDetailInfo;
	}
	
	public Map<String, List<MenuInfo>> getUserMenuInfoTreeMap() {
		return userMenuInfoTreeMap;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionLocal [permissions=");
		builder.append(permissions);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", nickname=");
		builder.append(nickname);
		builder.append(", userBaseId=");
		builder.append(userBaseId);
		builder.append(", rolesName=");
		builder.append(rolesName);
		builder.append(", realName=");
		builder.append(realName);
		builder.append(", remoteAddr=");
		builder.append(remoteAddr);
		builder.append(", lastDate=");
		builder.append(lastDate);
		builder.append(", loginDate=");
		builder.append(loginDate);
		builder.append(", lastRemoteAddr=");
		builder.append(lastRemoteAddr);
		builder.append(", password=");
		builder.append(password);
		builder.append(", sessionMobileSend=");
		builder.append(sessionMobileSend);
		builder.append(", attibuteMap=");
		builder.append(attibuteMap);
		builder.append(", userMenuInfoTreeMap=");
		builder.append(userMenuInfoTreeMap);
		builder.append(", userMenuInfoList=");
		builder.append(userMenuInfoList);
		builder.append(", userDetailInfo=");
		builder.append(userDetailInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
