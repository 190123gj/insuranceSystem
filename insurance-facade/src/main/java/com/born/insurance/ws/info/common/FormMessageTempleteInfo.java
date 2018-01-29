package com.born.insurance.ws.info.common;

import java.util.Date;

import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormMessageBaseOnEnum;
import com.born.insurance.ws.enums.FormMessageTypeEnum;

/**
 * 
 * 表单通知模板
 *
 * @author wuzj
 *
 */
public class FormMessageTempleteInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2975942719161196130L;
	//模板ID
	private long templeteId;
	//表单编码
	private FormCodeEnum formCode;
	//通知类型
	private FormMessageTypeEnum type;
	//主题
	private String subject;
	//html模板
	private String contentHtml;
	//文字模板
	private String contentTxt;
	//是否发送站内信
	private BooleanEnum isSendSiteMessage;
	//是否发送邮件
	private BooleanEnum isSendMail;
	//是否发送短信
	private BooleanEnum isSendSms;
	//发送方式基于
	private FormMessageBaseOnEnum baseOn;
	//消息是否附带审核历史
	private BooleanEnum withAuditOpinion;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getTempleteId() {
		return this.templeteId;
	}
	
	public void setTempleteId(long templeteId) {
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
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContentHtml() {
		return this.contentHtml;
	}
	
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}
	
	public String getContentTxt() {
		return this.contentTxt;
	}
	
	public void setContentTxt(String contentTxt) {
		this.contentTxt = contentTxt;
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
	
	public BooleanEnum getWithAuditOpinion() {
		return this.withAuditOpinion;
	}
	
	public void setWithAuditOpinion(BooleanEnum withAuditOpinion) {
		this.withAuditOpinion = withAuditOpinion;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public FormMessageBaseOnEnum getBaseOn() {
		return this.baseOn;
	}
	
	public void setBaseOn(FormMessageBaseOnEnum baseOn) {
		this.baseOn = baseOn;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
