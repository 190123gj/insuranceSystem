/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.domain.exception;

import com.born.insurance.domain.enums.InsuranceDomainResultEnum;
import com.yjf.common.lang.exception.ApplicationNestException;

/**
 * 
 * @Filename EstateDomainException.java
 * 
 * @Description 领域模型异常
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2013-2-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class InsuranceDomainException extends ApplicationNestException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -2527668261623906864L;
	
	private InsuranceDomainResultEnum domainResult;
	
	private String errorMsg;
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 */
	public InsuranceDomainException() {
		super();
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param domainResult
	 * @param errorMsg
	 */
	public InsuranceDomainException(InsuranceDomainResultEnum domainResult, String errorMsg) {
		super(errorMsg);
		this.domainResult = domainResult;
		this.errorMsg = errorMsg;
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param message
	 */
	public InsuranceDomainException(String message) {
		super(message);
	}
	
	/**
	 * 构建一个<code>EstateDomainException.java</code>
	 * @param cause
	 */
	public InsuranceDomainException(Throwable cause) {
		super(cause);
	}
	
	public InsuranceDomainResultEnum getDomainResult() {
		return domainResult;
	}
	
	public void setDomainResult(InsuranceDomainResultEnum domainResult) {
		this.domainResult = domainResult;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstateDomainException [domainResult=");
		builder.append(domainResult);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}
	
}
