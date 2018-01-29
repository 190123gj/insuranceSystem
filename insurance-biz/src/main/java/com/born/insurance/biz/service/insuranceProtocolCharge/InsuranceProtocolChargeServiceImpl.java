package com.born.insurance.biz.service.insuranceProtocolCharge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.CustomerBaseInfoDAO;
import com.born.insurance.dal.daointerface.InsuranceContactLetterDetailDAO;
import com.born.insurance.dal.daointerface.InsuranceProtocolChargeDAO;
import com.born.insurance.dal.daointerface.InsuranceProtocolInfoDAO;
import com.born.insurance.dal.dataobject.CustomerBaseInfoDO;
import com.born.insurance.dal.dataobject.InsuranceContactLetterDetailDO;
import com.born.insurance.dal.dataobject.InsuranceProtocolChargeDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProtocolCharge.InsuranceProtocolChargeInfo;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeOrder;
import com.born.insurance.ws.order.insuranceProtocolCharge.InsuranceProtocolChargeQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceProtocolCharge.InsuranceProtocolChargeService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceProtocolChargeService")
public class InsuranceProtocolChargeServiceImpl extends BaseAutowiredDomainService implements
																					InsuranceProtocolChargeService {
	@Autowired
	protected InsuranceProtocolChargeDAO insuranceProtocolChargeDAO;
	
	@Autowired
	protected InsuranceProtocolInfoDAO insuranceProtocolInfoDAO;
	
	@Autowired
	protected InsuranceContactLetterDetailDAO insuranceContactLetterDetailDAO;
	
	@Autowired
	protected CustomerBaseInfoDAO customerBaseInfoDAO;
	
	@Override
	public InsuranceBaseResult save(final InsuranceProtocolChargeOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				String id = order.getChargeId();
				if (StringUtil.isNotEmpty(id)) {
					InsuranceProtocolChargeDO doObj = new InsuranceProtocolChargeDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceProtocolChargeDAO.insert(doObj);
				} else {
					InsuranceProtocolChargeDO doObj = insuranceProtocolChargeDAO.findById(order
						.getChargeId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					insuranceProtocolChargeDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceProtocolChargeInfo findById(String id) {
		InsuranceProtocolChargeDO insuranceProtocolChargeDo = insuranceProtocolChargeDAO
			.findById(id);
		if (insuranceProtocolChargeDo != null) {
			InsuranceProtocolChargeInfo insuranceProtocolChargeInfo = new InsuranceProtocolChargeInfo();
			BeanCopier.staticCopy(insuranceProtocolChargeDo, insuranceProtocolChargeInfo);
			return insuranceProtocolChargeInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceProtocolChargeInfo> queryList(	InsuranceProtocolChargeQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceProtocolChargeInfo> batchResult = new QueryBaseBatchResult<InsuranceProtocolChargeInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceProtocolChargeInfo> pageList = new ArrayList<InsuranceProtocolChargeInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceProtocolChargeDO insuranceProtocolChargeDO = new InsuranceProtocolChargeDO();
			BeanCopier.staticCopy(queryOrder, insuranceProtocolChargeDO);
			long totalCount = insuranceProtocolChargeDAO
				.findByConditionCount(insuranceProtocolChargeDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceProtocolChargeDO> recordList = insuranceProtocolChargeDAO
				.findByCondition(insuranceProtocolChargeDO, queryOrder.getSortCol(),
					queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (InsuranceProtocolChargeDO item : recordList) {
				InsuranceProtocolChargeInfo info = new InsuranceProtocolChargeInfo();
				BeanCopier.staticCopy(item, info);
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
	public String queryProtocolCharge(InsuranceProtocolChargeQueryOrder queryOrder) {
		InsuranceProtocolChargeDO insuranceProtocolChargeDO = new InsuranceProtocolChargeDO();
		BeanCopier.staticCopy(queryOrder, insuranceProtocolChargeDO);
		insuranceProtocolChargeDO.setParentId("0");
		insuranceProtocolChargeDO.setDepth(1);
		List<InsuranceProtocolChargeDO> firstRecordList = insuranceProtocolChargeDAO
			.findByCondition(insuranceProtocolChargeDO, "charge_id", "asc", 0, 100);
		JSONArray jsonArray = new JSONArray();
		if (ListUtil.isNotEmpty(firstRecordList)) {
			for (InsuranceProtocolChargeDO firstCharge : firstRecordList) {
				insuranceProtocolChargeDO.setParentId(firstCharge.getChargeId());
				insuranceProtocolChargeDO.setDepth(2);
				List<InsuranceProtocolChargeDO> secondRecordList = insuranceProtocolChargeDAO
					.findByCondition(insuranceProtocolChargeDO, "charge_id", "asc", 0, 100);
				
				JSONObject jsonObject = new JSONObject();
				jsonArray.add(jsonObject);
				jsonObject.put("timeLimit", firstCharge.getVal());
				if (ListUtil.isNotEmpty(secondRecordList)) {
					List<String> l = new ArrayList<String>();
					jsonObject.put("periods", l);
					for (InsuranceProtocolChargeDO secondCharge : secondRecordList) {
						insuranceProtocolChargeDO.setParentId(secondCharge.getChargeId());
						insuranceProtocolChargeDO.setDepth(3);
						List<InsuranceProtocolChargeDO> thirdRecordList = insuranceProtocolChargeDAO
							.findByCondition(insuranceProtocolChargeDO, "charge_id", "asc", 0, 100);
						if (ListUtil.isNotEmpty(thirdRecordList)) {
							for (InsuranceProtocolChargeDO thirdCharge : thirdRecordList) {
								l.add(thirdCharge.getVal());
							}
						}
					}
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", jsonArray);
		
		insuranceProtocolChargeDO.setParentId("0");
		insuranceProtocolChargeDO.setDepth(2);
		String maxLength = insuranceProtocolChargeDAO.findByConditionMax(insuranceProtocolChargeDO);
		jsonObject.put("maxLength",StringUtil.defaultIfEmpty(maxLength,"0"));
		return jsonObject.toJSONString();
	}

	@Override
	public List<InsuranceProtocolChargeInfo> getInsuranceProductCharge(String productId, String chargeType) {
		List<InsuranceProtocolChargeInfo> pageList = new ArrayList<InsuranceProtocolChargeInfo>() ;
		List<InsuranceProtocolChargeDO> recordList = insuranceProtocolChargeDAO.getInsuranceProductCharge(new InsuranceProtocolChargeDO(),chargeType,productId);
			for (InsuranceProtocolChargeDO item : recordList) {
				InsuranceProtocolChargeInfo info = new InsuranceProtocolChargeInfo();
				BeanCopier.staticCopy(item, info);
				pageList.add(info);
			}
		return pageList;
	}

	@Override
	public InsuranceProtocolChargeInfo getInsuranceProductChargeRate(String productId, String chargeType, String certNo,
			String period) {
		InsuranceProtocolChargeInfo info = new InsuranceProtocolChargeInfo();
		InsuranceProtocolChargeDO insuranceProtocolChargeDO  = null;
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//计算投保人的年龄
		//根据身份证获取投保人的生日
		if (!StringUtils.isBlank(certNo) && idNumPattern.matcher(certNo).matches()) {
		
			int  age = getFullAge(certNo);
			try {
				insuranceProtocolChargeDO = insuranceProtocolChargeDAO.getInsuranceProductChargeRate(Long.valueOf(age),period,productId,chargeType);
				if (null != insuranceProtocolChargeDO) {
					BeanCopier.staticCopy(insuranceProtocolChargeDO, info);
				}
			} catch (Exception e) {
				logger.error("查询产品费率出错", info);
			}
		}
		return info;
	}
	
	public static int getFullAge(String certNo)  {
		Calendar cal1 = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        int year,month,day,len = certNo.length();
		if (len == 18) {
			year = Integer.parseInt(certNo.substring(6, 10));
			month = Integer.parseInt(certNo.substring(10, 12) );
			day = Integer.parseInt(certNo.substring(12, 14));
			cal1.set(year, month, day);
		} else {
			 year = Integer.parseInt(certNo.substring(6,8)) + 1900;
		     month = Integer.parseInt(certNo.substring(8,10));
		     day = Integer.parseInt(certNo.substring(10,12));
		     cal1.set(year, month, day);
		}
		return getAge(today,cal1);
	}

	private static int getAge(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m)/12;
	}

	@Override
	public InsuranceProtocolChargeInfo getBrokerageRate(String type,InsuranceProductInfo insuranceProductInfo,String letterId,
			String appserialPeriod) {
		InsuranceProtocolChargeInfo insuranceProtocolChargeInfo = new InsuranceProtocolChargeInfo();
		InsuranceProtocolChargeDO insuranceProtocolChargeDO = null ;
		Date now = new Date();
		long companyUserId = insuranceProductInfo.getCompanyUserId();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(String.valueOf(companyUserId));
		long catalogId = insuranceProductInfo.getCatalogId() ;
		long productId = insuranceProductInfo.getProductId();
		String period = "";
		//定额产品的缴费期限就是一次性
		if (StringUtils.isBlank(insuranceProductInfo.getIsLifeInsurance()) && insuranceProductInfo.getIsLifeInsurance().equals("YES")) {
			InsuranceContactLetterDetailDO insuranceContactLetterDetailDO = insuranceContactLetterDetailDAO.findInsuranceContactLetterDetail(Integer.parseInt(letterId),insuranceProductInfo.getProductId());
			period = insuranceContactLetterDetailDO.getPeriod();
			insuranceProtocolChargeDO = insuranceProtocolChargeDAO.getBrokerageRate(type,productId,customerBaseInfoDO.getUserId(),null,period,appserialPeriod,now);
			if (null == insuranceProtocolChargeDO) {
				insuranceProtocolChargeDO = insuranceProtocolChargeDAO.getBrokerageRate(type,productId,customerBaseInfoDO.getUserId(),String.valueOf(catalogId),period,appserialPeriod,now);
			}
		} else {
			insuranceProtocolChargeDO = insuranceProtocolChargeDAO.getUnLifeBrokerageRate(type,productId,customerBaseInfoDO.getUserId(),null,"1",now);
			if (null == insuranceProtocolChargeDO) {
				insuranceProtocolChargeDO = insuranceProtocolChargeDAO.getUnLifeBrokerageRate(type,productId,customerBaseInfoDO.getUserId(),String.valueOf(catalogId),"1",now);
			}
		}
		
		if (null != insuranceProtocolChargeDO) {
			BeanCopier.staticCopy(insuranceProtocolChargeDO, insuranceProtocolChargeInfo);
			return insuranceProtocolChargeInfo;
		}
		return null;
	}

	@Override
	public Integer getPeriodMax(InsuranceProductInfo insuranceProductInfo, String letterId) {
		String val ;
		Date now = new Date();
		long companyUserId = insuranceProductInfo.getCompanyUserId();
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(String.valueOf(companyUserId));
		long catalogId = insuranceProductInfo.getCatalogId() ;
		long productId = insuranceProductInfo.getProductId();
		InsuranceContactLetterDetailDO insuranceContactLetterDetailDO = insuranceContactLetterDetailDAO.findInsuranceContactLetterDetail(Integer.parseInt(letterId),insuranceProductInfo.getProductId());
		val = insuranceProtocolChargeDAO.findPeriodMax(productId, customerBaseInfoDO.getUserId(), null, insuranceContactLetterDetailDO.getPeriod(),now);
		if (StringUtils.isBlank(val)) {
			val = insuranceProtocolChargeDAO.findPeriodMax(productId, customerBaseInfoDO.getUserId(), String.valueOf(catalogId), insuranceContactLetterDetailDO.getPeriod(),now);
		}
		return StringUtils.isBlank(val) ? 0 :Integer.parseInt(val);
	}

	@Override
	public List<InsuranceProtocolChargeInfo> getInsuranceProductChargeInfo(long companyUserId, long catalogId,String productId) {
		List<InsuranceProtocolChargeInfo> pageList = new ArrayList<InsuranceProtocolChargeInfo>() ;
		List<InsuranceProtocolChargeDO> recordList = null ;
		CustomerBaseInfoDO customerBaseInfoDO = customerBaseInfoDAO.findByCustomerId(String.valueOf(companyUserId));
		recordList = insuranceProtocolChargeDAO.getInsuranceProductChargeInfo(null,String.valueOf( customerBaseInfoDO.getUserId()),productId);
		if (recordList == null || recordList.size() == 0) {
			recordList = insuranceProtocolChargeDAO.getInsuranceProductChargeInfo(String.valueOf(catalogId),String.valueOf( customerBaseInfoDO.getUserId()),null);
		}
		for (InsuranceProtocolChargeDO item : recordList) {
			InsuranceProtocolChargeInfo info = new InsuranceProtocolChargeInfo();
			BeanCopier.staticCopy(item, info);
			pageList.add(info);
		}
		return pageList;
	}
}