package com.born.insurance.biz.service.salesAreas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.SalesAreaDO;
import com.born.insurance.dal.daointerface.SalesAreaDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.salesAreas.SalesAreasInfo;
import com.born.insurance.ws.order.salesAreas.SalesAreasOrder;
import com.born.insurance.ws.order.salesAreas.SalesAreasQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.salesAreas.SalesAreasService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("salesAreasService")
public class SalesAreasServiceImpl extends BaseAutowiredDomainService implements SalesAreasService {
	@Autowired
	protected SalesAreaDAO salesAreaDAO;
	
	@Override
	public InsuranceBaseResult save(final SalesAreasOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				long id = order.getId();
				if (id <= 0) {
					SalesAreaDO doObj = new SalesAreaDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					salesAreaDAO.insert(doObj);
				} else {
					SalesAreaDO doObj = salesAreaDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					salesAreaDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public SalesAreasInfo findById(long id) {
		SalesAreaDO salesAreaDo = salesAreaDAO.findById(id);
		if (salesAreaDo != null) {
			SalesAreasInfo salesAreasInfo = new SalesAreasInfo();
			BeanCopier.staticCopy(salesAreaDo, salesAreasInfo);
			return salesAreasInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<SalesAreasInfo> queryList(SalesAreasQueryOrder queryOrder) {
		QueryBaseBatchResult<SalesAreasInfo> batchResult = new QueryBaseBatchResult<SalesAreasInfo>();
		
		try {
			queryOrder.check();
			List<SalesAreasInfo> pageList = new ArrayList<SalesAreasInfo>(
				(int) queryOrder.getPageSize());
			
			SalesAreaDO salesAreasDO = new SalesAreaDO();
			BeanCopier.staticCopy(queryOrder, salesAreasDO);
			long totalCount = salesAreaDAO.findByConditionCount(salesAreasDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<SalesAreaDO> recordList = salesAreaDAO.findByCondition(salesAreasDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (SalesAreaDO item : recordList) {
				SalesAreasInfo info = new SalesAreasInfo();
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