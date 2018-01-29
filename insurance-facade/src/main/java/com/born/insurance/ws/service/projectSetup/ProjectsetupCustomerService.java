package com.born.insurance.ws.service.projectSetup;

import java.util.List;

import com.born.insurance.ws.info.projectSetup.ProjectSetupCustomerInfo;

/**
 * 
 * @author jenny
 *
 */
public interface ProjectsetupCustomerService {

	/**
	 * 根据超权限id查询关联用户信息
	 * @param projectSetupId
	 * @return
	 */
	List<ProjectSetupCustomerInfo> findList(long projectSetupId);

}
