package com.born.insurance.biz.service.formTemplate;

import java.util.ArrayList;
import java.util.List;

import com.born.insurance.dal.dataobject.FormTemplateGridDO;
import com.born.insurance.ws.info.formTemplate.FormTemplateGridInfo;
import com.born.insurance.ws.info.formTemplate.FormTemplateTabInfo;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.FormTemplateDAO;
import com.born.insurance.dal.daointerface.FormTemplateFieldDAO;
import com.born.insurance.dal.daointerface.FormTemplateGridDAO;
import com.born.insurance.dal.daointerface.FormTemplateTabDAO;
import com.born.insurance.dal.dataobject.FormTemplateDO;
import com.born.insurance.dal.dataobject.FormTemplateTabDO;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.FormTemplateTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.formTemplate.FormTemplateInfo;
import com.born.insurance.ws.order.formTemplate.FormTemplateQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.formTemplate.FormTemplateService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateServiceImpl extends BaseAutowiredDomainService implements
																		FormTemplateService {
	@Autowired
	FormTemplateDAO formTemplateDAO;
	@Autowired
	FormTemplateTabDAO formTemplateTabDAO;
	@Autowired
	FormTemplateGridDAO formTemplateGridDAO;
	@Autowired
	FormTemplateFieldDAO formTemplateFieldDAO;
	
	@Override
	public QueryBaseBatchResult<FormTemplateInfo> queryList(FormTemplateQueryOrder queryOrder) {
		QueryBaseBatchResult<FormTemplateInfo> batchResult = new QueryBaseBatchResult<FormTemplateInfo>();
		
		try {
			queryOrder.check();
			List<FormTemplateInfo> pageList = new ArrayList<FormTemplateInfo>(
				(int) queryOrder.getPageSize());
			
			FormTemplateDO formTemplateDO = new FormTemplateDO();
			BeanCopier.staticCopy(queryOrder, formTemplateDO);
			long totalCount = formTemplateDAO.findByConditionCount(formTemplateDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<FormTemplateDO> recordList = formTemplateDAO.findByCondition(formTemplateDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (FormTemplateDO item : recordList) {
				FormTemplateInfo info = new FormTemplateInfo();
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
	public FormTemplateInfo queryInfoByFormTemplateId(long formTemplateId) {
		FormTemplateDO formTemplateDO = formTemplateDAO.findById(formTemplateId);
		if (formTemplateDO != null) {
			FormTemplateInfo info = new FormTemplateInfo();
			BeanCopier.staticCopy(formTemplateDO, info);
			if (StringUtil.isNotEmpty(formTemplateDO.getType())) {
				info.setType(FormTemplateTypeEnum.getByCode(formTemplateDO.getType()));
			}
			FormTemplateTabDO formTemplateTabDO = new FormTemplateTabDO();
			formTemplateTabDO.setTemplateId(info.getId());
			List<FormTemplateTabDO> tabDOs = formTemplateTabDAO.findByCondition(formTemplateTabDO,
				null, null, 0, 0);
			if (ListUtil.isNotEmpty(tabDOs)) {
				List<FormTemplateTabInfo> tabInfos = new ArrayList<FormTemplateTabInfo>();
				for (FormTemplateTabDO tabDO : tabDOs) {
					FormTemplateTabInfo tabInfo = new FormTemplateTabInfo();
					BeanCopier.staticCopy(tabDO, tabInfo);
					tabInfos.add(tabInfo);
					//todo 性能问题
					List<FormTemplateGridInfo> gridInfos = null;
					FormTemplateGridDO templateGridDO = new FormTemplateGridDO();
					templateGridDO.setModuleId(templateGridDO.getId());
					List<FormTemplateGridDO> gridDOs = formTemplateGridDAO.findByCondition(
						templateGridDO, null, null, 0, 0);
					if (ListUtil.isNotEmpty(gridDOs)) {
						gridInfos = new ArrayList<FormTemplateGridInfo>();
						for (FormTemplateGridDO gridDO : gridDOs) {
							FormTemplateGridInfo gridInfo = new FormTemplateGridInfo();
							BeanCopier.staticCopy(gridDO, gridInfo);
						}
					}
					tabInfo.setGrids(gridInfos);
				}
				info.setTabs(tabInfos);
			}
			return info;
		}
		return null;
	}
}
