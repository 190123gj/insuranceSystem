package com.born.insurance.ws.order.common;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.StringUtil;

/**
 * 简单用户信息
 * 
 * 
 * @author wuzj
 * 
 */
public class SimpleUserInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8872678534995277059L;
	
	/** 用户ID */
	private long userId;
	
	/** 用户账号 */
	private String userAccount;
	
	/** 用户姓名 */
	private String userName;
	
	/** 用户邮箱 */
	private String email;
	
	/** 用户电话 */
	private String mobile;
	
	/** 用户部门ID */
	private long deptId;
	
	/** 用户部门编号 */
	private String deptCode;
	
	/** 用户部门名称 */
	private String deptName;
	
	/** 用户部门路径 */
	private String deptPath;
	
	/** 用户部门路径名称 */
	private String deptPathName;
	
	public JSONObject toSimpleJson() {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("userAccount", userAccount);
		json.put("userName", userName);
		json.put("email", email);
		json.put("mobile", mobile);
		return json;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof SimpleUserInfo) {
			if (this.userId == ((SimpleUserInfo) obj).userId
				|| StringUtil.equals(this.userAccount, ((SimpleUserInfo) obj).userAccount)) {
				return true;
			} else
				return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.userId > 0)
			return new Long(this.userId).hashCode();
		else if (StringUtil.isNotBlank(this.userAccount)) {
			return this.userAccount.hashCode();
		} else
			return super.hashCode();
	}
}
