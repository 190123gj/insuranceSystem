package com.born.insurance.biz.service.customer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.customer.CustomerBnakInfoService;
import com.born.insurance.dal.daointerface.CustomerBankInfoDAO;
import com.born.insurance.dal.dataobject.CustomerBankInfoDO;
import com.born.insurance.ws.info.customer.CustomerBankInfo;
import com.born.insurance.ws.order.customer.CustomerBankInfoOrder;
import com.born.insurance.ws.order.customer.CustomerBankInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.log.LoggerFactory;


@Service("customerBnakInfoService")
public class CustomerBankInfoServiceImpl extends BaseAutowiredDomainService implements CustomerBnakInfoService {
	
	private final com.yjf.common.log.Logger logger = LoggerFactory
			.getLogger(this.getClass());
	@Autowired
	private CustomerBankInfoDAO customerBankInfoDAO;
	
	
	@Override
	public CustomerBankInfo queryCustomerBankInfoByID(
			CustomerBankInfoQueryOrder customerBankInfoQueryOrder) {
		logger.info("开始查询指定企业客户银行账户列息....customerBankInfoQueryOrder=[{}]"
				+ customerBankInfoQueryOrder.toString());
		CustomerBankInfoDO customerBankInfoDO=new CustomerBankInfoDO();
		CustomerBankInfo customerBankInfo=new CustomerBankInfo();
		BeanCopier.staticCopy(customerBankInfoQueryOrder, customerBankInfoDO);
		List<CustomerBankInfoDO> cusDO=customerBankInfoDAO.findByCondition(customerBankInfoDO,null,null,0,0);
		if(cusDO!=null){
			CustomerBankInfoDO bankInfoDo= new CustomerBankInfoDO();
			bankInfoDo=cusDO.get(0);
			BeanCopier.staticCopy(bankInfoDo, customerBankInfo);
			return customerBankInfo;
		}
		return null;
	}


//	@Override
//	public InsuranceBaseResult updateCustomerBankInfoByCondition(
//			CustomerBankInfoOrder customerBankInfoOrder) {
//		InsuranceBaseResult result=new InsuranceBaseResult();
//		result.setSuccess(false);
//		result.setMessage("修改失败");
//		CustomerBankInfoDO customerBankInfoDO=new CustomerBankInfoDO();
//		BeanCopier.staticCopy(customerBankInfoOrder, customerBankInfoDO);
//		long updateId=customerBankInfoDAO.update(customerBankInfoDO);
//		if(updateId > 0){
//			result.setSuccess(true);
//			result.setMessage("修改成功");
//		}
//		return null;
//	}
}
