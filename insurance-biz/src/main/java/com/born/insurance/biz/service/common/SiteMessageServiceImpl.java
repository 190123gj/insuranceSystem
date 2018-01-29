package com.born.insurance.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.integration.bpm.user.SysUser;
import com.born.insurance.integration.bpm.user.UserDetailsServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.born.insurance.biz.job.AsynchronousTaskJob;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.dataobject.MessageInfoDO;
import com.born.insurance.dal.dataobject.MessageReceivedDO;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.integration.common.PropertyConfigService;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormMessageVarEnum;
import com.born.insurance.ws.enums.MessageReceivedStatusEnum;
import com.born.insurance.ws.enums.MessageStatusEnum;
import com.born.insurance.ws.enums.MessageTypeEnum;
import com.born.insurance.ws.enums.MessageViewTypeEnum;
import com.born.insurance.ws.enums.NotificationTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.MessageInfo;
import com.born.insurance.ws.info.common.MessageReceivedInfo;
import com.born.insurance.ws.order.common.MessageOrder;
import com.born.insurance.ws.order.common.MyMessageOrder;
import com.born.insurance.ws.order.common.QueryMessageOrder;
import com.born.insurance.ws.order.common.QueryReceviedMessageOrder;
import com.born.insurance.ws.order.common.SendMessageOrder;
import com.born.insurance.ws.order.common.SimpleUserInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.MessageResult;
import com.google.common.collect.Lists;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("siteMessageService")
public class SiteMessageServiceImpl extends BaseAutowiredDomainService implements
																		SiteMessageService {
	
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	
	@Autowired
	PropertyConfigService propertyConfigService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	
	@Override
	public InsuranceBaseResult addMessageInfo(MessageOrder order) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			order.check();
		} catch (IllegalArgumentException e) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return baseResult;
		}
		
		try {
			MessageInfoDO infoDO = new MessageInfoDO();
			convert(order, infoDO);
			infoDO.setRawAddTime(new Date());
			infoDO.setRawUpdateTime(new Date());
			
			MessageInfo messageInfo = new MessageInfo();
			convertInfo(infoDO, messageInfo);
			Set<SimpleUserInfo> sendUsers = new HashSet<SimpleUserInfo>();
			//根据账号查询收件人的相关信息
			if (order.getSendUserAcounts() != null && order.getSendUserAcounts().length > 0) {
				
				for (String user : order.getSendUserAcounts()) {
					if (StringUtil.isBlank(user))
						continue;
					UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
						propertyConfigService.getBmpServiceUserDetailsService());
					SysUser sendUser = null;
					try {
						sendUser = serviceProxy.loadUserByUsername(user);
						if (sendUser != null) {
							SimpleUserInfo userInfo = new SimpleUserInfo();
							userInfo.setUserId(sendUser.getUserId());
							userInfo.setUserName(sendUser.getFullname());
							userInfo.setUserAccount(sendUser.getAccount());
							sendUsers.add(userInfo);
						}
					} catch (Exception e) {
						logger.error("查询用户信息出错 {} ：{}", user, e);
						throw ExceptionFactory.newFcsException(
							InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR, "查询用户信息出错");
					}
					
				}
			}
			//根据角色查询收件人的相关信息
			if (order.getMessageSenderRole() != null) {
				String sysParamValue = sysParameterService.getSysParameterValue(order
					.getMessageSenderRole());//查找系统参数
				try {
					List<SysUser> listUser = bpmUserQueryService.findUserByRoleAlias(sysParamValue);//查找角色
					
					if (listUser != null) {
						for (SysUser sysUser : listUser) {
							try {
								if (sysUser != null) {
									SimpleUserInfo user = new SimpleUserInfo();
									user.setUserAccount(sysUser.getAccount());
									user.setUserId(sysUser.getUserId());
									user.setUserName(sysUser.getFullname());
									sendUsers.add(user);
								}
							} catch (Exception e) {
								logger.error("查询用户信息出错 {} ：{}", sysUser, e);
								throw ExceptionFactory.newFcsException(
									InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR, "查询用户信息出错");
							}
						}
					}
					
				} catch (Exception e) {
					logger.error("系统参数出错 {} ：{}", e);
					throw ExceptionFactory.newFcsException(
						InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR, "系统参数不存在");
				}
			}
			if (order.getSendUsers() != null && order.getSendUsers().length > 0) {
				for (SimpleUserInfo simpleUserInfo : order.getSendUsers()) {
					sendUsers.add(simpleUserInfo);
				}
			}
			if (sendUsers.size() > 0) {
				order.setSendUsers(sendUsers.toArray(new SimpleUserInfo[sendUsers.size()]));
			}
			
			//设置通知对象(收件人)
			if (order.getSendUsers() != null && order.getSendUsers().length > 0) {
				String notifyObject = "";
				for (SimpleUserInfo user : order.getSendUsers()) {
					notifyObject += user.getUserName() + ",";
				}
				notifyObject = notifyObject.substring(0, notifyObject.length() - 1);
				messageInfo.setNotificationObject(notifyObject);
				infoDO.setNotificationObject(notifyObject);
				order.setNotificationObject(notifyObject);
			}
			
			if (order.getIsSendMessage() == BooleanEnum.YES) {
				infoDO.setMessageSendDate(new Date());
				infoDO.setMessageStatus(MessageStatusEnum.SENDING.code());
				long messageId = messageInfoDAO.insert(infoDO);
				messageInfo.setMessageId(messageId);
				sendBatchMessage(order, messageInfo);
			} else {
				infoDO.setMessageStatus(MessageStatusEnum.DRAFT.code());
				messageInfoDAO.insert(infoDO);
			}
			baseResult.setSuccess(true);
		} catch (Exception e) {
			logger.error("发送站内信失败：{}", e);
		}
		return baseResult;
	}
	
	private void sendBatchMessage(MessageOrder order, MessageInfo messageInfo) {
		if (order.getNotificationType() == NotificationTypeEnum.APPOINT) {
			if (order.getSendUsers() != null && order.getSendUsers().length > 0) {
				for (SimpleUserInfo sendUser : order.getSendUsers()) {
					if (sendUser != null) {
						SendMessageOrder sendOrder = new SendMessageOrder();
						BeanCopier.staticCopy(order, sendOrder);
						sendOrder.setSendUser(sendUser);
						sendOrder.setMessageId(messageInfo.getMessageId());
						sendOrder.setMessageSenderAccount(messageInfo.getMessageSenderAccount());
						sendOrder.setMessageSenderName(messageInfo.getMessageSenderName());
						sendOrder.setMessageSenderId(messageInfo.getMessageSenderId());
						InsuranceBaseResult baseResult = sendUserMessageInfo(sendOrder);
						if (!baseResult.isSuccess()) {
							logger.error("发送消息时，该用户sendUser = {}发送消息失败！", sendUser);
						}
					}
				}
			}
		} else {
			if (order.getMessagePlanSendDate() == null) {
				asynchronousTaskJob.addAsynchronousService(this,
					new Object[] { messageInfo, messageInfo.getNotificationType() });
			}
		}
	}
	
	@Override
	public InsuranceBaseResult deleteMessageInfo(long messageId) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			Assert.isTrue(messageId > 0, "消息id不能为空");
		} catch (IllegalArgumentException e) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return baseResult;
		}
		MessageInfoDO infoDO = messageInfoDAO.findById(messageId);
		if (MessageStatusEnum.DRAFT.code().equals(infoDO.getMessageStatus())) {
			messageInfoDAO.deleteById(messageId);
			baseResult.setSuccess(true);
		} else {
			baseResult.setSuccess(false);
			baseResult.setMessage("不能删除该消息");
		}
		return baseResult;
	}
	
	@Override
	public MessageResult readMessageInfo(MyMessageOrder myMessageOrder) {
		MessageResult baseResult = new MessageResult();
		try {
			myMessageOrder.check();
		} catch (IllegalArgumentException e) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return baseResult;
		}
		MessageReceivedDO receivedDO = messageReceivedDAO.findById(myMessageOrder.getReceivedId());
		if (receivedDO == null) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			baseResult.setSuccess(false);
			baseResult.setMessage("消息内容不存在");
			return baseResult;
		} else if (receivedDO.getMessageReceivedId() == myMessageOrder.getUserId()) {
			receivedDO.setMessageReceivedStatus(myMessageOrder.getMessageReceivedStatus());
			messageReceivedDAO.update(receivedDO);
			MessageReceivedInfo messageReceivedInfo = new MessageReceivedInfo();
			convertReceivedInfo(receivedDO, messageReceivedInfo);
			baseResult.setMessageReceivedInfo(messageReceivedInfo);
			baseResult.setSuccess(true);
		} else {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.NO_ACCESS);
			baseResult.setSuccess(false);
			baseResult.setMessage("无权限阅读该消息");
			return baseResult;
		}
		return baseResult;
	}
	
	@Override
	public InsuranceBaseResult updateReceivedMessageStatus(long userId,
															MessageReceivedStatusEnum status) {
		InsuranceBaseResult result = createResult();
		try {
			if (userId <= 0 || status == null) {
				result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
				result.setSuccess(false);
				result.setMessage("请求参数不完整");
			}
			
			messageReceivedDAO.updateStatus(status.code(), userId);
			result.setSuccess(true);
			result.setMessage("修改消息状态成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改消息状态出错");
			logger.error("修改消息状态出错:{}", e);
		}
		return result;
	}
	
	@Override
	public MessageResult deleteReceivedMessageInfo(MyMessageOrder myMessageOrder) {
		MessageResult baseResult = new MessageResult();
		try {
			myMessageOrder.check();
		} catch (IllegalArgumentException e) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return baseResult;
		}
		
		MessageReceivedDO receivedDO = messageReceivedDAO.findById(myMessageOrder.getReceivedId());
		if (receivedDO == null) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			baseResult.setSuccess(false);
			baseResult.setMessage("消息内容不存在");
			return baseResult;
		} else if (receivedDO.getMessageReceivedId() == myMessageOrder.getUserId()) {
			messageReceivedDAO.deleteById(myMessageOrder.getReceivedId());
			baseResult.setSuccess(true);
		} else {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.NO_ACCESS);
			baseResult.setSuccess(false);
			baseResult.setMessage("无权限阅读该消息");
			return baseResult;
		}
		return baseResult;
	}
	
	@Override
	public InsuranceBaseResult sendUserMessageInfo(final SendMessageOrder order) {
		return commonProcess(order, "发送站内信", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				SimpleUserInfo userInfo = order.getSendUser();
				if (userInfo == null && StringUtil.isNotBlank(order.getUserAccount())) {
					
					UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
						propertyConfigService.getBmpServiceUserDetailsService());
					SysUser sendUser = null;
					try {
						sendUser = serviceProxy.loadUserByUsername(order.getUserAccount());
						if (sendUser == null) {
							ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
								"用户不存在");
						}
					} catch (Exception e) {
						logger.error("查询用户信息出错 {} ：{}", order.getUserAccount(), e);
						throw ExceptionFactory.newFcsException(
							InsuranceResultEnum.CALL_REMOTE_SERVICE_ERROR, "查询用户信息出错");
					}
					
					userInfo = new SimpleUserInfo();
					userInfo.setUserId(sendUser.getUserId());
					userInfo.setUserName(sendUser.getFullname());
					userInfo.setUserAccount(sendUser.getAccount());
					order.setSendUser(userInfo);
				}
				
				//order.setMessageId(-1);
				MessageInfoDO infoDO = new MessageInfoDO();
				convert(order, infoDO);
				MessageInfo messageInfo = new MessageInfo();
				convertInfo(infoDO, messageInfo);
				
				String messageContent = messageInfo.getMessageContent();
				boolean hasSenderName = messageContent.indexOf("${" + FormMessageVarEnum.收件人 + "}") > 0;
				if (order.isWithSenderName() || hasSenderName) {
					if (hasSenderName) {
						messageContent = messageContent.replaceAll("\\$\\{"
																	+ FormMessageVarEnum.收件人
																	+ "\\}", userInfo.getUserName());
					} else {
						messageContent = "尊敬的" + userInfo.getUserName() + "：" + messageContent;
					}
					messageInfo.setMessageContent(messageContent);
				}
				InsuranceBaseResult sendResult = sendMessage(messageInfo, userInfo);
				if (!sendResult.isSuccess()) {
					ExceptionFactory.newFcsException(sendResult.getInsuranceResultEnum(),
						sendResult.getMessage());
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceBaseResult updateMessageInfo(MessageOrder order) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			order.check();
			Assert.isTrue(order.getMessageId() > 0, "messageId 必须大于0");
		} catch (IllegalArgumentException e) {
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseResult.setSuccess(false);
			baseResult.setMessage(e.getMessage());
			return baseResult;
		}
		MessageInfoDO infoDO = new MessageInfoDO();
		convert(order, infoDO);
		MessageInfo messageInfo = new MessageInfo();
		convertInfo(infoDO, messageInfo);
		if (order.getIsSendMessage() == BooleanEnum.YES) {
			infoDO.setMessageSendDate(new Date());
			infoDO.setMessageStatus(MessageStatusEnum.SENDING.code());
			messageInfoDAO.update(infoDO);
			sendBatchMessage(order, messageInfo);
		} else {
			infoDO.setMessageStatus(MessageStatusEnum.DRAFT.code());
			messageInfoDAO.update(infoDO);
		}
		baseResult.setSuccess(true);
		return baseResult;
	}
	
	private void convertInfo(MessageInfoDO infoDO, MessageInfo messageInfo) {
		BeanCopier.staticCopy(infoDO, messageInfo);
		
		messageInfo.setMessageType(MessageTypeEnum.getByCode(infoDO.getMessageType()));
		messageInfo.setMessageStatus(MessageStatusEnum.getByCode(infoDO.getMessageStatus()));
		messageInfo
			.setNotificationType(NotificationTypeEnum.getByCode(infoDO.getNotificationType()));
		messageInfo.setViewType(MessageViewTypeEnum.getByCode(infoDO.getViewType()));
	}
	
	private void convertReceivedInfo(MessageReceivedDO infoDO, MessageReceivedInfo messageInfo) {
		BeanCopier.staticCopy(infoDO, messageInfo);
		messageInfo.setMessageType(MessageTypeEnum.getByCode(infoDO.getMessageType()));
		messageInfo.setMessageReceivedStatus(MessageReceivedStatusEnum.getByCode(infoDO
			.getMessageReceivedStatus()));
		messageInfo.setViewType(MessageViewTypeEnum.getByCode(infoDO.getViewType()));
	}
	
	private void convert(MessageOrder order, MessageInfoDO infoDO) {
		BeanCopier.staticCopy(order, infoDO);
		infoDO.setMessageType(order.getMessageType().code());
		infoDO.setNotificationType(order.getNotificationType().code());
		infoDO.setViewType(order.getViewType().code());
		
	}
	
	private InsuranceBaseResult sendMessage(MessageInfo messageInfo, SimpleUserInfo sendUser) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			Date now = getSysdate();
			
			MessageReceivedDO messageReceivedDO = new MessageReceivedDO();
			BeanCopier.staticCopy(messageInfo, messageReceivedDO);
			messageReceivedDO.setMessageReceivedDate(new Date());
			messageReceivedDO.setMessageReceivedId(sendUser.getUserId());
			messageReceivedDO.setMessageReceivedName(sendUser.getUserName());
			messageReceivedDO.setMessageReceivedAccount(sendUser.getUserAccount());
			messageReceivedDO.setMessageReceivedStatus(MessageReceivedStatusEnum.UNREAD.code());
			messageReceivedDO.setViewType(messageInfo.getViewType().code());
			messageReceivedDO.setMessageType(messageInfo.getMessageType().code());
			messageReceivedDO.setRawAddTime(now);
			messageReceivedDAO.insert(messageReceivedDO);
			
			baseResult.setSuccess(true);
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
		} catch (Exception e) {
			logger.error("发送站内消息失败" + e.getMessage(), e);
			baseResult.setSuccess(false);
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			baseResult.setMessage("发送站内消息失败");
		}
		return baseResult;
	}
	
	@Override
	public QueryBaseBatchResult<MessageReceivedInfo> loadUnReadMyMessage(long userId) {
		QueryBaseBatchResult<MessageReceivedInfo> baseResult = new QueryBaseBatchResult<MessageReceivedInfo>();
		try {
			QueryReceviedMessageOrder queryMessageOrder = new QueryReceviedMessageOrder();
			queryMessageOrder.setMessageReceivedId(userId);
			List<MessageReceivedStatusEnum> enums = Lists.newArrayList();
			enums.add(MessageReceivedStatusEnum.UNREAD);
			queryMessageOrder.setStatusList(enums);
			baseResult = findReceviedMessage(queryMessageOrder);
		} catch (Exception e) {
			logger.error("查询未读站内消息失败" + e.getMessage(), e);
			baseResult.setSuccess(false);
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return baseResult;
	}
	
	@Override
	public long loadUnReadMyMessageCount(long userId) {
		try {
			List<MessageReceivedStatusEnum> statusList = Lists.newArrayList();
			statusList.add(MessageReceivedStatusEnum.UNREAD);
			MessageReceivedDO queryDO = new MessageReceivedDO();
			queryDO.setMessageReceivedId(userId);
			return messageReceivedDAO.findCountByCondition(queryDO, null, null, statusList);
		} catch (Exception e) {
			logger.error("查询未读站内消息数量失败" + e.getMessage(), e);
		}
		return 0;
	}
	
	@Override
	public QueryBaseBatchResult<MessageReceivedInfo> findReceviedMessage(	QueryReceviedMessageOrder queryMessageOrder) {
		QueryBaseBatchResult<MessageReceivedInfo> batchResult = new QueryBaseBatchResult<MessageReceivedInfo>();
		try {
			MessageReceivedDO queryDO = new MessageReceivedDO();
			BeanCopier.staticCopy(queryMessageOrder, queryDO);
			if (queryMessageOrder.getMessageType() != null) {
				queryDO.setMessageType(queryMessageOrder.getMessageType().getCode());
			}
			if (queryMessageOrder.getViewType() != null) {
				queryDO.setViewType(queryMessageOrder.getViewType().code());
			}
			
			List<String> statusList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(queryMessageOrder.getStatusList())) {
				for (MessageReceivedStatusEnum statusEnum : queryMessageOrder.getStatusList()) {
					statusList.add(statusEnum.code());
				}
			}
			long totalCount = messageReceivedDAO.findCountByCondition(queryDO,
				queryMessageOrder.getBeginReceivedDate(), queryMessageOrder.getEndReceivedDate(),
				statusList);
			PageComponent component = new PageComponent(queryMessageOrder, totalCount);
			List<MessageReceivedDO> list = messageReceivedDAO.findByCondition(queryDO,
				queryMessageOrder.getBeginReceivedDate(), queryMessageOrder.getEndReceivedDate(),
				queryMessageOrder.getSortCol(), queryMessageOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize(), statusList);
			List<MessageReceivedInfo> pageList = new ArrayList<MessageReceivedInfo>(list.size());
			for (MessageReceivedDO messageReceivedDO : list) {
				MessageReceivedInfo info = new MessageReceivedInfo();
				convertReceivedInfo(messageReceivedDO, info);
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
			return batchResult;
		} catch (Exception e) {
			logger.error("查询站内消息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<MessageInfo> findMessage(QueryMessageOrder queryMessageOrder) {
		QueryBaseBatchResult<MessageInfo> batchResult = new QueryBaseBatchResult<MessageInfo>();
		try {
			MessageInfoDO queryDO = new MessageInfoDO();
			BeanCopier.staticCopy(queryMessageOrder, queryDO);
			if (queryMessageOrder.getMessageType() != null) {
				queryDO.setMessageType(queryMessageOrder.getMessageType().getCode());
			}
			if (queryMessageOrder.getViewType() != null) {
				queryDO.setViewType(queryMessageOrder.getViewType().code());
			}
			
			List<String> statusList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(queryMessageOrder.getStatusList())) {
				for (MessageStatusEnum statusEnum : queryMessageOrder.getStatusList()) {
					statusList.add(statusEnum.code());
				}
			}
			long totalCount = messageInfoDAO.findCountByCondition(queryDO,
				queryMessageOrder.getBeginMessageSendDate(),
				queryMessageOrder.getEndMessageSendDate(),
				queryMessageOrder.getBeginMessageCreateDate(),
				queryMessageOrder.getEndMessageCreateDate(), statusList);
			PageComponent component = new PageComponent(queryMessageOrder, totalCount);
			List<MessageInfoDO> list = messageInfoDAO.findByCondition(queryDO,
				queryMessageOrder.getBeginMessageSendDate(),
				queryMessageOrder.getEndMessageSendDate(),
				queryMessageOrder.getBeginMessageCreateDate(),
				queryMessageOrder.getEndMessageCreateDate(), queryMessageOrder.getSortCol(),
				queryMessageOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize(), statusList);
			List<MessageInfo> pageList = new ArrayList<MessageInfo>(list.size());
			for (MessageInfoDO messageInfoDO : list) {
				MessageInfo info = new MessageInfo();
				convertInfo(messageInfoDO, info);
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
			return batchResult;
		} catch (Exception e) {
			logger.error("查询站内消息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public void execute(Object[] objects) {
	}
	
	@Override
	public MessageResult readMessageAndUpStatus(MyMessageOrder myMessageOrder) {
		MessageResult result = new MessageResult();
		MessageReceivedDO messageReceivedDO = messageReceivedDAO.findById(myMessageOrder
			.getReceivedId());
		if (messageReceivedDO == null) {
			result.setSuccess(false);
			result.setMessage("未查询到对应的消息");
			return result;
		}
		readMessageInfo(myMessageOrder);
		MessageReceivedInfo messageReceivedInfo = new MessageReceivedInfo();
		convertReceivedInfo(messageReceivedDO, messageReceivedInfo);
		result.setMessageReceivedInfo(messageReceivedInfo);
		result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
	}
	
	//	@Override
	//	public InsuranceBaseResult addAppSendMessage(MessageOrder order) {
	//		InsuranceBaseResult result = new InsuranceBaseResult();
	//		order.setMessageType(MessageTypeEnum.APP);
	//		order.setViewType(MessageViewTypeEnum.OPENAPP);
	//		order.setNotificationType(NotificationTypeEnum.APPUSER);
	//		MessageInfoDO infoDO = new MessageInfoDO();
	//		convert(order, infoDO);
	//		infoDO.setRawAddTime(new Date());
	//		infoDO.setRawUpdateTime(new Date());
	//		infoDO.setMessageStatus(MessageStatusEnum.FAIL.code());
	//		long messageid = messageInfoDAO.insert(infoDO);
	//		if (messageid > 0) {
	//			Date date = checkStartSendDate(infoDO.getMessagePlanSendDate());
	//			youmengOrder sendOrder = new youmengOrder();
	//			sendOrder.setTitle(infoDO.getMessageTitle());
	//			sendOrder.setText(infoDO.getMessageContent());
	//			sendOrder.setStart_time(DateUtil.simpleFormat(date));
	//			sendOrder.setExpire_time(DateUtil.simpleFormat(infoDO.getMessageSendDate()));
	//			YoumengSendResult sendResult = youmenMessageSendService.send(sendOrder);
	//			if (sendResult.isSuccess()) {
	//				infoDO.setLinkUrl(sendResult.getTask_id());
	//				infoDO.setMessageStatus(MessageStatusEnum.SUCESS.code());
	//				messageInfoDAO.update(infoDO);
	//				result.setSuccess(true);
	//				result.setMessage("消息推送成功");
	//			} else {
	//				result.setSuccess(false);
	//				result.setMessage(sendResult.getMessage());
	//			}
	//		} else {
	//			result.setSuccess(false);
	//			result.setMessage("本地存储失败");
	//		}
	//		return result;
	//	}
	
	//	@Override
	//	public InsuranceBaseResult cancelMessageSend(long messageId) {
	//		InsuranceBaseResult result = new InsuranceBaseResult();
	//		MessageInfoDO receivedDO = messageInfoDAO.findById(messageId);
	//		if (receivedDO == null) {
	//			result.setSuccess(false);
	//			result.setMessage("信息查询失败");
	//		} else {
	//			String taskIds = receivedDO.getLinkUrl();
	//			if (StringUtil.isNotBlank(taskIds)) {
	//				String[] taskIdArray = StringUtil.split(taskIds, "&");
	//				if (taskIdArray.length == 2) {
	//					YoumengSendResult cancelAndroid = youmenMessageSendService.cancel(
	//						taskIdArray[0], "android");
	//					logger.info("取消android消息推送结果：messageId={};cancelAndroid={}", messageId,
	//						cancelAndroid);
	//					YoumengSendResult cancelIos = youmenMessageSendService.cancel(taskIdArray[1],
	//						"ios");
	//					logger.info("取消ios消息推送结果：messageId={};cancelIos={}", messageId, cancelIos);
	//					boolean android = FcsResultEnum.EXECUTE_SUCCESS == cancelAndroid
	//						.getFcsResultEnum();
	//					boolean ios = FcsResultEnum.EXECUTE_SUCCESS == cancelIos.getFcsResultEnum();
	//					if (android && ios) {
	//						receivedDO.setMessageStatus(MessageStatusEnum.CANCEL.code());
	//						int upResult = messageInfoDAO.update(receivedDO);
	//						if (upResult == 1) {
	//							logger.info("取消推送，更新本地状态成功：messageId={}" + messageId);
	//							result.setSuccess(true);
	//							result.setMessage("已成功取消推送");
	//						} else {
	//							result.setSuccess(true);
	//							result.setMessage("取消推送失败:更新本地状态失败");
	//						}
	//						
	//					} else {
	//						result.setSuccess(false);
	//						String resultStr = "取消推送失败:错误码 ";
	//						if (!android) {
	//							resultStr += ":android=" + cancelAndroid.getError_code();
	//						}
	//						if (!ios) {
	//							resultStr += ";ios=" + cancelIos.getError_code();
	//						}
	//						result.setMessage(resultStr);
	//					}
	//					
	//				} else {
	//					result.setSuccess(false);
	//					result.setMessage("查询该消息任务id失败");
	//				}
	//			} else {
	//				result.setSuccess(false);
	//				result.setMessage("查询该消息任务id失败");
	//			}
	//		}
	//		return result;
	//	}
	
	//	@Override
	//	public InsuranceBaseResult reSendMessage(long messageId) {
	//		InsuranceBaseResult result = new InsuranceBaseResult();
	//		MessageInfoDO receivedDO = messageInfoDAO.findById(messageId);
	//		Date date = checkStartSendDate(receivedDO.getMessagePlanSendDate());
	//		youmengOrder sendOrder = new youmengOrder();
	//		sendOrder.setTitle(receivedDO.getMessageTitle());
	//		sendOrder.setText(receivedDO.getMessageContent());
	//		sendOrder.setStart_time(DateUtil.simpleFormat(date));
	//		sendOrder.setExpire_time(DateUtil.simpleFormat(receivedDO.getMessageSendDate()));
	//		YoumengSendResult sendResult = youmenMessageSendService.send(sendOrder);
	//		if (sendResult.isSuccess()) {
	//			receivedDO.setLinkUrl(sendResult.getTask_id());
	//			receivedDO.setMessageStatus(MessageStatusEnum.SUCESS.code());
	//			receivedDO.setMessagePlanSendDate(date);
	//			messageInfoDAO.update(receivedDO);
	//			result.setSuccess(true);
	//			result.setMessage("消息重新推送成功");
	//		} else {
	//			result.setSuccess(false);
	//			result.setMessage(sendResult.getMessage());
	//		}
	//		return result;
	//	}
	
	//	@Override
	//	public InsuranceBaseResult deleteSendMessage(long messageId) {
	//		InsuranceBaseResult result = new InsuranceBaseResult();
	//		MessageInfoDO receivedDO = messageInfoDAO.findById(messageId);
	//		String status = receivedDO.getMessageStatus();
	//		if (StringUtil.equals(status, MessageStatusEnum.FAIL.getCode())
	//			|| StringUtil.equals(status, MessageStatusEnum.CANCEL.getCode())
	//			|| receivedDO.getMessageSendDate().before(new Date())) {
	//			int resultInt = messageInfoDAO.deleteById(messageId);
	//			if (resultInt == 1) {
	//				result.setSuccess(true);
	//				result.setMessage("删除成功");
	//			} else {
	//				result.setSuccess(true);
	//				result.setMessage("删除失败:数据库异常");
	//			}
	//		} else {
	//			result.setSuccess(false);
	//			result.setMessage("推送中的消息不允许删除");
	//		}
	//		
	//		return result;
	//		
	//	}
	
	//	@Override
	//	public MessageResult viewMessage(long messageId) {
	//		MessageResult result = new MessageResult();
	//		MessageInfoDO receivedDO = messageInfoDAO.findById(messageId);
	//		if (receivedDO != null) {
	//			MessageReceivedInfo messageReceivedInfo = new MessageReceivedInfo();
	//			BeanCopier.staticCopy(receivedDO, messageReceivedInfo);
	//			messageReceivedInfo.setMessageStatus(MessageStatusEnum.getByCode(receivedDO
	//				.getMessageStatus()));
	//			result.setMessageReceivedInfo(messageReceivedInfo);
	//			result.setSuccess(true);
	//		} else {
	//			result.setSuccess(false);
	//			result.setMessage("查询失败");
	//		}
	//		
	//		return result;
	//	}
	
	//	/** 开始发送时间是否大于当前时间 */
	//	private Date checkStartSendDate(Date startDate) {
	//		if (startDate.before(new Date()) || startDate == null) {
	//			startDate = new Date((new Date()).getTime() + 180000);
	//		}
	//		return startDate;
	//		
	//	}
	
}
