package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 时间 单位
 * 
 * @author lirz
 *
 * 2016-3-8 下午3:22:18
 */
public enum TimeUnitEnum {
	
	YEAR("Y", "年", "年"),
	MONTH("M", "月", "个月"),
	DAY("D", "天", "天");
	
	/** 枚举值 */
	private final String code;
	
	private final String viewName;
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>TimeUnitEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private TimeUnitEnum(String code, String message, String viewName) {
		this.code = code;
		this.message = message;
		this.viewName = viewName;
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
	 * @return Returns the viewName.
	 */
	public String getViewName() {
		return viewName;
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
	 * @return Returns the viewName.
	 */
	public String viewName() {
		return viewName;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return TimeUnitEnum
	 */
	public static TimeUnitEnum getByCode(String code) {
		for (TimeUnitEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<TimeUnitEnum>
	 */
	public static List<TimeUnitEnum> getAllEnum() {
		List<TimeUnitEnum> list = new ArrayList<TimeUnitEnum>();
		for (TimeUnitEnum _enum : values()) {
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
		for (TimeUnitEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
