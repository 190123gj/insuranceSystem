package com.born.insurance.ws.base;

import java.io.Serializable;

public class WSResultBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 返回代码("1"-成功,"0"-失败)
	 */
	private String code;
	/**
	 * 返回消息描述
	 */
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "WSResultBase [code=" + code + ", message=" + message + "]";
	}
	
	
	

}
