package com.born.insurance.ws.order.bpm.enums;

public enum WorkflowStatusEnum {
	STATUS_SUSPEND("STATUS_SUSPEND", "挂起状态", 0),
	STATUS_RUNNING("STATUS_RUNNING", "运行状态", 1),
	STATUS_FINISH("STATUS_FINISH", "结束状态", 2),
	STATUS_MANUAL_FINISH("STATUS_MANUAL_FINISH", "人工终止", 3),
	STATUS_FORM("STATUS_FORM", "草稿状态", 4),
	STATUS_RECOVER("STATUS_RECOVER", "已撤销", 5),
	STATUS_REJECT("STATUS_REJECT", "已驳回", 6),
	STATUS_REDO("STATUS_REDO", "已追回", 7),
	STATUS_DELETE("STATUS_DELETE", "逻辑删除", 7);
	
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
	private WorkflowStatusEnum(String code, String message, int bpmStatus) {
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
	 * @return WorkflowStatusEnum
	 */
	public static WorkflowStatusEnum getByCode(String code) {
		for (WorkflowStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<WorkflowStatusEnum>
	 */
	public static java.util.List<WorkflowStatusEnum> getAllEnum() {
		java.util.List<WorkflowStatusEnum> list = new java.util.ArrayList<WorkflowStatusEnum>(
			values().length);
		for (WorkflowStatusEnum _enum : values()) {
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
		for (WorkflowStatusEnum _enum : values()) {
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
		WorkflowStatusEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	public static WorkflowStatusEnum getByBpmStatus(int bpmSatus) {
		for (WorkflowStatusEnum _enum : values()) {
			if (_enum.bpmStatus == bpmSatus) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(WorkflowStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
