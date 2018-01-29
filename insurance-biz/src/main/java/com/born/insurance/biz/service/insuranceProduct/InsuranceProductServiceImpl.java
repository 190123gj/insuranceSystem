package com.born.insurance.biz.service.insuranceProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.insuranceProtocol.SalesAreaCommService;
import com.born.insurance.biz.util.PinyinUtil;
import com.born.insurance.dal.daointerface.CustomerCertInfoDAO;
import com.born.insurance.dal.daointerface.InsuranceCatalogLiabilityDAO;
import com.born.insurance.dal.daointerface.InsuranceProductDAO;
import com.born.insurance.dal.daointerface.InsuranceProductLevelDAO;
import com.born.insurance.dal.daointerface.InsuranceProtocolChargeDAO;
import com.born.insurance.dal.dataobject.CustomerCertInfoDO;
import com.born.insurance.dal.dataobject.InsuranceCatalogLiabilityDO;
import com.born.insurance.dal.dataobject.InsuranceProductDO;
import com.born.insurance.dal.dataobject.InsuranceProductLevelDO;
import com.born.insurance.dal.dataobject.InsuranceProtocolChargeDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import com.born.insurance.ws.enums.InsuranceChargeEnum;
import com.born.insurance.ws.enums.LifeInsuranceTypeEnum;
import com.born.insurance.ws.enums.SalesAreaTypeEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.SysDbBackupConfigInfo;
import com.born.insurance.ws.info.customer.CustomerCertInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogLiabilityInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductLevelInfo;
import com.born.insurance.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.insurance.ws.order.insuranceCatalogLiability.InsuranceCatalogLiabilityOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductLevelOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceTypesService")
public class InsuranceProductServiceImpl extends SalesAreaCommService implements
																		InsuranceProductService {
	@Autowired
	protected InsuranceProductDAO insuranceProductDAO;
	
	@Autowired
	protected InsuranceCatalogLiabilityDAO insuranceCatalogLiabilityDAO;
	
	@Autowired
	protected InsuranceProductLevelDAO insuranceProductLevelDAO;
	
	@Autowired
	protected CustomerCertInfoDAO customerCertInfoDAO;
	
	@Autowired
	protected InsuranceProtocolChargeDAO insuranceProtocolChargeDAO;
	
	/**
	 * 编号
	 * @return
	 */
	private synchronized String genProductNo(InsuranceProductOrder order) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String priceCode = "CP" + "-" + PinyinUtil.getPinYinHeadChar(order.getCatalogName()) + year;
		String seqName = SysDateSeqNameEnum.INSURANCE_PRODUCT.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		priceCode += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return priceCode;
	}
	
	@Override
	public InsuranceBaseResult save(final InsuranceProductOrder order) {
		return commonProcess(order, "新增/修改产品", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				long id = order.getTypeId();
				if (id <= 0) {
					InsuranceProductDO doObj = new InsuranceProductDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					if (!StringUtils.isBlank(order.getCatalogId())) {
						doObj.setCatalogId(Integer.parseInt(order.getCatalogId()));
					}
				
					doObj.setStopSaleDate(!StringUtils.isBlank(order.getStopSaleDate()) ? DateUtil
						.parse(order.getStopSaleDate()) : null);
					doObj.setProductNo(genProductNo(order));
					if (order.getIsLifeInsurance().equals("NO")) {
						doObj.setInsurancePeriod(String.valueOf(1));
						doObj.setInsurancePeriodType("年");
					}
					if (null != order.getPeriodRate() ) {
						doObj.setPeriodRate(Double.valueOf(order.getPeriodRate()));
					}
					insuranceProductDAO.insert(doObj);
					id = doObj.getProductId();
					//添加保险档次
					if (null != order.getInsuranceProductLevelOrders()
						&& order.getInsuranceProductLevelOrders().size() > 0) {
						for (InsuranceProductLevelOrder insuranceProductLevelOrder : order
							.getInsuranceProductLevelOrders()) {
							InsuranceProductLevelDO insuranceProductLevelDO = new InsuranceProductLevelDO();
							BeanCopier.staticCopy(insuranceProductLevelOrder,
								insuranceProductLevelDO);
							insuranceProductLevelDO.setProductId(doObj.getProductId());
							insuranceProductLevelDAO.insert(insuranceProductLevelDO);
							
						}
					}
				} else {
					InsuranceProductDO doObj = insuranceProductDAO.findById(order.getTypeId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					
					//更新保险档次信息
					insuranceProductLevelDAO.deleteByProductId(order.getTypeId());
					//添加保险档次
					if (null != order.getInsuranceProductLevelOrders()
						&& order.getInsuranceProductLevelOrders().size() > 0) {
						for (InsuranceProductLevelOrder insuranceProductLevelOrder : order
							.getInsuranceProductLevelOrders()) {
							InsuranceProductLevelDO insuranceProductLevelDO = new InsuranceProductLevelDO();
							BeanCopier.staticCopy(insuranceProductLevelOrder,
								insuranceProductLevelDO);
							insuranceProductLevelDO.setProductId(doObj.getProductId());
							insuranceProductLevelDAO.insert(insuranceProductLevelDO);
							
						}
					}
					
					//险种所关联的保险责任
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					if (doObj.getIsLifeInsurance().equals("NO")) {
						doObj.setInsurancePeriod(String.valueOf(1));
						doObj.setInsurancePeriodType("年");
					}
					
					if (!StringUtils.isBlank(order.getCatalogId())) {
						doObj.setCatalogId(Integer.parseInt(order.getCatalogId()));
					}
					if (null != order.getPeriodRate()) {
						doObj.setPeriodRate(Double.valueOf(order.getPeriodRate()));
					}
					doObj.setUnitPrice(order.getUnitPrice());
					doObj.setStopSaleDate(!StringUtils.isBlank(order.getStopSaleDate()) ? DateUtil
						.parse(order.getStopSaleDate()) : null);
					insuranceProductDAO.update(doObj);
				}
				//新增险种与保险责任关联
				insuranceCatalogLiabilityDAO.deleteCalalogLiability(id);
				if (null != order.getInsuranceCatalogLiabilityOrders() && order.getInsuranceCatalogLiabilityOrders().size() > 0) {
					for (InsuranceCatalogLiabilityOrder insuranceCatalogLiabilityOrder : order.getInsuranceCatalogLiabilityOrders()) {
						InsuranceCatalogLiabilityDO insuranceCatalogLiabilityDO = new InsuranceCatalogLiabilityDO();
						BeanCopier.staticCopy(insuranceCatalogLiabilityOrder, insuranceCatalogLiabilityDO);
						insuranceCatalogLiabilityDO.setOwnerId(id);
						insuranceCatalogLiabilityDO.setType(InsuranceCatalogTypeEnum.product.getCode());
						insuranceCatalogLiabilityDAO.insert(insuranceCatalogLiabilityDO);
					}
				}
				saveProductCharge(id, order.getCharge());
				saveSalesAreas(id, order.getSalesAreasCollect(),
					SalesAreaTypeEnum.INSURANCE_PRODUCT);
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(id);
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceProductInfo findById(long id) {
		InsuranceProductDO insuranceTypesDo = insuranceProductDAO.findById(id);
		if (insuranceTypesDo != null) {
			InsuranceProductInfo insuranceTypesInfo = new InsuranceProductInfo();
			BeanCopier.staticCopy(insuranceTypesDo, insuranceTypesInfo);
			if (null != insuranceTypesDo.getStopSaleDate()) {
				insuranceTypesInfo.setStopSaleDate(insuranceTypesDo.getStopSaleDate());
			}
			insuranceTypesInfo.setPeriodRate(String.valueOf(insuranceTypesDo.getPeriodRate()));
			insuranceTypesInfo.setUnitPrice(insuranceTypesDo.getUnitPrice());
			querySalesAreas(insuranceTypesInfo, id, SalesAreaTypeEnum.INSURANCE_PRODUCT);
			queryChargeInfo(insuranceTypesInfo);
			return insuranceTypesInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceProductInfo> queryList(InsuranceProductQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceProductInfo> batchResult = new QueryBaseBatchResult<InsuranceProductInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceProductInfo> pageList = new ArrayList<InsuranceProductInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceProductDO insuranceTypeDO = new InsuranceProductDO();
			BeanCopier.staticCopy(queryOrder, insuranceTypeDO);
			long totalCount = insuranceProductDAO.findByConditionCount(insuranceTypeDO,queryOrder.getPriceProducts(),
				queryOrder.getProductIds(),queryOrder.getProjectSetUpCatalogIds());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceProductDO> recordList = insuranceProductDAO.findByCondition(
				insuranceTypeDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),queryOrder.getPriceProducts(),queryOrder.getProductIds(),queryOrder.getProjectSetUpCatalogIds() ,component.getFirstRecord(), component.getPageSize());
			for (InsuranceProductDO item : recordList) {
				InsuranceProductInfo info = new InsuranceProductInfo();
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
	public List<InsuranceCatalogLiabilityInfo> queryList(long ownerId) {
		List<InsuranceCatalogLiabilityInfo> queryList = new ArrayList<InsuranceCatalogLiabilityInfo>();
		List<InsuranceCatalogLiabilityDO> list = null;
		try {
			InsuranceCatalogLiabilityDO liabilityDO = new InsuranceCatalogLiabilityDO();
			liabilityDO.setType(InsuranceCatalogTypeEnum.product.getCode());
			list = insuranceCatalogLiabilityDAO.findByCondition(liabilityDO,null,null,0,100);
			for (InsuranceCatalogLiabilityDO item : list) {
				InsuranceCatalogLiabilityInfo info = new InsuranceCatalogLiabilityInfo();
				BeanCopier.staticCopy(item, info);
				queryList.add(info);
			}
		} catch (Exception e) {
			logger.error("查询保险责任异常", e);
		}
		return queryList;
	}
	
	@Override
	public List<InsuranceProductLevelInfo> queryProductLevelInfoList(long productId) {
		List<InsuranceProductLevelInfo> queryList = new ArrayList<InsuranceProductLevelInfo>();
		List<InsuranceProductLevelDO> list = null;
		try {
			list = insuranceProductLevelDAO.queryList(productId);
			for (InsuranceProductLevelDO item : list) {
				InsuranceProductLevelInfo info = new InsuranceProductLevelInfo();
				BeanCopier.staticCopy(item, info);
				queryList.add(info);
			}
		} catch (Exception e) {
			logger.error("查询产品档次异常", e);
		}
		return queryList;
	}
	
	private void queryChargeInfo(InsuranceProductInfo productInfo) {
		InsuranceProtocolChargeDO queryChargeDo = new InsuranceProtocolChargeDO();
		queryChargeDo.setProtocolInfoId(productInfo.getProductId());
		queryChargeDo.setType(InsuranceChargeEnum.PRODUCT.getCode());
		queryChargeDo.setParentId("0");
		queryChargeDo.setDepth(1);
		List<InsuranceProtocolChargeDO> yearDos = insuranceProtocolChargeDAO.findByCondition(
			queryChargeDo, null, null, 0, 100);
		if (ListUtil.isNotEmpty(yearDos)) {
			JSONArray yearJsonArray = new JSONArray();
			for (InsuranceProtocolChargeDO yearDo : yearDos) {
				JSONObject yearJsonObject = new JSONObject();
				yearJsonArray.add(yearJsonObject);
				yearJsonObject.put("year", yearDo.getVal());
				queryPeriodCharge(productInfo, yearDo, yearJsonObject, "man");
				queryPeriodCharge(productInfo, yearDo, yearJsonObject, "woman");
				
			}
			productInfo.setCharge(yearJsonArray);
		}
	}
	
	private void queryPeriodCharge(InsuranceProductInfo productInfo,
									InsuranceProtocolChargeDO yearDo, JSONObject yearJsonObject,
									String chargeType) {
		InsuranceProtocolChargeDO queryChargeDo;
		queryChargeDo = new InsuranceProtocolChargeDO();
		queryChargeDo.setProtocolInfoId(productInfo.getProductId());
		queryChargeDo.setType(InsuranceChargeEnum.PRODUCT.getCode());
		queryChargeDo.setChargeType(chargeType);
		queryChargeDo.setParentId(yearDo.getChargeId());
		queryChargeDo.setDepth(2);
		List<InsuranceProtocolChargeDO> periodDos = insuranceProtocolChargeDAO.findByCondition(
			queryChargeDo, null, null, 0, 100);
		if (ListUtil.isNotEmpty(periodDos)) {
			JSONArray manJsonArray = new JSONArray();
			yearJsonObject.put(chargeType, manJsonArray);
			for (InsuranceProtocolChargeDO periodDo : periodDos) {
				JSONObject periodJsonObject = new JSONObject();
				manJsonArray.add(periodJsonObject);
				periodJsonObject.put("period", periodDo.getVal());
				queryChargeDo = new InsuranceProtocolChargeDO();
				queryChargeDo.setProtocolInfoId(productInfo.getProductId());
				queryChargeDo.setType(InsuranceChargeEnum.PRODUCT.getCode());
				queryChargeDo.setChargeType(chargeType);
				queryChargeDo.setParentId(periodDo.getChargeId());
				queryChargeDo.setDepth(3);
				InsuranceProtocolChargeDO chargeDo = insuranceProtocolChargeDAO.findByCondition(
					queryChargeDo, null, null, 0, 100).get(0);
				periodJsonObject.put("charge", chargeDo.getVal());
			}
		}
	}
	
	private void saveProductCharge(long id, String charge) {
		if (StringUtil.isEmpty(charge)) {
			return;
		}
		insuranceProtocolChargeDAO.deleteProductByProtocolInfoId(id);
		
		JSONArray jsonArray = JSON.parseArray(charge);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject chargeJson = jsonArray.getJSONObject(i);
			InsuranceProtocolChargeDO yearChargeDO = new InsuranceProtocolChargeDO();
			yearChargeDO.setVal(chargeJson.getString("year"));
			yearChargeDO.setDepth(1);
			yearChargeDO.setProtocolInfoId(id);
			yearChargeDO.setParentId("0");
			yearChargeDO.setChargeId(BusinessNumberUtil.gainNumber());
			yearChargeDO.setNodePath(yearChargeDO.getChargeId() + ".");
			yearChargeDO.setType(InsuranceChargeEnum.PRODUCT.getCode());
			insuranceProtocolChargeDAO.insert(yearChargeDO);
			JSONArray manJson = chargeJson.getJSONArray("man");
			savePeriodCharge(yearChargeDO, manJson, "man");
			JSONArray womanJson = chargeJson.getJSONArray("woman");
			savePeriodCharge(yearChargeDO, womanJson, "woman");
		}
		
	}
	
	private void savePeriodCharge(InsuranceProtocolChargeDO yearChargeDO, JSONArray manJson,
									String chargeType) {
		for (int j = 0; j < manJson.size(); j++) {
			JSONObject periodJson = manJson.getJSONObject(j);
			InsuranceProtocolChargeDO periodChargeDO = new InsuranceProtocolChargeDO();
			periodChargeDO.setVal(periodJson.getString("period"));
			periodChargeDO.setDepth(2);
			periodChargeDO.setProtocolInfoId(yearChargeDO.getProtocolInfoId());
			periodChargeDO.setParentId(yearChargeDO.getChargeId());
			periodChargeDO.setChargeId(BusinessNumberUtil.gainNumber());
			periodChargeDO.setNodePath(yearChargeDO.getNodePath() + periodChargeDO.getChargeId()
										+ ".");
			periodChargeDO.setType(InsuranceChargeEnum.PRODUCT.getCode());
			periodChargeDO.setChargeType(chargeType);
			insuranceProtocolChargeDAO.insert(periodChargeDO);
			
			InsuranceProtocolChargeDO chargeDO = new InsuranceProtocolChargeDO();
			chargeDO.setVal(periodJson.getString("charge"));
			chargeDO.setDepth(3);
			chargeDO.setParentId(periodChargeDO.getChargeId());
			chargeDO.setChargeId(BusinessNumberUtil.gainNumber());
			chargeDO.setProtocolInfoId(yearChargeDO.getProtocolInfoId());
			chargeDO.setNodePath(periodChargeDO.getNodePath() + chargeDO.getChargeId() + ".");
			chargeDO.setType(InsuranceChargeEnum.PRODUCT.getCode());
			chargeDO.setChargeType(chargeType);
			insuranceProtocolChargeDAO.insert(chargeDO);
		}
	}
	
	@Override
	public List<CustomerCertInfo> getCustomerCertInfo(long customerUserid) {
		CustomerCertInfoDO customerCertInfoDO = new CustomerCertInfoDO();
		customerCertInfoDO.setUserId(customerUserid);
		List<CustomerCertInfo> pageList = new ArrayList<CustomerCertInfo>();
		List<CustomerCertInfoDO> list = customerCertInfoDAO.findByCondition(customerCertInfoDO,
			null, null, 0L, 999L);
		for (CustomerCertInfoDO item : list) {
			CustomerCertInfo info = new CustomerCertInfo();
			BeanCopier.staticCopy(item, info);
			info.setCertType(CertTypeEnum.getByCode(item.getCertType()));
			info.setCertTypeName(CertTypeEnum.getMsgByCode(item.getCertType()));
			pageList.add(info);
		}
		return pageList;
	}

	@Override
	public List<InsuranceProductLevelInfo> getInsuranceProductLevel(long productId) {
		List<InsuranceProductLevelInfo> pageList = new ArrayList<InsuranceProductLevelInfo>();
		List<InsuranceProductLevelDO> queryList = insuranceProductLevelDAO.queryList(productId);
		for (InsuranceProductLevelDO insuranceProductLevelDO : queryList) {
			InsuranceProductLevelInfo info = new InsuranceProductLevelInfo();
			BeanCopier.staticCopy(insuranceProductLevelDO, info);
			pageList.add(info);
		}
		return pageList;
	}

	@Override
	public InsuranceBaseResult runUpdateSaleStatus() {
		InsuranceBaseResult result = createResult();
		StringBuffer sb = new StringBuffer();
		try {
			List<InsuranceProductDO> list = insuranceProductDAO.findOutTimeProduct(new Date());
			if (ListUtil.isNotEmpty(list)) {
				for (InsuranceProductDO insuranceProductDO : list) {
					sb.append(insuranceProductDO.getProductId()).append(",");
				}
			} 
			String ids = sb.toString();
			if (!StringUtils.isBlank(ids)) {
				insuranceProductDAO.updateOutTimeProduct(ids.substring(0, ids.length()-1));
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("执行产品状态更新出错");
			logger.error("执行产品状态更新出错");
		}
		
		return result;
	}

	@Override
	public Map<String, String> queryInsuranceProtocolCharge(long productId, String chargeType, long year) {
		Map<String, String> maps = new HashMap<String, String>();
		try {
			maps = extraDAO.queryInsuranceProtocolCharge(productId, chargeType, year);
		} catch (Exception e) {
			logger.error("查询产品费率出错");
		}
		return maps;
	}
}