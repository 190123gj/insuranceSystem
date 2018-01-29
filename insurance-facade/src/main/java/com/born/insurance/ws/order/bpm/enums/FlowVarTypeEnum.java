package com.born.insurance.ws.order.bpm.enums;

public enum FlowVarTypeEnum {
	INT("int", "整型", null),
	LONG("long", "长整型", null),
	DOUBLE("double", "双精度浮点型", null),
	STRING("string", "字符串", null),
	DATE("date", "日期", "yyyy-MM-dd HH:mm:ss");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	/** 格式化字符串 */
	private final String dateFormat;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private FlowVarTypeEnum(String code, String message, String dateFormat) {
		this.code = code;
		this.message = message;
		this.dateFormat = dateFormat;
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
	
	public String getDateFormat() {
		return this.dateFormat;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FlowVarTypeEnum
	 */
	public static FlowVarTypeEnum getByCode(String code) {
		for (FlowVarTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FlowVarTypeEnum>
	 */
	public static java.util.List<FlowVarTypeEnum> getAllEnum() {
		java.util.List<FlowVarTypeEnum> list = new java.util.ArrayList<FlowVarTypeEnum>(
			values().length);
		for (FlowVarTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (FlowVarTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		FlowVarTypeEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(FlowVarTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
