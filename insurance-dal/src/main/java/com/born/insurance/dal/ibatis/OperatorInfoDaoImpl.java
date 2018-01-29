package com.born.insurance.dal.ibatis;

import java.util.List;
import java.util.Map;

import com.born.insurance.dal.daointerface.IOperatorInfoDao;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.insurance.dal.dataobject.OperatorInfoDO;

public class OperatorInfoDaoImpl extends SqlMapClientDaoSupport implements IOperatorInfoDao {
	@Override
	public long addOperatorInfo(OperatorInfoDO info) {
		return (Long) getSqlMapClientTemplate().insert("MS-OPERATOR-INFO-INSERT", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatorInfoDO> queryOperatorsByProperties(Map<String, Object> conditions) {

		return getSqlMapClientTemplate().queryForList("MS-OPERATOR-INFO-QUERYLISTBYCONDITIONS",
				conditions);
	}

	@Override
	public long queryOperatorsByPropertiesCount(Map<String, Object> conditions) {

		return (Long) getSqlMapClientTemplate().queryForObject(
				"MS-OPERATOR-INFO-QUERYLISTBYCONDITIONSCOUNT", conditions);
	}

	@Override
	public int updateOperatorInfo(Map<String, Object> conditions) {
		return getSqlMapClientTemplate().update("MS-OPERATOR-INFO-UPDATE", conditions);
	}

	@Override
	public boolean isFromSameOrgan(Map<String, Object> conditions) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				"MS-OPERATOR-INFO-FROM-SAME-ORGAN-ID", conditions) > 0;
	}

	@Override
	public int deleteAutotest(long baseId) throws DataAccessException {
		Long param = new Long(baseId);
		return getSqlMapClientTemplate().delete("MS-OPERATOR-INFO-DELETE", param);
	}

}
