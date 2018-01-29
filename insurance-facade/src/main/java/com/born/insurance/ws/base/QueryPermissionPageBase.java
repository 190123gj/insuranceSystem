package com.born.insurance.ws.base;

import java.util.List;

/**
 * 控制数据权限的分页查询
 *
 * 按人员维度查询 loginUserId
 * 
 * 按部门维度查询 deptIdList
 * 
 * 查看草稿的人 draftUserId
 * 
 * 表单提交人 formSubmitManId
 * 
 * 表单审核人 auditor
 * 
 * 
 * @author wuzj 2016年4月29日
 *
 */
public class QueryPermissionPageBase extends QueryPageBase {
	
	private static final long serialVersionUID = 8350199751850099385L;
	
	//当前登陆用户
	private long loginUserId;
	
	//用户所属部门列表
	private List<Long> deptIdList;
	
	/** 查看草稿的人 */
	private long draftUserId;
	
	/** 表单提交人 */
	private long formSubmitManId;
	
	/** 审核人 */
	private long auditor;
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getFormSubmitManId() {
		return this.formSubmitManId;
	}
	
	public void setFormSubmitManId(long formSubmitManId) {
		this.formSubmitManId = formSubmitManId;
	}
	
	public long getDraftUserId() {
		return this.draftUserId;
	}
	
	public void setDraftUserId(long draftUserId) {
		this.draftUserId = draftUserId;
	}
	
	public long getAuditor() {
		return this.auditor;
	}
	
	public void setAuditor(long auditor) {
		this.auditor = auditor;
	}
	
}
