package com.born.insurance.biz.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.order.common.SendMailOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/***
 * 
 * 邮件发送服务
 *
 * @author wuzj
 */
@WebService
public interface MailService {
	
	/**
	 * 发送简单邮件
	 * @param order
	 * @return
	 */
	InsuranceBaseResult sendTextEmail(SendMailOrder order);
	
	/**
	 * 发送HTML邮件
	 * @param order
	 * @return
	 */
	InsuranceBaseResult sendHtmlEmail(SendMailOrder order);
}
