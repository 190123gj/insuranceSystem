package com.born.insurance.ws.info.common;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 表单
 *
 * @author wuzj
 */
public class FormInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8463808602419094007L;
	
	private long formId;
	
	private FormCodeEnum formCode;
	
	private String formName;
	
	private String formUrl;
	
	private long actInstId;
	
	private long runId;
	
	private long taskId;
	
	private long defId;
	
	private String actDefId;
	
	private FormStatusEnum status;
	
	private String detailStatus;
	
	private long userId;
	
	private String userName;
	
	private String userAccount;
	
	private String userMobile;
	
	private String userEmail;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	private String checkStatus;
	
	private String checkName;
	
	private String relatedProjectCode;
	
	private String taskUserData;
	
	private String remark;
	
	private String trace;
	
	private Date submitTime;
	
	private Date finishTime;
	
	List<FormExecuteInfo> formExecuteInfo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("formId", formId);
		json.put("formCode", formCode);
		json.put("formName", formName);
		json.put("checkStatus", checkStatus);
		json.put("actInstId", actInstId);
		json.put("status", status);
		json.put("detailStatus", detailStatus);
		Date lastUpdateTime = rawUpdateTime == null ? rawAddTime : rawUpdateTime;
		if (lastUpdateTime != null)
			json.put("lastUpdateTime", DateUtil.simpleFormat(lastUpdateTime));
		return json;
	}
	
	/**
	 * 获取模块
	 * @return
	 */
	public String getModule() {
		String module = "projectMg";
		if (formCode != null && StringUtil.isNotBlank(formCode.getEditUrl())) {
			String[] stringSplit = formCode.getEditUrl().split("/");
			if (stringSplit.length > 0)
				module = stringSplit[1];
		}
		return module;
	}
	
	/**
	 * 获取系统名称
	 * @return
	 */
	public String getSysName() {
		String module = getModule();
		String sysName = "PM";
		if ("fundMg".equals(module)) {//资金
			sysName = "FM";
		} else if ("assetMg".equals(module)) {//资产
			sysName = "AM";
		} else if ("reportMg".equals(module)) { //报表
			sysName = "RM";
		} else if ("customerMg".equals(module)) { //客户管理
			sysName = "CRM";
		}
		return sysName;
	}
	
	/**
	 * 获取下步审核人
	 * @return
	 */
	public String getNextAuditor() {
		String nextAuditor = "";
		if (formExecuteInfo != null && formExecuteInfo.size() > 0) {
			for (FormExecuteInfo ex : formExecuteInfo) {
				if (ex.isExecute() || ex.isSetAgent())
					continue;
				if (ex.getUserId() > 0) {
					nextAuditor += ex.getUserName() + ",";
				} else if (ex.getCandidateUserList() != null) {
					for (SimpleUserInfo userInfo : ex.getCandidateUserList()) {
						nextAuditor += userInfo.getUserName() + ",";
					}
				}
			}
			if (nextAuditor != null && nextAuditor.length() > 0) {
				nextAuditor = nextAuditor.substring(0, nextAuditor.length() - 1);
			}
		}
		return nextAuditor;
	}
	
	/**
	 * 下步执行节点
	 * @return
	 */
	public String getNextNode() {
		String nextNode = "";
		if (formExecuteInfo != null && formExecuteInfo.size() > 0) {
			for (FormExecuteInfo ex : formExecuteInfo) {
				if (StringUtil.isNotBlank(ex.getTaskName())) {
					nextNode = ex.getTaskName();
				}
				if (ex.isExecute() || ex.isSetAgent())
					continue;
			}
		}
		return nextNode;
	}
	
	/**
	 * 表单是否验证通过
	 * @return
	 */
	public boolean isValid() {
		if (checkStatus == null || checkStatus.contains("0")) {
			return false;
		} else {
			return true;
		}
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public String getFormName() {
		return this.formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public String getFormUrl() {
		return this.formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public long getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public long getRunId() {
		return this.runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getDefId() {
		return this.defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public FormStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(FormStatusEnum status) {
		this.status = status;
	}
	
	public String getDetailStatus() {
		return this.detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getCheckName() {
		return this.checkName;
	}
	
	public void setCheckName(String checkName) {
		this.checkName = checkName;
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
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public String getTaskUserData() {
		return this.taskUserData;
	}
	
	public void setTaskUserData(String taskUserData) {
		this.taskUserData = taskUserData;
	}
	
	public List<FormExecuteInfo> getFormExecuteInfo() {
		return this.formExecuteInfo;
	}
	
	public void setFormExecuteInfo(List<FormExecuteInfo> formExecuteInfo) {
		this.formExecuteInfo = formExecuteInfo;
	}
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public Date getSubmitTime() {
		return this.submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTrace() {
		return this.trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
}
