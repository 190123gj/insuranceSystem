package com.born.insurance.biz.service.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.born.insurance.biz.service.common.MailService;
import com.born.insurance.biz.service.common.SiteMessageService;
import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.biz.service.common.WorkflowExtProcessService;
import com.born.insurance.integration.bpm.user.SysUser;
import com.born.insurance.ws.order.common.*;
import org.springframework.beans.factory.annotation.Autowired;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.insurance.util.CommonUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormMessageVarEnum;
import com.born.insurance.ws.info.common.FormExecuteInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;
import com.born.insurance.ws.info.common.FormRelatedUserInfo;
import com.born.insurance.ws.order.bpm.FlowVarField;
import com.born.insurance.ws.order.bpm.WorkflowProcessLog;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.FormNotifyAssginService;
import com.born.insurance.ws.service.common.FormNotifyNextService;
import com.born.insurance.ws.service.common.FormNotifyResultService;
import com.born.insurance.ws.service.common.SysWebAccessTokenService;
import com.born.insurance.ws.service.sms.SMSService;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.biz.service.bpm.WorkflowEngineClient;
import com.born.insurance.integration.bpm.result.WorkflowResult;
import com.born.insurance.integration.common.PropertyConfigService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

/**
 * 
 * 流程处理基类
 * 
 * @author lirz
 * 
 * 2016-4-25 下午2:22:15
 */
