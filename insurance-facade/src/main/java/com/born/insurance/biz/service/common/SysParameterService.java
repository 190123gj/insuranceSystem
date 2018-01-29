package com.born.insurance.biz.service.common;

import java.util.List;

import javax.jws.WebService;

import com.born.insurance.biz.service.common.info.SysParamInfo;
import com.born.insurance.ws.order.sysParam.SysParamOrder;
import com.born.insurance.ws.order.sysParam.SysParamQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/**
 * 
 * @Filename SysParamServcie.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-2-22</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
@WebService
public interface SysParameterService {
	public String getSysParameterValue(String paramName);
	
	public SysParamInfo getSysParameterValueDO(String paramName);
	
	public InsuranceBaseResult updateSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public InsuranceBaseResult insertSysParameterValueDO(SysParamOrder sysParamOrder);
	
	public void deleteSysParameterValue(String paramName);
	
	public List<SysParamInfo> getSysParameterValueList(String paramName);
	
	public QueryBaseBatchResult<SysParamInfo> querySysPram(SysParamQueryOrder sysParamQueryOrder);
	
	public void clearCache();
	
}
