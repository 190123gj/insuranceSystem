/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.daointerface;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.insurance.dal.dataobject.BillSettlementApplyDO;
import com.born.insurance.dal.dataobject.BusinessBillDO;
import com.born.insurance.dal.dataobject.CustomerRelationDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.dataobject.BillSettlementApplyFormDO;
import com.born.insurance.dataobject.BusinessBillFormDO;
import com.born.insurance.dataobject.ChargeNoticeFormDO;
import com.born.insurance.dataobject.CompanyInfoVo;
import com.born.insurance.dataobject.CompanyQueryDO;
import com.born.insurance.dataobject.CompanyRelationExtraDO;
import com.born.insurance.dataobject.CustomerCompanyDO;
import com.born.insurance.dataobject.CustomerPersonalDO;
import com.born.insurance.dataobject.CustomerProtocolVo;
import com.born.insurance.dataobject.InsuranceContactLetterExtraDO;
import com.born.insurance.dataobject.PriceContactLetterExtraDo;
import com.born.insurance.dataobject.ProjectSetupExtraDO;
import com.born.insurance.ws.info.businessBill.BusinessBillFormInfo;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * @Filename ExtraDAO.java
 * @Description 手动写的sql
 * @Version 1.0
 * @Author peigen
 * @Email peigen@yiji.com
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface ExtraDAO {
	
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public Date getSysdate();
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearMonthSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertDateSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearMonthSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * 
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	boolean selectDateSeq(String name, Date date) throws DataAccessException;





	
	boolean selectYearSeq(String name, Date date) throws DataAccessException;

	
	Long selectYearSeqNum(String name, Date date) throws DataAccessException;
	
	boolean selectYearMonthSeq(String name, Date date) throws DataAccessException;
	
	Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException;
	
	long queryCompanyInfoountByConditionCount(CompanyQueryDO queryOrder)throws DataAccessException;
	
	long queryRelationListByConditionCount(CompanyRelationExtraDO companyRelationExtraDO)throws DataAccessException;
	
	List<CompanyInfoVo> queryCompanyInfoountByCondition(CompanyQueryDO queryOrder,long limitStart,long pageSize)throws DataAccessException;
	
	List<CompanyRelationExtraDO> queryRelationListByCondition(CompanyRelationExtraDO companyRelationExtraDO,long limitStart,long pageSize)throws DataAccessException;
	
	public CustomerPersonalDO queryCustomerPersonByUserId(long userId) throws DataAccessException;

	public CustomerCompanyDO queryCustomerCompanyByUserId(long userId) throws DataAccessException;
	
	public List<CustomerPersonalDO> queryCustomerPersonByCondition(CustomerPersonalDO customerPersonalDO,String sortCol,String sortOrder,long limitStart,long pageSize) throws DataAccessException;

	public long queryCustomerPersonCountByCondition(CustomerPersonalDO customerPersonalDO) throws DataAccessException;
	
	public List<CustomerCompanyDO> queryCustomerCompanyByCondition(CustomerCompanyDO customerConpanyDO,String sortCol,String sortOrder,long limitStart,long pageSize,String isThirdParty,String abbr,String firstLevel) throws DataAccessException;
	
	public long queryCustomerCompanyCountByCondition(CustomerCompanyDO customerConpanyDO,String isThirdParty,String abbr,String firstLevel) throws DataAccessException;
	
	public CustomerRelationDO queryCustomerRelationByCondition(long parentId,long childId);
	
	public long updateCustomerRelationByCondition(CustomerRelationDO customerRelationDO);
	
	/**
	 * 查询协议列表
	 * <p>Title: queryCustomerProtocolList</p>
	 * <p>Description: </p>
	 * @param customerProtocolQueryOrder
	 * @param date 
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	public List<CustomerProtocolVo> queryCustomerProtocolList(CustomerProtocolQueryOrder customerProtocolQueryOrder,Date date, long limitStart,long pageSize) throws DataAccessException;
	/**
	 * 统计条数
	 * <p>Title: queryCustomerProtocolQueryOrderByConditionCount</p>
	 * <p>Description: </p>
	 * @param customerProtocolQueryOrder
	 * @param date 
	 * @return
	 */
	public long queryCustomerProtocolQueryOrderByConditionCount(CustomerProtocolQueryOrder customerProtocolQueryOrder, Date date);




	public List<PriceContactLetterExtraDo> findFormPriceContactLetterByCondition(PriceContactLetterExtraDo letterExtraDo,long limitStart,long pageSize);


	public long findFormPriceContactLetterByConditionCount(PriceContactLetterExtraDo letterExtraDo);

	public long findProjectSetupByConditionCount(ProjectSetupExtraDO projectSetupExtraDO,Date currentSystemTime);

	public List<ProjectSetupExtraDO> findProjectSetupByCondition(ProjectSetupExtraDO projectSetupExtraDO,Date currentSystemTime,
			String sortCol, String sortOrder, long limitStart, long pageSize);

	public long findInsuranceContactLetterByConditionCount(InsuranceContactLetterExtraDO insuranceContactLetterExtraDO);

	public List<InsuranceContactLetterExtraDO> findInsuranceContactLetterByCondition(
			InsuranceContactLetterExtraDO insuranceContactLetterExtraDO, String sortCol, String sortOrder,
			long limitStart, long pageSize);
	
	/**
	 * 统计佣金收入
	 * @param businessUserId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public String getInComeTotalSum(long businessUserId, Date beginDate, Date endDate);

	/**
	 * 
	 * @param businessUserId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public String getExtractTotalSum(long businessUserId, Date beginDate, Date endDate);
	
	/**
	 * 获取上期转结的金额
	 * @param businessUserId
	 * @param beginDate
	 * @return
	 */
	public String getLastTotal(long businessUserId, Date beginDate);

	/**
	 * 获取日期
	 * @param personCommissionDetailQueryOrder
	 * @return
	 */
	public List<String> getXAxisData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder);
	/**
	 * 收入佣金明细
	 * @param personCommissionDetailQueryOrder
	 * @param xAxisData 
	 * @return
	 */
	public List<BigDecimal> getPositiveData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder, List<String> xAxisData);

	/**
	 * 佣金提取明细
	 * @param personCommissionDetailQueryOrder
	 * @return
	 */
	public List<BigDecimal> getNegativexData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder,List<String> xAxisData);




	public Map<String,String> queryInsuranceProtocolCharge(long productId,String chargeType,long year);

	/**
	 * 统计保单
	 * @param businessBillDO
	 * @return
	 */
	public long findByConditionCount(BusinessBillFormDO businessBillDO);

	/**
	 *  查询保单列表
	 * @param businessBillDO
	 * @return
	 */
	public List<BusinessBillFormDO> findByCondition(BusinessBillFormDO businessBillDO, String sortCol, String sortOrder,
			long firstRecord, long pageSize);

	/**
	 * 根据保单号查询保单信息
	 * @param insuranceNo
	 * @return
	 */
	public BusinessBillFormDO findBusinessBillByNo(String insuranceNo);

	/**
	 * 结算单统计
	 * @param billSettlementApplyDO
	 * @return
	 */
	public long findByConditionCount(BillSettlementApplyFormDO billSettlementApplyDO);

	/**
	 * 结算清单查询
	 * @param billSettlementApplyDO
	 * @param sortCol
	 * @param sortOrder
	 * @param firstRecord
	 * @param pageSize
	 * @return
	 */
	public List<BillSettlementApplyFormDO> findByCondition(BillSettlementApplyFormDO billSettlementApplyDO, String sortCol,
			String sortOrder, long firstRecord, long pageSize);

	/**
	 * 结算通知单统计
	 * @param chargeNoticeFormDO
	 * @return
	 */
	public long findByConditionCount(ChargeNoticeFormDO chargeNoticeFormDO);
	
	/**
	 * 结算通知单查询
	 * @param chargeNoticeFormDO
	 * @param sortCol
	 * @param sortOrder
	 * @param firstRecord
	 * @param pageSize
	 * @return
	 */
	public List<ChargeNoticeFormDO> findByCondition(ChargeNoticeFormDO chargeNoticeFormDO, String sortCol,
			String sortOrder, long firstRecord, long pageSize);











}
