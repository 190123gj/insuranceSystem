package com.born.insurance.biz.service.baseDataInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.BaseDataInfoDO;
import com.born.insurance.dal.daointerface.BaseDataInfoDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.baseDataInfo.BaseDataInfoInfo;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoOrder;
import com.born.insurance.ws.order.baseDataInfo.BaseDataInfoQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.baseDataInfo.BaseDataInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("baseDataInfoService")
public class BaseDataInfoServiceImpl extends BaseAutowiredDomainService implements
																		BaseDataInfoService {
	@Autowired
	protected BaseDataInfoDAO baseDataInfoDAO;
	
	@Override
	public InsuranceBaseResult save(final BaseDataInfoOrder order) {
		return commonProcess(order, "基础数据增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					BaseDataInfoDO doObj = new BaseDataInfoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					baseDataInfoDAO.insert(doObj);
				} else {
					BaseDataInfoDO doObj = baseDataInfoDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					baseDataInfoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BaseDataInfoInfo findById(long id) {
		BaseDataInfoDO baseDataInfoDo = baseDataInfoDAO.findById(id);
		if (baseDataInfoDo != null) {
			BaseDataInfoInfo baseDataInfoInfo = new BaseDataInfoInfo();
			BeanCopier.staticCopy(baseDataInfoDo, baseDataInfoInfo);
			return baseDataInfoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BaseDataInfoInfo> queryList(BaseDataInfoQueryOrder queryOrder) {
		QueryBaseBatchResult<BaseDataInfoInfo> batchResult = new QueryBaseBatchResult<BaseDataInfoInfo>();
		
		try {
			queryOrder.check();
			List<BaseDataInfoInfo> pageList = new ArrayList<BaseDataInfoInfo>(
				(int) queryOrder.getPageSize());
			
			BaseDataInfoDO baseDataInfoDO = new BaseDataInfoDO();
			BeanCopier.staticCopy(queryOrder, baseDataInfoDO);
			if(queryOrder.getType() != null){
				baseDataInfoDO.setType(queryOrder.getType().getCode());
			}
			long totalCount = baseDataInfoDAO.findByConditionCount(baseDataInfoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BaseDataInfoDO> recordList = baseDataInfoDAO.findByCondition(baseDataInfoDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (BaseDataInfoDO item : recordList) {
				BaseDataInfoInfo info = new BaseDataInfoInfo();
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
}