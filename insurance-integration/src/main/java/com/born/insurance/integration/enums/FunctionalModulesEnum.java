/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.integration.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Filename FunctionalModules.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-24</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public enum FunctionalModulesEnum {
	
	/** 找回密码功能 */
	//TODO  根据app测试需求由3更改为200 上线前更改回来
	FORGOT_PASSWORD("FORGOT_PASSWORD", "找回密码", 3),
	
	/** 账户激活功能 */
	ACCOUNT_ACTIVATION("ACCOUNT_ACTIVATION", "账户激活", 300),
	
	/** 其他功能 */
	OTHER("OTHER", "其他", 3);
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	/** 枚举描述 */
	private final int sendCount;
	
	/**
	 * 构造一个<code>FunctionalModulesEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FunctionalModulesEnum(String code, String message, int sendCount) {
		this.code = code;
		this.message = message;
		this.sendCount = sendCount;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	public int getSendCount() {
		return sendCount;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FunctionalModulesEnum
	 */
	public static FunctionalModulesEnum getByCode(String code) {
		for (FunctionalModulesEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FunctionalModulesEnum>
	 */
	public List<FunctionalModulesEnum> getAllEnum() {
		List<FunctionalModulesEnum> list = new ArrayList<FunctionalModulesEnum>();
		for (FunctionalModulesEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (FunctionalModulesEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
