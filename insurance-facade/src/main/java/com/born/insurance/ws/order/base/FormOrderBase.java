package com.born.insurance.ws.order.base;

import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 表单基础Order
 *
 * @author wuzj
 */
public class FormOrderBase extends ProcessOrder {
	
	private static final long serialVersionUID = 1187037862442826014L;
	
	/** 手机 */
	protected String userMobile;
	
	/** email */
	protected String userEmail;
	
	/** 部门ID */
	protected Long deptId;
	
	/** 部门编号 */
	protected String deptCode;
	
	/** 部门名称 */
	protected String deptName;
	
	/** 部门路径 deptId.deptId. */
	protected String deptPath;
	
	/** 部门路径名称 deptPathName/deptPathName/ */
	protected String deptPathName;
	
	/** 验证的顺序索引 */
	protected Integer checkIndex;
	
	/** tab验证结果 (1:通过; 0:未通过) */
	protected Integer checkStatus;
	
	/** 默认的验证状态 默认为formCode中checkStatus　 */
	protected String defaultCheckStatus;
	
	/** 表单ID */
	protected Long formId;
	
	/** 表单编码 */
	protected FormCodeEnum formCode;
	
	/** 要跳转的标签页index，NULL表示按顺序跳转 */
	protected Integer toIndex;
	
	/** 0:没有改动,不需要保存;1:有改动,需要保存数据 */
	protected Integer fm5;
	
	/** 相关项目编号，多条用,隔开 */
	protected String relatedProjectCode;
	
	/** 是否签报 */
	protected BooleanEnum isFormChangeApply;
	
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
	
	public Integer getCheckIndex() {
		return this.checkIndex;
	}
	
	public void setCheckIndex(Integer checkIndex) {
		this.checkIndex = checkIndex;
	}
	
	public Integer getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getDefaultCheckStatus() {
		return this.defaultCheckStatus;
	}
	
	public void setDefaultCheckStatus(String defaultCheckStatus) {
		this.defaultCheckStatus = defaultCheckStatus;
	}
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public Integer getToIndex() {
		return (null == this.toIndex) ? this.checkIndex : this.toIndex;
	}
	
	public void setToIndex(Integer toIndex) {
		this.toIndex = toIndex;
	}
	
	public Integer getFm5() {
		return fm5;
	}
	
	public void setFm5(Integer fm5) {
		this.fm5 = fm5;
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
	
	public BooleanEnum getIsFormChangeApply() {
		return this.isFormChangeApply;
	}
	
	public void setIsFormChangeApply(BooleanEnum isFormChangeApply) {
		this.isFormChangeApply = isFormChangeApply;
	}
	
	/**
	 * @return true 不需要保存数据,直接返回
	 */
	public boolean noNeedSave() {
		return (null != this.fm5 && this.fm5 == 0);
	}
	
	@Override
	public void check() {
		validateNotNull(checkIndex, "checkIndex");
		validateNotNull(checkStatus, "checkStatus");
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
