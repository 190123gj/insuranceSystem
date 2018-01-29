package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 子系统枚举
 * 
 * @author wuzj
 */
public enum SystemEnum {
	
	CRM("CRM", "客户管理系统"),
	PM("PM", "项目管理系统"),
	AM("AM", "资产管理系统"),
	FM("FM", "资金管理系统"),
	RM("RM", "报表系统");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SystemEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SystemEnum(String code, String message) {
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
	 * @return SystemEnum
	 */
	public static SystemEnum getByCode(String code) {
		for (SystemEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SystemEnum>
	 */
	public static List<SystemEnum> getAllEnum() {
		List<SystemEnum> list = new ArrayList<SystemEnum>();
		for (SystemEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (SystemEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
