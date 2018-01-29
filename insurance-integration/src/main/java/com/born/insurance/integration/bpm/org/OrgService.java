package com.born.insurance.integration.bpm.org;

import com.born.insurance.integration.bpm.info.org.OrgInfo;
import com.born.insurance.integration.bpm.user.UserInfo;

import java.util.List;


public interface OrgService {
	
	/**
	 * 查询某个组织机构及其下属机构
	 * 
	 * @param orgCode
	 * @return
	 */
	OrgInfo findOrgInSystemByCode(String orgCode);
	
	/**
	 * 获取部门/组织结构下的人员
	 * 
	 * @param orgCode
	 * @return
	 */
	List<UserInfo> findOrgMenbersByCode(String orgCode);
	
}
