package com.born.insurance.biz.service.chargeNotice;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.daointerface.ChargeNoticeDAO;
import com.born.insurance.dal.dataobject.ChargeNoticeDO;
import com.born.insurance.dataobject.ChargeNoticeFormDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.InsuranceTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeFormInfo;
import com.born.insurance.ws.info.chargeNotice.ChargeNoticeInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeOrder;
import com.born.insurance.ws.order.chargeNotice.ChargeNoticeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.chargeNotice.ChargeNoticeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("chargeNoticeService")
public class ChargeNoticeServiceImpl extends BaseFormAutowiredDomainService implements ChargeNoticeService {
	@Autowired
	protected ChargeNoticeDAO chargeNoticeDAO;

	@Override
	public InsuranceBaseResult save(final ChargeNoticeOrder order) {
		return commonFormSaveProcess(order, "", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
				}
				long id = order.getId();
				final Date now = InsuranceDomainHolder.get().getSysDate();
				if (id <= 0) {
					ChargeNoticeDO doObj = new ChargeNoticeDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setRowAddTime(now);
					chargeNoticeDAO.insert(doObj);
				} else {
					ChargeNoticeDO doObj = chargeNoticeDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					doObj.setRecievedFee(order.getRecievedFee());
					if (doObj.getInsuranceTypeName().equals(InsuranceTypeEnum.LIFEINSURANCE.getMessage())) {
						doObj.setCommissionType(order.getCommissionType());
						doObj.setPersistencyRateWard(order.getPersistencyRateWard());
						doObj.setManageGrant(order.getManageGrant());
						doObj.setPackageFee(order.getPackageFee());
						doObj.setInsuranceOther(order.getInsuranceOther());
					}
					chargeNoticeDAO.update(doObj);
				}
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(form.getFormId());
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ChargeNoticeInfo findById(long id) {
		ChargeNoticeDO chargeNoticeDo= chargeNoticeDAO.findById(id);
        if(chargeNoticeDo != null){
            ChargeNoticeInfo chargeNoticeInfo = new ChargeNoticeInfo();
            BeanCopier.staticCopy(chargeNoticeDo,chargeNoticeInfo);
            return chargeNoticeInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ChargeNoticeInfo> queryList(ChargeNoticeQueryOrder queryOrder) {
		QueryBaseBatchResult<ChargeNoticeInfo> batchResult = new QueryBaseBatchResult<ChargeNoticeInfo>();
		
		try {
			queryOrder.check();
			List<ChargeNoticeInfo> pageList = new ArrayList<ChargeNoticeInfo>((int) queryOrder.getPageSize());
			
			ChargeNoticeDO chargeNoticeDO = new ChargeNoticeDO();
			BeanCopier.staticCopy(queryOrder, chargeNoticeDO);
			long totalCount = chargeNoticeDAO.findByConditionCount(chargeNoticeDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ChargeNoticeDO> recordList = chargeNoticeDAO.findByCondition(chargeNoticeDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (ChargeNoticeDO item : recordList) {
				ChargeNoticeInfo info = new ChargeNoticeInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}

	@Override
	public QueryBaseBatchResult<ChargeNoticeFormInfo> queryFormList(ChargeNoticeQueryOrder queryOrder) {

		QueryBaseBatchResult<ChargeNoticeFormInfo> batchResult = new QueryBaseBatchResult<ChargeNoticeFormInfo>();
		
		try {
			queryOrder.check();
			List<ChargeNoticeFormInfo> pageList = new ArrayList<ChargeNoticeFormInfo>((int) queryOrder.getPageSize());
			
			ChargeNoticeFormDO chargeNoticeFormDO = new ChargeNoticeFormDO();
			BeanCopier.staticCopy(queryOrder, chargeNoticeFormDO);
			long totalCount = extraDAO.findByConditionCount(chargeNoticeFormDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ChargeNoticeFormDO> recordList = extraDAO.findByCondition(chargeNoticeFormDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (ChargeNoticeFormDO item : recordList) {
				ChargeNoticeFormInfo info = new ChargeNoticeFormInfo();
				BeanCopier.staticCopy(item, info);
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}

	@Override
	public ChargeNoticeInfo findByFormId(long formId) {
		ChargeNoticeInfo info = new ChargeNoticeInfo();
		ChargeNoticeDO chargeNoticeDO = chargeNoticeDAO.findByFormId(formId);
		if (null != chargeNoticeDO) {
			BeanCopier.staticCopy(chargeNoticeDO, info);
			return info ;
		}
		
		return info;
	}

	@Override
	public ChargeNoticeInfo findByBusinessBillId(String businessBillId) {
		ChargeNoticeInfo info = new ChargeNoticeInfo();
		ChargeNoticeDO chargeNoticeDO = chargeNoticeDAO.findByBusinessBillId(Long.valueOf(businessBillId));
		if (null != chargeNoticeDO) {
			BeanCopier.staticCopy(chargeNoticeDO, info);
			return info ;
		}
		
		return null;
	}
}