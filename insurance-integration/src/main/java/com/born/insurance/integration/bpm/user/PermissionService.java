package com.born.insurance.integration.bpm.user;


import com.born.insurance.ws.result.base.InsuranceBaseResult;

public interface PermissionService {
	
	InsuranceBaseResult loadSystemPermission(String systemAlias);
	
}
