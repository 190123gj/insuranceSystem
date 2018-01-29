package com.born.insurance.ws.service.businessBillCustomer;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

import java.util.List;

import com.born.insurance.ws.info.businessBill.BusinessBillBeneficiaryInfo;
import com.born.insurance.ws.info.businessBillCustomer.BusinessBillCustomerInfo;
import com.born.insurance.ws.order.businessBillBeneficiary.BusinessBillBeneficiaryOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerOrder;
import com.born.insurance.ws.order.businessBillCustomer.BusinessBillCustomerQueryOrder;

public interface BusinessBillCustomerService  {
	/**
     * 保存(新增/修改)
     *
     * @param order 不能为null
     * @return 成功/失败
     */
    InsuranceBaseResult save(BusinessBillCustomerOrder order);

    /**
     * 查询信息
     *
     * @param id 单据编号
     * @return 风险预警对象
     */
    BusinessBillCustomerInfo findById(long id);

    /**
     * 根据条件查询列表
     *
     * @param queryOrder 查询条件
     * @return 结果集
     */
    QueryBaseBatchResult<BusinessBillCustomerInfo> queryList(BusinessBillCustomerQueryOrder queryOrder);
    
    /**
     * 保单详情 保险人 与被保险人
     * @param businessBillId
     * @return
     */
    List<BusinessBillCustomerInfo> findBusinessBillCustomers(String billNo);
    
    
    /**
     * 根据业务单号查询受益人信息
     * @param billNo
     * @return
     */
    List<BusinessBillBeneficiaryInfo> findBusinessBillBeneficiarys(long letterId);
	
}	