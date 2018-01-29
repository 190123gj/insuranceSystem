package com.born.insurance.dal.daointerface;

import java.util.List;
import java.util.Map;

import com.born.insurance.dal.dataobject.OperatorInfoDO;

public interface IOperatorInfoDao {

	long addOperatorInfo(OperatorInfoDO info);

	List<OperatorInfoDO> queryOperatorsByProperties(Map<String, Object> conditions);

	long queryOperatorsByPropertiesCount(Map<String, Object> conditions);

	// 更新
	int updateOperatorInfo(Map<String, Object> conditions);

	boolean isFromSameOrgan(Map<String, Object> conditions);

	int deleteAutotest(long baseId);

}
