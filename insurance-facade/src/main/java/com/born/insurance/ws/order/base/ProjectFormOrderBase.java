package com.born.insurance.ws.order.base;

public class ProjectFormOrderBase extends FormOrderBase {
	
	private static final long serialVersionUID = 7028698869926654069L;
	
	//项目编号
	private String projectCode;
	//项目名称
	private String projectName;
	//客户ID
	private Long customerId;
	//客户名称
	private String customerName;
	
	@Override
	public void check () {
//		validateHasText(projectCode, "项目编号");
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
		super.setRelatedProjectCode(projectCode);
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
