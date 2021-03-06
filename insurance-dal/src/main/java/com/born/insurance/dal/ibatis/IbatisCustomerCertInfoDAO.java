/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.insurance.dal.daointerface.CustomerCertInfoDAO;


// auto generated imports
import com.born.insurance.dal.dataobject.CustomerCertInfoDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.insurance.dal.daointerface.CustomerCertInfoDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/customer_cert_info.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisCustomerCertInfoDAO extends SqlMapClientDaoSupport implements CustomerCertInfoDAO {
	/**
	 *  Insert one <tt>CustomerCertInfoDO</tt> object to DB table <tt>customer_cert_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into customer_cert_info(id,user_id,customer_name,cert_type,cert_type_name,cert_no,cert_exp_date,long_time,sort_order) values (?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param customerCertInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(CustomerCertInfoDO customerCertInfo) throws DataAccessException {
    	if (customerCertInfo == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-CUSTOMER-CERT-INFO-INSERT", customerCertInfo);

        return customerCertInfo.getId();
    }

	/**
	 *  Update DB table <tt>customer_cert_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update customer_cert_info set user_id=?, customer_name=?, cert_type=?, cert_type_name=?, cert_no=?, cert_exp_date=?, long_time=?, sort_order=? where (id = ?)</tt>
	 *
	 *	@param customerCertInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(CustomerCertInfoDO customerCertInfo) throws DataAccessException {
    	if (customerCertInfo == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-CUSTOMER-CERT-INFO-UPDATE", customerCertInfo);
    }

	/**
	 *  Query DB table <tt>customer_cert_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, user_id, customer_name, cert_type, cert_type_name, cert_no, cert_exp_date, long_time, sort_order from customer_cert_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return CustomerCertInfoDO
	 *	@throws DataAccessException
	 */	 
    public CustomerCertInfoDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (CustomerCertInfoDO) getSqlMapClientTemplate().queryForObject("MS-CUSTOMER-CERT-INFO-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>customer_cert_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from customer_cert_info where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-CUSTOMER-CERT-INFO-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>customer_cert_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from customer_cert_info where (user_id = ?)</tt>
	 *
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByUserId(long userId) throws DataAccessException {
        Long param = new Long(userId);

        return getSqlMapClientTemplate().delete("MS-CUSTOMER-CERT-INFO-DELETE-BY-USER-ID", param);
    }

	/**
	 *  Query DB table <tt>customer_cert_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, user_id, customer_name, cert_type, cert_type_name, cert_no, cert_exp_date, long_time, sort_order from customer_cert_info where (1 = 1)</tt>
	 *
	 *	@param customerCertInfo
	 *	@param sortCol
	 *	@param sortOrder
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<CustomerCertInfoDO>
	 *	@throws DataAccessException
	 */	 
    public List<CustomerCertInfoDO> findByCondition(CustomerCertInfoDO customerCertInfo, String sortCol, String sortOrder, long limitStart, long pageSize) throws DataAccessException {
    	if (customerCertInfo == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("customerCertInfo", customerCertInfo);
        param.put("sortCol", sortCol);
        param.put("sortOrder", sortOrder);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-CUSTOMER-CERT-INFO-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>customer_cert_info</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from customer_cert_info where (1 = 1)</tt>
	 *
	 *	@param customerCertInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(CustomerCertInfoDO customerCertInfo) throws DataAccessException {
    	if (customerCertInfo == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-CUSTOMER-CERT-INFO-FIND-BY-CONDITION-COUNT", customerCertInfo);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}