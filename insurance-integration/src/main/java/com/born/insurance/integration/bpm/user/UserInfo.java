package com.born.insurance.integration.bpm.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.born.insurance.ws.enums.bpm.UserStateEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.common.collect.Lists;
import com.yjf.common.lang.util.ListUtil;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = 6420947438647890077L;
	String userName;
	long userId;
	String realName;
	UserStateEnum state = UserStateEnum.NORMAL;
	String password;
	long pwdErrorCount;
	Date changeLockTime;
	String email;
	String moblie;
	SysOrg primaryOrg;
	SysOrg orgs;//orgType 0=集团\r\n  1=公司\r\n    2=部门\r\n       3=其他组织'
	SysOrg dept;//orgType 0=集团\r\n  1=公司\r\n    2=部门\r\n       3=其他组织'
	SysOrg otherOrg;//3=其他组织
	String roleName;
	String roleAlisName;
	List<SysOrg> orgList;
	List<Long> orgIdList;
	List<SysOrg> deptList;
	List<Long> deptIdList;
	List<SysOrg> otherOrgList;
	List<Long> otherOrgIdList;
	List<String> roleAliasList = Lists.newArrayList();
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getRealName() {
		return this.realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public UserStateEnum getState() {
		return this.state;
	}
	
	public void setState(UserStateEnum state) {
		this.state = state;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getPwdErrorCount() {
		return this.pwdErrorCount;
	}
	
	public void setPwdErrorCount(long pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}
	
	public Date getChangeLockTime() {
		return this.changeLockTime;
	}
	
	public void setChangeLockTime(Date changeLockTime) {
		this.changeLockTime = changeLockTime;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMoblie() {
		return this.moblie;
	}
	
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
	public String getOrgName() {
		if (orgs != null) {
			return orgs.getOrgPathname();
		}
		return "";
	}
	
	public SysOrg getOrgs() {
		if (ListUtil.isNotEmpty(this.orgList))
			return this.orgList.get(0);
		return null;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public SysOrg getDept() {
		if (ListUtil.isNotEmpty(this.deptList))
			return this.deptList.get(0);
		if (ListUtil.isNotEmpty(this.orgList))
			return this.orgList.get(0);
		return null;
	}
	
	public String getDeptName() {
		if (this.dept == null)
			return this.orgs.getOrgPathname();
		return this.dept.getOrgPathname();
	}
	
	public SysOrg getOtherOrg() {
		if (ListUtil.isNotEmpty(this.otherOrgList))
			return this.otherOrgList.get(0);
		return null;
	}
	
	public void setOtherOrg(SysOrg otherOrg) {
		this.otherOrg = otherOrg;
	}
	
	public List<SysOrg> getOrgList() {
		return this.orgList;
	}
	
	public void setOrgList(List<SysOrg> orgList) {
		this.orgList = orgList;
	}
	
	public List<Long> getOrgIdList() {
		return this.orgIdList;
	}
	
	public void setOrgIdList(List<Long> orgIdList) {
		this.orgIdList = orgIdList;
	}
	
	public List<SysOrg> getDeptList() {
		return this.deptList;
	}
	
	public void setDeptList(List<SysOrg> deptList) {
		this.deptList = deptList;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<SysOrg> getOtherOrgList() {
		return this.otherOrgList;
	}
	
	public void setOtherOrgList(List<SysOrg> otherOrgList) {
		this.otherOrgList = otherOrgList;
	}
	
	public List<Long> getOtherOrgIdList() {
		return this.otherOrgIdList;
	}
	
	public void setOtherOrgIdList(List<Long> otherOrgIdList) {
		this.otherOrgIdList = otherOrgIdList;
	}
	
	public SysOrg getPrimaryOrg() {
		return this.primaryOrg;
	}
	
	public void setPrimaryOrg(SysOrg primaryOrg) {
		this.primaryOrg = primaryOrg;
	}
	
	public String getRoleAlisName() {
		return this.roleAlisName;
	}
	
	public void setRoleAlisName(String roleAlisName) {
		this.roleAlisName = roleAlisName;
	}
	
	public List<String> getRoleAliasList() {
		return this.roleAliasList;
	}
	
	public void setRoleAliasList(List<String> roleAliasList) {
		this.roleAliasList = roleAliasList;
	}
	
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
