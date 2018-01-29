package com.born.insurance.ws.order.common;

import java.util.Date;

import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.order.base.ProcessOrder;

public class SysDbBackupLogOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -2339015220068549311L;
	/** 主键 */
	private long logId;
	/** 备份数据库 */
	private String schemeName;
	/** 备份文件 */
	private String backupFile;
	/** 备份时间 */
	private Date backupTime;
	/** 是否成功 */
	private BooleanEnum isSuccess;
	/** 备份是否已删除 */
	private BooleanEnum isDel;
	/** 失败原因 */
	private String remark;
	/** 删除时间 */
	private Date delTime;
	/** 删除人 */
	private String delMan;
	
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
	
	public Date getBackupTime() {
		return this.backupTime;
	}
	
	public void setBackupTime(Date backupTime) {
		this.backupTime = backupTime;
	}
	
	public BooleanEnum getIsSuccess() {
		return this.isSuccess;
	}
	
	public void setIsSuccess(BooleanEnum isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public Date getDelTime() {
		return this.delTime;
	}
	
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	
	public String getDelMan() {
		return this.delMan;
	}
	
	public void setDelMan(String delMan) {
		this.delMan = delMan;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
