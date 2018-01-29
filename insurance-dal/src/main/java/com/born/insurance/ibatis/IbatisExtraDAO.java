/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.ibatis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.insurance.dal.dataobject.BillSettlementApplyDO;
import com.born.insurance.dal.dataobject.CustomerRelationDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.daointerface.ExtraDAO;
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
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.info.businessBill.BusinessBillFormInfo;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @Filename IbatisExtraDAO.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@SuppressWarnings("deprecation")
public class IbatisExtraDAO extends SqlMapClientDaoSupport implements ExtraDAO {
	
	/**
	 * @return
	 */
	@Override
	public Date getSysdate() {
		return (Date) getSqlMapClientTemplate().queryForObject("MS-COMMON-GET-SYSDATE");
	}
	
	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 *
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertYearSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearSeqNum(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearMonthSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}
	

	
	@Override
	public long getNextYearMonthSeq(String name, Date date, long cacheNumber)
																				throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@Override
	public long insertYearMonthSeq(String name, Date date) throws DataAccessException {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}

	@Override
	public long queryCompanyInfoountByConditionCount(
			CompanyQueryDO queryOrder) throws DataAccessException{
		if(queryOrder == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-COMPANY-SEELCT-COUNT",queryOrder);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<CompanyInfoVo> queryCompanyInfoountByCondition(
			CompanyQueryDO queryOrder, long limitStart, long pageSize)
			throws DataAccessException {
		if(queryOrder == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("queryOrder", queryOrder);
		param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-COMPANY-SEELCT",param);
	}

	
	
	
	
	
	@Override
	public List<CompanyRelationExtraDO> queryRelationListByCondition(
			CompanyRelationExtraDO companyRelationExtraDO, long limitStart,
			long pageSize) throws DataAccessException {
		if(companyRelationExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("queryOrder", companyRelationExtraDO);
		param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-RELATION-SEELCT-RELATION-LIST",param);
	}

	@Override
	public long queryRelationListByConditionCount(CompanyRelationExtraDO companyRelationExtraDO)
			throws DataAccessException {
		if(companyRelationExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-RELATION-SEELCT-COUNT",companyRelationExtraDO);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public CustomerPersonalDO queryCustomerPersonByUserId(long userId)
			throws DataAccessException {
		Map param=new HashMap();
		param.put("userId", userId);
		return (CustomerPersonalDO) getSqlMapClientTemplate().queryForObject("MS-PERSONAL-CUSTOMER-DETAIL-FIND-BY-ID", param);
	}

	@Override
	public CustomerCompanyDO queryCustomerCompanyByUserId(long userId)
			throws DataAccessException {
		Map param=new HashMap();
		param.put("userId", userId);
		return (CustomerCompanyDO) getSqlMapClientTemplate().queryForObject("MS-COMPANY-CUSTOMER-DETAIL-FIND-BY-ID", param);
	}
	

	@Override
	public CustomerRelationDO queryCustomerRelationByCondition(long parentId,long childId)  throws DataAccessException{
		Map param=new HashMap();
		param.put("parentId", parentId);
		param.put("childId", childId);
		return (CustomerRelationDO) getSqlMapClientTemplate().queryForObject("MS-CUSTOMER-RELATION-FIND-BY-ID-EXTRA", param);
	}





	@Override
	public List<CustomerPersonalDO> queryCustomerPersonByCondition(CustomerPersonalDO customerPersonalDO,	String sortCol,

			String sortOrder, long limitStart, long pageSize) throws DataAccessException {
		if(customerPersonalDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("customerPersonalDO", customerPersonalDO);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		return getSqlMapClientTemplate().queryForList("MS-PERSONAL-CUSTOMER-BY-CONDITION-EXTRA",param);
	}

	@Override
	public long queryCustomerPersonCountByCondition(CustomerPersonalDO customerPersonalDO) throws DataAccessException {
		if(customerPersonalDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("customerPersonalDO", customerPersonalDO);
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-PERSONAL-CUSTOMER-COUNT-BY-CONDITION-EXTRA",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<CustomerProtocolVo> queryCustomerProtocolList(CustomerProtocolQueryOrder customerProtocolQueryOrder,Date date ,long limitStart, long pageSize) throws DataAccessException {
		if(customerProtocolQueryOrder == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("queryOrder", customerProtocolQueryOrder);
		param.put("date", date);
		param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-CUSTOMER-SEELCT-CUSTOMERPROTOCOL-LIST",param);
	}

	@Override
	public long queryCustomerProtocolQueryOrderByConditionCount(CustomerProtocolQueryOrder customerProtocolQueryOrder,Date date ) {
		if(customerProtocolQueryOrder == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("date", date);
		param.put("queryOrder", customerProtocolQueryOrder);
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-CUSTOMER-SEELCT-CUSTOMERPROTOCOL-LIST-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<CustomerCompanyDO> queryCustomerCompanyByCondition(
			CustomerCompanyDO customerCompanyDO, String sortCol,
			String sortOrder, long limitStart, long pageSize,String isThirdParty,String abbr,String firstLevel)
			throws DataAccessException {
		if(customerCompanyDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("customerCompanyDO", customerCompanyDO);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("isThirdParty", isThirdParty);
		param.put("abbr", abbr);
		param.put("firstLevel", firstLevel);
		return getSqlMapClientTemplate().queryForList("MS-COMPANY-CUSTOMER-BY-CONDITION-EXTRA",param);
	}

	@Override
	public long queryCustomerCompanyCountByCondition(
			CustomerCompanyDO customerCompanyDO,String isThirdParty,String abbr,String firstLevel) throws DataAccessException {
		if(customerCompanyDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("customerCompanyDO", customerCompanyDO);
		param.put("isThirdParty", isThirdParty);
		param.put("abbr", abbr);
		param.put("firstLevel", firstLevel);
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-CUSTOMER-SEELCT-COMPANY-LIST-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public long updateCustomerRelationByCondition(CustomerRelationDO customerRelationDO) {
		if (customerRelationDO == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}
        return getSqlMapClientTemplate().update("MS-CUSTOMER-RELATION-UPDATE-EXTRA", customerRelationDO);
	}





	@Override
	public List<PriceContactLetterExtraDo> findFormPriceContactLetterByCondition(PriceContactLetterExtraDo letterExtraDo, long limitStart, long pageSize) {
		if(letterExtraDo == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("formId", letterExtraDo.getFormId());
		param.put("formStatus", letterExtraDo.getFormStatus());
		param.put("draftUserId",letterExtraDo.getDraftUserId());
		param.put("priceContactLetterExtra", letterExtraDo);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-PRICE-CONTACT-LETTER-BY-CONDITION",param);
	}

	@Override
	public long findFormPriceContactLetterByConditionCount(PriceContactLetterExtraDo letterExtraDo) {
		if(letterExtraDo == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("priceContactLetterExtra", letterExtraDo);
		param.put("formId", letterExtraDo.getFormId());
		param.put("formStatus", letterExtraDo.getFormStatus());
		param.put("draftUserId",letterExtraDo.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-PRICE-CONTACT-LETTER-BY-CONDITION-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public long findProjectSetupByConditionCount(ProjectSetupExtraDO projectSetupExtraDO,Date currentSystemTime) {
		if(projectSetupExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("projectSetupExtra", projectSetupExtraDO);
		param.put("currentSystemTime", currentSystemTime);
		param.put("formId", projectSetupExtraDO.getFormId());
		param.put("formStatus", projectSetupExtraDO.getFormStatus());
		param.put("draftUserId",projectSetupExtraDO.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-EXTRA-PROJECT-SET-UP-BY-CONDITION-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<ProjectSetupExtraDO> findProjectSetupByCondition(ProjectSetupExtraDO projectSetupExtraDO,Date currentSystemTime,
			String sortCol, String sortOrder, long limitStart, long pageSize) {
		if(projectSetupExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("currentSystemTime", currentSystemTime);
		param.put("formId", projectSetupExtraDO.getFormId());
		param.put("formStatus", projectSetupExtraDO.getFormStatus());
		param.put("draftUserId",projectSetupExtraDO.getDraftUserId());
		param.put("projectSetupExtra", projectSetupExtraDO);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("sortCol", sortCol);
		param.put("sortOrder", sortOrder);
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-PROJECT-SET-UP-CONDITION",param);
	}

	@Override
	public long findInsuranceContactLetterByConditionCount(
			InsuranceContactLetterExtraDO insuranceContactLetterExtraDO) {
		if(insuranceContactLetterExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("insuranceContactLetterExtra", insuranceContactLetterExtraDO);
		param.put("formId", insuranceContactLetterExtraDO.getFormId());
		param.put("formStatus", insuranceContactLetterExtraDO.getFormStatus());
		param.put("draftUserId",insuranceContactLetterExtraDO.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-INSURANCE-CONTACT-LETTER-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<InsuranceContactLetterExtraDO> findInsuranceContactLetterByCondition(
			InsuranceContactLetterExtraDO insuranceContactLetterExtraDO, String sortCol, String sortOrder,
			long limitStart, long pageSize) {
		if(insuranceContactLetterExtraDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("formId", insuranceContactLetterExtraDO.getFormId());
		param.put("formStatus", insuranceContactLetterExtraDO.getFormStatus());
		param.put("draftUserId",insuranceContactLetterExtraDO.getDraftUserId());
		param.put("insuranceContactLetterExtra", insuranceContactLetterExtraDO);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("sortCol", sortCol);
		param.put("sortOrder", sortOrder);
		return getSqlMapClientTemplate().queryForList("MS-INSURANCE-CONTACT-LETTER-CONDITION",param);
	}

	@Override
	public String getInComeTotalSum(long businessUserId, Date beginDate, Date endDate) {
		Map param=new HashMap();
		param.put("businessUserId", new Long(businessUserId));
		param.put("beginDate",beginDate);
		param.put("endDate",endDate);
		Object obj = getSqlMapClientTemplate().queryForObject("MS-PERSON-COMMISSION-IN-COME-COUNT",param);
		return null != obj ? obj.toString():"0";
	}

	@Override
	public String getExtractTotalSum(long businessUserId, Date beginDate, Date endDate) {
		Map param=new HashMap();
		param.put("businessUserId", new Long(businessUserId));
		param.put("beginDate",beginDate);
		param.put("endDate",endDate);
		Object obj = getSqlMapClientTemplate().queryForObject("MS-PERSON-COMMISSION-EXTRACTTOTAL-COUNT",param);
		return null != obj ? obj.toString():"0";
	}
	
	@Override
	public String getLastTotal(long businessUserId, Date beginDate) {
		Map param=new HashMap();
		param.put("businessUserId", new Long(businessUserId));
		param.put("beginDate",beginDate);
		Object obj = getSqlMapClientTemplate().queryForObject("MS-PERSON-COMMISSION-LASTTOTAL-COUNT",param);
		return null != obj ? obj.toString():"0";
	}

	@Override
	public List<String> getXAxisData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) {
		List<String> strList = new ArrayList<String>();
		Map param = new HashMap<>();
		param.put("businessUserId", new Long(personCommissionDetailQueryOrder.getBusinessUserId()));
		param.put("beginDate",personCommissionDetailQueryOrder.getBeginDate());
		param.put("endDate",personCommissionDetailQueryOrder.getEndDate());
		Object object = getSqlMapClientTemplate().queryForObject("MS-PERSON-COMMISSION-XAXISDATA-SELECT",param);
		if (null != object) {
			String commission_times = object.toString();
			String[] times = commission_times.split(",");
			for (String s : times) {
				strList.add(s);
			}
		}
		return strList;
	}

	@Override
	public List<BigDecimal> getPositiveData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder,List<String> xAxisData) {
		Map param = new HashMap<>();
		Map<String, BigDecimal> datas = new HashMap<>();
		param.put("businessUserId", new Long(personCommissionDetailQueryOrder.getBusinessUserId()));
		param.put("beginDate",personCommissionDetailQueryOrder.getBeginDate());
		param.put("endDate",personCommissionDetailQueryOrder.getEndDate());
		List queryForList = getSqlMapClientTemplate().queryForList("MS-PERSON-COMMISSION-POSITIVEDATA-SELECT",param);
		if (null != queryForList) {
			List<PersonCommissionDetailDO> personCommissionDetails = (List<PersonCommissionDetailDO>)queryForList;
			if (null != personCommissionDetails) {
				for (PersonCommissionDetailDO personCommissionDetailDO : personCommissionDetails) {
					datas.put(DateUtil.formatDay(personCommissionDetailDO.getCommissionTime()), personCommissionDetailDO.getCommissionAmount().getAmount());
				}
			}
		}
		List<BigDecimal> lists = new ArrayList<BigDecimal>(xAxisData.size());
		for (int i = 0; i < xAxisData.size(); i++) {
			if (datas.containsKey(xAxisData.get(i))) {
				BigDecimal bigDecimal = datas.get(xAxisData.get(i));
				lists.add(i, bigDecimal);
			} else {
				lists.add(i, null);
			}
			
		}
		
		return lists;
	}

	@Override
	public List<BigDecimal> getNegativexData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder,List<String> xAxisData) {
		Map param = new HashMap<>();
		Map<String, BigDecimal> datas = new HashMap<>();
		param.put("businessUserId", new Long(personCommissionDetailQueryOrder.getBusinessUserId()));
		param.put("beginDate",personCommissionDetailQueryOrder.getBeginDate());
		param.put("endDate",personCommissionDetailQueryOrder.getEndDate());
		List queryForList = getSqlMapClientTemplate().queryForList("MS-PERSON-COMMISSION-NEGATIVEXDATA-SELECT",param);
		if (null != queryForList) {
			List<PersonCommissionDetailDO> personCommissionDetails = (List<PersonCommissionDetailDO>)queryForList;
			if (null != personCommissionDetails) {
				for (PersonCommissionDetailDO personCommissionDetailDO : personCommissionDetails) {
					Money zero = Money.amout("0");
					datas.put(DateUtil.formatDay(personCommissionDetailDO.getCommissionTime()), zero.subtract(personCommissionDetailDO.getCommissionAmount()).getAmount());
				}
			}
		}
		List<BigDecimal> lists = new ArrayList<BigDecimal>(xAxisData.size());
		for (int i = 0; i < xAxisData.size(); i++) {
			if (datas.containsKey(xAxisData.get(i))) {
				BigDecimal bigDecimal = datas.get(xAxisData.get(i));
				lists.add(i, bigDecimal);
			} else {
				lists.add(i, null);
			}
			
		}
		return lists;
	}

	@Override
	public Map<String,String> queryInsuranceProtocolCharge(long productId,String chargeType,long year) {
		Map param=new HashMap();
		param.put("productId", new Long(productId));
		param.put("chargeType",chargeType);
		param.put("year",new Long(year));
		return (Map<String,String>) getSqlMapClientTemplate().queryForMap("select_into_map",param,"age","val");
	}

	@Override
	public long findByConditionCount(BusinessBillFormDO businessBillFormDO) {
		if(businessBillFormDO  == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("businessBillDO", businessBillFormDO);
		param.put("formId", businessBillFormDO.getFormId());
		param.put("formStatus", businessBillFormDO.getStatus());
		param.put("draftUserId",businessBillFormDO.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-BUSINESS-BILL-FORM-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<BusinessBillFormDO> findByCondition(BusinessBillFormDO businessBillFormDO, String sortCol, String sortOrder,
			long limitStart, long pageSize) {
		if(businessBillFormDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("businessBillFormDO", businessBillFormDO);
		param.put("formId", businessBillFormDO.getFormId());
		param.put("formStatus", businessBillFormDO.getFormStatus());
		param.put("draftUserId",businessBillFormDO.getDraftUserId());
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("sortCol", sortCol);
		param.put("sortOrder", sortOrder);
		return getSqlMapClientTemplate().queryForList("MS-BUSINESS-BILL-FORM-CONDITION",param);
	}

	@Override
	public BusinessBillFormDO findBusinessBillByNo(String insuranceNo) {
		Map param=new HashMap();
		param.put("insuranceNo", insuranceNo);
		List<BusinessBillFormDO> queryForList = getSqlMapClientTemplate().queryForList("MS-BUSINESS-BILL-FORM-CONDITION-BY-NO",param);
		return (queryForList == null || queryForList.size() == 0) ? null : queryForList.get(0); 
	}

	@Override
	public long findByConditionCount(BillSettlementApplyFormDO billSettlementApplyFormDO) {
		if(billSettlementApplyFormDO  == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("billSettlementApplyDO", billSettlementApplyFormDO);
		param.put("formId", billSettlementApplyFormDO.getFormId());
		param.put("formStatus", billSettlementApplyFormDO.getStatus());
		param.put("draftUserId",billSettlementApplyFormDO.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-BILL-SETTLEMENT-FORM-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<BillSettlementApplyFormDO> findByCondition(BillSettlementApplyFormDO billSettlementApplyFormDO, String sortCol,
			String sortOrder, long limitStart, long pageSize) {
		if(billSettlementApplyFormDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("businessBillFormDO", billSettlementApplyFormDO);
		param.put("formId", billSettlementApplyFormDO.getFormId());
		param.put("formStatus", billSettlementApplyFormDO.getFormStatus());
		param.put("draftUserId",billSettlementApplyFormDO.getDraftUserId());
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("sortCol", sortCol);
		param.put("sortOrder", sortOrder);
		return getSqlMapClientTemplate().queryForList("MS-BILL-SETTLEMENT-FORM-CONDITION",param);
	}

	@Override
	public long findByConditionCount(ChargeNoticeFormDO chargeNoticeFormDO) {
		if(chargeNoticeFormDO  == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("chargeNoticeFormDO", chargeNoticeFormDO);
		param.put("formId", chargeNoticeFormDO.getFormId());
		param.put("formStatus", chargeNoticeFormDO.getFormStatus());
		param.put("draftUserId",chargeNoticeFormDO.getDraftUserId());
		long retObj=(long) getSqlMapClientTemplate().queryForObject("MS-BILL-SETTLEMENT-NOTICE-FORM-COUNT",param);
		if(retObj==0){
			return 0;
		}else{
			return retObj;
		}
	}

	@Override
	public List<ChargeNoticeFormDO> findByCondition(ChargeNoticeFormDO chargeNoticeFormDO, String sortCol,
			String sortOrder, long limitStart, long pageSize) {
		if(chargeNoticeFormDO == null){
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		Map param=new HashMap();
		param.put("chargeNoticeFormDO", chargeNoticeFormDO);
		param.put("formId", chargeNoticeFormDO.getFormId());
		param.put("draftUserId",chargeNoticeFormDO.getDraftUserId());
		param.put("limitStart", new Long(limitStart));
		param.put("formStatus", chargeNoticeFormDO.getFormStatus());
		param.put("pageSize", new Long(pageSize));
		param.put("sortCol", sortCol);
		param.put("sortOrder", sortOrder);
		return getSqlMapClientTemplate().queryForList("MS-BILL-SETTLEMENT-NOTICE-FORM-CONDITION",param);
	}

}
