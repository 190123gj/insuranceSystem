/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.insurance.daointerface.BusiDAO;
import com.born.insurance.dataobject.BackTaskDO;

/**
 * @Filename IbatisBusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-7-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@SuppressWarnings("deprecation")
public class IbatisBusiDAO extends SqlMapClientDaoSupport implements BusiDAO {
	

	@SuppressWarnings("unchecked")
	@Override
	public List<BackTaskDO> backTaskList(long userId) throws DataAccessException {
		List<BackTaskDO> list = getSqlMapClientTemplate().queryForList("MS-BUSI-BACK-TASK", userId);
		return list;
	}
	

}
