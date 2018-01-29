/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dal.common;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @Filename MoneyTypeHandlerCallback.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author zjl
 * 
 * @Email zjialin@yiji.com
 * 
 * @History <li>Author: zjl</li> <li>Date: 2013-6-7</li> <li>Version: 1.0</li>
 *          <li>Content: create</li>
 * 
 */
public class MoneyTypeHandlerCallback implements TypeHandlerCallback {

	@Override
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		setter.setLong(((Money) parameter).getCent());
	}

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		Money money = new Money();
		money.setCent(Long.valueOf(getter.getLong()));
		return money;
	}

	@Override
	public Object valueOf(String s) {
		return null;
	}

}
