package com.born.insurance.ws.order.common;

import java.util.List;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.ExeStatusEnum;
import com.born.insurance.ws.enums.FormCodeEnum;

/**
 * 相关人员查询Order
 *
 * @author wuzj
 * 
 * 2016年4月29日
 */
public class RelatedUserQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -3057358312719411636L;
	
	private long relatedId; //主键
	
	private long formId; //表单ID
	
	private long taskId; //任务ID
	
	private String projectCode; //项目编号
	

	
	private ExeStatusEnum exeStatus; //任务执行状态
	
	private List<String> exeStatusList; //任务执行状态
	
	private FormCodeEnum formCode; //表单类型
	
	/**
	 * 当前人员
	 */
	private long userId;
	
	private String userAccount;
	
	/**
	 * 原始人员
	 */
	private long originalUserId;
	
	/**
	 * 是否当前
	 */
	private BooleanEnum isCurrent;
	/**
	 * 是否删除
	 */
	private BooleanEnum isDel;
	
	/**
	 * 部门ID
	 */
	private List<Long> deptIdList;
	
	public long getRelatedId() {
		return this.relatedId;
	}
	
	public void setRelatedId(long relatedId) {
		this.relatedId = relatedId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	
	public long getOriginalUserId() {
		return this.originalUserId;
	}
	
	public void setOriginalUserId(long originalUserId) {
		this.originalUserId = originalUserId;
	}
	
	public BooleanEnum getIsCurrent() {
		return this.isCurrent;
	}
	
	public void setIsCurrent(BooleanEnum isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public ExeStatusEnum getExeStatus() {
		return this.exeStatus;
	}
	
	public void setExeStatus(ExeStatusEnum exeStatus) {
		this.exeStatus = exeStatus;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public List<String> getExeStatusList() {
		return this.exeStatusList;
	}
	
	public void setExeStatusList(List<String> exeStatusList) {
		this.exeStatusList = exeStatusList;
	}
	
}
