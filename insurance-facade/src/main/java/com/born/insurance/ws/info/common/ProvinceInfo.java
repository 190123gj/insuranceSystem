package com.born.insurance.ws.info.common;

import java.util.List;

/**
 * Created by wqh on 2017-1-17.
 */
public class ProvinceInfo extends SalesAreasInfo {

	public ProvinceInfo() {
	}

	public ProvinceInfo(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private List<CityInfo> cityInfoList;

	private String children;

	public List<CityInfo> getCityInfoList() {
		return cityInfoList;
	}

	public void setCityInfoList(List<CityInfo> cityInfoList) {
		this.cityInfoList = cityInfoList;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}
}
