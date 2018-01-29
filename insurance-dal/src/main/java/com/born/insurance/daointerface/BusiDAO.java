/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.daointerface;

import java.util.List;

import com.born.insurance.dataobject.BackTaskDO;
import org.springframework.dao.DataAccessException;

/**
 * @Filename BusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-7-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface BusiDAO {
	
	/**
	 * 驳回的任务列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	List<BackTaskDO> backTaskList(long userId) throws DataAccessException;

	

}
