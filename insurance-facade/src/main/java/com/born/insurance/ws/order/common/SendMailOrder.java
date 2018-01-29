package com.born.insurance.ws.order.common;

import java.util.List;

import org.springframework.util.Assert;

import com.born.insurance.ws.order.base.ValidateOrderBase;

public class SendMailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5337371024486045144L;
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String content;
	
	/**
	 * 收件人列表
	 */
	List<String> sendTo;
	
	/**
	 * 抄送人
	 */
	List<String> sendCc;
	
	/**
	 * 邮件附件（简单邮件不支持）
	 */
	List<SendMailAttachOrder> attachment;
	
	@Override
	public void check() {
		validateHasText(subject, "邮件主题");
		validateHasText(content, "邮件内容");
		Assert.isTrue(sendTo != null && sendTo.size() > 0, "收件人不能为空");
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getSendTo() {
		return this.sendTo;
	}
	
	public void setSendTo(List<String> sendTo) {
		this.sendTo = sendTo;
	}
	
	public List<String> getSendCc() {
		return this.sendCc;
	}
	
	public void setSendCc(List<String> sendCc) {
		this.sendCc = sendCc;
	}
	
	public List<SendMailAttachOrder> getAttachment() {
		return this.attachment;
	}
	
	public void setAttachment(List<SendMailAttachOrder> attachment) {
		this.attachment = attachment;
	}
	
}
