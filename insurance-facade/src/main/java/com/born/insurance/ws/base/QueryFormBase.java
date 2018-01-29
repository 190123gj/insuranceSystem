package com.born.insurance.ws.base;


/**
 * 
 * 表单查询
 * 
 * @author wuzj
 * 
 */
public class QueryFormBase extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 4490208963628131207L;
	
	private Long formUserId;
	
	private Long formId; //表单ID
	
	private String formCode; //表单编码
	
	private String formStatus; //表单状态
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public String getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public Long getFormUserId() {
		return this.formUserId;
	}
	
	public void setFormUserId(Long formUserId) {
		this.formUserId = formUserId;
	}
	
}
