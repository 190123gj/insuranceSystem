package com.born.insurance.biz.service.common;

import java.net.URL;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.SysParamEnum;
import com.born.insurance.ws.order.common.SendMailAttachOrder;
import com.born.insurance.ws.order.common.SendMailOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * 发送邮件
 * @author wuzj
 */
@Service("mailService")
public class MailServiceImpl extends BaseAutowiredDomainService implements MailService {
	
	private String hostName;
	private int smtpPort;
	private String userName;
	private String password;
	private String nickName;
	
	class sendThread extends Thread {
		
		Email email;
		
		SendMailOrder order;
		
		public sendThread(Email email, SendMailOrder order) {
			this.email = email;
			this.order = order;
		}
		
		@Override
		public void run() {
			try {
				if (email != null) {
					logger.info("发送邮件:{}", order);
					email.send();
					logger.info("邮件发送成功 :{}", order);
				}
			} catch (EmailException e) {
				logger.error("邮件发送失败 :{} {} , {}", e, email.getToAddresses(), order);
			}
		}
	}
	
	//初始化参数
	private void init() {
		hostName = sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_MAIL_SERVER
			.code());
		smtpPort = NumberUtil.parseInt(sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_MAIL_SERVERPORT.code()));
		userName = sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_MAIL_USERNAME
			.code());
		password = sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_MAIL_PASSWORD
			.code());
		nickName = sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_MAIL_NICKNAME
			.code());
	}
	
	/**
	 * 设置邮件公用属性
	 * @param email
	 * @throws Exception
	 */
	private void setMailCommonProperty(Email email) throws Exception {
		
		init();
		
		email.setHostName(hostName);
		if (smtpPort > 0)
			email.setSmtpPort(smtpPort);
		
		// 登陆邮件服务器的用户名和密码
		email.setAuthentication(userName, password);
		email.setSSLOnConnect(true);
		// 发送人
		if (StringUtil.isNotBlank(nickName)) {
			email.setFrom(userName, nickName);
			
		} else {
			email.setFrom(userName);
		}
		email.setCharset("UTF-8");
		email.setSocketConnectionTimeout(15000);
		email.setSocketTimeout(15000);
	}
	
	@Override
	public InsuranceBaseResult sendTextEmail(SendMailOrder order) {
		
		InsuranceBaseResult result = createResult();
		
		try {
			order.check();
			
			//新建html邮件
			
			SimpleEmail email = new SimpleEmail();
			setMailCommonProperty(email);
			
			//邮件主题
			email.setSubject(order.getSubject());
			//邮件内容
			email.setMsg(order.getContent());
			
			//过滤非email地址
			List<String> toList = Lists.newArrayList();
			for (String to : order.getSendTo()) {
				if (StringUtil.isEmail(to))
					toList.add(to);
			}
			
			if (toList.size() == 0) {
				result.setSuccess(false);
				result.setMessage("收件人地址为空");
				return result;
			}
			
			//添加收件人
			for (String to : toList) {
				email.addTo(to);
			}
			
			//添加抄送人
			if (ListUtil.isNotEmpty(order.getSendCc())) {
				for (String cc : order.getSendCc()) {
					if (StringUtil.isEmail(cc))
						email.addCc(cc);
				}
			}
			
			//发送邮件
			//email.send();
			//放到线程池发送
			taskExecutor.execute(new sendThread(email, order));
			//new sendThread(email, order).run();
			result.setSuccess(true);
			logger.info("启动邮件发送线程成功： {}", order);
			result.setMessage("启动邮件发送线程成功 ");
		} catch (Exception e) {
			logger.error("邮件发送失败：{} {}", order, e);
			result.setSuccess(false);
			result.setMessage("邮件发送失败 ");
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult sendHtmlEmail(SendMailOrder order) {
		
		InsuranceBaseResult result = createResult();
		
		try {
			order.check();
			
			HtmlEmail email = new HtmlEmail();
			setMailCommonProperty(email);
			
			//邮件主题
			email.setSubject(order.getSubject());
			email.setTextMsg(order.getSubject());
			//邮件内容
			email.setHtmlMsg(order.getContent());
			
			//过滤非email地址
			List<String> toList = Lists.newArrayList();
			for (String to : order.getSendTo()) {
				if (StringUtil.isEmail(to))
					toList.add(to);
			}
			
			if (toList.size() == 0) {
				result.setSuccess(false);
				result.setMessage("收件人地址为空");
				return result;
			}
			
			//添加收件人
			for (String to : toList) {
				email.addTo(to);
			}
			
			//添加抄送人
			if (ListUtil.isNotEmpty(order.getSendCc())) {
				for (String cc : order.getSendCc()) {
					if (StringUtil.isEmail(cc))
						email.addCc(cc);
				}
			}
			
			//添加附件
			if (ListUtil.isNotEmpty(order.getAttachment())) {
				for (SendMailAttachOrder attach : order.getAttachment()) {
					if (attach != null) {
						EmailAttachment eAttach = new EmailAttachment();
						eAttach.setName(attach.getName());
						eAttach.setDescription(attach.getDesc());
						if (attach.isOnlinePath()) {
							eAttach.setURL(new URL(attach.getPath()));
						} else {
							eAttach.setPath(attach.getPath());
						}
						email.attach(eAttach);
					}
				}
			}
			
			//发送邮件
			//email.send();
			
			//线程池里面发送
			taskExecutor.execute(new sendThread(email, order));
			
			result.setSuccess(true);
			result.setMessage("启动邮件发送线程成功");
		} catch (Exception e) {
			logger.error("发送邮件失败：{}", e);
			result.setSuccess(false);
			result.setMessage("邮件发送失败 ");
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		
		HtmlEmail email = new HtmlEmail();
		
		email.setHostName("smtp.qq.com");
		//email.setSmtpPort(465);
		
		// 登陆邮件服务器的用户名和密码
		email
			.setAuthenticator(new DefaultAuthenticator("wz_jerry@foxmail.com", "coakpbkvuktmbeee"));
		email.setSSLOnConnect(true);
		
		// 接收人
		email.addTo("yuanying@yiji.com");
		
		//抄送
		//email.addCc("ee@ee.com");
		
		// 发送人
		email.setFrom("wz_jerry@foxmail.com", "jerry");
		
		email.setCharset("UTF-8");
		email.setSocketConnectionTimeout(15000);
		email.setSocketTimeout(15000);
		
		//URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		//String cid = email.embed(url, "Apache logo");
		// set the html message
		//email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");
		email.setSubject("测试邮件");
		email
			.setHtmlMsg("<html>发一张图片： <img src='http://www.apache.org/images/asf_logo_wide.gif'></html>");
		
		// set the alternative message
		email.setTextMsg("呵呵呵呵呵呵呵呵呵呵呵呵呵呵呵");
		EmailAttachment attach = new EmailAttachment();
		attach.setPath("D:/dataSql.txt");
		attach.setDescription("这是一个附件DESC");
		
		email.attach(attach);
		
		EmailAttachment attach1 = new EmailAttachment();
		attach1.setURL(new URL(
			"http://192.169.2.245:30000/uploadfile/images/2016-06/07/110_3550335124.jpg"));
		attach.setDescription("这是一张图片DESC");
		email.attach(attach1);
		email.send();
		
		System.out.println("success");
	}
}
