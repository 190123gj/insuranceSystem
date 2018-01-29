package com.born.insurance.biz.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.common.info.SysParamInfo;
import com.born.insurance.dal.daointerface.SysParamDAO;
import com.born.insurance.dal.dataobject.SysParamDO;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.order.sysParam.SysParamOrder;
import com.born.insurance.ws.order.sysParam.SysParamQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename SysParameterServiceImp.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai1</li> <li>Date: 2013-2-22</li> <li>Version:
 * 1.0</li> <li>Content: create</li>
 * 
 */
@Service("sysParameterService")
public class SysParameterServiceImpl implements SysParameterService, InitializingBean {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SysParamDAO sysParamDAO;
	
	private static Map<String, String> paramMap = null;
	
	@Override
	public String getSysParameterValue(String paramName) {
		if (paramMap == null || paramMap.isEmpty()) {
			initParamMap();
		}
		String paramValue = paramMap.get(paramName);
		return paramValue;
	}
	
	private SysParamInfo getDbValue(String paramName) {
		SysParamDO sysParamDO = sysParamDAO.findById(paramName);
		if (sysParamDO == null)
			return null;
		SysParamInfo paramInfo = new SysParamInfo();
		BeanCopier.staticCopy(sysParamDO, paramInfo);
		return paramInfo;
	}
	
	/**
	 * 
	 */
	private synchronized void initParamMap() {
		if (paramMap == null)
			paramMap = new HashMap<String, String>();
		synchronized (paramMap) {
			List<SysParamDO> paramDOs = sysParamDAO.findAll();
			if (paramDOs != null) {
				for (SysParamDO item : paramDOs) {
					paramMap.put(item.getParamName(), item.getParamValue());
				}
			}
		}
	}
	
	@Override
	public List<SysParamInfo> getSysParameterValueList(String paramName) {
		List<SysParamDO> paramDOs = sysParamDAO.findByLike(paramName + "%");
		List<SysParamInfo> newList = Lists.newArrayList();
		if (ListUtil.isEmpty(paramDOs))
			return newList;
		MiscUtil.convertList(paramDOs, newList, SysParamInfo.class);
		return newList;
	}
	
	@Override
	public void clearCache() {
		synchronized (SysParameterServiceImpl.class) {
			if (paramMap != null) {
				paramMap.clear();
				paramMap = null;
				
			}
		}
	}
	
	@Override
	public SysParamInfo getSysParameterValueDO(String paramName) {
		return getDbValue(paramName);
	}
	
	@Override
	public InsuranceBaseResult updateSysParameterValueDO(SysParamOrder sysParamOrder) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		logger.info("sysParamOrder，sysParamOrder={}", sysParamOrder);
		try {
			if ("".equalsIgnoreCase(sysParamOrder.getParamName())
				|| sysParamOrder.getParamName() == null) {
				result.setSuccess(false);
				result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
				return result;
			}
			SysParamDO sysParamDO = new SysParamDO();
			BeanCopier.staticCopy(sysParamOrder, sysParamDO);
			sysParamDAO.update(sysParamDO);
			result.setSuccess(true);
			
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(InsuranceResultEnum.INCOMPLETE_REQ_PARAM.getMessage() + "["
								+ e.getMessage() + "]");
		} catch (DataAccessException e) {
			logger.error("数据库异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("未知异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult insertSysParameterValueDO(SysParamOrder sysParamOrder) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		logger.info("sysParamOrder，sysParamOrder={}", sysParamOrder);
		try {
			sysParamOrder.check();
			SysParamDO sysParamDO = new SysParamDO();
			BeanCopier.staticCopy(sysParamOrder, sysParamDO);
			sysParamDAO.insert(sysParamDO);
			result.setSuccess(true);
		} catch (IllegalArgumentException e) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage(InsuranceResultEnum.INCOMPLETE_REQ_PARAM.getMessage() + "["
								+ e.getMessage() + "]");
		} catch (DataAccessException e) {
			logger.error("数据库异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("未知异常:e={}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public void deleteSysParameterValue(String paramName) {
		sysParamDAO.deleteByParamName(paramName);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	@Override
	public QueryBaseBatchResult<SysParamInfo> querySysPram(SysParamQueryOrder sysParamQueryOrder) {
		QueryBaseBatchResult<SysParamInfo> batchResult = new QueryBaseBatchResult<SysParamInfo>();
		try {
			sysParamQueryOrder.check();
			List<SysParamInfo> pageList = new ArrayList<SysParamInfo>(
				(int) sysParamQueryOrder.getPageSize());
			
			SysParamDO queryDO = new SysParamDO();
			BeanCopier.staticCopy(sysParamQueryOrder, queryDO);
			long totalCount = sysParamDAO.paramInfoQueryCount(queryDO);
			PageComponent component = new PageComponent(sysParamQueryOrder, totalCount);
			List<SysParamDO> recordList = sysParamDAO.paramInfoQueryList(queryDO,
				sysParamQueryOrder.getSortCol(), sysParamQueryOrder.getSortOrder(),
				sysParamQueryOrder.getLimitStart(), sysParamQueryOrder.getPageSize());
			List<SysParamInfo> newList = Lists.newArrayList();
			MiscUtil.convertList(recordList, newList, SysParamInfo.class);
			pageList.addAll(newList);
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(newList);
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
