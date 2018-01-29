package com.born.insurance.integration.bpm.info.menu;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class MenuInfo implements Serializable {
	
	private static final long serialVersionUID = 2794468054115276407L;
	String mainName;
	String iconName;
	String alias;
	String url;
	long rank;
	List<MenuInfo> subclass = Lists.newArrayList();
	long parentId;
	long resId;
	boolean isViewMenu = false;
	
	String topAlias;
	
	public String getMainName() {
		return this.mainName;
	}
	
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	
	public String getIconName() {
		return this.iconName;
	}
	
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getRank() {
		return this.rank;
	}
	
	public void setRank(long rank) {
		this.rank = rank;
	}
	
	public List<MenuInfo> getSubclass() {
		return this.subclass;
	}
	
	public void setSubclass(List<MenuInfo> subclass) {
		this.subclass = subclass;
	}
	
	public long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public long getResId() {
		return this.resId;
	}
	
	public void setResId(long resId) {
		this.resId = resId;
	}
	
	public boolean isViewMenu() {
		return this.isViewMenu;
	}
	
	public void setViewMenu(boolean isViewMenu) {
		this.isViewMenu = isViewMenu;
	}
	
	public String getTopAlias() {
		return this.topAlias;
	}
	
	public void setTopAlias(String topAlias) {
		this.topAlias = topAlias;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuInfo [mainName=");
		builder.append(mainName);
		builder.append(", iconName=");
		builder.append(iconName);
		builder.append(", alias=");
		builder.append(alias);
		builder.append(", url=");
		builder.append(url);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", resId=");
		builder.append(resId);
		builder.append(", isViewMenu=");
		builder.append(isViewMenu);
		builder.append(", topAlias=");
		builder.append(topAlias);
		builder.append("]");
		return builder.toString();
	}
	
}
