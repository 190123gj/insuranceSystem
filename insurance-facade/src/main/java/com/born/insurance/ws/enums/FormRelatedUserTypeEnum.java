package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 相关人员类型
 * 
 * @author wuzj
 *
 */
public enum FormRelatedUserTypeEnum {
	
	FLOW_SUBMIT_MAN("FLOW_SUBMIT_MAN", "流程发起人"),
	
	FLOW_PROCESS_MAN("FLOW_PROCESS_MAN", "流程处理人员"),
	
	FLOW_CANDIDATE_MAN("FLOW_CANDIDATE_MAN", "流程候选人员"),
	
	OTHER("OTHER", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>RelatedUserTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormRelatedUserTypeEnum(String code, String message) {
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
	 * @return RelatedUserTypeEnum
	 */
	public static FormRelatedUserTypeEnum getByCode(String code) {
		for (FormRelatedUserTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<RelatedUserTypeEnum>
	 */
	public static List<FormRelatedUserTypeEnum> getAllEnum() {
		List<FormRelatedUserTypeEnum> list = new ArrayList<FormRelatedUserTypeEnum>();
		for (FormRelatedUserTypeEnum _enum : values()) {
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
		for (FormRelatedUserTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
