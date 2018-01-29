/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:54:16 创建
 */
package com.born.insurance.ws.service.user;

import javax.jws.WebService;

import com.born.insurance.ws.info.user.UserExtraMessageInfo;
import com.born.insurance.ws.order.user.UserExtraMessageAddOrder;
import com.born.insurance.ws.order.user.UserExtraMessageOrder;
import com.born.insurance.ws.order.user.UserExtraMessageQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.user.UserExtraMessageResult;

/**
 * 
 * 用户额外信息维护
 * @author hjiajie
 * 
 */
@WebService
public interface UserExtraMessageService {
	
	/**
	 * 查询用户额外属性
	 * @param userAccount
	 * @return
	 */
	UserExtraMessageResult findByUserAccount(String userAccount);
	
	/**
	 * 查询用户额外属性
	 * @param userAccount
	 * @return
	 */
	UserExtraMessageResult findByUserId(Long userId);
	
	/**
	 * 查询用户额外属性
	 * @param userAccount
	 * @return
	 */
	UserExtraMessageResult findById(Long id);
	
	/**
	 * 插入一条数据(若已有数据，选择不修改并报错结束.)
	 * @param order
	 * @return
	 */
	UserExtraMessageResult insert(UserExtraMessageAddOrder order);
	
	/**
	 * 插入一条数据(若已有数据，选择修订原数据;若和N条数据冲突，选择报错)
	 * @param order
	 * @return
	 */
	UserExtraMessageResult insertOrUpdate(UserExtraMessageAddOrder order);
	
	/**
	 * 修改数据
	 * @param order
	 * @return
	 */
	UserExtraMessageResult update(UserExtraMessageOrder order);
	
	/** 列查 */
	QueryBaseBatchResult<UserExtraMessageInfo> queryUserExtraMessage(	UserExtraMessageQueryOrder order);
	
}
