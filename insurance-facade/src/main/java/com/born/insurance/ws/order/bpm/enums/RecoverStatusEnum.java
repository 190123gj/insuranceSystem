package com.born.insurance.ws.order.bpm.enums;

public enum RecoverStatusEnum {
	
	RECOVER_NO("RECOVER_NO", "不追回", 0),
	RECOVER("RECOVER", "追回", 1);
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	private final int bpmStatus;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private RecoverStatusEnum(String code, String message, int bpmStatus) {
		this.code = code;
		this.message = message;
		this.bpmStatus = bpmStatus;
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
	 * @return RecoverStatusEnum
	 */
	public static RecoverStatusEnum getByCode(String code) {
		for (RecoverStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	public static RecoverStatusEnum getByBpmStatus(int bpmSatus) {
		for (RecoverStatusEnum _enum : values()) {
			if (_enum.bpmStatus == bpmSatus) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<RecoverStatusEnum>
	 */
	public static java.util.List<RecoverStatusEnum> getAllEnum() {
		java.util.List<RecoverStatusEnum> list = new java.util.ArrayList<RecoverStatusEnum>(
			values().length);
		for (RecoverStatusEnum _enum : values()) {
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
		for (RecoverStatusEnum _enum : values()) {
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
		RecoverStatusEnum _enum = getByCode(code);
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
	public static String getCode(RecoverStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
