package com.born.insurance.biz.service.common;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.dal.daointerface.SysWebAccessTokenDAO;
import com.born.insurance.dal.dataobject.SysWebAccessTokenDO;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.info.common.SysWebAccessTokenInfo;
import com.born.insurance.ws.order.common.SysWebAccessTokenOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.common.SysWebAccessTokenService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

@Service("sysWebAccessTokenService")
public class SysWebAccessTokenServiceImpl extends BaseAutowiredDomainService implements
																			SysWebAccessTokenService {
	
	@Autowired
	private SysWebAccessTokenDAO sysWebAccessTokenDAO;
	
	@Override
	public SysWebAccessTokenInfo query(String accessToken) {
		if (StringUtil.isBlank(accessToken))
			return null;
		SysWebAccessTokenDO token = new SysWebAccessTokenDO();
		token.setAccessToken(accessToken);
		//token.setIsValid(BooleanEnum.IS.code());
		List<SysWebAccessTokenDO> tokens = sysWebAccessTokenDAO.findList(token);
		if (ListUtil.isNotEmpty(tokens)) {
			token = tokens.get(0);
			SysWebAccessTokenInfo info = new SysWebAccessTokenInfo();
			BeanCopier.staticCopy(token, info);
			info.setIsValid(BooleanEnum.getByCode(token.getIsValid()));
			return info;
		}
		return null;
	}
	
	@Override
	public InsuranceBaseResult use(String accessToken) {
		
		InsuranceBaseResult result = new InsuranceBaseResult();
		try {
			sysWebAccessTokenDAO.invalidByToken(accessToken);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("禁用密钥出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public InsuranceBaseResult active(String accessToken) {
		
		InsuranceBaseResult result = new InsuranceBaseResult();
		try {
			sysWebAccessTokenDAO.validByToken(accessToken);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("激活密钥出错 {}", e);
		}
		
		return result;
	}
	
	@Override
	public InsuranceBaseResult addUserAccessToken(SysWebAccessTokenOrder order) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		try {
			
			SysWebAccessTokenDO token = new SysWebAccessTokenDO();
			BeanCopier.staticCopy(order, token);
			if (StringUtil.isBlank(token.getAccessToken())) {
				token.setAccessToken(UUID.randomUUID().toString());
				token.setIsValid(BooleanEnum.IS.code());
			}
			token.setRawAddTime(new Date());
			sysWebAccessTokenDAO.insert(token);
			result.setSuccess(true);
			result.setUrl(token.getAccessToken());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("新增密钥出错 {}", e);
		}
		
		return result;
	}
	
}
