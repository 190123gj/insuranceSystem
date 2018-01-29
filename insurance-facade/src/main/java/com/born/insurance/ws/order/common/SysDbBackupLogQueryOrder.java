package com.born.insurance.ws.order.common;

import com.born.insurance.ws.base.QueryPageBase;

/**
 * 查询备份日志Order
 *
 *
 * @author wuzj
 *
 */
public class SysDbBackupLogQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 2069195128583756136L;
	/** 主键 */
	private long logId;
	/** 备份数据库 */
	private String schemeName;
	/** 备份文件 */
	private String backupFile;
	/** 是否成功 */
	private String isSuccess;
	/** 备份是否已删除 */
	private String isDel;
	
	public long getLogId() {
		return this.logId;
	}
	
	public void setLogId(long logId) {
		this.logId = logId;
	}
	
	public String getSchemeName() {
		return this.schemeName;
	}
	
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	public String getBackupFile() {
		return this.backupFile;
	}
	
	public void setBackupFile(String backupFile) {
		this.backupFile = backupFile;
	}
	
	public String getIsSuccess() {
		return this.isSuccess;
	}
	
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public String getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
}
