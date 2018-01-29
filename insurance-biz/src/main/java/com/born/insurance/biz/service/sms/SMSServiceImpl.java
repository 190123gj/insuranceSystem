package com.born.insurance.biz.service.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.info.sms.SMSInfo;
import com.born.insurance.ws.service.sms.SMSService;
import com.yjf.common.net.HttpUtil;

@Service("smsService")
public class SMSServiceImpl extends BaseAutowiredDomainService implements SMSService {
	@Value("${hprpt2.eucp.b2m.url}")
	public String url;
	
	@Value("${hprpt2.eucp.b2m.cdkey}")
	public String cdkey;
	
	@Value("${hprpt2.eucp.b2m.password}")
	public String password;
	
	class sendSMSThread extends Thread {
		String url;
		
		public sendSMSThread(String url) {
			this.url = url;
		}
		
		@Override
		public void run() {
			try {
				if (url != null) {
					//					url.replaceAll(" ", "%20").replaceAll("\\?","%3F").replaceAll("&","%26")
					//							.replaceAll("\\|","%124").replaceAll("\\+","%2B")
					//							.replaceAll("/","%2F").replaceAll("%","%25")
					//							.replaceAll("#","%23").replaceAll("=","%3D");
					logger.info("发送短信:{}", url);
					HttpUtil.HttpResult httpResult = HttpUtil.getInstance().connectTimeout(10000)
						.readTimeout(60000).maxPerRoute(500).maxTotal(1000)
						.post(url, null, "utf-8");
					Document document = (Document) DocumentHelper.parseText(httpResult.getBody()
						.trim());
					Element root = document.getRootElement();
					String error = root.element("error").getText();
					String message = root.element("message").getText();
					SMSInfo smsInfo = new SMSInfo();
					smsInfo.setError(error);
					smsInfo.setMessage(message);
					if ("0".equals(error)) {
						smsInfo.setSuccess(true);
						logger.info("短信发送成功 :{}", httpResult.getBody());
					} else {
						smsInfo.setSuccess(false);
						logger.info("短信发送失败 :{}", httpResult.getBody());
					}
					
					InsuranceDomainHolder.get().addAttribute("SMSInfo", smsInfo);
					//					if("0".equals(error)){
					//						logger.info("短信发送成功 :{}", httpResult.getBody());
					//					}else {
					//						logger.info("短信发送失败 :{}", httpResult.getBody());
					//					}
				}
			} catch (Exception e) {
				logger.error("短信发送失败 :{} {}", e, url);
			}
		}
	}
	
