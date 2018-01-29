/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ws.result.base;

import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import org.springframework.util.Assert;

import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename YrdBaseResult.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class InsuranceBaseResult extends ResultBase {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 5156892170604621621L;
	/** 返回结果码 */
	InsuranceResultEnum insuranceResultEnum = InsuranceResultEnum.UN_KNOWN_EXCEPTION;
	
	private String url;
	
	/** 新增后返回的主键 */
	private long keyId;
	
	/** 需要返回的对象 */
	private Object returnObject;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getKeyId() {
		return this.keyId;
	}
	
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}
	
	@Override
	public boolean isExecuted() {
		
		return InsuranceResultEnum.EXECUTE_SUCCESS == insuranceResultEnum ? true : false;
	}
	
	public InsuranceResultEnum getInsuranceResultEnum() {
		return insuranceResultEnum;
	}
	
	public void setInsuranceResultEnum(InsuranceResultEnum fcsResultEnum) {
		this.insuranceResultEnum = fcsResultEnum;
		if (this.insuranceResultEnum != null) {
			if (StringUtil.isEmpty(this.getMessage())) {
				this.setMessage(this.insuranceResultEnum.getMessage());
			}
			
		}
	}
	
	@Override
	public void setSuccess(boolean success) {
		super.setSuccess(success);
		if (success)
			insuranceResultEnum = InsuranceResultEnum.EXECUTE_SUCCESS;
	}
	
	public Object getReturnObject() {
		return this.returnObject;
	}
	
	public void setReturnObject(Object returnObject) {
		Assert.isTrue(returnObject != this, "返回对象不能是自己");
		this.returnObject = returnObject;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsuranceBaseResult [insuranceResultEnum=");
		builder.append(insuranceResultEnum);
		builder.append(", url=");
		builder.append(url);
		builder.append(", keyId=");
		builder.append(keyId);
		builder.append(", returnObject=");
		builder.append(returnObject);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
