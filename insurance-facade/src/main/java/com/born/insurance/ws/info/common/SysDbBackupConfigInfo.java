package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.BooleanEnum;

/**
 * 数据备份配置
 *
 * @author wuzj
 */
public class SysDbBackupConfigInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4661472265318022916L;
	/** 配置ID */
	private long configId;
	/** 数据库目录 */
	private String dbHome;
	/** 数据库用户 */
	private String dbUser;
	/** 数据库密码 */
	private String dbPsw;
	/** 数据库地址 */
	private String dbHost;
	/** 数据库端口 */
	private int dbPort;
	/** 数据库名称 */
	private String schemeName;
	/** 备份目录 */
	private String backupFolder;
	/** 备份频率分钟数 */
	private int backupInvervalMinute;
	/** 备份开始时间 */
	private String backupTimeBegin;
	/** 备份结束时间 */
	private String backupTimeEnd;
	/** 使用状态 */
	private BooleanEnum inUse;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SysDbBackupConfigInfo [");
		sb.append("configId=").append(configId);
		sb.append("dbHome=").append(dbHome);
		sb.append("dbHost=").append(dbHost);
		sb.append("dbPort=").append(dbPort);
		sb.append("dbUser=").append(dbUser);
		sb.append("schemeName=").append(schemeName);
		sb.append("backupFolder=").append(backupFolder);
		sb.append("backupInvervalMinute=").append(backupInvervalMinute);
		sb.append("backupTimeBegin=").append(backupTimeBegin);
		sb.append("backupTimeEnd=").append(backupTimeEnd);
		sb.append("inUse=").append(inUse);
		sb.append("]");
		return sb.toString();
	}
	
	public long getConfigId() {
		return this.configId;
	}
	
	public void setConfigId(long configId) {
		this.configId = configId;
	}
	
	public String getDbHome() {
		return this.dbHome;
	}
	
	public void setDbHome(String dbHome) {
		this.dbHome = dbHome;
	}
	
	public String getDbUser() {
		return this.dbUser;
	}
	
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	public String getDbPsw() {
		return this.dbPsw;
	}
	
	public void setDbPsw(String dbPsw) {
		this.dbPsw = dbPsw;
	}
	
	public String getDbHost() {
		return this.dbHost;
	}
	
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	
	public int getDbPort() {
		return this.dbPort;
	}
	
	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}
	
	public String getSchemeName() {
		return this.schemeName;
	}
	
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	public String getBackupFolder() {
		return this.backupFolder;
	}
	
	public void setBackupFolder(String backupFolder) {
		this.backupFolder = backupFolder;
	}
	
	public int getBackupInvervalMinute() {
		return this.backupInvervalMinute;
	}
	
	public void setBackupInvervalMinute(int backupInvervalMinute) {
		this.backupInvervalMinute = backupInvervalMinute;
	}
	
	public String getBackupTimeBegin() {
		return this.backupTimeBegin;
	}
	
	public void setBackupTimeBegin(String backupTimeBegin) {
		this.backupTimeBegin = backupTimeBegin;
	}
	
	public String getBackupTimeEnd() {
		return this.backupTimeEnd;
	}
	
	public void setBackupTimeEnd(String backupTimeEnd) {
		this.backupTimeEnd = backupTimeEnd;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public BooleanEnum getInUse() {
		return this.inUse;
	}
	
	public void setInUse(BooleanEnum inUse) {
		this.inUse = inUse;
	}
	
}
