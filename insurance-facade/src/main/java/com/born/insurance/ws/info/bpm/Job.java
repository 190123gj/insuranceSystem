package com.born.insurance.ws.info.bpm;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 用户职位
 *
 *
 * @author wuzj
 *
 */
public class Job extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5852476931669893313L;
	
	/** 职位ID */
	private long id;
	/** 职位编码 */
	private String code;
	/** 职位名称 */
	private String name;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
