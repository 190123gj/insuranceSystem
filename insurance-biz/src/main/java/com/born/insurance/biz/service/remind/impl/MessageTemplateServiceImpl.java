package com.born.insurance.biz.service.remind.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.remind.MessageTemplateService;
import com.born.insurance.dal.daointerface.MessageTemplateDAO;
import com.born.insurance.dal.daointerface.MessageTemplateWayDAO;
import com.born.insurance.dal.dataobject.MessageTemplateDO;
import com.born.insurance.dal.dataobject.MessageTemplateWayDO;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.RemindContactTypeEnum;
import com.born.insurance.ws.enums.RemindTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.MessageTemplateInfo;
import com.born.insurance.ws.info.common.MessageTemplateWayInfo;
import com.born.insurance.ws.order.common.MessageTemplateOrder;
import com.born.insurance.ws.order.common.MessageTemplateQueryOrder;
import com.born.insurance.ws.order.common.MessageTemplateWayOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("messageTemplateService")
public class MessageTemplateServiceImpl extends BaseAutowiredDomainService implements MessageTemplateService {

	private final com.yjf.common.log.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageTemplateDAO messageTemplateDAO;
	
	@Autowired
	private MessageTemplateWayDAO messageTemplateWayDAO;
	
	@Override
	public QueryBaseBatchResult<MessageTemplateInfo> queryMessageTemplateInfoByCondition(
			MessageTemplateQueryOrder messageTemplateQueryOrder) {
		logger.info("开始查询提醒列设置表信息....messageTemplateQueryOrder=[{}]" + messageTemplateQueryOrder.toString());
		QueryBaseBatchResult<MessageTemplateInfo> baseBatchResult =new QueryBaseBatchResult<MessageTemplateInfo>();
		try {
			messageTemplateQueryOrder.check();
			List<MessageTemplateInfo> pageList = new ArrayList<MessageTemplateInfo>((int) messageTemplateQueryOrder.getPageSize());
			MessageTemplateDO messageTemplateDO=new MessageTemplateDO();
			BeanCopier.staticCopy(messageTemplateQueryOrder, messageTemplateDO);
			if(messageTemplateQueryOrder.getNotifyType()!=null){
				messageTemplateDO.setNotifyType(messageTemplateQueryOrder.getNotifyType().code());
			}
			long totalCount=messageTemplateDAO.findByConditionCount(messageTemplateDO,messageTemplateQueryOrder.getStartTime(),messageTemplateQueryOrder.getEndTime());
			PageComponent component = new PageComponent(messageTemplateQueryOrder, totalCount);
			List<MessageTemplateDO> messDo=messageTemplateDAO.findByCondition(messageTemplateDO, messageTemplateQueryOrder.getSortCol(), messageTemplateQueryOrder.getSortOrder(), messageTemplateQueryOrder.getLimitStart(), messageTemplateQueryOrder.getPageSize(),messageTemplateQueryOrder.getStartTime(),messageTemplateQueryOrder.getEndTime());
			for(MessageTemplateDO mtDo:messDo){
				MessageTemplateInfo info= new MessageTemplateInfo();
				BeanCopier.staticCopy(mtDo, info);
				if(StringUtil.isNotEmpty(mtDo.getNotifyType()))
				{
					info.setNotifyType(RemindTypeEnum.getByCode(mtDo.getNotifyType()));
				}
				pageList.add(info);
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(pageList);
			baseBatchResult.setSuccess(true);
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return baseBatchResult;
	}

	
	
	
	@Override
	public MessageTemplateInfo queryMessageTemplateInfoByID(MessageTemplateQueryOrder messageTemplateQueryOrder) {
		logger.info("开始查询指定提醒信息....messageTemplateQueryOrder=[{}]"
				+ messageTemplateQueryOrder.toString());
		MessageTemplateDO messageTemplateDO = new MessageTemplateDO();
		BeanCopier.staticCopy(messageTemplateQueryOrder, messageTemplateDO);
		MessageTemplateDO meeageDo=messageTemplateDAO.findById(messageTemplateDO.getId());
		if(meeageDo != null){
			MessageTemplateInfo messageTemplateInfo = new MessageTemplateInfo();
			BeanCopier.staticCopy(meeageDo, messageTemplateInfo);
			if(meeageDo.getNotifyType() !=null){
				messageTemplateInfo.setNotifyType(RemindTypeEnum.getByCode(meeageDo.getNotifyType()));
			}
			messageTemplateInfo.setWayInfos(queryMessageTemplateWayInfoById(messageTemplateDO));
			return messageTemplateInfo;
		}
		return null;
	}
	
	
	
	private List<MessageTemplateWayInfo> queryMessageTemplateWayInfoById(MessageTemplateDO messageTemplateDO) {
		logger.info("开始查询指提醒信息....messageTemplateDO=[{}]"+ messageTemplateDO.toString());
		MessageTemplateWayDO messageTemplateWayDO = new MessageTemplateWayDO();
		messageTemplateWayDO.setMessageTemplateId(messageTemplateDO.getId());
		List<MessageTemplateWayDO> meWayDo=messageTemplateWayDAO.findByCondition(messageTemplateWayDO, null, null, 0, 999);
		if(ListUtil.isNotEmpty(meWayDo)){
			List<MessageTemplateWayInfo> messageTemplateWayInfo = new ArrayList<MessageTemplateWayInfo>();
			for(MessageTemplateWayDO meDo:meWayDo){
				MessageTemplateWayInfo info = new MessageTemplateWayInfo();
				BeanCopier.staticCopy(meDo, info);
				if(meDo.getNotifyWay()!=null){
					info.setNotifyWay(RemindContactTypeEnum.getByCode(meDo.getNotifyWay()));
				}
				messageTemplateWayInfo.add(info);
			}
			return messageTemplateWayInfo;
		}
		return null;
	}
	
	
	private void updateMessageTemplateWayInfoById(long id,MessageTemplateOrder messageTemplateOrder){
		messageTemplateWayDAO.deleteById(id);
		List<MessageTemplateWayOrder> wayInfos=messageTemplateOrder.getWayOrders();
		if(ListUtil.isNotEmpty(wayInfos)){
			for(MessageTemplateWayOrder orders:wayInfos){
				MessageTemplateWayDO messageTemplateWayDO = new MessageTemplateWayDO();
				BeanCopier.staticCopy(orders, messageTemplateWayDO);
				if(orders.getNotifyWay()!=null){
					messageTemplateWayDO.setNotifyWay(orders.getNotifyWay().code());
				}
				messageTemplateWayDAO.insert(messageTemplateWayDO);
			}
		}
	}
	
	



	@Override
	public InsuranceBaseResult updateMessageTemplateInfoByID(final
			MessageTemplateOrder messageTemplateOrder) {
		return commonProcess(messageTemplateOrder, "修改提醒设置", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				MessageTemplateDO messageTemplateDO=messageTemplateDAO.findById(messageTemplateOrder.getId());
				BeanCopier.staticCopy(messageTemplateOrder, messageTemplateDO);
				if(messageTemplateOrder.getNotifyType()!=null){
					messageTemplateDO.setNotifyType(messageTemplateOrder.getNotifyType().code());
				}
				messageTemplateDAO.update(messageTemplateDO);
				updateMessageTemplateWayInfoById(messageTemplateOrder.getId(), messageTemplateOrder);
				return null;
			}
		}, null, null);
	}
}
