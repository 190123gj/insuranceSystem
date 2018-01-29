package com.born.insurance.ws.enums.bpm;

/**
 * 组织类型
 *
 *
 * @author wuzj
 *
 */
public enum OrgTypeEnum {
	
	GROUP("GROUP", "集团", "1"),
	FIRM("FIRM", "公司", "2"),
	DEPT("DEPT", "部门", "3"),
	TEAM("TEAM", "小组", "4"),
	OTHER("OTHER", "其他", "5");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	private String value;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 * @param value 枚举值
	 */
	private OrgTypeEnum(String code, String message, String value) {
		this.code = code;
		this.message = message;
		this.value = value;
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
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
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
	 * @return Returns the value.
	 */
	public String value() {
		return value;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return OrgTypeEnum
	 */
	public static OrgTypeEnum getByCode(String code) {
		for (OrgTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return OrgTypeEnum
	 */
	public static OrgTypeEnum getByValue(String value) {
		for (OrgTypeEnum _enum : values()) {
			if (_enum.getValue().equals(value)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<OrgTypeEnum>
	 */
	public static java.util.List<OrgTypeEnum> getAllEnum() {
		java.util.List<OrgTypeEnum> list = new java.util.ArrayList<OrgTypeEnum>(values().length);
		for (OrgTypeEnum _enum : values()) {
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
		for (OrgTypeEnum _enum : values()) {
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
		OrgTypeEnum _enum = getByCode(code);
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
	public static String getCode(OrgTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
