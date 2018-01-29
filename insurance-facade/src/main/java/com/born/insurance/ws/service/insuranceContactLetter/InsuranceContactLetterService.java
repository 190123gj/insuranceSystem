package com.born.insurance.ws.service.insuranceContactLetter;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterFormInfo;
import com.born.insurance.ws.info.insuranceContactLetter.InsuranceContactLetterInfo;
import com.born.insurance.ws.info.insuranceContactLetterDetail.InsuranceContactLetterDetailInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterOrder;
import com.born.insurance.ws.order.insuranceContactLetter.InsuranceContactLetterQueryOrder;

public interface InsuranceContactLetterService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
	FormBaseResult save(InsuranceContactLetterOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    InsuranceContactLetterInfo findById(long id);



    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<InsuranceContactLetterFormInfo> queryList(InsuranceContactLetterQueryOrder queryOrder);
    
    /**
     * 投保内容查询
     * @param letterId
     * @return
     */
    List<InsuranceContactLetterDetailInfo> getInsuranceContactLetterDetails(long letterId);

    /**
     * 根据formId查询投保申请
     * @param formId
     * @return
     */
	InsuranceContactLetterInfo findByFormId(long formId);
	
	/**
	 * 查询非寿险非定额产品的档次信息
	 * @param productLevelId
	 * @return
	 */
	InsuranceProductLevelInfo findProductLevelInfo(long productLevelId);
	
	/**
	 * 查询客户的证件信息
	 * @param customerUserId
	 * @return
	 */
	public List<CustomerCertInfo> findCustomerCertList(long customerUserId);
	
}	