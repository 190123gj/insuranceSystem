package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 子系统对应表单类型
 * 
 * @author Ji
 *
 */
public enum SubsystemDockFormTypeEnum {
	
	ASSETS_TRANSFER("ASSETS_TRANSFER", "资产转让");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SiteStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SubsystemDockFormTypeEnum(String code, String message) {
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
	 * @return BusiTypeEnum
	 */
	public static SubsystemDockFormTypeEnum getByCode(String code) {
		for (SubsystemDockFormTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BusiTypeEnum>
	 */
	public static List<SubsystemDockFormTypeEnum> getAllEnum() {
		List<SubsystemDockFormTypeEnum> list = new ArrayList<SubsystemDockFormTypeEnum>();
		for (SubsystemDockFormTypeEnum _enum : values()) {
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
		for (SubsystemDockFormTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