public class BaseProcessService extends BaseAutowiredDomainService implements
																	WorkflowExtProcessService {
	
	@Autowired
	protected WorkflowEngineClient workflowEngineClient;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	protected PropertyConfigService propertyConfigService;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
	@Autowired
	protected MailService mailService;
	
	@Autowired
	protected SMSService smsService;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenService;
	
	/**
	 * 获取站外访问密钥
	 * @param userInfo
	 * @return
	 */
	protected String getAccessToken(SimpleUserInfo userInfo) {
		SysWebAccessTokenOrder tokenOrder = new SysWebAccessTokenOrder();
		BeanCopier.staticCopy(userInfo, tokenOrder);
		tokenOrder.setRemark("站外访问链接");
		InsuranceBaseResult tokenResult = sysWebAccessTokenService.addUserAccessToken(tokenOrder);
		if (tokenResult != null && tokenResult.isSuccess()) {
			return tokenResult.getUrl();
		} else {
			return "";
		}
	}
	
	/**
	 * 构建审核历史table
	 * @param pLog
	 * @param isEmail
	 * @return
	 */
	protected String buildAuditOpinionTable(QueryBaseBatchResult<WorkflowProcessLog> pLog,
											boolean isEmail) {
		StringBuffer sbf = new StringBuffer();
		try {
			if (pLog != null && pLog.isSuccess() && ListUtil.isNotEmpty(pLog.getPageList())) {
				sbf.append("<table width='100%' border='1' style='margin:10px 0px;text-align:center;border-spacing:0;border-collapse:collapse'>");
				sbf.append("<thead><tr><th width='40'>序号</th><th width='200'>任务名称</th><th width='150'>开始时间</th><th width='150'>结束时间</th><th width='80'>执行人</th><th>审批意见</th><th width='80'>审批状态</th></tr></thead>");
				sbf.append("<tbody>");
				int index = 0;
				for (WorkflowProcessLog log : pLog.getPageList()) {
					String opinion = StringUtil.defaultIfEmpty(log.getOpinion(), "-");
					sbf.append("<tr>");
					sbf.append("<td>").append(++index).append("</td>");
					sbf.append("<td>").append(StringUtil.defaultIfEmpty(log.getTaskName(), "-"))
						.append("</td>");
					sbf.append("<td>").append(StringUtil.defaultIfEmpty(log.getStartTime(), "-"))
						.append("</td>");
					sbf.append("<td>").append(StringUtil.defaultIfEmpty(log.getEndTime(), "-"))
						.append("</td>");
					sbf.append("<td>").append(StringUtil.defaultIfEmpty(log.getExeFullname(), "-"))
						.append("</td>");
					sbf.append("<td class='fn-csp fnAuditMoreExamineRecord'>");
					if (isEmail) {
						sbf.append(opinion);
					} else {
						sbf.append(StringUtil.subString(StringUtil.trimToEmpty(opinion), 12, "..."));
						sbf.append("<div class='opinion-detail' style='display:none'>");
						sbf.append(opinion);
						sbf.append("</div>");
					}
					sbf.append("</td>");
					sbf.append("<td>")
						.append(StringUtil.defaultIfEmpty(log.getCheckStatusMessage(), "-"))
						.append("</td>");
					sbf.append("</tr>");
				}
				sbf.append("</tbody>");
				sbf.append("</table>");
			}
		} catch (Exception e) {
			logger.error("构建审核历史列表出错：{}", e);
		}
		return sbf.toString();
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 */
	protected SimpleUserInfo queryUserById(long userId) {
		try {
			SysUser sysUser = bpmUserQueryService.findUserByUserId(userId);
			if (sysUser != null) {
				SimpleUserInfo user = new SimpleUserInfo();
				user.setUserId(sysUser.getUserId());
				user.setUserAccount(sysUser.getAccount());
				user.setUserName(sysUser.getFullname());
				user.setMobile(sysUser.getMobile());
				user.setEmail(sysUser.getEmail());
			}
		} catch (Exception e) {
			logger.error("查询用户信息出错{}", e);
		}
		return null;
	}
	
	@Override
	protected FormBaseResult createResult() {
		return new FormBaseResult();
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		return new ArrayList<>(0);
	}
	
	@Override
	public InsuranceBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		InsuranceBaseResult result = createResult();
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理(BASE)
	}
	
	@Override
	public InsuranceBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		InsuranceBaseResult result = createResult();
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 审核后业务处理(BASE)
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 结束流程后业务处理(BASE)
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理(BASE)
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		//删除表单后业务处理(BASE)
	}
	
	@Override
	public void cancelAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//撤销表单后业务处理(BASE)
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//提交人终止作废表单后业务处理(BASE)
	}
	
	//默认返回表单的提交人
	@Override
	public List<SimpleUserInfo> resultNotifyUserList(FormInfo formInfo) {
		List<SimpleUserInfo> list = Lists.newArrayList();
		SimpleUserInfo user = new SimpleUserInfo();
		BeanCopier.staticCopy(formInfo, user);
		user.setMobile(formInfo.getUserMobile());
		user.setEmail(formInfo.getUserEmail());
		list.add(user);
		return list;
	}
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		return Maps.newHashMap();
	}
	
	@Override
	public FormNotifyNextService getNotifyNextService() {
		return new FormNotifyNextService() {
			
			@Override
			public void notifyNext(FormInfo formInfo, FormMessageTempleteInfo templete,
									Map<String, String> messageVars) {
				
				//表单下步执行情况
				List<FormExecuteInfo> executeInfos = formInfo.getFormExecuteInfo();
				if (ListUtil.isNotEmpty(executeInfos)) {
					
					//审核历史
					QueryBaseBatchResult<WorkflowProcessLog> plog = null;
					if (templete.getWithAuditOpinion() == BooleanEnum.YES) {
						if (formInfo.getActInstId() > 0)
							plog = workflowEngineClient.getProcessOpinionByActInstId(String
								.valueOf(formInfo.getActInstId()));
					}
					
					String contentHtml = templete.getContentHtml();
					String contentTxt = templete.getContentTxt();
					
					if (contentHtml == null)
						contentHtml = "";
					
					if (contentTxt == null)
						contentTxt = "";
					
					//替换变量
					if (messageVars != null && !messageVars.isEmpty()) {
						for (String key : messageVars.keySet()) {
							String value = messageVars.get(key);
							if (value == null) {
								value = "";
							}
							contentHtml = contentHtml.replaceAll("\\$\\{" + key + "\\}", value);
							contentTxt = contentTxt.replaceAll("\\$\\{" + key + "\\}", value);
						}
					}
					
					//纯文本消息去掉链接
					contentTxt = contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.审核地址.code()
														+ "\\}", "");
					contentTxt = contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.单据地址.code()
														+ "\\}", "");
					
					//表单地址
					String formUrl = CommonUtil.getRedirectUrl(formInfo.getFormUrl()) + "&formId="
										+ formInfo.getFormId();
					
					for (FormExecuteInfo executeInfo : executeInfos) {
						if (executeInfo.isExecute() || executeInfo.isSetAgent()) //执行过了就不通知了
							continue;
						List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
						if (executeInfo.getUserId() > 0) {
							SimpleUserInfo sendUser = new SimpleUserInfo();
							sendUser.setUserId(executeInfo.getUserId());
							sendUser.setUserAccount(executeInfo.getUserAccount());
							sendUser.setUserName(executeInfo.getUserName());
							sendUser.setMobile(executeInfo.getMobile());
							sendUser.setEmail(executeInfo.getEmail());
							notifyUserList.add(sendUser);
						} else {
							notifyUserList = executeInfo.getCandidateUserList();
						}
						
						if (ListUtil.isNotEmpty(notifyUserList)) {
							
							//发送站内信
							if (templete.getIsSendSiteMessage() == BooleanEnum.IS) {
								
								SimpleUserInfo[] sendUsers = notifyUserList
									.toArray(new SimpleUserInfo[notifyUserList.size()]);
								
								//表单站内地址
								String inSiteFormUrl = "<a href='" + formUrl + "'>查看详情</a>";
								
								//替换单据地址
								String messageHtml = contentHtml.replaceAll(
									"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}",
									inSiteFormUrl);
								
								//站内审核地址
								String inSiteUrl = "<a href='" + executeInfo.getRedirectTaskUrl()
													+ "'>点击处理</a>";
								
								//替换审核地址
								String messageContent = messageHtml.replaceAll(
									"\\$\\{" + FormMessageVarEnum.审核地址.code() + "\\}", inSiteUrl);
								
								if (StringUtil.isNotBlank(messageContent)) {
									messageContent += buildAuditOpinionTable(plog, false); //附加审核历史
									MessageOrder order = MessageOrder.newSystemMessageOrder();
									order.setMessageSubject(templete.getSubject());
									order.setMessageTitle(templete.getSubject());
									order.setMessageContent(messageContent);
									order.setSendUsers(sendUsers);
									siteMessageService.addMessageInfo(order);
								}
							}
							
							for (SimpleUserInfo userInfo : notifyUserList) {
								
								//发送邮件
								if (templete.getIsSendMail() == BooleanEnum.IS
									&& StringUtil.isEmail(userInfo.getEmail())) {
									
									String accessToken = getAccessToken(userInfo);
									
									//站外审核地址
									String outSiteUrl = "<a href=\"" + getFaceWebUrl()
														+ executeInfo.getRedirectTaskUrl()
														+ "&accessToken=" + accessToken
														+ "\" target=\"_blank\">点击处理</a>";
									
									//站外表单地址
									String outSiteFormUrl = "<a href=\"" + getFaceWebUrl()
															+ formUrl + "&accessToken="
															+ accessToken
															+ "\" target=\"_blank\">查看详情 </a>";
									
									//替换变量
									String mailHtml = contentHtml.replaceAll(
										"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}",
										outSiteFormUrl);
									
									String mailContent = mailHtml.replaceAll(
										"\\$\\{" + FormMessageVarEnum.审核地址.code() + "\\}",
										outSiteUrl);
									
									//替换收件人
									mailContent = mailContent.replaceAll(
										"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
										userInfo.getUserName());
									
									if (StringUtil.isNotBlank(mailContent)) {
										mailContent += buildAuditOpinionTable(plog, true); //附加审核历史
										//邮件地址
										List<String> mailAddress = Lists.newArrayList(userInfo
											.getEmail());
										SendMailOrder mailOrder = new SendMailOrder();
										mailOrder.setSendTo(mailAddress);
										mailOrder.setSubject(templete.getSubject());
										mailOrder.setContent(mailContent);
										mailService.sendHtmlEmail(mailOrder);
									}
								}
								if (templete.getIsSendSms() == BooleanEnum.IS
									&& StringUtil.isMobile(userInfo.getMobile())
									&& StringUtil.isNotBlank(contentTxt)) {
									
									//替换收件人
									contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.收件人.code()
															+ "\\}", userInfo.getUserName());
									contentTxt = StringUtil.filterHtml(contentTxt);
									//发送短信
									smsService.sendSMS(userInfo.getMobile(), contentTxt, null);
								}
							}
						}
						
					}
				}
			}
		};
	}
	
	@Override
	public FormNotifyAssginService getNotifyAssignService() {
		return new FormNotifyAssginService() {
			
			@Override
			public void notifyAssign(FormInfo formInfo, FormRelatedUserInfo assignMan,
										FormMessageTempleteInfo templete,
										Map<String, String> messageVars) {
				//表单下步执行情况
				List<FormExecuteInfo> executeInfos = formInfo.getFormExecuteInfo();
				
				//交办的任务ID
				FormExecuteInfo executeInfo = null;
				for (FormExecuteInfo eInfo : executeInfos) {
					if (StringUtil.equals(String.valueOf(assignMan.getTaskId()), eInfo.getTaskId())) {
						executeInfo = eInfo;
						break;
					}
				}
				
				if (executeInfo != null) {
					//审核历史
					QueryBaseBatchResult<WorkflowProcessLog> plog = null;
					if (templete.getWithAuditOpinion() == BooleanEnum.YES) {
						if (formInfo.getActInstId() > 0)
							plog = workflowEngineClient.getProcessOpinionByActInstId(String
								.valueOf(formInfo.getActInstId()));
					}
					
					String contentHtml = templete.getContentHtml();
					String contentTxt = templete.getContentTxt();
					
					if (contentHtml == null)
						contentHtml = "";
					
					if (contentTxt == null)
						contentTxt = "";
					
					//替换变量
					if (messageVars != null && !messageVars.isEmpty()) {
						for (String key : messageVars.keySet()) {
							String value = messageVars.get(key);
							if (value == null) {
								value = "";
							}
							contentHtml = contentHtml.replaceAll("\\$\\{" + key + "\\}", value);
							contentTxt = contentTxt.replaceAll("\\$\\{" + key + "\\}", value);
						}
					}
					
					//纯文本消息去掉链接
					contentTxt = contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.审核地址.code()
														+ "\\}", "");
					contentTxt = contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.单据地址.code()
														+ "\\}", "");
					
					//表单地址
					String formUrl = CommonUtil.getRedirectUrl(formInfo.getFormUrl()) + "&formId="
										+ formInfo.getFormId();
					
					//交办人
					SimpleUserInfo userInfo = assignMan.toSimpleUserInfo();
					
					if (userInfo != null) {
						
						//发送站内信
						if (templete.getIsSendSiteMessage() == BooleanEnum.IS) {
							
							SimpleUserInfo[] sendUsers = new SimpleUserInfo[] { userInfo };
							
							//表单站内地址
							String inSiteFormUrl = "<a href='" + formUrl + "'>查看详情</a>";
							
							//替换单据地址
							String messageHtml = contentHtml.replaceAll(
								"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}", inSiteFormUrl);
							
							//站内审核地址
							String inSiteUrl = "<a href='" + executeInfo.getRedirectTaskUrl()
												+ "'>点击处理</a>";
							
							//替换审核地址
							String messageContent = messageHtml.replaceAll(
								"\\$\\{" + FormMessageVarEnum.审核地址.code() + "\\}", inSiteUrl);
							
							if (StringUtil.isNotBlank(messageContent)) {
								messageContent += buildAuditOpinionTable(plog, false); //附加审核历史
								MessageOrder order = MessageOrder.newSystemMessageOrder();
								order.setMessageSubject(templete.getSubject() + " [ 转办 ]");
								order.setMessageTitle(templete.getSubject() + " [ 转办 ]");
								order.setMessageContent(messageContent);
								order.setSendUsers(sendUsers);
								siteMessageService.addMessageInfo(order);
							}
						}
						
						//发送邮件
						if (templete.getIsSendMail() == BooleanEnum.IS
							&& StringUtil.isEmail(userInfo.getEmail())) {
							
							String accessToken = getAccessToken(userInfo);
							
							//站外审核地址
							String outSiteUrl = "<a href=\"" + getFaceWebUrl()
												+ executeInfo.getRedirectTaskUrl()
												+ "&accessToken=" + accessToken
												+ "\" target=\"_blank\">点击处理</a>";
							
							//站外表单地址
							String outSiteFormUrl = "<a href=\"" + getFaceWebUrl() + formUrl
													+ "&accessToken=" + accessToken
													+ "\" target=\"_blank\">查看详情 </a>";
							
							//替换变量
							String mailHtml = contentHtml.replaceAll(
								"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}", outSiteFormUrl);
							
							String mailContent = mailHtml.replaceAll(
								"\\$\\{" + FormMessageVarEnum.审核地址.code() + "\\}", outSiteUrl);
							
							//替换收件人
							mailContent = mailContent.replaceAll(
								"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
								userInfo.getUserName());
							
							if (StringUtil.isNotBlank(mailContent)) {
								mailContent += buildAuditOpinionTable(plog, true); //附加审核历史
								//邮件地址
								List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
								SendMailOrder mailOrder = new SendMailOrder();
								mailOrder.setSendTo(mailAddress);
								mailOrder.setSubject(templete.getSubject() + " [ 转办 ]");
								mailOrder.setContent(mailContent);
								mailService.sendHtmlEmail(mailOrder);
							}
						}
						if (templete.getIsSendSms() == BooleanEnum.IS
							&& StringUtil.isMobile(userInfo.getMobile())
							&& StringUtil.isNotBlank(contentTxt)) {
							
							//替换收件人
							contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
								userInfo.getUserName());
							contentTxt = StringUtil.filterHtml(contentTxt);
							//发送短信
							smsService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
				}
			}
		};
	}
	
	@Override
	public FormNotifyResultService getNotifyResultService() {
		return new FormNotifyResultService() {
			
			@Override
			public void notifyResult(FormInfo formInfo, FormMessageTempleteInfo templete,
										Map<String, String> messageVars,
										List<SimpleUserInfo> notifyUserList) {
				
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					//审核历史
					QueryBaseBatchResult<WorkflowProcessLog> plog = null;
					if (templete.getWithAuditOpinion() == BooleanEnum.YES) {
						if (formInfo.getActInstId() > 0)
							plog = workflowEngineClient.getProcessOpinionByActInstId(String
								.valueOf(formInfo.getActInstId()));
					}
					
					String contentHtml = templete.getContentHtml();
					String contentTxt = templete.getContentTxt();
					
					if (contentHtml == null)
						contentHtml = "";
					
					if (contentTxt == null)
						contentTxt = "";
					
					//替换变量
					if (messageVars != null && !messageVars.isEmpty()) {
						for (String key : messageVars.keySet()) {
							contentHtml = contentHtml.replaceAll("\\$\\{" + key + "\\}",
								messageVars.get(key));
							contentTxt = contentTxt.replaceAll("\\$\\{" + key + "\\}",
								messageVars.get(key));
						}
					}
					
					//纯文本消息去掉连接 
					contentTxt = contentTxt.replaceAll("\\$\\{" + FormMessageVarEnum.单据地址.code()
														+ "\\}", "");
					
					//表单地址
					String formUrl = CommonUtil.getRedirectUrl(formInfo.getFormUrl()) + "&formId="
										+ formInfo.getFormId();
					
					if (templete.getIsSendSiteMessage() == BooleanEnum.IS) {
						
						SimpleUserInfo[] sendUsers = notifyUserList
							.toArray(new SimpleUserInfo[notifyUserList.size()]);
						
						//表单站内地址
						String inSiteFormUrl = "<a href='" + formUrl + "'>查看详情</a>";
						
						//替换表单地址
						String messageContent = contentHtml.replaceAll(
							"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}", inSiteFormUrl);
						
						if (StringUtil.isNotBlank(messageContent)) {
							messageContent += buildAuditOpinionTable(plog, false); //附带审核历史
							//发送站内信
							MessageOrder order = MessageOrder.newSystemMessageOrder();
							order.setMessageSubject(templete.getSubject());
							order.setMessageTitle(templete.getSubject());
							order.setMessageContent(messageContent);
							order.setSendUsers(sendUsers);
							siteMessageService.addMessageInfo(order);
						}
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						String accessToken = getAccessToken(userInfo);
						
						//发送邮件
						if (templete.getIsSendMail() == BooleanEnum.IS
							&& StringUtil.isEmail(userInfo.getEmail())) {
							
							//站外表单地址
							String outSiteFormUrl = "<a href=\"" + getFaceWebUrl() + formUrl
													+ "&accessToken=" + accessToken
													+ "\" target=\"_blank\">查看详情 </a>";
							
							//替换单据地址
							String mailContent = contentHtml.replaceAll(
								"\\$\\{" + FormMessageVarEnum.单据地址.code() + "\\}", outSiteFormUrl);
							
							//替换收件人
							mailContent = mailContent.replaceAll(
								"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
								userInfo.getUserName());
							
							if (StringUtil.isNotBlank(mailContent)) {
								mailContent += buildAuditOpinionTable(plog, true); //附带审核历史
								SendMailOrder mailOrder = new SendMailOrder();
								List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
								mailOrder.setSendTo(mailAddress);
								mailOrder.setSubject(templete.getSubject());
								mailOrder.setContent(mailContent);
								mailService.sendHtmlEmail(mailOrder);
							}
						}
						
						//发送短信
						if (templete.getIsSendSms() == BooleanEnum.IS
							&& StringUtil.isMobile(userInfo.getMobile())
							&& StringUtil.isNotBlank(contentTxt)) {
							contentTxt = contentTxt.replaceAll(
								"\\$\\{" + FormMessageVarEnum.收件人.code() + "\\}",
								userInfo.getUserName());
							smsService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
				}
			}
		};
	}
	
}
