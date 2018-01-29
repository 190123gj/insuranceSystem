/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.exception;

import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.yjf.common.lang.exception.ApplicationNestException;

/**
 * 
 * @Filename EstateException.java
 * 
 * @Description
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
public class InsuranceBizException extends ApplicationNestException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 554229467642044021L;
	
	private InsuranceResultEnum resultCode;
	
	private String errorMsg;
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 */
	public InsuranceBizException() {
		super();
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 * @param arg1
	 */
	public InsuranceBizException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public InsuranceBizException(String arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param arg0
	 */
	public InsuranceBizException(Throwable arg0) {
		super(arg0);
	}
	
	/**
	 * 构建一个<code>EstateException.java</code>
	 * @param resultCode
	 * @param errorMsg
	 */
	public InsuranceBizException(InsuranceResultEnum resultCode, String errorMsg) {
		super(errorMsg);
		this.resultCode = resultCode;
		this.errorMsg = errorMsg;
	}
	
	public InsuranceResultEnum getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(InsuranceResultEnum resultCode) {
		this.resultCode = resultCode;
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
		builder.append("EstateException [resultCode=");
		builder.append(resultCode);
		builder.append(", errorMsg=");
		builder.append(errorMsg);
		builder.append("]");
		return builder.toString();
	}
	
}
