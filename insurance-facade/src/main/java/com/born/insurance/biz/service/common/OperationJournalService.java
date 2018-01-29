/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.info.common.OperationJournalInfo;
import com.born.insurance.ws.order.common.OperationJournalAddOrder;
import com.born.insurance.ws.order.common.OperationJournalQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.OperationJournalServiceResult;

/**
 * 
 * @Filename OperationJournalService.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author jiajie
 * 
 * @Email hjiajie@yiji.com
 * 
 * @History <li>Author: jiajie</li> <li>Date: 2013-1-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@WebService
public interface OperationJournalService {
	
	/**
	 * 添加一条操作日志
	 * @param operationJournalAddOrder
	 * @return
	 */
	public OperationJournalServiceResult addOperationJournalInfo(	OperationJournalAddOrder operationJournalAddOrder);
	
	QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(	OperationJournalQueryOrder queryOrder);
	
}
