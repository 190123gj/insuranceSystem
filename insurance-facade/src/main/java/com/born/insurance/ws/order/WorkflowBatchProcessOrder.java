package com.born.insurance.ws.order;

import com.born.insurance.ws.order.base.ProcessOrder;
import org.springframework.util.Assert;


/**
 * 流程批量审核Order
 * @author wuzj
 */
public class WorkflowBatchProcessOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -2266083537756232628L;
	
	/** 子系统名称 */
	private String sysName;
	/** 批量审核formId */
	private Long[] processFormIds;
	/** 审核service */
	private String processServiceName;
	/** 处理类型 pass/back2start */
	private String processType;
	/** 处理意见 */
	private String processContent;
	
	//手机
	private String userMobile;
	//email
	private String userEmail;
	//部门ID
	private Long deptId;
	//部门编号
	private String deptCode;
	//部门名称
	private String deptName;
	//部门路径 deptId.deptId. 
	private String deptPath;
	//部门路径名称 deptPathName/deptPathName/
	private String deptPathName;
	//相关项目编号
	private String relatedProjectCode;
	
	@Override
	public void check() {
		validateNotNull(processFormIds, "formId");
		validateNotNull(processType, "处理类型");
		Assert.isTrue(
			"pass".equalsIgnoreCase(processType) || "back2start".equalsIgnoreCase(processType),
			"处理类型错误");
	}
	
	public String getSysName() {
		return this.sysName;
	}
	
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public Long[] getProcessFormIds() {
		return this.processFormIds;
	}
	
	public void setProcessFormIds(Long[] processFormIds) {
		this.processFormIds = processFormIds;
	}
	
	public String getProcessServiceName() {
		return this.processServiceName;
	}
	
	public void setProcessServiceName(String processServiceName) {
		this.processServiceName = processServiceName;
	}
	
	public String getProcessType() {
		return this.processType;
	}
	
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	
	public String getProcessContent() {
		return this.processContent;
	}
	
	public void setProcessContent(String processContent) {
		this.processContent = processContent;
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
	
	public Long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Long deptId) {
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
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
}
