package com.born.insurance.ws.enums;

public enum NotificationTypeEnum {
	
	APPOINT("APPOINT", "指定用户", null),
	APPUSER("APPUSER", "手机端用户", null);
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	/** 查询用户的方法注解名 */
	private final String searchUserMethodName;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private NotificationTypeEnum(String code, String message, String searchUserMethodName) {
		this.code = code;
		this.message = message;
		this.searchUserMethodName = searchUserMethodName;
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
	
	public String getSearchUserMethodName() {
		return this.searchUserMethodName;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return NotificationTypeEnum
	 */
	public static NotificationTypeEnum getByCode(String code) {
		for (NotificationTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<NotificationTypeEnum>
	 */
	public static java.util.List<NotificationTypeEnum> getAllEnum() {
		java.util.List<NotificationTypeEnum> list = new java.util.ArrayList<NotificationTypeEnum>(
			values().length);
		for (NotificationTypeEnum _enum : values()) {
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
		for (NotificationTypeEnum _enum : values()) {
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
		NotificationTypeEnum _enum = getByCode(code);
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
	public static String getCode(NotificationTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
