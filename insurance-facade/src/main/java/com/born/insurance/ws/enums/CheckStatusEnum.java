package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

public enum CheckStatusEnum {
	
	/** 未申请 */
	NOT_APPLY("NOT_APPLY", "未申请"),
	/** 申请中 */
	APPLYING("APPLYING", "申请中"),
	/** 审核通过 */
	CHECK_PASS("CHECK_PASS", "审核通过"),
	/** 审核未通过 */
	CHECK_NOT_PASS("CHECK_NOT_PASS", "审核未通过");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CheckStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CheckStatusEnum(String code, String message) {
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
	 * @return CheckStatusEnum
	 */
	public static CheckStatusEnum getByCode(String code) {
		for (CheckStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CheckStatusEnum>
	 */
	public List<CheckStatusEnum> getAllEnum() {
		List<CheckStatusEnum> list = new ArrayList<CheckStatusEnum>();
		for (CheckStatusEnum _enum : values()) {
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
		for (CheckStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