	@Override
	public SMSInfo registCdKey() {
		String a = "";
		String b = "";
		HttpUtil.HttpResult httpResult = HttpUtil.getInstance().connectTimeout(10000)
			.readTimeout(60000).maxPerRoute(500).maxTotal(1000)
			.post(url + "regist.action?cdkey=" + cdkey + "&password=" + password, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo logoutCdKey() {
		HttpUtil.HttpResult httpResult = HttpUtil.getInstance().connectTimeout(10000)
			.readTimeout(60000).maxPerRoute(500).maxTotal(1000)
			.post(url + "logout.action?cdkey=" + cdkey + "&password=" + password, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo registdetailinfo(String ename, String linkman, String phonenum, String mobile,
									String email, String fax, String address, String postcode) {
		HttpUtil.HttpResult httpResult = HttpUtil
			.getInstance()
			.connectTimeout(10000)
			.readTimeout(60000)
			.maxPerRoute(500)
			.maxTotal(1000)
			.post(
				url + "registdetailinfo.action?cdkey=" + cdkey + "&password=" + password
						+ "&ename=" + ename + "&linkman=" + linkman + "&phonenum=" + phonenum
						+ "&mobile=" + mobile + "&email=" + email + "&fax=" + fax + "&address="
						+ address + "&postcode=" + postcode, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo sendSMS(String mobileNumber, String smsContent, String addSerial) {
		SMSInfo smsInfo = new SMSInfo();
		if (null == addSerial) {
			addSerial = "";
		}
		smsInfo = checkPhoneNum(mobileNumber);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		smsInfo = checkContent(smsContent);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		smsInfo = checkAddSerial(addSerial);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		
		String env = System.getProperty("spring.profiles.active");
		//线上环境才发？
		if ("online".equals(env)) {
			
		}
		String sendUrl = null;
		try {
			String logUrl = url + "sendsms.action?cdkey=" + cdkey + "&password=" + password
							+ "&phone=" + mobileNumber + "&message=" + smsContent + "&addserial="
							+ addSerial;
			logger.info("短信发送 :{}", logUrl);
			sendUrl = url + "sendsms.action?cdkey=" + cdkey + "&password=" + password + "&phone="
						+ mobileNumber + "&message=" + URLEncoder.encode(smsContent, "UTF-8")
						+ "&addserial=" + addSerial;
			//放到线程池发送
			taskExecutor.execute(new sendSMSThread(sendUrl));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//				HttpUtil.HttpResult httpResult = HttpUtil
		//					.getInstance()
		//					.connectTimeout(10000)
		//					.readTimeout(60000)
		//					.maxPerRoute(500)
		//					.maxTotal(1000)
		//					.post(
		//						url + "sendsms.action?cdkey=" + cdkey + "&password=" + password + "&phone="
		//								+ mobileNumber + "&message=" + smsContent + "&addserial=" + addSerial,
		//						null, "utf-8");
		//				smsInfo = getSmsInfo(httpResult.getBody());
		return null;
	}
	
	@Override
	public SMSInfo sendTimeSMS(String mobileNumber, String smsContent, String addSerial,
								String sendtime) {
		SMSInfo smsInfo = new SMSInfo();
		if (null == addSerial) {
			addSerial = "";
		}
		smsInfo = checkPhoneNum(mobileNumber);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		smsInfo = checkContent(smsContent);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		smsInfo = checkAddSerial(addSerial);
		if (!smsInfo.isSuccess()) {
			logger.info("短信发送失败 :{}", smsInfo.getMessage());
			return smsInfo;
		}
		HttpUtil.HttpResult httpResult = HttpUtil
			.getInstance()
			.connectTimeout(10000)
			.readTimeout(60000)
			.maxPerRoute(500)
			.maxTotal(1000)
			.post(
				url + "sendtimesms.action?cdkey=" + cdkey + "&password=" + password + "&phone="
						+ mobileNumber + "&message=" + smsContent + "&addserial=" + addSerial
						+ "&sendtime=" + sendtime, null, "utf-8");
		smsInfo = getSmsInfo(httpResult.getBody());
		String sendUrl = null;
		try {
			String logUrl = url + "sendtimesms.action?cdkey=" + cdkey + "&password=" + password
							+ "&phone=" + mobileNumber + "&message=" + smsContent + "&addserial="
							+ addSerial + "&sendtime=" + sendtime;
			logger.info("短信发送 :{}", logUrl);
			sendUrl = url + "sendtimesms.action?cdkey=" + cdkey + "&password=" + password
						+ "&phone=" + mobileNumber + "&message="
						+ URLEncoder.encode(smsContent, "UTF-8") + "&addserial=" + addSerial
						+ "&sendtime=" + sendtime;
			//放到线程池发送
			taskExecutor.execute(new sendSMSThread(sendUrl));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public SMSInfo getMo() {
		HttpUtil.HttpResult httpResult = HttpUtil.getInstance().connectTimeout(10000)
			.readTimeout(60000).maxPerRoute(500).maxTotal(1000)
			.post(url + "getmo.action?cdkey=" + cdkey + "&password=" + password, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo querybalance() {
		HttpUtil.HttpResult httpResult = HttpUtil
			.getInstance()
			.connectTimeout(10000)
			.readTimeout(60000)
			.maxPerRoute(500)
			.maxTotal(1000)
			.post(url + "querybalance.action?cdkey=" + cdkey + "&password=" + password, null,
				"utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo chargeUp(String cardNo, String cardPass) {
		HttpUtil.HttpResult httpResult = HttpUtil
			.getInstance()
			.connectTimeout(10000)
			.readTimeout(60000)
			.maxPerRoute(500)
			.maxTotal(1000)
			.post(
				url + "chargeup.action?cdkey=" + cdkey + "&password=" + password + "&cardno="
						+ cardNo + "&cardpass=" + cardPass, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	@Override
	public SMSInfo changePassword(String password, String newPassword) {
		HttpUtil.HttpResult httpResult = HttpUtil
			.getInstance()
			.connectTimeout(10000)
			.readTimeout(60000)
			.maxPerRoute(500)
			.maxTotal(1000)
			.post(
				url + "changepassword.action?cdkey=" + cdkey + "&password=" + password
						+ "&newPassword=" + newPassword, null, "utf-8");
		SMSInfo smsInfo = getSmsInfo(httpResult.getBody());
		return smsInfo;
	}
	
	private SMSInfo checkAddSerial(String addSerial) {
		if (null == addSerial) {
			addSerial = "";
		}
		SMSInfo smsInfo = new SMSInfo();
		smsInfo.setSuccess(true);
		if (addSerial.length() > 10) {
			smsInfo.setSuccess(false);
			smsInfo.setError("");
			smsInfo.setMessage("附加号最长10位");
		}
		return smsInfo;
	}
	
	private SMSInfo checkPhoneNum(String mobileNumber) {
		SMSInfo smsInfo = new SMSInfo();
		smsInfo.setSuccess(true);
		String phones[] = mobileNumber.split(",");
		if (phones.length > 200) {
			smsInfo.setSuccess(false);
			smsInfo.setError("");
			smsInfo.setMessage("手机号码最多200个");
		}
		return smsInfo;
	}
	
	private SMSInfo checkContent(String smsContent) {
		SMSInfo smsInfo = new SMSInfo();
		try {
			smsContent = new String(smsContent.getBytes("GB2312"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			smsInfo.setSuccess(false);
			smsInfo.setError("");
			smsInfo.setMessage("字符串格式转换错误");
			return smsInfo;
		}
		smsInfo.setSuccess(true);
		if (smsContent.length() > 1000) {
			smsInfo.setSuccess(false);
			smsInfo.setError("");
			smsInfo.setMessage("短信内容最多500个汉字或1000个纯英文");
		}
		return smsInfo;
	}
	
	private SMSInfo getSmsInfo(String text) {
		SMSInfo smsInfo = new SMSInfo();
		try {
			Document document = (Document) DocumentHelper.parseText(text.trim());
			Element root = document.getRootElement();
			String error = root.element("error").getText();
			String message = root.element("message").getText();
			smsInfo.setError(error);
			smsInfo.setMessage(message);
			if ("0".equals(error)) {
				smsInfo.setSuccess(true);
			} else {
				smsInfo.setSuccess(false);
			}
			
		} catch (DocumentException e) {
			smsInfo.setSuccess(false);
			smsInfo.setError("");
			smsInfo.setMessage(e.getMessage());
		}
		return smsInfo;
	}
}
