package com.born.insurance.biz.service.insuranceLiability;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.InsuranceLiabilityDO;
import com.born.insurance.dal.daointerface.InsuranceLiabilityDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceLiability.InsuranceLiabilityInfo;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityOrder;
import com.born.insurance.ws.order.insuranceLiability.InsuranceLiabilityQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceLiability.InsuranceLiabilityService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceLiabilityService")
public class InsuranceLiabilityServiceImpl extends BaseAutowiredDomainService implements
																				InsuranceLiabilityService {
	@Autowired
	protected InsuranceLiabilityDAO insuranceLiabilityDAO;
	
	@Override
	public InsuranceBaseResult save(final InsuranceLiabilityOrder order) {
		return commonProcess(order, "保险责任增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				long id = order.getId();
				if (StringUtils.isBlank(order.getName())) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.FORM_CHECK_ERROR, "保险名称不能为空");
				}
				//校验保险名称唯一性
				InsuranceLiabilityDO commonObj = new InsuranceLiabilityDO();
				BeanCopier.staticCopy(order, commonObj, UnBoxingConverter.getInstance());
				InsuranceLiabilityDO insuranceLiabilityDO = insuranceLiabilityDAO.checkUnique(commonObj);
				if (null != insuranceLiabilityDO) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.EXECUTE_FAILURE,
							"该保险责任已存在");
				}
				
				if (id <= 0) {
					InsuranceLiabilityDO doObj = new InsuranceLiabilityDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setRawAddTime(getSysdate());
					insuranceLiabilityDAO.insert(doObj);
				} else {
					InsuranceLiabilityDO doObj = insuranceLiabilityDAO.findById(order
						.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"该保险责任不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceLiabilityDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceLiabilityInfo findById(long id) {
		InsuranceLiabilityDO insuranceLiabilityDo = insuranceLiabilityDAO.findById(id);
		if (insuranceLiabilityDo != null) {
			InsuranceLiabilityInfo insuranceLiabilityInfo = new InsuranceLiabilityInfo();
			BeanCopier.staticCopy(insuranceLiabilityDo, insuranceLiabilityInfo);
			return insuranceLiabilityInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceLiabilityInfo> queryList(	InsuranceLiabilityQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceLiabilityInfo> batchResult = new QueryBaseBatchResult<InsuranceLiabilityInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceLiabilityInfo> pageList = new ArrayList<InsuranceLiabilityInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceLiabilityDO insuranceLiabilityDO = new InsuranceLiabilityDO();
			BeanCopier.staticCopy(queryOrder, insuranceLiabilityDO);
			long totalCount = insuranceLiabilityDAO.findByConditionCount(insuranceLiabilityDO,queryOrder.getLiabilitys(),DateUtil.parse(queryOrder.getBeginDate()),DateUtil.parse(queryOrder.getEndDate()));
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceLiabilityDO> recordList = insuranceLiabilityDAO.findByCondition(
				insuranceLiabilityDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize(),queryOrder.getLiabilitys(),DateUtil.parse(queryOrder.getBeginDate()),DateUtil.parse(queryOrder.getEndDate()));
			for (InsuranceLiabilityDO item : recordList) {
				InsuranceLiabilityInfo info = new InsuranceLiabilityInfo();
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
	public InsuranceBaseResult deleteInsuranceLiability(final InsuranceLiabilityOrder order) {
		return commonProcess(order, "删除保险责任", new BeforeProcessInvokeService() {
				
				@Override
				public Domain before() {
					InsuranceLiabilityDO insuranceLiabilityDO = insuranceLiabilityDAO.findById(order.getId());
					if (null == insuranceLiabilityDO) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"该保险责任不存在");
					}
					insuranceLiabilityDAO.deleteById(order.getId());
					return null;
				}
			}, null, null);
	}
}