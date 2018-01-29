package com.born.insurance.ws.info.bpm;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 
 * 用户岗位
 *
 * @author wuzj
 *
 */
public class Position extends BaseToStringInfo {
	
	private static final long serialVersionUID = 6109989162560941771L;
	
	/** 岗位ID */
	private long id;
	
	/** 组织ID */
	private long orgId;
	
	/** 职位ID */
	private long jobId;
	
	/** 岗位编码 = 组织ID对应的组织编码_职位ID对应的职位编码（必须默认拼音首字母才是正确的 ） */
	private String code;
	
	/** 岗位名称 */
	private String name;
	
	/** 是否主岗位 */
	private boolean isPrimary;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getOrgId() {
		return this.orgId;
	}
	
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	public long getJobId() {
		return this.jobId;
	}
	
	public void setJobId(long jobId) {
		this.jobId = jobId;
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
	
	public boolean isPrimary() {
		return this.isPrimary;
	}
	
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
