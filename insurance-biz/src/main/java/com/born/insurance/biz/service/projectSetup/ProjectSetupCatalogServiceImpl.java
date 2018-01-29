package com.born.insurance.biz.service.projectSetup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDAOService;
import com.born.insurance.dal.daointerface.ProjectSetupCatalogDAO;
import com.born.insurance.dal.dataobject.ProjectSetupCatalogDO;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCatalogInfo;
import com.born.insurance.ws.order.projectSetup.ProjectSetupCataLogQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.projectSetup.ProjectSetupCatalogService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service
public class ProjectSetupCatalogServiceImpl extends BaseAutowiredDAOService implements ProjectSetupCatalogService {
	
	@Autowired
	private ProjectSetupCatalogDAO  projectSetupCatalogDAO;
	

	@Override
	public QueryBaseBatchResult<ProjectSetupCatalogInfo> queryList(ProjectSetupCataLogQueryOrder queryOrder) {
QueryBaseBatchResult<ProjectSetupCatalogInfo> batchResult = new QueryBaseBatchResult<ProjectSetupCatalogInfo>();
		
		try {
			queryOrder.check();
			List<ProjectSetupCatalogInfo> pageList = new ArrayList<ProjectSetupCatalogInfo>(
				(int) queryOrder.getPageSize());
			
			ProjectSetupCatalogDO projectSetupCatalogDO = new ProjectSetupCatalogDO();
			BeanCopier.staticCopy(queryOrder, projectSetupCatalogDO);
			long totalCount = projectSetupCatalogDAO.findByConditionCount(projectSetupCatalogDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ProjectSetupCatalogDO> recordList = projectSetupCatalogDAO.findByCondition(projectSetupCatalogDO,
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (ProjectSetupCatalogDO item : recordList) {
				ProjectSetupCatalogInfo info = new ProjectSetupCatalogInfo();
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
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}

	@Override
	public List<ProjectSetupCatalogInfo> findList(long projectSetupId) {
		List<ProjectSetupCatalogInfo> catalogInfoList = new  ArrayList<ProjectSetupCatalogInfo>();
		List<ProjectSetupCatalogDO> list = projectSetupCatalogDAO.findListByProjectupId(projectSetupId);
		for (ProjectSetupCatalogDO projectSetupCatalogDO : list) {
			ProjectSetupCatalogInfo info = new ProjectSetupCatalogInfo();
			BeanCopier.staticCopy(projectSetupCatalogDO, info);
			catalogInfoList.add(info);
		}
		return catalogInfoList;
	}

}
