package com.born.insurance.biz.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.born.insurance.biz.exception.ExceptionFactory;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.insurance.biz.exception.InsuranceBizException;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.dataobject.FormMessageTempleteDO;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormMessageBaseOnEnum;
import com.born.insurance.ws.enums.FormMessageTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormMessageTempleteInfo;
import com.born.insurance.ws.order.common.FormMessageTempleteOrder;
import com.born.insurance.ws.order.common.FormMessageTempleteQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.FormMessageTempleteService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service("formMessageTempleteService")
public class FormMessageTempleteServiceImpl extends BaseAutowiredDomainService implements
																				FormMessageTempleteService {
	
	private static Map<String, FormMessageTempleteInfo> templeteMap = Maps.newHashMap();
	
	@Override
	public void clearCache() {
		if (templeteMap != null) {
			templeteMap.clear();
		}
	}
	
	@Override
	public QueryBaseBatchResult<FormMessageTempleteInfo> queryPage(	FormMessageTempleteQueryOrder order) {
		
		QueryBaseBatchResult<FormMessageTempleteInfo> batchResult = new QueryBaseBatchResult<FormMessageTempleteInfo>();
		
		try {
			
			if (StringUtil.isEmpty(order.getSortCol())) {
				order.setSortCol("templete_id");
				order.setSortOrder("desc");
			}
			
			FormMessageTempleteDO templete = new FormMessageTempleteDO();
			BeanCopier.staticCopy(order, templete);
			
			if (order.getFormCode() != null) {
				templete.setFormCode(order.getFormCode().code());
			}
			
			if (order.getTempleteId() != null) {
				templete.setTempleteId(order.getTempleteId());
			}
			
			if (order.getType() != null) {
				templete.setType(order.getType().code());
			}
			
			if (order.getIsSendMail() != null) {
				templete.setIsSendMail(order.getIsSendMail().code());
			}
			
			if (order.getIsSendSiteMessage() != null) {
				templete.setIsSendSiteMessage(order.getIsSendSiteMessage().code());
			}
			
			if (order.getIsSendSms() != null) {
				templete.setIsSendSms(order.getIsSendSms().code());
			}
			
			long totalCount = formMessageTempleteDAO.findByConditionCount(templete);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FormMessageTempleteDO> list = formMessageTempleteDAO.findByCondition(templete,
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			
			List<FormMessageTempleteInfo> pageList = new ArrayList<FormMessageTempleteInfo>(
				list.size());
			for (FormMessageTempleteDO item : list) {
				pageList.add(convertDO2Info(item));
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询模板信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
		
	}
	
	private FormMessageTempleteInfo convertDO2Info(FormMessageTempleteDO DO) {
		if (DO == null)
			return null;
		
		FormMessageTempleteInfo info = new FormMessageTempleteInfo();
		BeanCopier.staticCopy(DO, info);
		info.setType(FormMessageTypeEnum.getByCode(DO.getType()));
		info.setIsSendMail(BooleanEnum.getByCode(DO.getIsSendMail()));
		info.setIsSendSiteMessage(BooleanEnum.getByCode(DO.getIsSendSiteMessage()));
		info.setIsSendSms(BooleanEnum.getByCode(DO.getIsSendSms()));
		info.setWithAuditOpinion(BooleanEnum.getByCode(DO.getWithAuditOpinion()));
		info.setFormCode(FormCodeEnum.getByCode(DO.getFormCode()));
		info.setBaseOn(FormMessageBaseOnEnum.getByCode(DO.getBaseOn()));
		return info;
	}
	
	@Override
	public FormMessageTempleteInfo findByFormCodeAndType(FormCodeEnum formCode,
															FormMessageTypeEnum type) {
		String key = formCode.code() + "_" + type.code();
		FormMessageTempleteInfo templete = templeteMap.get(key);
		if (templete == null) {
			templete = convertDO2Info(formMessageTempleteDAO.findByFormCodeAndType(formCode.code(),
				type.code()));
			templeteMap.put(key, templete);
		}
		return templete;
	}
	
	@Override
	public FormMessageTempleteInfo findByTempleteId(long templeteId) {
		return convertDO2Info(formMessageTempleteDAO.findById(templeteId));
	}
	
	@Override
	public InsuranceBaseResult saveTemplete(final FormMessageTempleteOrder order) {
		InsuranceBaseResult result = createResult();
		try {
			
			logger.info("保存消息模板:{}", order);
			
			FormMessageTempleteDO templete = null;
			if (order.getTempleteId() != null && order.getTempleteId() > 0) {
				templete = formMessageTempleteDAO.findById(order.getTempleteId());
				if (templete == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
						"模板不存在");
				}
			}
			
			boolean isAdd = false;
			if (templete == null) {
				isAdd = true;
				templete = new FormMessageTempleteDO();
			}
			
			BeanCopier.staticCopy(order, templete);
			
			if (order.getFormCode() != null) {
				templete.setFormCode(order.getFormCode().code());
			}
			
			if (order.getTempleteId() != null) {
				templete.setTempleteId(order.getTempleteId());
			}
			
			if (order.getType() != null) {
				templete.setType(order.getType().code());
			}
			
			if (order.getIsSendMail() != null) {
				templete.setIsSendMail(order.getIsSendMail().code());
			} else {
				templete.setIsSendMail(BooleanEnum.NO.code());
			}
			
			if (order.getIsSendSiteMessage() != null) {
				templete.setIsSendSiteMessage(order.getIsSendSiteMessage().code());
			} else {
				templete.setIsSendSiteMessage(BooleanEnum.NO.code());
			}
			
			if (order.getIsSendSms() != null) {
				templete.setIsSendSms(order.getIsSendSms().code());
			} else {
				templete.setIsSendSms(BooleanEnum.NO.code());
			}
			
			if (order.getWithAuditOpinion() != null) {
				templete.setWithAuditOpinion(order.getWithAuditOpinion().code());
			} else {
				templete.setWithAuditOpinion(BooleanEnum.YES.code());
			}
			
			if (order.getBaseOn() != null) {
				templete.setBaseOn(order.getBaseOn().code());
			} else {
				templete.setBaseOn(FormMessageBaseOnEnum.TEMPELTE.code());
			}
			
			//unescape
			templete.setContentHtml(StringUtil.unescape(templete.getContentHtml()));
			
			if (isAdd) {
				templete.setRawAddTime(getSysdate());
				formMessageTempleteDAO.insert(templete);
			} else {
				formMessageTempleteDAO.update(templete);
			}
			
			clearCache();
			
			result.setSuccess(true);
			result.setMessage("保存消息模板成功");
		} catch (InsuranceBizException e) {
			logger.error("保存消息模板失败 :{}", e);
			result.setSuccess(false);
			result.setMessage(e.getErrorMsg());
		} catch (Exception e) {
			logger.error("保存消息模板出错:{}", e);
			result.setSuccess(false);
			result.setMessage("保存消息模板出错");
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult deleteByTempleteId(long templeteId) {
		InsuranceBaseResult result = createResult();
		try {
			formMessageTempleteDAO.deleteById(templeteId);
			result.setSuccess(true);
			result.setMessage("删除消息模板成功");
		} catch (Exception e) {
			logger.error("删除消息模板出错{}", e);
			result.setSuccess(false);
			result.setMessage("删除消息模板出错");
		}
		return result;
	}
	
}
