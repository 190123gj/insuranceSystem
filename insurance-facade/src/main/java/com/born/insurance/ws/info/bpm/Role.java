package com.born.insurance.ws.info.bpm;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 用户角色
 *
 *
 * @author wuzj
 *
 */
public class Role extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8457269248758115616L;
	
	/** 角色编码 */
	private String code;
	
	/** 角色名称 */
	private String name;
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
