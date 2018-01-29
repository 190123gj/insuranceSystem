package com.born.insurance.ws.order.common;

import com.born.insurance.ws.base.QueryPageBase;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormMessageTypeEnum;

/**
 * 
 * 表单通知模板
 *
 * @author wuzj
 *
 */
public class FormMessageTempleteQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2370167309408989449L;
	//模板ID
	private Long templeteId;
	//表单编码
	private FormCodeEnum formCode;
	//通知类型
	private FormMessageTypeEnum type;
	//是否发送站内信
	private BooleanEnum isSendSiteMessage;
	//是否发送邮件
	private BooleanEnum isSendMail;
	//是否发送短信
	private BooleanEnum isSendSms;
	
	public Long getTempleteId() {
		return this.templeteId;
	}
	
	public void setTempleteId(Long templeteId) {
		this.templeteId = templeteId;
	}
	
	public FormCodeEnum getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public FormMessageTypeEnum getType() {
		return this.type;
	}
	
	public void setType(FormMessageTypeEnum type) {
		this.type = type;
	}
	
	public BooleanEnum getIsSendSiteMessage() {
		return this.isSendSiteMessage;
	}
	
	public void setIsSendSiteMessage(BooleanEnum isSendSiteMessage) {
		this.isSendSiteMessage = isSendSiteMessage;
	}
	
	public BooleanEnum getIsSendMail() {
		return this.isSendMail;
	}
	
	public void setIsSendMail(BooleanEnum isSendMail) {
		this.isSendMail = isSendMail;
	}
	
	public BooleanEnum getIsSendSms() {
		return this.isSendSms;
	}
	
	public void setIsSendSms(BooleanEnum isSendSms) {
		this.isSendSms = isSendSms;
	}
	
}
