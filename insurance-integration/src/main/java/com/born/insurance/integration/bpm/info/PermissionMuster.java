package com.born.insurance.integration.bpm.info;

import java.io.Serializable;
import java.util.List;

import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.google.common.collect.Lists;

public class PermissionMuster implements Serializable {
	
	private static final long serialVersionUID = 4495955546142025872L;
	long parentId;
	String parentAlias;
	List<MenuInfo> permissionInfos = Lists.newArrayList();
	MenuInfo permissionInfo;
	boolean isRoot = false;
	
	public long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public String getParentAlias() {
		return this.parentAlias;
	}
	
	public void setParentAlias(String parentAlias) {
		this.parentAlias = parentAlias;
	}
	
	public List<MenuInfo> getPermissionInfos() {
		return this.permissionInfos;
	}
	
	public MenuInfo add(MenuInfo permissionInfo) {
		this.permissionInfos.add(permissionInfo);
		return permissionInfo;
	}
	
	public MenuInfo getPermissionInfo() {
		return this.permissionInfo;
	}
	
	public void setPermissionInfo(MenuInfo permissionInfo) {
		this.permissionInfo = permissionInfo;
	}
	
	public boolean isRoot() {
		return this.isRoot;
	}
	
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PermissionMuster [parentId=");
		builder.append(parentId);
		builder.append(", parentAlias=");
		builder.append(parentAlias);
		builder.append(", permissionInfos=");
		builder.append(permissionInfos);
		builder.append("]");
		return builder.toString();
	}
	
}
