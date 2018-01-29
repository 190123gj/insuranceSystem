/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:14:20 创建
 */
package com.born.insurance.biz.service.user;

import java.util.ArrayList;
import java.util.List;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.exception.InsuranceBizException;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.insurance.dal.dataobject.UserExtraMessageDO;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.user.UserExtraMessageInfo;
import com.born.insurance.ws.order.user.UserExtraMessageAddOrder;
import com.born.insurance.ws.order.user.UserExtraMessageOrder;
import com.born.insurance.ws.order.user.UserExtraMessageQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.user.UserExtraMessageResult;
import com.born.insurance.ws.service.user.UserExtraMessageService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("userExtraMessageService")
public class UserExtraMessageServiceImpl extends BaseFormAutowiredDomainService implements
																				UserExtraMessageService {
	
	/**
	 * @param userAccount
	 * @return
	 * @see UserExtraMessageService#findByUserAccount(java.lang.String)
	 */
	@Override
	public UserExtraMessageResult findByUserAccount(String userAccount) {
		logger.info("进入userExtraMessageService的findByUserAccount方法，入参：" + userAccount);
		UserExtraMessageResult result = new UserExtraMessageResult();
		if (StringUtil.isBlank(userAccount)) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			return result;
		}
		try {
			UserExtraMessageDO userExtraMessageDO = userExtraMessageDAO.findByAccount(userAccount);
			UserExtraMessageInfo messageInfo = convertDO2Info(userExtraMessageDO);
			result.setUserExtraMessageInfo(messageInfo);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error("数据库异常：" + e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		} catch (Exception e) {
			logger.error("未知异常：" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private UserExtraMessageInfo convertDO2Info(UserExtraMessageDO userExtraMessageDO) {
		UserExtraMessageInfo info = new UserExtraMessageInfo();
		MiscUtil.copyPoObject(info, userExtraMessageDO);
		return info;
	}
	
	/**
	 * @param userId
	 * @return
	 */
	@Override
	public UserExtraMessageResult findByUserId(Long userId) {
		logger.info("进入userExtraMessageService的findByUserId方法，入参：" + userId);
		UserExtraMessageResult result = new UserExtraMessageResult();
		if (userId <= 0) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			return result;
		}
		try {
			UserExtraMessageDO userExtraMessageDO = userExtraMessageDAO.findByUserId(userId);
			UserExtraMessageInfo messageInfo = convertDO2Info(userExtraMessageDO);
			result.setUserExtraMessageInfo(messageInfo);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error("数据库异常：" + e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		} catch (Exception e) {
			logger.error("未知异常：" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 * @see UserExtraMessageService#findById(java.lang.Long)
	 */
	@Override
	public UserExtraMessageResult findById(Long id) {
		logger.info("进入userExtraMessageService的findById方法，入参：" + id);
		UserExtraMessageResult result = new UserExtraMessageResult();
		if (id <= 0) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			return result;
		}
		try {
			UserExtraMessageDO userExtraMessageDO = userExtraMessageDAO.findById(id);
			UserExtraMessageInfo messageInfo = convertDO2Info(userExtraMessageDO);
			result.setUserExtraMessageInfo(messageInfo);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error("数据库异常：" + e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		} catch (Exception e) {
			logger.error("未知异常：" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see UserExtraMessageService#insert(UserExtraMessageAddOrder)
	 */
	@Override
	public UserExtraMessageResult insert(UserExtraMessageAddOrder order) {
		logger.info("进入userExtraMessageService的insert方法，入参：" + order);
		UserExtraMessageResult result = new UserExtraMessageResult();
		try {
			order.check();
			// 判定是否已有数据，若有，直接报错
			UserExtraMessageDO findByUserIdDO = userExtraMessageDAO.findByUserId(order.getUserId());
			if (findByUserIdDO == null) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.USER_EXIST,
					"该用户id数据已存在！");
			}
			UserExtraMessageDO findByUserAccountDO = userExtraMessageDAO.findByAccount(order
				.getUserAccount());
			if (findByUserAccountDO == null) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.USER_EXIST,
					"该用户登录名数据已存在！");
			}
			UserExtraMessageDO messageDO = convertAddOrder2DO(order);
			messageDO.setRawAddTime(getSysdate());
			userExtraMessageDAO.insert(messageDO);
			result.setSuccess(true);
		} catch (InsuranceBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setInsuranceResultEnum(eex.getResultCode());
			result.setMessage("插入数据异常[" + eex.getErrorMsg() + "]");
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("未知异常：" + e.getMessage(), e);
		}
		return result;
	}
	
	private UserExtraMessageDO convertAddOrder2DO(UserExtraMessageAddOrder order) {
		UserExtraMessageDO messageDO = new UserExtraMessageDO();
		MiscUtil.copyPoObject(messageDO, order);
		messageDO.setUserId(order.getUserId());
		return messageDO;
	}
	
	/**
	 * @param order
	 * @return
	 * @see UserExtraMessageService#insertOrUpdate(UserExtraMessageAddOrder)
	 */
	@Override
	public UserExtraMessageResult insertOrUpdate(UserExtraMessageAddOrder order) {
		logger.info("进入userExtraMessageService的insertOrUpdate方法，入参：" + order);
		UserExtraMessageResult result = new UserExtraMessageResult();
		try {
			order.check();
			// 判定  若已有数据，选择修订原数据;若和N条数据冲突，选择报错
			UserExtraMessageDO findByUserIdDO = userExtraMessageDAO.findByUserId(order.getUserId());
			UserExtraMessageDO findByUserAccountDO = userExtraMessageDAO.findByAccount(order
				.getUserAccount());
			if (findByUserIdDO != null && findByUserAccountDO != null
				&& findByUserIdDO.getId() != findByUserAccountDO.getId()) {
				throw ExceptionFactory.newFcsException(InsuranceResultEnum.USER_EXIST, "该用户数据已重复！");
			} else if (findByUserIdDO != null) {
				// 已有数据 更新
				UserExtraMessageDO messageDO = convertAddOrder2DO(order);
				messageDO.setId(findByUserIdDO.getId());
				userExtraMessageDAO.update(messageDO);
			} else if (findByUserAccountDO != null) {
				// 已有数据 更新
				UserExtraMessageDO messageDO = convertAddOrder2DO(order);
				messageDO.setId(findByUserAccountDO.getId());
				userExtraMessageDAO.update(messageDO);
			} else {
				// 无任何数据，插入
				UserExtraMessageDO messageDO = convertAddOrder2DO(order);
				messageDO.setRawAddTime(getSysdate());
				userExtraMessageDAO.insert(messageDO);
			}
			result.setSuccess(true);
		} catch (InsuranceBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setInsuranceResultEnum(eex.getResultCode());
			result.setMessage("插入数据异常[" + eex.getErrorMsg() + "]");
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("未知异常：" + e.getMessage(), e);
			result.setMessage("未知异常：" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see UserExtraMessageService#update(UserExtraMessageOrder)
	 */
	@Override
	public UserExtraMessageResult update(UserExtraMessageOrder order) {
		logger.info("进入userExtraMessageService的update方法，入参：" + order);
		UserExtraMessageResult result = new UserExtraMessageResult();
		try {
			order.check();
			UserExtraMessageDO messageDO = new UserExtraMessageDO();
			MiscUtil.copyPoObject(messageDO, order);
			userExtraMessageDAO.update(messageDO);
			
			result.setSuccess(true);
		} catch (InsuranceBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setInsuranceResultEnum(eex.getResultCode());
			result.setMessage("更改数据异常[" + eex.getErrorMsg() + "]");
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("未知异常：" + e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<UserExtraMessageInfo> queryUserExtraMessage(UserExtraMessageQueryOrder order) {
		logger.info("进入userExtraMessageService的queryUserExtraMessage方法，入参：" + order);
		QueryBaseBatchResult<UserExtraMessageInfo> result = new QueryBaseBatchResult<UserExtraMessageInfo>();
		
		try {
			UserExtraMessageDO queryDO = new UserExtraMessageDO();
			BeanCopier.staticCopy(order, queryDO);
			
			long totalCount = 0;
			// 查询totalcount
			totalCount = userExtraMessageDAO.findByConditionCount(queryDO);
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<UserExtraMessageDO> list = Lists.newArrayList();
			list = userExtraMessageDAO.findByCondition(queryDO, order.getLimitStart(),
				order.getPageSize());
			
			List<UserExtraMessageInfo> pageList = new ArrayList<UserExtraMessageInfo>();
			for (UserExtraMessageDO item : list) {
				UserExtraMessageInfo info = convertDO2Info(item);
				
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询内部借款单信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
}
