package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 任务执行状态
 * 
 * @author wuzj
 *
 */
public enum ExeStatusEnum {
	
	WAITING("WAITING", "待执行"),
	READ("READ", "已读"),
	
	EXCUTED("EXCUTED", "已执行"),
	AGENT_SET("AGENT_SET", "被代理"),
	TASK_ASSIGN("TASK_ASSIGN", "转交"),
	NOT_TASK("NOT_TASK", "非任务"),
	
	IN_PROGRESS("IN_PROGRESS", "流程中"),
	END_AGREE("END_AGREE", "流程结束"),
	END_DISAGREE("END_PASS", "流程结束"),
	END("END", "作废"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ExeStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ExeStatusEnum(String code, String message) {
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
	 * @return ExeStatusEnum
	 */
	public static ExeStatusEnum getByCode(String code) {
		for (ExeStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ExeStatusEnum>
	 */
	public static List<ExeStatusEnum> getAllEnum() {
		List<ExeStatusEnum> list = new ArrayList<ExeStatusEnum>();
		for (ExeStatusEnum _enum : values()) {
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
		for (ExeStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
