package com.born.insurance.biz.service.customer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import com.born.insurance.biz.service.customer.CustomerPersonService;
import com.born.insurance.dal.daointerface.CustomerBaseInfoDAO;
import com.born.insurance.dal.daointerface.CustomerContactWayDAO;
import com.born.insurance.dal.daointerface.CustomerPersonDetailDAO;
import com.born.insurance.dal.daointerface.CustomerRelationDAO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.CustomerContactWayDO;
import com.born.insurance.dal.dataobject.CustomerPersonDetailDO;
import com.born.insurance.dal.dataobject.CustomerRelationDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.CompanyRelationInfoDO;
import com.born.insurance.dataobject.CompanyRelationInfoOrder;
import com.born.insurance.dataobject.CustomerPersonalDO;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.ContactTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerContactWayInfo;
import com.born.insurance.ws.info.customer.CustomerPersonInfo;
import com.born.insurance.ws.info.customer.CustomerRelationInfo;
import com.born.insurance.ws.order.customer.CustomerContactWayOrder;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.order.customer.CustomerRelationOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.CommonAttachmentService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("customerPersonService")
public class CustomerPersonServiceImpl extends CustomerBaseService implements CustomerPersonService {
	
	private final com.yjf.common.log.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CustomerBaseInfoDAO customerBaseInfoDAO;
	
	@Autowired
	private CustomerPersonDetailDAO customerPersonDetailDAO;
	
