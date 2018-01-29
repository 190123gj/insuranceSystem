package com.born.insurance.ws.order.sysParam;

import com.born.insurance.ws.base.QueryPageBase;

/**
 * Created by wqh on 2014/5/21.
 */
public class SysParamQueryOrder extends QueryPageBase {
	private static final long serialVersionUID = -4239124486323136636L;
	/** 参数名称 */
	private String paramName;
	/** 参数描述 */
	private String description;
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
