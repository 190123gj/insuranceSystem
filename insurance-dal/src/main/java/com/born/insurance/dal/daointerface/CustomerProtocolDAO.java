/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dal.daointerface;

// auto generated imports
import com.born.insurance.dal.dataobject.CustomerProtocolDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>customer_protocol</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/customer_protocol.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface CustomerProtocolDAO {
	/**
	 *  Insert one <tt>CustomerProtocolDO</tt> object to DB table <tt>customer_protocol</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into customer_protocol(id,name,no,customer_user_id,customer_user_name,cert_type,cert_no,beginDate,endDate,status,remark,relation_protocol_id,creator_id,creator_name,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param customerProtocol
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(CustomerProtocolDO customerProtocol) throws DataAccessException;

	/**
	 *  Update DB table <tt>customer_protocol</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update customer_protocol set name=?, no=?, customer_user_id=?, customer_user_name=?, cert_type=?, cert_no=?, beginDate=?, endDate=?, status=?, remark=?, relation_protocol_id=?, creator_id=?, creator_name=? where (id = ?)</tt>
	 *
	 *	@param customerProtocol
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(CustomerProtocolDO customerProtocol) throws DataAccessException;

	/**
	 *  Query DB table <tt>customer_protocol</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, name, no, customer_user_id, customer_user_name, cert_type, cert_no, beginDate, endDate, status, remark, relation_protocol_id, creator_id, creator_name, raw_add_time, raw_update_time from customer_protocol where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return CustomerProtocolDO
	 *	@throws DataAccessException
	 */	 
    public CustomerProtocolDO findById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>customer_protocol</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from customer_protocol where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>customer_protocol</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, name, no, customer_user_id, customer_user_name, cert_type, cert_no, beginDate, endDate, status, remark, relation_protocol_id, creator_id, creator_name, raw_add_time, raw_update_time from customer_protocol where (1 = 1)</tt>
	 *
	 *	@param customerProtocol
	 *	@param sortCol
	 *	@param sortOrder
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<CustomerProtocolDO>
	 *	@throws DataAccessException
	 */	 
    public List<CustomerProtocolDO> findByCondition(CustomerProtocolDO customerProtocol, String sortCol, String sortOrder, long limitStart, long pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>customer_protocol</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from customer_protocol where (1 = 1)</tt>
	 *
	 *	@param customerProtocol
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(CustomerProtocolDO customerProtocol) throws DataAccessException;

}