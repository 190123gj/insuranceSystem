package com.born.insurance.biz.service.cooperativeinstitution.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.cooperation.CooperativeInstitutionService;
import com.born.insurance.dal.daointerface.CustomerBaseInfoDAO;
import com.born.insurance.dal.daointerface.CustomerCompanyDetailDAO;
import com.born.insurance.dal.daointerface.CustomerRelationDAO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.CustomerCompanyDetailDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.CompanyInfo;
import com.born.insurance.dataobject.CompanyInfoVo;
import com.born.insurance.dataobject.CompanyQueryDO;
import com.born.insurance.dataobject.CompanyRelationExtraDO;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.customer.CustomerRelationInfo;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("cooperativeInstitutionService")
public class CooperativeInstitutionServiceImpl extends BaseAutowiredDomainService implements
		CooperativeInstitutionService {
	private final com.yjf.common.log.Logger logger = LoggerFactory
			.getLogger(this.getClass());
	@Autowired
	private CustomerCompanyDetailDAO customerCompanyDetailDAO;
	
	@Autowired 
	private CustomerBaseInfoDAO customerBaseInfoDAO;
	
	@Autowired
	private ExtraDAO extraDAO;
	
	@Autowired
	private CustomerRelationDAO customerRelationDAO;
	
	//添加保险机构用户
	@Override
	public InsuranceBaseResult addCooperativeInstitutionInsuranceInstitutionsInfo(final
																				  CustomerPersonOrder customerBaseInfoOrder) {
		return commonProcess(customerBaseInfoOrder, "添加指定合作机构（保险机构）信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String customerId=BusinessNumberUtil.gainOutBizNoNumber();
				customerBaseInfoOrder.setCustomerId(customerId);
				CustomerCompanyDetailDO customerCompanyDetailDO=new CustomerCompanyDetailDO();
				CustomerBaseInfoDO customerBaseInfoDO=new CustomerBaseInfoDO();
				BeanCopier.staticCopy(customerBaseInfoOrder, customerCompanyDetailDO);
				BeanCopier.staticCopy(customerBaseInfoOrder, customerBaseInfoDO);
				if(customerBaseInfoOrder.getCertType()!=null){
					customerBaseInfoDO.setCertType(customerBaseInfoOrder.getCertType().code());
				}
				customerBaseInfoDO.setCustomerType(CustomerTypeEnum.INSURANCE_INSTITUTIONS.code());
				customerCompanyDetailDAO.insert(customerCompanyDetailDO);
				customerBaseInfoDAO.insert(customerBaseInfoDO);
				return null;
			}
		}, null, null);
	}
	
	//添加第三方机构用户
	@Override
	public InsuranceBaseResult addCooperativeInstitutionThirdPartyMechanismInfo(final
																				CustomerPersonOrder customerBaseInfoOrder) {
		return commonProcess(customerBaseInfoOrder, "添加指定合作机构（第三方机构）信息", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				String customerId=BusinessNumberUtil.gainOutBizNoNumber();
				customerBaseInfoOrder.setCustomerId(customerId);
				CustomerCompanyDetailDO customerCompanyDetailDO=new CustomerCompanyDetailDO();
				CustomerBaseInfoDO customerBaseInfoDO=new CustomerBaseInfoDO();
				BeanCopier.staticCopy(customerBaseInfoOrder, customerCompanyDetailDO);
				BeanCopier.staticCopy(customerBaseInfoOrder, customerBaseInfoDO);
				if(customerBaseInfoOrder.getCertType()!=null){
					customerBaseInfoDO.setCertType(customerBaseInfoOrder.getCertType().code());
				}
				customerBaseInfoDO.setCustomerType(CustomerTypeEnum.THIRD_PARTY_MECHANISM.code());
				customerCompanyDetailDAO.insert(customerCompanyDetailDO);
				customerBaseInfoDAO.insert(customerBaseInfoDO);
				return null;
			}
		}, null, null);
	}

	@Override
	public QueryBaseBatchResult<CompanyInfo> queryCooperativeInstitutionByCondition(
			CompanyQueryDO companyQueryDO) {
		logger.info("开始查询合作管理列表信息....companyQueryDO=[{}]"
				+ companyQueryDO.toString());
		QueryBaseBatchResult<CompanyInfo> baseBatchResult= new QueryBaseBatchResult<CompanyInfo>();
		long totalCount=extraDAO.queryCompanyInfoountByConditionCount(companyQueryDO);
		PageComponent component= new PageComponent(companyQueryDO, totalCount);
		List<CompanyInfoVo> list=extraDAO.queryCompanyInfoountByCondition(companyQueryDO, companyQueryDO.getLimitStart(), companyQueryDO.getPageSize());
		List<CompanyInfo> infolist=new ArrayList<CompanyInfo>();
		for(CompanyInfoVo vo:list){
			CompanyInfo companyInfo=new CompanyInfo();
			BeanCopier.staticCopy(vo, companyInfo);
			infolist.add(companyInfo);
		}
		baseBatchResult.setPageCount(infolist.size());
		baseBatchResult.initPageParam(component);
		baseBatchResult.setSuccess(true);
		logger.info("结束查询合作管理列表信息....");
		return baseBatchResult;
	}

	
	//查询机构人员列表
	@Override
	public QueryBaseBatchResult<CustomerRelationInfo> querySubordinatePersonnelByCondition(
			CustomerPersonQueryOrder customerBaseInfoQueryOrder) {
		logger.info("开始查询用户关系列表信息....customerBaseInfoQueryOrder=[{}]"
				+ customerBaseInfoQueryOrder.toString());
		QueryBaseBatchResult<CustomerRelationInfo> baseBatchResult=new QueryBaseBatchResult<CustomerRelationInfo>();
		CompanyRelationExtraDO companyRelationExtraDO= new CompanyRelationExtraDO();
		companyRelationExtraDO.setParentId(customerBaseInfoQueryOrder.getUserId());
		long totalCount=extraDAO.queryRelationListByConditionCount(companyRelationExtraDO);
		PageComponent component= new PageComponent(customerBaseInfoQueryOrder, totalCount);
		List<CompanyRelationExtraDO> list=extraDAO.queryRelationListByCondition(companyRelationExtraDO, customerBaseInfoQueryOrder.getLimitStart(),customerBaseInfoQueryOrder.getPageSize());
		List<CustomerRelationInfo> infolist=new ArrayList<CustomerRelationInfo>();
		for(CompanyRelationExtraDO relationExtraDO:list){
			CustomerRelationInfo relationInfon=new CustomerRelationInfo();
			BeanCopier.staticCopy(relationExtraDO, relationInfon);
			if(StringUtil.isNotEmpty(relationExtraDO.getCertType())){
				relationInfon.setCertType(CertTypeEnum.getByCode(relationExtraDO.getCertType()));
			}
			infolist.add(relationInfon);
		}
		baseBatchResult.setPageCount(totalCount);
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(infolist);
		baseBatchResult.setSuccess(true);
		logger.info("结束查询用户关系列表信息....");
		return baseBatchResult;
	}
}
