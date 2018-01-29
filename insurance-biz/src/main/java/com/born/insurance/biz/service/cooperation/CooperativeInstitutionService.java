package com.born.insurance.biz.service.cooperation;

import com.born.insurance.dataobject.CompanyInfo;
import com.born.insurance.dataobject.CompanyQueryDO;
import com.born.insurance.ws.info.customer.CustomerRelationInfo;
import com.born.insurance.ws.order.customer.CustomerPersonOrder;
import com.born.insurance.ws.order.customer.CustomerPersonQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

public interface CooperativeInstitutionService {
		public InsuranceBaseResult addCooperativeInstitutionInsuranceInstitutionsInfo(CustomerPersonOrder CustomerBaseInfoOrder);
		
		public InsuranceBaseResult addCooperativeInstitutionThirdPartyMechanismInfo(CustomerPersonOrder CustomerBaseInfoOrder);
		
		public QueryBaseBatchResult<CompanyInfo> queryCooperativeInstitutionByCondition(CompanyQueryDO companyQueryDO);
		
		public QueryBaseBatchResult<CustomerRelationInfo> querySubordinatePersonnelByCondition(CustomerPersonQueryOrder customerBaseInfoQueryOrder);
}
