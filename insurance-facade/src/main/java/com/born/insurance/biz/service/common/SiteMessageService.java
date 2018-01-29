package com.born.insurance.biz.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.enums.MessageReceivedStatusEnum;
import com.born.insurance.ws.info.common.MessageInfo;
import com.born.insurance.ws.info.common.MessageReceivedInfo;
import com.born.insurance.ws.order.common.MessageOrder;
import com.born.insurance.ws.order.common.MyMessageOrder;
import com.born.insurance.ws.order.common.QueryMessageOrder;
import com.born.insurance.ws.order.common.QueryReceviedMessageOrder;
import com.born.insurance.ws.order.common.SendMessageOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.MessageResult;

@WebService
public interface SiteMessageService extends AsynchronousService {
	/**
	 * 添加消息
	 * @param order
	 * @return
	 */
	InsuranceBaseResult addMessageInfo(MessageOrder order);
	
	//	/**
	//	 * 添加app端推送消息
	//	 * @param MessageOrder
	//	 * @return
	//	 */
	//	InsuranceBaseResult addAppSendMessage(MessageOrder order);
	
	//	/**
	//	 * app取消推送消息
	//	 * @param messageId
	//	 * @return
	//	 */
	//	InsuranceBaseResult cancelMessageSend(long messageId);
	
	//	/**
	//	 * app重新推送消息
	//	 * @param messageId
	//	 * @return
	//	 */
	//	InsuranceBaseResult reSendMessage(long messageId);
	
	//	/**
	//	 * app删除消息
	//	 * @param messageId
	//	 * @return
	//	 */
	//	InsuranceBaseResult deleteSendMessage(long messageId);
	
	//	/**
	//	 * app查看消息
	//	 * @param messageId
	//	 * @return
	//	 */
	//	MessageResult viewMessage(long messageId);
	
	/**
	 * 更新消息
	 * @param order
	 * @return
	 */
	InsuranceBaseResult updateMessageInfo(MessageOrder order);
	
	/**
	 * 发送消息
	 * @param order
	 * @return
	 */
	InsuranceBaseResult sendUserMessageInfo(SendMessageOrder order);
	
	/**
	 * 查询收到的消息
	 * @param queryMessageOrder
	 * @return
	 */
	QueryBaseBatchResult<MessageReceivedInfo> findReceviedMessage(	QueryReceviedMessageOrder queryMessageOrder);
	
	/**
	 * 加载未读的消息
	 * @param userId
	 * @return
	 */
	QueryBaseBatchResult<MessageReceivedInfo> loadUnReadMyMessage(long userId);
	
	/**
	 * 未读消息数
	 * @param userId
	 * @return
	 */
	long loadUnReadMyMessageCount(long userId);
	
	/**
	 * 条件查询
	 * @param queryMessageOrder
	 * @return
	 */
	QueryBaseBatchResult<MessageInfo> findMessage(QueryMessageOrder queryMessageOrder);
	
	/**
	 * 删除接收到的消息
	 * @param myMessageOrder
	 * @return
	 */
	MessageResult deleteReceivedMessageInfo(MyMessageOrder myMessageOrder);
	
	/**
	 * 删除未发送的消息
	 * @param messageId
	 * @return
	 */
	InsuranceBaseResult deleteMessageInfo(long messageId);
	
	/**
	 * 消息修改状态
	 * @param myMessageOrder
	 * @return
	 */
	MessageResult readMessageInfo(MyMessageOrder myMessageOrder);
	
	/**
	 * 修改用户消息状态
	 * @param userId
	 * @param status
	 * @return
	 */
	InsuranceBaseResult updateReceivedMessageStatus(long userId, MessageReceivedStatusEnum status);
	
	/**
	 * 查看消息并修改状态
	 * */
	MessageResult readMessageAndUpStatus(MyMessageOrder myMessageOrder);
}
