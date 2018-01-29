package com.born.insurance.biz.service.demo;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.dataobject.DemoDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.demo.DemoInfo;
import com.born.insurance.ws.order.demo.DemoOrder;
import com.born.insurance.ws.order.demo.DemoQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.demo.DemoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("demoService")
public class DemoServiceImpl extends BaseFormAutowiredDomainService implements DemoService {
	@Override
	public FormBaseResult save(final DemoOrder order) {
		return commonFormSaveProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
						"获取表单信息出错");
				}
				long id = order.getId();
				if (id <= 0) {
					DemoDO doObj = new DemoDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					
					demoDAO.insert(doObj);
				} else {
					DemoDO doObj = demoDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					demoDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public DemoInfo findById(long id) {
		DemoDO demoDO = demoDAO.findById(id);
		if (demoDO != null) {
			DemoInfo demoInfo = new DemoInfo();
			BeanCopier.staticCopy(demoDO, demoInfo);
			return demoInfo;
		}
		return null;
	}
	
	@Override
	public DemoInfo findByFormId(long formId) {
		DemoDO demoDO = demoDAO.findByFormId(formId);
		if (demoDO != null) {
			DemoInfo demoInfo = new DemoInfo();
			BeanCopier.staticCopy(demoDO, demoInfo);
			return demoInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<DemoInfo> queryList(DemoQueryOrder queryOrder) {
		QueryBaseBatchResult<DemoInfo> batchResult = new QueryBaseBatchResult<DemoInfo>();
		
		try {
			queryOrder.check();
			List<DemoInfo> pageList = new ArrayList<DemoInfo>((int) queryOrder.getPageSize());
			
			DemoDO demoDO = new DemoDO();
			BeanCopier.staticCopy(queryOrder, demoDO);
			long totalCount = demoDAO.findByConditionCount(demoDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<DemoDO> recordList = demoDAO.findByCondition(demoDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (DemoDO item : recordList) {
				DemoInfo info = new DemoInfo();
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
