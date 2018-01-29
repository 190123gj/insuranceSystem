package com.born.insurance.biz.service.customer.impl;

import java.util.ArrayList;
import java.util.List;

import com.born.insurance.dal.daointerface.*;
import com.born.insurance.dal.dataobject.*;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.CustomerCompanyDO;
import com.born.insurance.integration.util.SessionLocal;
import com.born.insurance.integration.util.ShiroSessionUtils;
import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.ContactTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.info.customer.*;
import com.born.insurance.ws.order.common.CommonAttachmentOrder;
import com.born.insurance.ws.order.common.CommonAttachmentQueryOrder;
import com.born.insurance.ws.order.customer.*;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.CommonAttachmentService;
import com.yjf.common.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dataobject.CustomerPersonalDO;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.ui.Model;

/**
 * Created by Administrator on 2017-1-20.
 */
public abstract class CustomerBaseService extends BaseAutowiredDomainService {
	@Autowired
	private CustomerCertInfoDAO customerCertInfoDAO;

	@Autowired
	private ValueTaxInfoDAO valueTaxInfoDAO;

	@Autowired
	protected CustomerBankInfoDAO customerBankInfoDAO;

	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected CustomerBaseInfoDAO customerBaseInfoDAO;


	@Autowired
	private CommonAttachmentService commonAttachmentService;

	@Autowired
	private CustomerHisBusinessBillDAO customerHisBusinessBillDAO;

	@Autowired
	protected CustomerContactWayDAO customerContactWayDAO;


	protected void storeCustomerContactAddress(long userId, List<CustomerContactWayOrder> addressOrders,boolean isDelete) {
		if (isDelete) {
			customerContactWayDAO.deleteByUserId(userId);
		}
		if (ListUtil.isNotEmpty(addressOrders)) {
			for (CustomerContactWayOrder wayOrder : addressOrders) {
				CustomerContactWayDO customerContactWayDO = new CustomerContactWayDO();
				BeanCopier.staticCopy(wayOrder, customerContactWayDO);
				customerContactWayDO.setUserId(userId);
				customerContactWayDO.setType("address");
				customerContactWayDAO.insert(customerContactWayDO);
			}
		}
	}

	protected List<CustomerContactWayInfo> getContactWayInfos(CustomerBaseInfoDO per,
															ContactTypeEnum contactTypeEnum) {

		CustomerContactWayDO wayDO = new CustomerContactWayDO();
		wayDO.setUserId(per.getUserId());
		wayDO.setType(contactTypeEnum.getCode());
		List<CustomerContactWayDO> contactWayDOs = customerContactWayDAO.findByCondition(wayDO,
				null, null, 0, 0);
		if (ListUtil.isNotEmpty(contactWayDOs)) {
			List<CustomerContactWayInfo> wayInfos = new ArrayList<CustomerContactWayInfo>();
			for (CustomerContactWayDO contactWayDO : contactWayDOs) {
				CustomerContactWayInfo wayInfo = new CustomerContactWayInfo();
				BeanCopier.staticCopy(contactWayDO, wayInfo);
				if (StringUtil.isNotEmpty(contactWayDO.getType())) {
					wayInfo.setType(ContactTypeEnum.getByCode(contactWayDO.getType()));
				}
				wayInfos.add(wayInfo);
			}
			return wayInfos;
		}
		return null;
	}
	
