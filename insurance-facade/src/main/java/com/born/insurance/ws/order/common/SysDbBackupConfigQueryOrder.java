package com.born.insurance.ws.order.common;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.BooleanEnum;

public class SysDbBackupConfigQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 8572065471933055587L;
	/** 配置ID */
	private long configId;
	/** 数据库地址 */
	private String dbHost;
	/** 数据库名 */
	private String schemeName;
	/** 配置使用状态 */
	private BooleanEnum inUse;
	
	public long getConfigId() {
		return this.configId;
	}
	
	public void setConfigId(long configId) {
		this.configId = configId;
	}
	
	public String getDbHost() {
		return this.dbHost;
	}
	
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	
	public String getSchemeName() {
		return this.schemeName;
	}
	
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	public BooleanEnum getInUse() {
		return this.inUse;
	}
	
	public void setInUse(BooleanEnum inUse) {
		this.inUse = inUse;
	}
}
