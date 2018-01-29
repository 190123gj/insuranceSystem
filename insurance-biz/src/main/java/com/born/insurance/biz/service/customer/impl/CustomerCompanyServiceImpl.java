package com.born.insurance.biz.service.customer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.insurance.ws.enums.ContactTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.customer.CustomerCompanyService;
import com.born.insurance.dal.daointerface.CustomerBankInfoDAO;
import com.born.insurance.dal.daointerface.CustomerBaseInfoDAO;
import com.born.insurance.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.insurance.dal.daointerface.ValueTaxInfoDAO;
import com.born.insurance.dal.dataobject.CustomerBankInfoDO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.CustomerCompanyDetailDO;
import com.born.insurance.dal.dataobject.ValueTaxInfoDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.CustomerCompanyDO;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.customer.CustomerBankInfo;
import com.born.insurance.ws.info.customer.CustomerBaseInfo;
import com.born.insurance.ws.info.customer.CustomerCompanyInfo;
import com.born.insurance.ws.info.customer.ValueTaxInfo;
import com.born.insurance.ws.order.customer.CustomerBankInfoOrder;
import com.born.insurance.ws.order.customer.CustomerBankInfoQueryOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyOrder;
import com.born.insurance.ws.order.customer.CustomerCompanyQueryOrder;
import com.born.insurance.ws.order.customer.ValueTaxInfoOrder;
import com.born.insurance.ws.order.customer.ValueTaxInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("customerCompanyService")
public class CustomerCompanyServiceImpl extends CustomerBaseService implements
																	CustomerCompanyService {
	
	private final com.yjf.common.log.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CustomerCompanyDetailDAO customerCompanyDetailDAO;
	
	@Autowired
	private CustomerBaseInfoDAO customerBaseInfoDAO;
	

	

	@Override
	public QueryBaseBatchResult<CustomerCompanyInfo> queryList(	CustomerCompanyQueryOrder customerCompanyQueryOrder) {
		QueryBaseBatchResult<CustomerCompanyInfo> baseBatchResult = new QueryBaseBatchResult<CustomerCompanyInfo>();
		try {
			customerCompanyQueryOrder.check();
			List<CustomerCompanyInfo> pageList = new ArrayList<CustomerCompanyInfo>(
				(int) customerCompanyQueryOrder.getPageSize());
			CustomerCompanyDO customerCompanyDO = new CustomerCompanyDO();
			BeanCopier.staticCopy(customerCompanyQueryOrder, customerCompanyDO);
			if (customerCompanyQueryOrder.getCertType() != null) {
				customerCompanyDO.setCertType(customerCompanyQueryOrder.getCertType().code());
			}
			if (customerCompanyQueryOrder.getCustomerType() != null) {
				customerCompanyDO.setCustomerType(customerCompanyQueryOrder.getCustomerType()
					.getCode());
			}
			long totalCount = extraDAO.queryCustomerCompanyCountByCondition(customerCompanyDO,
				customerCompanyQueryOrder.getIsThirdParty(),customerCompanyQueryOrder.getAbbr(),customerCompanyQueryOrder.getFirstLevel());
			PageComponent component = new PageComponent(customerCompanyQueryOrder, totalCount);
			List<CustomerCompanyDO> list = extraDAO.queryCustomerCompanyByCondition(
				customerCompanyDO, customerCompanyQueryOrder.getSortCol(),
				customerCompanyQueryOrder.getSortOrder(),
				customerCompanyQueryOrder.getLimitStart(), customerCompanyQueryOrder.getPageSize(),
				customerCompanyQueryOrder.getIsThirdParty(),customerCompanyQueryOrder.getAbbr(),customerCompanyQueryOrder.getFirstLevel());
			for (CustomerCompanyDO DO : list) {
				CustomerCompanyInfo info = new CustomerCompanyInfo();
				BeanCopier.staticCopy(DO, info);
				if (StringUtil.isNotEmpty(DO.getCertType())) {
					info.setCertType(CertTypeEnum.getByCode(DO.getCertType()));
				}
				if (StringUtil.isNotEmpty(DO.getCustomerType())) {
					info.setCustomerType(CustomerTypeEnum.getByCode(DO.getCustomerType()));
				}
				pageList.add(info);
			}
			baseBatchResult.initPageParam(component);
			baseBatchResult.setPageList(pageList);
			baseBatchResult.setSuccess(true);
		} catch (IllegalArgumentException e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			baseBatchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			baseBatchResult.setSuccess(false);
			baseBatchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return baseBatchResult;
	}
	
	@Override
	public InsuranceBaseResult updateCustomerCompany(final CustomerCompanyOrder customerCompanyOrder) {
		return commonProcess(customerCompanyOrder, "修改企业客户信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO
					.findById(customerCompanyOrder.getUserId());
				String customerId = customerBaseInfoDO.getCustomerId();
				CustomerCompanyDetailDO customerCompanyDetailDO = customerCompanyDetailDAO
					.findById(customerId);
				customerCompanyOrder.setCustomerId(customerId);
				BeanCopier.staticCopy(customerCompanyOrder, customerBaseInfoDO);
				BeanCopier.staticCopy(customerCompanyOrder, customerCompanyDetailDO);
				if (customerCompanyOrder.getCertType() != null) {
					customerBaseInfoDO.setCertType(customerCompanyOrder.getCertType().code());
				}
				if (customerCompanyOrder.getCustomerType() != null) {
					customerBaseInfoDO.setCustomerType(customerCompanyOrder.getCustomerType()
						.code());
				}
				setOptimalCustomerCert(customerBaseInfoDO,customerCompanyOrder.getCertOrders());
				customerBaseInfoDAO.update(customerBaseInfoDO);
				customerCompanyDetailDAO.update(customerCompanyDetailDO);
				storeCustomerCert(customerBaseInfoDO,customerCompanyOrder);
				storeCustomerContactAddress(customerBaseInfoDO.getUserId(),customerCompanyOrder.getAddressOrders(),true);
				storeCustomerHisBill(customerBaseInfoDO,customerCompanyOrder);
				storeCustomerBank(customerCompanyOrder.getUserId(),
					customerCompanyOrder.getBankOrders());
				storeValueTaxInfo(customerCompanyOrder);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceBaseResult addCustomerCompany(final CustomerCompanyOrder customerCompanyOrder) {
		return commonProcess(customerCompanyOrder, "添加企业客户信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String customerId = BusinessNumberUtil.gainOutBizNoNumber();
				customerCompanyOrder.setCustomerId(customerId);
				CustomerCompanyDetailDO customerCompanyDetailDO = new CustomerCompanyDetailDO();
				CustomerBaseInfoDO customerBaseInfoDO = new CustomerBaseInfoDO();
				BeanCopier.staticCopy(customerCompanyOrder, customerBaseInfoDO);
				BeanCopier.staticCopy(customerCompanyOrder, customerCompanyDetailDO);
				if (customerCompanyOrder.getCertType() != null) {
					customerBaseInfoDO.setCertType(customerCompanyOrder.getCertType().code());
				}
				if (customerCompanyOrder.getCustomerType() != null) {
					customerBaseInfoDO.setCustomerType(CustomerTypeEnum
						.getCode(customerCompanyOrder.getCustomerType()));
				}
				customerBaseInfoDO.setRawAddTime(new Date());
				customerCompanyDetailDAO.insert(customerCompanyDetailDO);
				setOptimalCustomerCert(customerBaseInfoDO,customerCompanyOrder.getCertOrders());
				long customerUserId = customerBaseInfoDAO.insert(customerBaseInfoDO);
				customerCompanyOrder.setUserId(customerUserId);
				storeValueTaxInfo(customerCompanyOrder);
				storeCustomerBank(customerUserId, customerCompanyOrder.getBankOrders());
				storeCustomerCert(customerBaseInfoDO,customerCompanyOrder);
				storeCustomerContactAddress(customerUserId,customerCompanyOrder.getAddressOrders(),true);
				storeCustomerHisBill(customerBaseInfoDO,customerCompanyOrder);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public CustomerCompanyInfo queryCustomerCompanyByUserId(long userId) {
		CustomerCompanyDO cusDO = extraDAO.queryCustomerCompanyByUserId(userId);
		if (cusDO != null) {
			CustomerCompanyInfo info = new CustomerCompanyInfo();
			BeanCopier.staticCopy(cusDO, info);
			if (StringUtil.isNotEmpty(cusDO.getCertType())) {
				info.setCertType(CertTypeEnum.getByCode(cusDO.getCertType()));
			}
			info.setCertInfos(getCustomerCertInfo(cusDO.getUserId()));
			info.setAddressInfos(getContactWayInfos(cusDO, ContactTypeEnum.ADDRESS));
			info.setBillInfos(getCustomerHisBillInfo(userId));
			info.setBankInfos(getBankInfos(userId));
			info.setTaxInfos(queryValueTaxInfoByUserId(cusDO));
			return info;
		}
		return null;
	}
	
	@Override
	public List<CustomerBaseInfo> findAgencyCompany(String customerType) {
		return super.findAgencyCompany(customerType);
	}
}