	protected void storeCustomerCert(CustomerBaseInfoDO customerBaseInfoDO,
										CustomerBaseOrder customerBaseOrder) {
		customerCertInfoDAO.deleteByUserId(customerBaseInfoDO.getUserId());
		List<CustomerCertOrder> customerCertOrders = customerBaseOrder.getCertOrders();
		if (ListUtil.isNotEmpty(customerCertOrders)) {
			for (CustomerCertOrder customerCertOrder : customerCertOrders) {
				CustomerCertInfoDO customerCertInfoDO = new CustomerCertInfoDO();
				BeanCopier.staticCopy(customerCertOrder, customerCertInfoDO);
				customerCertInfoDO.setCertExpDate(DateUtil.strToDtSimpleFormat(customerCertOrder
					.getCertExpDate()));
				customerCertInfoDO.setUserId(customerBaseInfoDO.getUserId());
				customerCertInfoDO.setCustomerName(customerBaseInfoDO.getCustomerName());
				customerCertInfoDAO.insert(customerCertInfoDO);

				addAttachFile(customerBaseInfoDO.getUserId()+"",customerCertInfoDO.getId()+"",customerCertOrder.getCertPic(),
						customerBaseInfoDO.getUserId()+"", "证件附件",
						CommonAttachmentTypeEnum.CERT_ATTACH);
			}
		}
	}

	protected void storeCustomerHisBill(CustomerBaseInfoDO customerBaseInfoDO,
									 CustomerBaseOrder customerBaseOrder) {
		customerHisBusinessBillDAO.deleteByUserId(customerBaseInfoDO.getUserId());
		List<CustomerHisBusinessBillOrder> customerCertOrders = customerBaseOrder.getBillOrders();
		if (ListUtil.isNotEmpty(customerCertOrders)) {
			for (CustomerHisBusinessBillOrder customerCertOrder : customerCertOrders) {
				CustomerHisBusinessBillDO customerCertInfoDO = new CustomerHisBusinessBillDO();
				BeanCopier.staticCopy(customerCertOrder, customerCertInfoDO);
				customerCertInfoDO.setBillCustomerId(customerBaseInfoDO.getUserId());
				customerCertInfoDO.setBillCustomerName(customerBaseInfoDO.getCustomerName());
				customerHisBusinessBillDAO.insert(customerCertInfoDO);
			}
		}
	}


	
	protected void setOptimalCustomerCert(CustomerBaseInfoDO customerBaseInfoDO ,List<CustomerCertOrder> customerCertOrders) {
		if (ListUtil.isEmpty(customerCertOrders)) {
			return ;
		}
		
		CustomerCertOrder certOrder = customerCertOrders.get(0);
		for (CustomerCertOrder customerCertOrder : customerCertOrders) {
			if (CertTypeEnum.getByCode(certOrder.getCertType()).getOrder() > CertTypeEnum
				.getByCode(customerCertOrder.getCertType()).getOrder()) {
				certOrder = customerCertOrder;
			}
		}

        customerBaseInfoDO.setCertType(certOrder.getCertType());
        customerBaseInfoDO.setCertNo(certOrder.getCertNo());
        customerBaseInfoDO.setCertExpDate(DateUtil.strToDtSimpleYmFormat(certOrder.getCertExpDate()));
		
	}
	
	protected List<CustomerCertInfo> getCustomerCertInfo(long userId) {
		CustomerCertInfoDO customerCertInfoDO = new CustomerCertInfoDO();
		customerCertInfoDO.setUserId(userId);
		List<CustomerCertInfoDO> customerCertInfoDOs = customerCertInfoDAO.findByCondition(
			customerCertInfoDO, null, null, 0, 100);
		if (ListUtil.isNotEmpty(customerCertInfoDOs)) {
			List<CustomerCertInfo> customerCertInfos = new ArrayList<CustomerCertInfo>();
			for (CustomerCertInfoDO certInfoDO : customerCertInfoDOs) {
				CustomerCertInfo info = new CustomerCertInfo();
				BeanCopier.staticCopy(certInfoDO, info);
				info.setCertType(CertTypeEnum.getByCode(certInfoDO.getCertType()));
				queryCommonAttachmentData(info,userId+"",CommonAttachmentTypeEnum.CERT_ATTACH,info.getId()+"");
				customerCertInfos.add(info);
			}
			return customerCertInfos;
		}
		return null;
	}


