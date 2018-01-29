package com.born.insurance.ws.order.common;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.BooleanEnum;

/**
 * 业务类型查询
 *
 * @author wuzj
 */
public class BusiTypeQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -5819256822909960410L;
	
	private Integer id;
	
	private String code;
	
	private String name;
	
	private Integer parentId;
	
	private String customerType;
	
	private String setupFormCode;
	
	private BooleanEnum isDel;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getSetupFormCode() {
		return this.setupFormCode;
	}
	
	public void setSetupFormCode(String setupFormCode) {
		this.setupFormCode = setupFormCode;
	}
	
	public BooleanEnum getIsDel() {
		return this.isDel;
	}
	
	public void setIsDel(BooleanEnum isDel) {
		this.isDel = isDel;
	}
}
