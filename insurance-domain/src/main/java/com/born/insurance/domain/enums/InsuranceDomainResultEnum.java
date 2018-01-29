/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.domain.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Filename EstateDomainResultEnum.java
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
public enum InsuranceDomainResultEnum {
	
	/** 未知异常 */
	UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),
	/** 请求参数不完整 */
	INCOMPLETE_REQ_PARAM("INCOMPLETE_REQ_PARAM", "请求参数不完整"),
	/** 数据库异常 */
	DATABASE_EXCEPTION("DATABASE_EXCEPTION", "数据库异常"),
	/** 没有数据 */
	HAVE_NOT_DATA("HAVE_NOT_DATA", "没有数据"),
	/** 自检失败 */
	EXAM_SELF_NOT_PASS("EXAM_SELF_NOT_PASS", "自检失败"),
	/** 对数据无访问权限 */
	NO_ACCESS("NO_ACCESS", "对数据无访问权限"),
	/** action 无对应的方法 */
	NO_ACTION("NO_ACTION", "无对应的方法"),
	/** 数据处理状态 不对 */
	DO_ACTION_STATUS_ERROR("DO_ACTION_STATUS_ERROR", "数据处理状态不对或已经处理完成"),
	/** 执行成功 */
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"),
	/** 执行失败 */
	EXECUTE_FAILURE("EXECUTE_FAILURE", "执行失败");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>EstateDomainResultEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private InsuranceDomainResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
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
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return EstateDomainResultEnum
	 */
	public static InsuranceDomainResultEnum getByCode(String code) {
		for (InsuranceDomainResultEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EstateDomainResultEnum>
	 */
	public List<InsuranceDomainResultEnum> getAllEnum() {
		List<InsuranceDomainResultEnum> list = new ArrayList<InsuranceDomainResultEnum>();
		for (InsuranceDomainResultEnum _enum : values()) {
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
		for (InsuranceDomainResultEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
