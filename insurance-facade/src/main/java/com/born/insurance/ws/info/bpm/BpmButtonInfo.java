package com.born.insurance.ws.info.bpm;

import java.io.Serializable;

public class BpmButtonInfo implements Serializable {
	//开始按钮
	
	private static final long serialVersionUID = -2284556240448762039L;
	
	/**
	 * 提交
	 */
	public final static int START_BUTTON_TYPE_START = 1;
	
	/**
	 * 流程示意图
	 */
	public final static int START_BUTTON_TYPE_IMAGE = 2;
	
	/**
	 * 打印
	 */
	public final static int START_BUTTON_TYPE_PRINT = 3;
	
	/**
	 * 保存草稿
	 */
	public final static int START_BUTTON_TYPE_SAVE = 6;
	
	//end 开始按钮
	
	//任务按钮
	/**
	 * 同意
	 */
	public final static int NODE_BUTTON_TYPE_COMPLETE = 1;
	
	/**
	 * 反对
	 */
	public final static int NODE_BUTTON_TYPE_OPPOSE = 2;
	
	/**
	 * 弃权
	 */
	public final static int NODE_BUTTON_TYPE_ABSTENT = 3;
	
	/**
	 * 驳回
	 */
	public final static int NODE_BUTTON_TYPE_BACK = 4;
	
	/**
	 * 驳回到发起人
	 */
	public final static int NODE_BUTTON_TYPE_BACKTOSTART = 5;
	
	/**
	 * 转办
	 */
	public final static int NODE_BUTTON_TYPE_ASSIGNEE = 6;
	
	/**
	 * 补签
	 */
	public final static int NODE_BUTTON_TYPE_ADDSIGN = 7;
	
	/**
	 * 保存表单
	 */
	public final static int NODE_BUTTON_TYPE_SAVEFORM = 8;
	
	/**
	 * 流程示意图
	 */
	public final static int NODE_BUTTON_TYPE_IMAGE = 9;
	
	/**
	 * 打印
	 */
	public final static int NODE_BUTTON_TYPE_PRINT = 10;
	
	/**
	 * 审批历史
	 */
	public final static int NODE_BUTTON_TYPE_HIS = 11;
	
	/**
	 * 终止
	 */
	public final static int NODE_BUTTON_TYPE_END = 15;
	
	/**
	 * 沟通
	 */
	public final static int NODE_BUTTON_TYPE_COMMU = 16;
	
	/**
	 * 转办管理员
	 */
	public final static int NODE_BUTTON_TYPE_ASSIGNEEMANAGE = 17;
	/**
	 * 沟通反馈
	 */
	public final static int NODE_BUTTON_TYPE_FEEDBACK = 18;
	/**
	 * 撤销
	 */
	public final static int NODE_BUTTON_TYPE_REVERT = 43;
	
	/**
	 * 保存表单
	 */
	public final static int NODE_BUTTON_TYPE_COMPLETE_END = 55;
	
	String buttonName;
	int operatortype;
	
	public String getButtonName() {
		return this.buttonName;
	}
	
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	
	public int getOperatortype() {
		return this.operatortype;
	}
	
	public void setOperatortype(int operatortype) {
		this.operatortype = operatortype;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BpmButtonInfo [buttonName=");
		builder.append(buttonName);
		builder.append(", operatortype=");
		builder.append(operatortype);
		builder.append("]");
		return builder.toString();
	}
	
}
