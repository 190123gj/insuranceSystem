package com.born.insurance.ws.enums.bpm;

public enum UserStateEnum {
	
	FREEZE("freeze", "冻结"),
	
	INACTIVE("inactive", "未激活"),
	
	DISABLE("disable", "禁用"),
	
	LOCKED("locked", "锁定"),
	
	NORMAL("normal", "正常"),
	
	DELETED("deleted", "删除");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private UserStateEnum(String code, String message) {
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
	 * @return UserStateEnum
	 */
	public static UserStateEnum getByCode(String code) {
		for (UserStateEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<UserStateEnum>
	 */
	public static java.util.List<UserStateEnum> getAllEnum() {
		java.util.List<UserStateEnum> list = new java.util.ArrayList<UserStateEnum>(values().length);
		for (UserStateEnum _enum : values()) {
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
		for (UserStateEnum _enum : values()) {
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
		UserStateEnum _enum = getByCode(code);
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
	public static String getCode(UserStateEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