	@Autowired
	private CustomerRelationDAO customerRelationDAO;
	

	
	@Autowired
	private ExtraDAO extraDAO;

	
	@Override
	public QueryBaseBatchResult<CustomerPersonInfo> queryCommonPersonByCondition(	CustomerPersonQueryOrder queryOrder) {
		QueryBaseBatchResult<CustomerPersonInfo> batchResult = new QueryBaseBatchResult<CustomerPersonInfo>();
		
		try {
			queryOrder.check();
			List<CustomerPersonInfo> pageList = new ArrayList<CustomerPersonInfo>(
				(int) queryOrder.getPageSize());
			
			CustomerBaseInfoDO customerPersonalDO = new CustomerBaseInfoDO();
			BeanCopier.staticCopy(queryOrder, customerPersonalDO);
			if (StringUtils.isBlank(queryOrder.getCustomerType()) && !StringUtils.isBlank(queryOrder.getCompanyCustomer())) {
				customerPersonalDO.setCustomerType(queryOrder.getCompanyCustomer());
			}
			long totalCount = customerBaseInfoDAO.findByConditionCount(customerPersonalDO,queryOrder.getCompanys(),queryOrder.getRemoveCompanys());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<CustomerBaseInfoDO> recordList = customerBaseInfoDAO.findByCondition(
				customerPersonalDO,queryOrder.getCompanys(),queryOrder.getRemoveCompanys(), queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (CustomerBaseInfoDO item : recordList) {
				CustomerPersonInfo info = new CustomerPersonInfo();
				BeanCopier.staticCopy(item, info);
				if (StringUtil.isNotEmpty(item.getCertType())) {
					info.setCertType(CertTypeEnum.getByCode(item.getCertType()));
				}
				if (StringUtil.isNotEmpty(item.getCustomerType())) {
					info.setCustomerType(CustomerTypeEnum.getByCode(item.getCustomerType()));
				}
				CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO.findById(item.getCustomerId());
				if (null != customerPersonDetailDO) {
					info.setSex(customerPersonDetailDO.getSex());
					info.setBirthDay(customerPersonDetailDO.getBirthDay());
				}
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<CustomerPersonInfo> queryCustomerPersonByCondition(	CustomerPersonQueryOrder queryOrder) {
		QueryBaseBatchResult<CustomerPersonInfo> batchResult = new QueryBaseBatchResult<CustomerPersonInfo>();
		
		try {
			queryOrder.check();
			List<CustomerPersonInfo> pageList = new ArrayList<CustomerPersonInfo>(
				(int) queryOrder.getPageSize());
			
			CustomerPersonalDO customerPersonalDO = new CustomerPersonalDO();
			BeanCopier.staticCopy(queryOrder, customerPersonalDO);
			long totalCount = extraDAO.queryCustomerPersonCountByCondition(customerPersonalDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<CustomerPersonalDO> recordList = extraDAO.queryCustomerPersonByCondition(
				customerPersonalDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (CustomerPersonalDO item : recordList) {
				CustomerPersonInfo info = doConvertInfo(item);
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}
	
	private CustomerPersonInfo doConvertInfo(CustomerPersonalDO item) {
		CustomerPersonInfo info = new CustomerPersonInfo();
		BeanCopier.staticCopy(item, info);
		if (StringUtil.isNotEmpty(item.getCertType())) {
			info.setCertType(CertTypeEnum.getByCode(item.getCertType()));
		}
		if (StringUtil.isNotEmpty(item.getCustomerType())) {
			info.setCustomerType(CustomerTypeEnum.getByCode(item.getCustomerType()));
		}
		return info;
	}
	
	@Override
	public InsuranceBaseResult updateCustomerPerson(final CustomerPersonOrder customerPersonOrder) {
		return commonProcess(customerPersonOrder, "修改个人客户信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO
					.findById(customerPersonOrder.getUserId());
				String customerId = customerBaseInfoDO.getCustomerId();
				CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO
					.findById(customerId);
				customerPersonOrder.setCustomerId(customerId);
				BeanCopier.staticCopy(customerPersonOrder, customerBaseInfoDO);
				BeanCopier.staticCopy(customerPersonOrder, customerPersonDetailDO);
				if (customerPersonOrder.getCertType() != null) {
					customerBaseInfoDO.setCertType(customerPersonOrder.getCertType().code());
				}
				if (customerPersonOrder.getCustomerType() != null) {
					customerBaseInfoDO
						.setCustomerType(customerPersonOrder.getCustomerType().code());
				}
				setOptimalCustomerCert(customerBaseInfoDO, customerPersonOrder.getCertOrders());
				customerBaseInfoDO.setRawUpdateTime(new Date());
				customerBaseInfoDAO.update(customerBaseInfoDO);
				customerPersonDetailDAO.update(customerPersonDetailDO);
				storeCustomerContact(customerBaseInfoDO.getUserId(),
					customerPersonOrder.getMobileOrders(), true);
				storeCustomerContact(customerBaseInfoDO.getUserId(),
					customerPersonOrder.getEmailOrders(), false);
				storeCustomerContactQQ(customerBaseInfoDO.getUserId(),customerPersonOrder.getQqOrders(),false);
				storeCustomerContactWeiXin(customerBaseInfoDO.getUserId(),customerPersonOrder.getWeixinOrders(),false);
				storeCustomerContactCorp(customerBaseInfoDO.getUserId(),customerPersonOrder.getCorpOrders(),false);
				customerPersonOrder.setChildId(customerPersonOrder.getUserId());
				customerPersonOrder.setChildName(customerPersonOrder.getCustomerName());
				storeCustomerRelation(customerBaseInfoDO, customerPersonOrder);
				storeCustomerCert(customerBaseInfoDO, customerPersonOrder);
				storeCustomerContactAddress(customerBaseInfoDO.getUserId(),customerPersonOrder.getAddressOrders(),false);
				storeCustomerBank(customerPersonOrder.getUserId(),
					customerPersonOrder.getBankOrders());
				storeCustomerHisBill(customerBaseInfoDO,customerPersonOrder);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceBaseResult deleteCustomerPersonInfo(final CustomerPersonOrder customerPersonOrder) {
		return commonProcess(customerPersonOrder, "删除指定的个人用户信息以及其他关联信息",
			new BeforeProcessInvokeService() {
				@Override
				public Domain before() {
					customerPersonOrder.setChildId(customerPersonOrder.getUserId());
					customerBaseInfoDAO.deleteById(customerPersonOrder.getUserId());
					customerPersonDetailDAO.deleteById(customerPersonOrder.getCustomerId());
					customerContactWayDAO.deleteByUserId(customerPersonOrder.getUserId());
					customerBankInfoDAO.deleteByUserId(customerPersonOrder.getUserId());
					customerRelationDAO.deleteByChildId(customerPersonOrder.getChildId());
					return null;
				}
			}, null, null);
	}
	
	@Override
	public InsuranceBaseResult addCustomerPerson(final CustomerPersonOrder customerPersonOrder) {
		return commonProcess(customerPersonOrder, "添加个人客户列表信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String customerId = BusinessNumberUtil.gainOutBizNoNumber();
				customerPersonOrder.setCustomerId(customerId);
				CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
				CustomerPersonDetailDO customerPersonDetailDO = new CustomerPersonDetailDO();
				CustomerRelationDO customerRelationDO = new CustomerRelationDO();
				BeanCopier.staticCopy(customerPersonOrder, customerBaseInfoDO);
				BeanCopier.staticCopy(customerPersonOrder, customerPersonDetailDO);
				if (customerPersonOrder.getCertType() != null) {
					customerBaseInfoDO.setCertType(customerPersonOrder.getCertType().code());
				}
				if (customerPersonOrder.getCustomerType() != null) {
					customerBaseInfoDO
						.setCustomerType(customerPersonOrder.getCustomerType().code());
				}
				customerBaseInfoDO.setRawAddTime(new Date());
				setOptimalCustomerCert(customerBaseInfoDO, customerPersonOrder.getCertOrders());
				long userId = customerBaseInfoDAO.insert(customerBaseInfoDO);
				customerPersonDetailDAO.insert(customerPersonDetailDO);
				customerRelationDO.setParentName(customerPersonOrder.getParentName());
				customerRelationDO.setParentId(customerPersonOrder.getParentId());
				customerRelationDO.setChildId(userId);
				customerRelationDO.setChildName(customerPersonOrder.getCustomerName());
				customerRelationDAO.insert(customerRelationDO);
				storeCustomerContact(userId, customerPersonOrder.getMobileOrders(), true);
				storeCustomerContact(userId, customerPersonOrder.getEmailOrders(), false);
				storeCustomerContactQQ(userId,customerPersonOrder.getQqOrders(),false);
				storeCustomerContactWeiXin(userId,customerPersonOrder.getWeixinOrders(),false);
				storeCustomerContactCorp(userId,customerPersonOrder.getCorpOrders(),false);
				storeCustomerRelation(customerBaseInfoDO, customerPersonOrder);
				storeCustomerBank(userId, customerPersonOrder.getBankOrders());
				storeCustomerCert(customerBaseInfoDO, customerPersonOrder);
				storeCustomerContactAddress(customerBaseInfoDO.getUserId(),customerPersonOrder.getAddressOrders(),false);
				storeCustomerHisBill(customerBaseInfoDO,customerPersonOrder);
				return null;
			}
		}, null, null);
	}



	
	private void storeCustomerRelation(CustomerBaseInfoDO customerBaseInfoDO,
										CustomerPersonOrder customerPersonOrder) {
		customerRelationDAO.deleteByChildId(customerBaseInfoDO.getUserId());
		List<CustomerRelationOrder> relationOrders = customerPersonOrder.getRelationOrders();
		if (ListUtil.isNotEmpty(relationOrders)) {
			for (CustomerRelationOrder relationOrder : relationOrders) {
				CustomerRelationDO customerRelationDO = new CustomerRelationDO();
				BeanCopier.staticCopy(relationOrder, customerRelationDO);
				customerRelationDO.setChildId(customerBaseInfoDO.getUserId());
				customerRelationDO.setChildName(relationOrder.getChildName());
				customerRelationDO.setRawAddTime(getSysdate());
				customerRelationDAO.insert(customerRelationDO);
			}
		}
	}
	
	private void storeCustomerContact(long userId, List<CustomerContactWayOrder> wayOrders,
										boolean isDelete) {
		if (isDelete) {
			customerContactWayDAO.deleteByUserId(userId);
		}
		if (ListUtil.isNotEmpty(wayOrders)) {
			for (CustomerContactWayOrder wayOrder : wayOrders) {
				CustomerContactWayDO customerContactWayDO = new CustomerContactWayDO();
				BeanCopier.staticCopy(wayOrder, customerContactWayDO);
				customerContactWayDO.setUserId(userId);
				customerContactWayDAO.insert(customerContactWayDO);
			}
		}
	}

	private void storeCustomerContactQQ(long userId, List<CustomerContactWayOrder> wayOrders,
									  boolean isDelete) {
		if (isDelete) {
			customerContactWayDAO.deleteByUserId(userId);
		}
		if (ListUtil.isNotEmpty(wayOrders)) {
			for (CustomerContactWayOrder wayOrder : wayOrders) {
				CustomerContactWayDO customerContactWayDO = new CustomerContactWayDO();
				BeanCopier.staticCopy(wayOrder, customerContactWayDO);
				customerContactWayDO.setUserId(userId);
				customerContactWayDO.setType("qq");
				customerContactWayDAO.insert(customerContactWayDO);
			}
		}
	}

	private void storeCustomerContactWeiXin(long userId, List<CustomerContactWayOrder> wayOrders,
										boolean isDelete) {
		if (isDelete) {
			customerContactWayDAO.deleteByUserId(userId);
		}
		if (ListUtil.isNotEmpty(wayOrders)) {
			for (CustomerContactWayOrder wayOrder : wayOrders) {
				CustomerContactWayDO customerContactWayDO = new CustomerContactWayDO();
				BeanCopier.staticCopy(wayOrder, customerContactWayDO);
				customerContactWayDO.setUserId(userId);
				customerContactWayDO.setType("weixin");
				customerContactWayDAO.insert(customerContactWayDO);
			}
		}
	}

	private void storeCustomerContactCorp(long userId, List<CustomerContactWayOrder> wayOrders,
											boolean isDelete) {
		if (isDelete) {
			customerContactWayDAO.deleteByUserId(userId);
		}
		if (ListUtil.isNotEmpty(wayOrders)) {
			for (CustomerContactWayOrder wayOrder : wayOrders) {
				CustomerContactWayDO customerContactWayDO = new CustomerContactWayDO();
				BeanCopier.staticCopy(wayOrder, customerContactWayDO);
				customerContactWayDO.setUserId(userId);
				customerContactWayDO.setType("corp");
				customerContactWayDAO.insert(customerContactWayDO);
			}
		}
	}

	@Override
	public CustomerBaseInfo queryCustomerPersonByOwnerId(long UserId) {
		CustomerBaseInfoDO per = customerBaseInfoDAO.findByOwnerId(UserId);
		if (per != null) {
			CustomerBaseInfo customerPersonInfo = new CustomerBaseInfo();
			BeanCopier.staticCopy(per, customerPersonInfo);
			if (StringUtil.isNotEmpty(per.getCertType())) {
				customerPersonInfo.setCertType(CertTypeEnum.getByCode(per.getCertType()));
			}
			if (StringUtil.isNotEmpty(per.getCustomerType())) {
				customerPersonInfo
						.setCustomerType(CustomerTypeEnum.getByCode(per.getCustomerType()));
			}
			customerPersonInfo.setContactEmailInfos(getContactWayInfos(per, ContactTypeEnum.EMAIL));
			customerPersonInfo
					.setContactMobileInfos(getContactWayInfos(per, ContactTypeEnum.MOBILE));

	    	return customerPersonInfo;
		}

		return null;
	}

	@Override
	public CustomerPersonInfo queryCustomerPersonByUserId(long userId) {
		CustomerPersonalDO per = extraDAO.queryCustomerPersonByUserId(userId);
		if (per != null) {
			CustomerPersonInfo customerPersonInfo = new CustomerPersonInfo();
			BeanCopier.staticCopy(per, customerPersonInfo);
			if (StringUtil.isNotEmpty(per.getCertType())) {
				customerPersonInfo.setCertType(CertTypeEnum.getByCode(per.getCertType()));
			}
			if (StringUtil.isNotEmpty(per.getCustomerType())) {
				customerPersonInfo
					.setCustomerType(CustomerTypeEnum.getByCode(per.getCustomerType()));
			}
			customerPersonInfo.setContactEmailInfos(getContactWayInfos(per, ContactTypeEnum.EMAIL));
			customerPersonInfo
				.setContactMobileInfos(getContactWayInfos(per, ContactTypeEnum.MOBILE));

			customerPersonInfo.setContactQQInfos(getContactWayInfos(per,ContactTypeEnum.QQ));
			customerPersonInfo.setContactWeiXinInfos(getContactWayInfos(per,ContactTypeEnum.WEIXIN));
			customerPersonInfo.setAddressInfos(getContactWayInfos(per,ContactTypeEnum.ADDRESS));
			customerPersonInfo.setCorpInfos(getContactWayInfos(per,ContactTypeEnum.CORP));
			customerPersonInfo.setBankInfos(getBankInfos(userId));
			customerPersonInfo.setRelationInfos(getCustomerRelation(per));
			customerPersonInfo.setCertInfos(getCustomerCertInfo(userId));
			customerPersonInfo.setBillInfos(getCustomerHisBillInfo(userId));
			return customerPersonInfo;
		}
		
		return null;
	}
	
	private List<CustomerRelationInfo> getCustomerRelation(CustomerPersonalDO per) {
		
		CustomerRelationDO customerRelationDO = new CustomerRelationDO();
		customerRelationDO.setChildId(per.getUserId());
		List<CustomerRelationDO> bankInfoDOs = customerRelationDAO.findByCondition(
			customerRelationDO, null, null, 0, 1000);
		if (ListUtil.isNotEmpty(bankInfoDOs)) {
			List<CustomerRelationInfo> relationInfos = new ArrayList<CustomerRelationInfo>();
			for (CustomerRelationDO bankInfoDO : bankInfoDOs) {
				CustomerRelationInfo info = new CustomerRelationInfo();
				BeanCopier.staticCopy(bankInfoDO, info);
				relationInfos.add(info);
			}
			return relationInfos;
		}
		return null;
	}
	
	/**
	 * 查询客户的联系方式
	 * @param companyRelationInfoOrder
	 * @return
	 */

	
	//添加机构人员同时添加用户关系
	@Override
	public InsuranceBaseResult addRelationBaseInfo(	final CompanyRelationInfoOrder companyRelationInfoOrder) {
		return commonProcess(companyRelationInfoOrder, "添加个人客户列表和用户关系列表信息",
			new BeforeProcessInvokeService() {
				@Override
				public Domain before() {
					String customerId = BusinessNumberUtil.gainOutBizNoNumber();
					companyRelationInfoOrder.setCustomerId(customerId);
					CompanyRelationInfoDO companyRelationInfoDO = new CompanyRelationInfoDO();
					CustomerPersonDetailDO customerPersonDetailDO = new CustomerPersonDetailDO();
					CustomerRelationDO customerRelationDO = new CustomerRelationDO();
					CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
					BeanCopier.staticCopy(companyRelationInfoOrder, companyRelationInfoDO);
					BeanCopier.staticCopy(companyRelationInfoDO, customerBaseInfoDO);
					BeanCopier.staticCopy(companyRelationInfoOrder, customerPersonDetailDO);
					if (companyRelationInfoOrder.getCertType() != null) {
						companyRelationInfoDO.setCertType(companyRelationInfoOrder.getCertType());
					}
					companyRelationInfoDO.setCustomerType(CustomerTypeEnum.INDIVIDUAL_CUSTOMER
						.code());
					long childId = customerBaseInfoDAO.insert(customerBaseInfoDO);
					customerPersonDetailDAO.insert(customerPersonDetailDO);
					customerRelationDO.setParentId(companyRelationInfoOrder.getParentId());
					customerRelationDO.setChildId(childId);
					customerRelationDO.setRawAddTime(getSysdate());
					customerRelationDAO.insert(customerRelationDO);
					return null;
				}
			}, null, null);
	}
	
	@Override
	public CustomerBaseInfoDO findById(long userId) {
		return customerBaseInfoDAO.findById(userId);
	}

	@Override
	public CustomerBaseInfo findByCustomerUserId(long customerUserId) {
		CustomerBaseInfo info = new CustomerBaseInfo();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findById(customerUserId);
		if (null != customerBaseInfoDO) {
			CustomerPersonDetailDO customerPersonDetailDO = customerPersonDetailDAO.findById(customerBaseInfoDO.getCustomerId());
			if (null != customerPersonDetailDO) {
				info.setSex(customerPersonDetailDO.getSex());
				info.setBirth(customerPersonDetailDO.getBirthDay());
				info.setProvinceName(customerBaseInfoDO.getProvinceName());
				info.setCityName(customerBaseInfoDO.getCityName());
				info.setCountyName(customerBaseInfoDO.getCountyName());
			}
		}
		return info;
	}
	
}
