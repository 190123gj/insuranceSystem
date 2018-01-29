package com.born.insurance.biz.service.projectSetup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.dal.daointerface.ProjectSetupCustomerDAO;
import com.born.insurance.dal.dataobject.ProjectSetupCustomerDO;
import com.born.insurance.ws.info.projectSetup.ProjectSetupCustomerInfo;
import com.born.insurance.ws.service.projectSetup.ProjectsetupCustomerService;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Service
public class ProjectsetupCustomerServiceImpl implements ProjectsetupCustomerService {
	
	@Autowired
	protected ProjectSetupCustomerDAO projectSetupCustomerDAO;

	@Override
	public List<ProjectSetupCustomerInfo> findList(long projectSetupId) {
		List<ProjectSetupCustomerInfo> catalogInfoList = new  ArrayList<ProjectSetupCustomerInfo>();
		List<ProjectSetupCustomerDO> list = projectSetupCustomerDAO.findListByProjectupId(projectSetupId);
		for (ProjectSetupCustomerDO ProjectSetupCustomerDO : list) {
			ProjectSetupCustomerInfo info = new ProjectSetupCustomerInfo();
			BeanCopier.staticCopy(ProjectSetupCustomerDO, info);
			catalogInfoList.add(info);
		}
		return catalogInfoList;
	}

}
