package com.born.insurance.integration.common.impl;

import com.born.insurance.integration.common.PropertyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.ws.enums.SysParamEnum;
import com.yjf.common.env.Env;

@Service("propertyConfigService")
public class PropertyConfigServiceImpl implements
		PropertyConfigService {
	
	@Autowired
	private SysParameterService sysParameterService;
	
	@Value("${born.bpm.service.endpoint}")
	private String bornBpmServiceEndpoint;
	
	@Value("${born.bpm.contextpath}")
	private String bornBpmContextpath;
	
	@Override
	public String getBmpServiceEndpoint() {
		return this.bornBpmServiceEndpoint;
	}
	
	@Override
	public String getBmpServiceUserDetailsService() {
		return this.bornBpmServiceEndpoint + "/UserDetailsService";
	}
	
	@Override
	public String getBmpServiceProcessService() {
		return this.bornBpmServiceEndpoint + "/ProcessService";
	}
	
	@Override
	public String getBmpServiceSystemResourcesService() {
		return this.bornBpmServiceEndpoint + "/SystemResourcesService";
	}
	
	@Override
	public String getBmpServiceUserOrgService() {
		return this.bornBpmServiceEndpoint + "/UserOrgService";
	}
	
	@Override
	public String getBmpRootUrl() {
		if (Env.isDev()) {
			return sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL
				.code()) + "/" + bornBpmContextpath;
		} else {
			return "/" + bornBpmContextpath;
		}
	}
	
	@Override
	public String getBmpServiceFlowService() {
		return this.bornBpmServiceEndpoint + "/FlowService";
	}
	
}