	protected List<CustomerHisBusinessBillInfo> getCustomerHisBillInfo(long userId) {
		CustomerHisBusinessBillDO businessBillDO = new CustomerHisBusinessBillDO();
		businessBillDO.setBillCustomerId(userId);
		List<CustomerHisBusinessBillDO> businessBillDOs = customerHisBusinessBillDAO.findByCondition(
				businessBillDO, null, null, 0, 100);
		if (ListUtil.isNotEmpty(businessBillDOs)) {
			List<CustomerHisBusinessBillInfo> businessBillInfoList = new ArrayList<CustomerHisBusinessBillInfo>();
			for (CustomerHisBusinessBillDO certInfoDO : businessBillDOs) {
				CustomerHisBusinessBillInfo info = new CustomerHisBusinessBillInfo();
				BeanCopier.staticCopy(certInfoDO, info);
				businessBillInfoList.add(info);
			}
			return businessBillInfoList;
		}
		return null;
	}


	/**
	 * 查询企业客户的增值税信息
	 * @param customerCompanyDO
	 * @return
	 */
	protected List<ValueTaxInfo> queryValueTaxInfoByUserId(CustomerCompanyDO customerCompanyDO) {
		ValueTaxInfoDO valueTaxInfoDO = new ValueTaxInfoDO();
		valueTaxInfoDO.setCustomerUserId(customerCompanyDO.getUserId());
		List<ValueTaxInfoDO> valueDo = (List<ValueTaxInfoDO>) valueTaxInfoDAO.findByCondition(
				valueTaxInfoDO, null, null, 0, 999999);
		if (ListUtil.isNotEmpty(valueDo)) {
			List<ValueTaxInfo> list = new ArrayList<ValueTaxInfo>();
			for (ValueTaxInfoDO Do : valueDo) {
				ValueTaxInfo valueTaxInfo = new ValueTaxInfo();
				BeanCopier.staticCopy(Do, valueTaxInfo);
				list.add(valueTaxInfo);
			}
			return list;
		}
		return null;
	}


