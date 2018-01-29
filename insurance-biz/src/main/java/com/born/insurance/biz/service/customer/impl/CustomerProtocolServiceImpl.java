/**
 * <p>Title: CustomerProtocolServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: www.itcast.cn</p>
 * @author 郭靖
 * @date 2016年12月28日上午9:56:52
 * @version 1.0
 */
package com.born.insurance.biz.service.customer.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.NumberUtil;

import org.bouncycastle.asn1.cms.PasswordRecipientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.service.customer.CustomerProtocolService;
import com.born.insurance.dal.daointerface.CustomerProtocolDAO;
import com.born.insurance.dal.dataobject.CustomerBankInfoDO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.CustomerContactWayDO;
import com.born.insurance.dal.dataobject.CustomerPersonDetailDO;
import com.born.insurance.dal.dataobject.CustomerProtocolDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.CustomerProtocolVo;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.enums.StatusEnum;
import com.born.insurance.ws.order.customer.CustomerBankInfoOrder;
import com.born.insurance.ws.order.customer.CustomerContactWayOrder;
import com.born.insurance.ws.order.customer.CustomerProtocolOrder;
import com.born.insurance.ws.order.customer.CustomerProtocolQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * <p>Title: CustomerProtocolServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	郭靖
 * @date	2016年12月28日上午9:56:52
 * @version 1.0
 */
@Service("customerProtocolService")
public class CustomerProtocolServiceImpl extends BaseAutowiredDomainService implements CustomerProtocolService {
	

	private Logger logger = LoggerFactory.getLogger(CustomerProtocolServiceImpl.class);
	
	@Autowired
	ExtraDAO extraDAO;

	@Autowired
	CustomerProtocolDAO customerProtocolDAO;
	
	/**
	 * <p>Title: addCustomerProtocol</p>
	 * <p>Description: </p>
	 * @param customerProtocolDO
	 * @return
	 * @see com.born.insurance.biz.service.customer.CustomerProtocolSersvice#addCustomerProtocol(com.born.insurance.dal.dataobject.CustomerProtocolDO)
	 */
	@Override
	public InsuranceBaseResult addCustomerProtocol(final CustomerProtocolOrder order) {
		return commonProcess(order, "添加客户协议", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				CustomerProtocolDO customerProtocol = new CustomerProtocolDO();
				long id = NumberUtil.parseLong(order.getId());
				BeanCopier.staticCopy(order, customerProtocol);
				if (order.getId().equals("0")) {
					customerProtocol.setRawAddTime(getSysdate());
					customerProtocol.setStatus(StatusEnum.NORMAL.getCode());
					customerProtocol.setCustomerUserId(Long.valueOf(order.getCustomerUserId()));
					if (!StringUtils.isEmpty(order.getRelationProtocolId())) {
						customerProtocol.setRelationProtocolId(Integer.parseInt(order.getRelationProtocolId()));
					} else {
						customerProtocol.setRelationProtocolId(0);
					}
					customerProtocol.setNo(StringUtil.getRandom());
					customerProtocol.setBeginDate(DateUtil.parse(order.getBeginDate()));
					customerProtocol.setEndDate(DateUtil.parse(order.getEndDate()));
					customerProtocolDAO.insert(customerProtocol);
					id = customerProtocol.getId();
				} else {
					CustomerProtocolDO customerProtocolDO = customerProtocolDAO.findById(Integer.parseInt(order.getId()));
					customerProtocolDO.setCustomerUserId(Integer.parseInt(order.getCustomerUserId()));
					customerProtocolDO.setCustomerUserName(order.getCustomerUserName());
					customerProtocolDO.setName(order.getName());
					customerProtocolDO.setBeginDate(DateUtil.parse(order.getBeginDate()));
					customerProtocolDO.setEndDate(DateUtil.parse(order.getEndDate()));
					if (!StringUtils.isEmpty(order.getRelationProtocolId())) {
						customerProtocolDO.setRelationProtocolId(Integer.parseInt(order.getRelationProtocolId()));
					} else {
						customerProtocolDO.setRelationProtocolId(0);
					}
					customerProtocolDAO.update(customerProtocolDO);
				}

				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}

	/**
	 * 
	 * <p>Title: queryCustomerProtocolList</p>
	 * <p>Description: </p>
	 * @param customerProtocolQueryOrder
	 * @return
	 * @see com.born.insurance.biz.service.customer.CustomerProtocolService#queryCustomerProtocolList(com.born.insurance.dataobject.CustomerProtocolQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<CustomerProtocolVo> queryCustomerProtocolList(CustomerProtocolQueryOrder customerProtocolQueryOrder) {
		logger.info("开始查询客户协议列表信息....customerProtocolVo=[{}]");
		long totalCount=extraDAO.queryCustomerProtocolQueryOrderByConditionCount(customerProtocolQueryOrder,new Date());
		QueryBaseBatchResult<CustomerProtocolVo> baseBatchResult = new QueryBaseBatchResult<CustomerProtocolVo>();
		PageComponent component = new PageComponent(customerProtocolQueryOrder, totalCount);
		List<CustomerProtocolVo> queryCustomerProtocolList = extraDAO.queryCustomerProtocolList(customerProtocolQueryOrder,new Date(),customerProtocolQueryOrder.getLimitStart(),customerProtocolQueryOrder.getPageSize());
		if (queryCustomerProtocolList.size() > 0) {
			for (CustomerProtocolVo customerProtocolVo : queryCustomerProtocolList) {
				String customerType = customerProtocolVo.getCustomerType();
				if (!StringUtils.isEmpty(customerType)) {
					customerProtocolVo.setCustomerType(CustomerTypeEnum.getMsgByCode(customerType));
				}
				String certType = customerProtocolVo.getCertType();
				if (!StringUtils.isEmpty(certType)) {
					customerProtocolVo.setCertType(CertTypeEnum.getMsgByCode(certType));
				}
				setStatus(customerProtocolVo);
			}
		}
		baseBatchResult.setPageList(queryCustomerProtocolList);
		baseBatchResult.initPageParam(component);
		baseBatchResult.setSuccess(true);
		logger.info("结束始查询客户协议信息....");
		return baseBatchResult;
	}

	/**
	 * 
	 * <p>Title: findById</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @see com.born.insurance.biz.service.customer.CustomerProtocolService#findById(long)
	 */
	@Override
	public CustomerProtocolDO findById(long id) {
		return customerProtocolDAO.findById(id);
	}
	
	
	public void setStatus(CustomerProtocolVo customerProtocolVo){
		Date now = new Date();
		Date beginTime = customerProtocolVo.getBeginDate();
		Date endTime = customerProtocolVo.getEndDate();
		if (now.after(beginTime) && now.before(endTime)) {
			customerProtocolVo.setStatus(StatusEnum.NORMAL.getCode());
		} else {
			customerProtocolVo.setStatus(StatusEnum.EXPIRE.getCode());
		}
	}
	
	

}
