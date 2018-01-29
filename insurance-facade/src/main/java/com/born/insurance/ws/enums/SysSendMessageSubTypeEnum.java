package com.born.insurance.ws.enums;

/**
 * Created by Administrator on 2015/3/12.
 */
public enum SysSendMessageSubTypeEnum {
	
	/** 子类型A */
	SUB_TYPE_A("A", "子类型A"),
	
	/** 子类型B */
	SUB_TYPE_B("B", "子类型B"),
	
	/** 子类型C */
	SUB_TYPE_C("C", "子类型C"),
	
	/** 子类型D */
	SUB_TYPE_D("D", "子类型D");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SysSendMessageSubTypeEnum(String code, String message) {
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
	 * @return CertifyStatusEnum
	 */
	public static SysSendMessageSubTypeEnum getByCode(String code) {
		for (SysSendMessageSubTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<CertifyStatusEnum>
	 */
	public static java.util.List<SysSendMessageSubTypeEnum> getAllEnum() {
		java.util.List<SysSendMessageSubTypeEnum> list = new java.util.ArrayList<SysSendMessageSubTypeEnum>(
			values().length);
		for (SysSendMessageSubTypeEnum _enum : values()) {
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
		for (SysSendMessageSubTypeEnum _enum : values()) {
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
		SysSendMessageSubTypeEnum _enum = getByCode(code);
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
	public static String getCode(SysSendMessageSubTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
