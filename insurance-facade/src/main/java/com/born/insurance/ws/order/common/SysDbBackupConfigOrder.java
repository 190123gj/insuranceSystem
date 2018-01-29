package com.born.insurance.ws.order.common;

import com.born.insurance.ws.order.base.ProcessOrder;

public class SysDbBackupConfigOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -2452645695573544541L;
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
	
	@Override
	public void check() {
		validateHasText(dbHost, "数据库地址");
		validateHasText(dbUser, "数据库用户名");
		validateGreaterThan(dbPort, "数据库端口号");
		validateGreaterThan(backupInvervalMinute, "备份频率分钟数");
		validateHasText(dbPsw, "数据库密码");
		validateHasText(schemeName, "备份数据库名称");
		validateHasText(backupFolder, "备份目录");
	}
	
	public long getConfigId() {
		return this.configId;
	}
	
	public void setConfigId(long configId) {
		this.configId = configId;
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
	
	public String getDbHome() {
		return this.dbHome;
	}
	
	public void setDbHome(String dbHome) {
		this.dbHome = dbHome;
	}
	
}
