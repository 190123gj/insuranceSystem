package com.born.insurance.ws.order.customer;

import com.born.insurance.ws.order.base.ProcessOrder;

public class CustomerProtocolOrder extends ProcessOrder {

	/**
	 * 
	 */
private static final long serialVersionUID = 1717567165151916912L;

	//协议的id
	private String id;

	// 客户的类别
	private String customerType;

	// 客户证件类型
	private String certType;

	// 客户证件号码
	private String certNo;

	// 协议状态
	private String status;
	
	//用户id
	private String customerUserId;

	// 客户名称
	private String customerUserName;
	
	//协议编码
	private String no;
	
	//关联协议id
	private String relationProtocolId ;

	// 协议名称
	private String name;
	
	private String beginDate;
	
	private String endDate;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getCustomerUserId() {
		return customerUserId;
	}

	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
	}

	public String getRelationProtocolId() {
		return relationProtocolId;
	}

	public void setRelationProtocolId(String relationProtocolId) {
		this.relationProtocolId = relationProtocolId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public void check() {
		validateHasText(name, "协议名称");
		validateHasText(customerUserId, "用户id");
		validateHasText(name, "协议名称");
	}

}
