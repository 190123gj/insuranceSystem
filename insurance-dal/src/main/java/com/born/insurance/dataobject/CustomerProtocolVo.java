package com.born.insurance.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.dal.dataobject.CustomerProtocolDO;

public class CustomerProtocolVo extends CustomerProtocolDO {

	/** serialVersionUID*/
	private static final long serialVersionUID = 682893042395032324L;
	
	//客户的类别
	private String customerType;
	
	//客户证件类型
	private String certType;

	//客户证件号码
	private String certNo;

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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
