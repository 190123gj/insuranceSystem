package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单状态
 *
 * @author wuzj
 */
public enum FormStatusEnum {
	
	DELETED("DELETED", -1, "已删除"),
	
	DRAFT("DRAFT", 1, "草稿"),
	
	SUBMIT("SUBMIT", 2, "待审核"),
	
	CANCEL("CANCEL", 3, "撤销"),
	
	AUDITING("AUDITING", 4, "审核中"),
	
	BACK("BACK", 5, "驳回"),
	
	APPROVAL("APPROVAL", 6, "通过"),
	
	DENY("DENY", 7, "不通过"),
	
	END("END", 8, "作废");
	
	/** 枚举值 */
	private final String code;
	
	private final int value;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FormStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormStatusEnum(String code, int value, String message) {
		this.code = code;
		this.value = value;
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
	
	public int getValue() {
		return this.value;
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
	
	public int value() {
		return value;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FormStatusEnum
	 */
	public static FormStatusEnum getByCode(String code) {
		for (FormStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormStatusEnum>
	 */
	public static List<FormStatusEnum> getAllEnum() {
		List<FormStatusEnum> list = new ArrayList<FormStatusEnum>();
		for (FormStatusEnum _enum : values()) {
			if (_enum != DELETED) //删除的状态不显示
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
		for (FormStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
