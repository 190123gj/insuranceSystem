package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 表单通知类型
 * 
 * @author wuzj
 *
 */
public enum FormMessageTypeEnum {
	
	STEP("STEP", "步骤通知"),
	RESULT("RESULT", "结果通知");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FormMessageTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormMessageTypeEnum(String code, String message) {
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
	 * @return FormMessageTypeEnum
	 */
	public static FormMessageTypeEnum getByCode(String code) {
		for (FormMessageTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormMessageTypeEnum>
	 */
	public static List<FormMessageTypeEnum> getAllEnum() {
		List<FormMessageTypeEnum> list = new ArrayList<FormMessageTypeEnum>();
		for (FormMessageTypeEnum _enum : values()) {
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
		for (FormMessageTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