	/**
	 * 添加附件
	 * @param keyId
	 * @param childId
	 * @param pathValues
	 * @param projectCode
	 * @param remark
	 * @param types
	 * @return
	 */
	protected InsuranceBaseResult addAttachFile(String keyId, String childId, String pathValues,
												String projectCode, String remark,
												CommonAttachmentTypeEnum... types) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		if (null == types || types.length <= 0) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}

		long uploaderId = 0L;
		String uploaderAccount = "";
		String uploaderName = "";
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			uploaderId = sessionLocal.getUserId();
			uploaderAccount = sessionLocal.getUserName();
			uploaderName = sessionLocal.getRealName();
		}

		List<CommonAttachmentOrder> orders = new ArrayList<CommonAttachmentOrder>();
		//先删除，再保存
		commonAttachmentService.deleteByBizNoAndChildIdModuleType(keyId, childId, types);

		for (CommonAttachmentTypeEnum type : types) {
			if (null == type) {
				continue;
			}
			if (StringUtil.isNotBlank(pathValues)) {
				String[] attachPaths = pathValues.split(";");
				int j = 1;
				for (String path : attachPaths) {
					String paths[] = path.split(",");
					if (null != paths && paths.length >= 3) {
						CommonAttachmentOrder commonAttachmentOrder = new CommonAttachmentOrder();
						commonAttachmentOrder.setUploaderId(uploaderId);
						commonAttachmentOrder.setUploaderAccount(uploaderAccount);
						commonAttachmentOrder.setUploaderName(uploaderName);
						commonAttachmentOrder.setBizNo(keyId);
						commonAttachmentOrder.setChildId(childId);
						commonAttachmentOrder.setModuleType(type);
						commonAttachmentOrder.setIsort(j++);
						commonAttachmentOrder.setFileName(paths[0]);
						commonAttachmentOrder.setFilePhysicalPath(paths[1]);
						commonAttachmentOrder.setRequestPath(paths[2]);
						commonAttachmentOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
						commonAttachmentOrder.setProjectCode(projectCode);
						commonAttachmentOrder.setRemark(remark);
						orders.add(commonAttachmentOrder);
					}
				}
			}
		}

		if (ListUtil.isNotEmpty(orders)) {
			return commonAttachmentService.insertAll(orders);
		} else {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.HAVE_NOT_DATA);
			result.setMessage("没有附件数据");
			return result;
		}
	}


	protected void queryCommonAttachmentData(CustomerCertInfo certInfo, String bizNo,
											 CommonAttachmentTypeEnum type, String... childId) {
		CommonAttachmentQueryOrder attachQueryOrder = new CommonAttachmentQueryOrder();
		if (childId != null && childId.length > 0) {
			attachQueryOrder.setChildId(childId[0]);
		}
		attachQueryOrder.setBizNo(bizNo);
		List<CommonAttachmentTypeEnum> moduleTypeList = new ArrayList<>();
		moduleTypeList.add(type);
		attachQueryOrder.setModuleTypeList(moduleTypeList);
		QueryBaseBatchResult<CommonAttachmentInfo> attaches = commonAttachmentService
				.queryCommonAttachment(attachQueryOrder);
		if (null != attaches && ListUtil.isNotEmpty(attaches.getPageList())) {
			certInfo.setAttaches(attaches.getPageList());
			StringBuilder urls = new StringBuilder();
			for (CommonAttachmentInfo attach : attaches.getPageList()) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
						.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
			certInfo.setHiddenUrls(urls.toString());
			certInfo.setCommonAttachementList(attaches.getPageList());
		}
	}

	protected void storeCustomerBank(long userId, List<CustomerBankInfoOrder> bankOrders) {
		customerBankInfoDAO.deleteByUserId(userId);
		if (ListUtil.isNotEmpty(bankOrders)) {
			for (CustomerBankInfoOrder bankInfoOrder : bankOrders) {
				CustomerBankInfoDO customerBankInfoDO = new CustomerBankInfoDO();
				BeanCopier.staticCopy(bankInfoOrder, customerBankInfoDO);
				customerBankInfoDO.setUserId(userId);
				customerBankInfoDAO.insert(customerBankInfoDO);
			}
		}
	}

	protected void storeValueTaxInfo(CustomerCompanyOrder customerCompanyOrder){
		valueTaxInfoDAO.deleteByUserId(customerCompanyOrder.getUserId());
		for (ValueTaxInfoOrder tax : customerCompanyOrder.getTaxOrders()) {
			ValueTaxInfoDO valueTaxInfoDO = new ValueTaxInfoDO();
			BeanCopier.staticCopy(tax, valueTaxInfoDO);
			valueTaxInfoDO.setCustomerUserId(customerCompanyOrder.getUserId());
			valueTaxInfoDAO.insert(valueTaxInfoDO);
		}
	}

	protected List<CustomerBankInfo> getBankInfos(long userId) {

		CustomerBankInfoDO customerBankInfoDO = new CustomerBankInfoDO();
		customerBankInfoDO.setUserId(userId);
		List<CustomerBankInfoDO> bankInfoDOs = customerBankInfoDAO.findByCondition(
				customerBankInfoDO, null, null, 0, 0);
		if (ListUtil.isNotEmpty(bankInfoDOs)) {
			List<CustomerBankInfo> bankInfos = new ArrayList<CustomerBankInfo>();
			for (CustomerBankInfoDO bankInfoDO : bankInfoDOs) {
				CustomerBankInfo info = new CustomerBankInfo();
				BeanCopier.staticCopy(bankInfoDO, info);
				bankInfos.add(info);
			}
			return bankInfos;
		}
		return null;
	}

	public List<CustomerBaseInfo> findAgencyCompany(String customerType) {
		List<CustomerBaseInfo> pageList = new ArrayList<CustomerBaseInfo>();
		List<CustomerBaseInfoDO> customerBaseInfoDO = 	customerBaseInfoDAO.findAgencyCompany(customerType);
		for (CustomerBaseInfoDO customerBaseInfoDO2 : customerBaseInfoDO) {
			CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
			BeanCopier.staticCopy(customerBaseInfoDO2,customerBaseInfo);
			pageList.add(customerBaseInfo);
		}
			return pageList;
	}
}
