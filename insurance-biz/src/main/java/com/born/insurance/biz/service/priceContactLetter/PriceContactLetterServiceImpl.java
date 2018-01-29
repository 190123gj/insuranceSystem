package com.born.insurance.biz.service.priceContactLetter;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.daointerface.*;
import com.born.insurance.dal.dataobject.*;
import com.born.insurance.dataobject.PriceContactLetterExtraDo;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterFormInfo;
import com.born.insurance.ws.info.priceContactLetterAskCompany.PriceContactLetterAskCompanyChargeInfo;
import com.born.insurance.ws.info.priceContactLetterAskCompany.PriceContactLetterAskCompanyInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;
import com.born.insurance.ws.info.priceContactLetterDemandDetail.PriceContactLetterDemandDetailInfo;
import com.born.insurance.ws.info.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceDetailInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceInfo;
import com.born.insurance.ws.info.priceContactLetterReportPrice.PriceContactLetterReportPriceInfo;
import com.born.insurance.ws.info.priceContactLetterScheme.PriceContactLetterSchemeInfo;
import com.born.insurance.ws.info.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailInfo;
import com.born.insurance.ws.order.priceContactLetterAskCompany.PriceContactLetterAskCompanyOrder;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandOrder;
import com.born.insurance.ws.order.priceContactLetterDemand.PriceContactLetterDemandQueryOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetail.PriceContactLetterDemandDetailQueryOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoOrder;
import com.born.insurance.ws.order.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoQueryOrder;
import com.born.insurance.ws.order.priceContactLetterReportPrice.*;
import com.born.insurance.ws.order.priceContactLetterScheme.PriceContactLetterSchemeQueryOrder;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailOrder;
import com.born.insurance.ws.order.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.info.priceContactLetter.PriceContactLetterInfo;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterOrder;
import com.born.insurance.ws.order.priceContactLetter.PriceContactLetterQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.priceContactLetter.PriceContactLetterService;
import com.born.insurance.ws.service.priceContactLetterDemand.PriceContactLetterDemandService;
import com.born.insurance.ws.service.priceContactLetterDemandDetail.PriceContactLetterDemandDetailService;
import com.born.insurance.ws.service.priceContactLetterDemandDetailTwo.PriceContactLetterDemandDetailTwoService;
import com.born.insurance.ws.service.priceContactLetterReportPrice.PriceContactLetterReportPriceService;
import com.born.insurance.ws.service.priceContactLetterScheme.PriceContactLetterSchemeService;
import com.born.insurance.ws.service.priceContactLetterSchemeDetail.PriceContactLetterSchemeDetailService;
import com.google.common.collect.Maps;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import rop.thirdparty.com.google.common.collect.Lists;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("priceContactLetterService")
public class PriceContactLetterServiceImpl extends BaseFormAutowiredDomainService implements
																					PriceContactLetterService {
	@Autowired
	protected PriceContactLetterDAO priceContactLetterDAO;
	
	@Autowired
	protected PriceContactLetterDemandDAO priceContactLetterDemandDAO;
	
	@Autowired
	protected PriceContactLetterDemandDetailDAO priceContactLetterDemandDetailDAO;
	
	@Autowired
	protected PriceContactLetterDemandDetailTwoDAO priceContactLetterDemandDetailTwoDAO;
	
	@Autowired
	protected PriceContactLetterSchemeDAO priceContactLetterSchemeDAO;
	
	@Autowired
	protected PriceContactLetterSchemeDetailDAO priceContactLetterSchemeDetailDAO;
	
	@Autowired
	protected PriceContactLetterReportPriceDAO priceContactLetterReportPriceDAO;
	
	@Autowired
	protected PriceContactLetterCompanyReportPriceDAO priceContactLetterCompanyReportPriceDAO;
	
	@Autowired
	protected PriceContactLetterCompanyReportPriceDetailDAO priceContactLetterCompanyReportPriceDetailDAO;
	
	@Autowired
	protected PriceContactLetterDemandService priceContactLetterDemandService;
	
	@Autowired
	protected PriceContactLetterDemandDetailService priceContactLetterDemandDetailService;
	
	@Autowired
	protected PriceContactLetterDemandDetailTwoService priceContactLetterDemandDetailTwoService;
	
	@Autowired
	protected PriceContactLetterSchemeService priceContactLetterSchemeService;
	
	@Autowired
	protected PriceContactLetterSchemeDetailService priceContactLetterSchemeDetailService;
	
	@Autowired
	protected PriceContactLetterReportPriceService priceContactLetterReportPriceService;
	
	@Autowired
	protected PriceContactLetterAskCompanyDAO priceContactLetterAskCompanyDAO;
	
	@Autowired
	protected PriceContactLetterAskCompanyChargeDAO priceContactLetterAskCompanyChargeDAO;
	
	@Autowired
	protected InsuranceCatalogRelationDAO insuranceCatalogRelationDAO;
	
	@Override
	public FormBaseResult save(final PriceContactLetterOrder order) {
		return commonFormSaveProcess(order, "询价方案增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
						"获取表单信息出错");
				}
				long id = order.getId();
				if (id <= 0) {
					PriceContactLetterDO doObj = new PriceContactLetterDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setBillNo(genPriceCode());
					priceContactLetterDAO.insert(doObj);
					
					if (ListUtil.isNotEmpty(order.getDemandOrders())) {
						for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
							PriceContactLetterDemandDO demandDO = new PriceContactLetterDemandDO();
							BeanCopier.staticCopy(demandOrder, demandDO,
								UnBoxingConverter.getInstance());
							demandDO.setContactLetterId(doObj.getId());
							priceContactLetterDemandDAO.insert(demandDO);
							if (ListUtil.isNotEmpty(demandOrder.getDetailOrders())) {
								for (PriceContactLetterDemandDetailOrder demandDetailOrder : demandOrder
									.getDetailOrders()) {
									PriceContactLetterDemandDetailDO demandDetailDO = orderConvertDemandDetailDO(demandDetailOrder);
									demandDetailDO.setLetterDemandId(demandDO.getId());
									priceContactLetterDemandDetailDAO.insert(demandDetailDO);
								}
							}
							
							if (ListUtil.isNotEmpty(demandOrder.getDetailTwoOrders())) {
								for (PriceContactLetterDemandDetailTwoOrder demandDetailTwoOrder : demandOrder
									.getDetailTwoOrders()) {
									PriceContactLetterDemandDetailTwoDO demandDetailTwoDO = orderConvertDemandDetailTwoDO(demandDetailTwoOrder);
									demandDetailTwoDO.setLetterDemandId(demandDO.getId());
									priceContactLetterDemandDetailTwoDAO.insert(demandDetailTwoDO);
								}
							}
							
							if (demandOrder.getSchemeOrder() != null) {
								if (ListUtil.isNotEmpty(demandOrder.getSchemeOrder()
									.getSchemeDetailOrders())) {
									PriceContactLetterSchemeDO schemeDO = new PriceContactLetterSchemeDO();
									BeanCopier.staticCopy(demandOrder.getSchemeOrder(), schemeDO,
										UnBoxingConverter.getInstance());
									schemeDO.setLetterDemandId(demandDO.getId());
									priceContactLetterSchemeDAO.insert(schemeDO);
									for (PriceContactLetterSchemeDetailOrder schemeDetailOrder : demandOrder
										.getSchemeOrder().getSchemeDetailOrders()) {
										PriceContactLetterSchemeDetailDO schemeDetailDO = orderConvertSchemeDetailDO(schemeDetailOrder);
										schemeDetailDO.setLetterSchemeId(schemeDO.getId());
										priceContactLetterSchemeDetailDAO.insert(schemeDetailDO);
									}
								}
							}
							
							if (ListUtil.isNotEmpty(demandOrder.getAskCompanyOrders())) {
								for (PriceContactLetterAskCompanyOrder askCompanyOrder : demandOrder
									.getAskCompanyOrders()) {
									PriceContactLetterAskCompanyDO askCompanyDO = new PriceContactLetterAskCompanyDO();
									BeanCopier.staticCopy(askCompanyOrder, askCompanyDO,
										UnBoxingConverter.getInstance());
									askCompanyDO.setLetterDemandId(demandDO.getId());
									priceContactLetterAskCompanyDAO.insert(askCompanyDO);
									saveAskCompanyCharge(askCompanyOrder, askCompanyDO);
								}
							}
						}
						
					}
					
				} else {
					PriceContactLetterDO doObj = priceContactLetterDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					priceContactLetterDAO.update(doObj);
					
					updatePriceContactLetterDetail(order);
					
				}
				return null;
			}
		}, null, null);
	}
	
	private void saveAskCompanyCharge(PriceContactLetterAskCompanyOrder askCompanyOrder,
										PriceContactLetterAskCompanyDO askCompanyDO) {
		String[] catalogIds = askCompanyOrder.getCatalogIds().split(",");
		String[] catalogNames = askCompanyOrder.getCatalogNames().split(",");
		String[] intentionFees = askCompanyOrder.getIntentionFees().split(",");
		String[] protocolFees = askCompanyOrder.getProtocolFees().split(",");
		priceContactLetterAskCompanyChargeDAO.deleteByAskCompanyId(askCompanyDO.getId());
		for (int i = 0; i < catalogIds.length; i++) {
			PriceContactLetterAskCompanyChargeDO chargeDO = new PriceContactLetterAskCompanyChargeDO();
			chargeDO.setAskCompanyId(askCompanyDO.getId());
			chargeDO.setCatalogId(NumberUtil.parseLong(catalogIds[i]));
			chargeDO.setCatalogName(catalogNames[i]);
			chargeDO.setIntentionFee(NumberUtil.parseDouble(intentionFees[i]));
			chargeDO.setProtocolFee(NumberUtil.parseDouble(protocolFees[i]));
			priceContactLetterAskCompanyChargeDAO.insert(chargeDO);
		}
	}
	
	private void updatePriceContactLetterDetail(PriceContactLetterOrder order) {
		long priceContactLetterId = order.getId();
		
		PriceContactLetterDemandDO queryDo = new PriceContactLetterDemandDO();
		queryDo.setContactLetterId(priceContactLetterId);
		List<PriceContactLetterDemandDO> demandDOList = priceContactLetterDemandDAO
			.findByCondition(queryDo, null, null, 0, 100);
		if (ListUtil.isNotEmpty(order.getDemandOrders())) {
			for (PriceContactLetterDemandDO letterDemandDO : demandDOList) {
				boolean isExist = false;
				for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
					if (letterDemandDO.getId() == demandOrder.getId()) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					priceContactLetterDemandDAO.deleteById(letterDemandDO.getId());
				}
			}
			
			for (PriceContactLetterDemandDO demandDO : demandDOList) {
				PriceContactLetterDemandDetailDO queryDemandDetailDO = new PriceContactLetterDemandDetailDO();
				queryDemandDetailDO.setLetterDemandId(demandDO.getId());
				List<PriceContactLetterDemandDetailDO> demandDetailDOList = priceContactLetterDemandDetailDAO
					.findByCondition(queryDemandDetailDO, null, null, 0, 100);
				List<PriceContactLetterDemandDetailDO> deleteDemandDetailList = new ArrayList<PriceContactLetterDemandDetailDO>();
				for (PriceContactLetterDemandDetailDO demandDetailDO : demandDetailDOList) {
					boolean isExist = false;
					for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
						if (ListUtil.isNotEmpty(demandOrder.getDetailOrders())) {
							for (PriceContactLetterDemandDetailOrder demandDetailOrder : demandOrder
								.getDetailOrders()) {
								if (demandDetailOrder.getId() == demandDetailDO.getId()) {
									isExist = true;
								}
							}
						}
					}
					
					if (!isExist) {
						priceContactLetterDemandDetailDAO.deleteById(demandDetailDO.getId());
					}
				}
				
				PriceContactLetterDemandDetailTwoDO queryDemandDetailTwoDO = new PriceContactLetterDemandDetailTwoDO();
				queryDemandDetailTwoDO.setLetterDemandId(demandDO.getId());
				List<PriceContactLetterDemandDetailTwoDO> demandDetailTwoDOList = priceContactLetterDemandDetailTwoDAO
					.findByCondition(queryDemandDetailTwoDO, null, null, 0, 100);
				for (PriceContactLetterDemandDetailTwoDO demandDetailTwoDO : demandDetailTwoDOList) {
					boolean isExist = false;
					for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
						if (ListUtil.isNotEmpty(demandOrder.getDetailTwoOrders())) {
							for (PriceContactLetterDemandDetailTwoOrder demandDetailTwoOrder : demandOrder
								.getDetailTwoOrders()) {
								if (demandDetailTwoOrder.getId() == demandDetailTwoDO.getId()) {
									isExist = true;
								}
							}
						}
					}
					
					if (!isExist) {
						priceContactLetterDemandDetailTwoDAO.deleteById(demandDetailTwoDO.getId());
					}
				}
				
				for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
					PriceContactLetterSchemeDO querySchemeDO = new PriceContactLetterSchemeDO();
					querySchemeDO.setLetterDemandId(demandOrder.getId());
					List<PriceContactLetterSchemeDO> schemeDOList = priceContactLetterSchemeDAO
						.findByCondition(querySchemeDO, null, null, 0, 100);
					for (PriceContactLetterSchemeDO schemeDO : schemeDOList) {
						PriceContactLetterSchemeDetailDO queryPriceContactLetterSchemeDetailDO = new PriceContactLetterSchemeDetailDO();
						queryPriceContactLetterSchemeDetailDO.setLetterSchemeId(schemeDO.getId());
						List<PriceContactLetterSchemeDetailDO> schemeDetailDOList = priceContactLetterSchemeDetailDAO
							.findByCondition(queryPriceContactLetterSchemeDetailDO, null, null, 0,
								100);
						for (PriceContactLetterSchemeDetailDO schemeDetailDO : schemeDetailDOList) {
							boolean isExist = false;
							for (PriceContactLetterSchemeDetailOrder schemeDetailOrder : demandOrder
								.getSchemeOrder().getSchemeDetailOrders()) {
								if (schemeDetailDO.getId() == schemeDetailOrder.getId()) {
									isExist = true;
								}
							}
							
							if (!isExist) {
								priceContactLetterSchemeDetailDAO
									.deleteById(schemeDetailDO.getId());
							}
						}
						
						priceContactLetterSchemeDAO.deleteById(schemeDO.getId());
					}
					
				}
				
				for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
					PriceContactLetterAskCompanyDO queryAskCompanyDo = new PriceContactLetterAskCompanyDO();
					queryAskCompanyDo.setLetterDemandId(demandOrder.getId());
					List<PriceContactLetterAskCompanyDO> askCompanyDOList = priceContactLetterAskCompanyDAO
						.findByCondition(queryAskCompanyDo, null, null, 0, 100);
					for (PriceContactLetterAskCompanyDO askCompanyDO : askCompanyDOList) {
						boolean isExist = false;
						for (PriceContactLetterAskCompanyOrder askCompanyOrder : demandOrder
							.getAskCompanyOrders()) {
							if (askCompanyDO.getId() == askCompanyOrder.getId()) {
								isExist = true;
							}
						}
						
						if (!isExist) {
							priceContactLetterAskCompanyDAO.deleteById(askCompanyDO.getId());
						}
					}
				}
				
			}
			
			for (PriceContactLetterDemandOrder demandOrder : order.getDemandOrders()) {
				PriceContactLetterDemandDO demandDO = new PriceContactLetterDemandDO();
				BeanCopier.staticCopy(demandOrder, demandDO, UnBoxingConverter.getInstance());
				demandDO.setContactLetterId(order.getId());
				if (demandDO.getId() > 0) {
					priceContactLetterDemandDAO.update(demandDO);
				} else {
					priceContactLetterDemandDAO.insert(demandDO);
				}
				
				if (ListUtil.isNotEmpty(demandOrder.getDetailOrders())) {
					for (PriceContactLetterDemandDetailOrder demandDetailOrder : demandOrder
						.getDetailOrders()) {
						PriceContactLetterDemandDetailDO demandDetailDO = orderConvertDemandDetailDO(demandDetailOrder);
						demandDetailDO.setLetterDemandId(demandDO.getId());
						if (demandDetailDO.getId() > 0) {
							priceContactLetterDemandDetailDAO.update(demandDetailDO);
						} else {
							priceContactLetterDemandDetailDAO.insert(demandDetailDO);
						}
						
					}
				}
				
				if (ListUtil.isNotEmpty(demandOrder.getDetailTwoOrders())) {
					for (PriceContactLetterDemandDetailTwoOrder demandDetailTwoOrder : demandOrder
						.getDetailTwoOrders()) {
						PriceContactLetterDemandDetailTwoDO demandDetailTwoDO = orderConvertDemandDetailTwoDO(demandDetailTwoOrder);
						demandDetailTwoDO.setLetterDemandId(demandDO.getId());
						if (demandDetailTwoDO.getId() > 0) {
							priceContactLetterDemandDetailTwoDAO.update(demandDetailTwoDO);
						} else {
							priceContactLetterDemandDetailTwoDAO.insert(demandDetailTwoDO);
						}
						
					}
				}
				
				if (demandOrder.getSchemeOrder() != null) {
					if (ListUtil.isNotEmpty(demandOrder.getSchemeOrder().getSchemeDetailOrders())) {
						PriceContactLetterSchemeDO schemeDO = new PriceContactLetterSchemeDO();
						BeanCopier.staticCopy(demandOrder.getSchemeOrder(), schemeDO,
							UnBoxingConverter.getInstance());
						schemeDO.setLetterDemandId(demandDO.getId());
						if (schemeDO.getId() > 0) {
							priceContactLetterSchemeDAO.update(schemeDO);
						} else {
							priceContactLetterSchemeDAO.insert(schemeDO);
						}
						
						for (PriceContactLetterSchemeDetailOrder schemeDetailOrder : demandOrder
							.getSchemeOrder().getSchemeDetailOrders()) {
							PriceContactLetterSchemeDetailDO schemeDetailDO = orderConvertSchemeDetailDO(schemeDetailOrder);
							schemeDetailDO.setLetterSchemeId(schemeDO.getId());
							if (schemeDetailDO.getId() > 0) {
								priceContactLetterSchemeDetailDAO.update(schemeDetailDO);
							} else {
								priceContactLetterSchemeDetailDAO.insert(schemeDetailDO);
							}
							
						}
					}
				}
				
				if (ListUtil.isNotEmpty(demandOrder.getAskCompanyOrders())) {
					for (PriceContactLetterAskCompanyOrder askCompanyOrder : demandOrder
						.getAskCompanyOrders()) {
						PriceContactLetterAskCompanyDO askCompanyDO = new PriceContactLetterAskCompanyDO();
						BeanCopier.staticCopy(askCompanyOrder, askCompanyDO,
							UnBoxingConverter.getInstance());
						askCompanyDO.setLetterDemandId(demandDO.getId());
						if (askCompanyDO.getId() > 0) {
							priceContactLetterAskCompanyDAO.update(askCompanyDO);
						} else {
							priceContactLetterAskCompanyDAO.insert(askCompanyDO);
						}
						saveAskCompanyCharge(askCompanyOrder, askCompanyDO);
					}
				}
			}
			
		}
	}
	
	private PriceContactLetterSchemeDetailDO orderConvertSchemeDetailDO(PriceContactLetterSchemeDetailOrder schemeDetailOrder) {
		PriceContactLetterSchemeDetailDO schemeDetailDO = new PriceContactLetterSchemeDetailDO();
		BeanCopier.staticCopy(schemeDetailOrder, schemeDetailDO, UnBoxingConverter.getInstance());
		return schemeDetailDO;
	}
	
	@Override
	public PriceContactLetterInfo findByFormId(long id) {
		PriceContactLetterDO priceContactLetterDo = priceContactLetterDAO.findByFormId(id);
		return queryPriceContactLetterInfoDetail(priceContactLetterDo, false);
		
	}
	
	private PriceContactLetterInfo queryPriceContactLetterInfoDetail(	PriceContactLetterDO priceContactLetterDo,
																		boolean clearId) {
		if (priceContactLetterDo != null) {
			PriceContactLetterInfo priceContactLetterInfo = new PriceContactLetterInfo();
			BeanCopier.staticCopy(priceContactLetterDo, priceContactLetterInfo);
			queryPriceDemandInfos(priceContactLetterInfo, clearId);
			queryReportPrice(priceContactLetterInfo,false);
			return priceContactLetterInfo;
		}
		return null;
	}

	@Override
	public void initReportPriceInfo(PriceContactLetterInfo letterInfo) {
		queryReportPrice(letterInfo,true);
	}

	private void queryReportPrice(PriceContactLetterInfo priceContactLetterInfo,boolean removeDup) {
		PriceContactLetterReportPriceDO queryPriceDO = new PriceContactLetterReportPriceDO();
		queryPriceDO.setContactLetterId(priceContactLetterInfo.getId());
		List<PriceContactLetterReportPriceDO> priceDOList = priceContactLetterReportPriceDAO
			.findByCondition(queryPriceDO, null, null, 0, 1);
		if (ListUtil.isNotEmpty(priceDOList)) {
			PriceContactLetterReportPriceDO priceDO = priceDOList.get(0);
			PriceContactLetterReportPriceInfo reportPriceInfo = new PriceContactLetterReportPriceInfo();
			BeanCopier.staticCopy(priceDO, reportPriceInfo);
			priceContactLetterInfo.setReportPriceInfo(reportPriceInfo);
			PriceContactLetterCompanyReportPriceDO queryCompanyReportPriceDO = new PriceContactLetterCompanyReportPriceDO();
			queryCompanyReportPriceDO.setReportPriceId(reportPriceInfo.getReportPriceId());
			List<PriceContactLetterCompanyReportPriceDO> companyReportPriceDOList = priceContactLetterCompanyReportPriceDAO
				.findByCondition(queryCompanyReportPriceDO, null, null, 0, 100);
			if (ListUtil.isNotEmpty(companyReportPriceDOList)) {
				List<PriceContactLetterCompanyReportPriceInfo> reportPriceInfoList = new ArrayList<PriceContactLetterCompanyReportPriceInfo>();
				for (PriceContactLetterCompanyReportPriceDO companyReportPriceDO : companyReportPriceDOList) {
					PriceContactLetterCompanyReportPriceInfo companyReportPriceInfo = new PriceContactLetterCompanyReportPriceInfo();
					BeanCopier.staticCopy(companyReportPriceDO, companyReportPriceInfo);
					reportPriceInfoList.add(companyReportPriceInfo);
					PriceContactLetterCompanyReportPriceDetailDO queryCompanyReportPriceDetailDO = new PriceContactLetterCompanyReportPriceDetailDO();
					queryCompanyReportPriceDetailDO.setCompanyReportPriceId(companyReportPriceDO
						.getId());
					List<PriceContactLetterCompanyReportPriceDetailDO> companyReportPriceDetailDOList = priceContactLetterCompanyReportPriceDetailDAO
						.findByCondition(queryCompanyReportPriceDetailDO, null, null, 0, 100);
					
					if (ListUtil.isNotEmpty(companyReportPriceDetailDOList)) {
						
						List<PriceContactLetterCompanyReportPriceDetailInfo> priceDetailInfoList = new ArrayList<PriceContactLetterCompanyReportPriceDetailInfo>();
						Map<String,PriceContactLetterCompanyReportPriceDetailInfo> map = Maps.newHashMap();
						for (PriceContactLetterCompanyReportPriceDetailDO companyReportPriceDetailDO : companyReportPriceDetailDOList) {
							PriceContactLetterCompanyReportPriceDetailInfo companyReportPriceDetailInfo = new PriceContactLetterCompanyReportPriceDetailInfo();
							BeanCopier.staticCopy(companyReportPriceDetailDO,
								companyReportPriceDetailInfo);
							companyReportPriceDetailInfo
								.setInsuranceAmount(companyReportPriceDetailDO.getInsuranceAmount()
									.toStandardString());
							companyReportPriceDetailInfo.setBorkerAmount(companyReportPriceDetailDO
								.getBorkerAmount().toStandardString());
							companyReportPriceDetailInfo
								.setExpectPremiumAmount(companyReportPriceDetailDO
									.getExpectPremiumAmount().toStandardString());
							companyReportPriceDetailInfo
								.setPremiumAmount(companyReportPriceDetailDO.getPremiumAmount()
									.toStandardString());
							companyReportPriceDetailInfo.setLimitAmount(companyReportPriceDetailDO.getLimitAmount().toStandardString());
							if(map.get(companyReportPriceDetailInfo.getProductId() +"") != null){
								continue;
							}
							map.put(""+companyReportPriceDetailInfo.getProductId(),companyReportPriceDetailInfo);
							priceDetailInfoList.add(companyReportPriceDetailInfo);
						}
						companyReportPriceInfo.setDetailInfoList(priceDetailInfoList);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("lists", priceDetailInfoList);
						JSONObject otherObject = new JSONObject();
						otherObject.put("quotationTime", companyReportPriceInfo.getCreateDate());
						otherObject.put("lineTime", companyReportPriceInfo.getExpiryDate());
						otherObject.put("remark",companyReportPriceInfo.getRemark());
						jsonObject.put("others", otherObject);

						companyReportPriceInfo.setDetailInfos(jsonObject.toJSONString());
					}
				}
				reportPriceInfo.setCompanyReportPriceInfos(reportPriceInfoList);
			}
		}
	}
	
	@Override
	public List<PriceContactLetterCompanyReportPriceInfo> queryReportPriceCustomer(	long priceContactLetterId) {
		PriceContactLetterReportPriceDO queryPriceDO = new PriceContactLetterReportPriceDO();
		queryPriceDO.setContactLetterId(priceContactLetterId);
		List<PriceContactLetterReportPriceDO> priceDOList = priceContactLetterReportPriceDAO
			.findByCondition(queryPriceDO, null, null, 0, 1);
		List<PriceContactLetterCompanyReportPriceInfo> reportPriceInfoList = new ArrayList<PriceContactLetterCompanyReportPriceInfo>();
		if (ListUtil.isNotEmpty(priceDOList)) {
			PriceContactLetterReportPriceDO priceDO = priceDOList.get(0);
			PriceContactLetterCompanyReportPriceDO queryCompanyReportPriceDO = new PriceContactLetterCompanyReportPriceDO();
			queryCompanyReportPriceDO.setReportPriceId(priceDO.getReportPriceId());
			List<PriceContactLetterCompanyReportPriceDO> companyReportPriceDOList = priceContactLetterCompanyReportPriceDAO
				.findByCondition(queryCompanyReportPriceDO, null, null, 0, 100);
			if (ListUtil.isNotEmpty(companyReportPriceDOList)) {
				for (PriceContactLetterCompanyReportPriceDO companyReportPriceDO : companyReportPriceDOList) {
					PriceContactLetterCompanyReportPriceInfo companyReportPriceInfo = new PriceContactLetterCompanyReportPriceInfo();
					BeanCopier.staticCopy(companyReportPriceDO, companyReportPriceInfo);
					reportPriceInfoList.add(companyReportPriceInfo);
				}
			}
		}
		return reportPriceInfoList;
	}
	
	@Override
	public List<PriceContactLetterCompanyReportPriceDetailInfo> queryReportPriceDetail(	ReportPriceDetailQueryOrder reportPriceDetailQueryOrder) {
		List<PriceContactLetterCompanyReportPriceDetailInfo> priceDetailInfoList = new ArrayList<PriceContactLetterCompanyReportPriceDetailInfo>();
		
		PriceContactLetterReportPriceDO queryPriceDO = new PriceContactLetterReportPriceDO();
		queryPriceDO.setContactLetterId(reportPriceDetailQueryOrder.getPriceContactId());
		List<PriceContactLetterReportPriceDO> priceDOList = priceContactLetterReportPriceDAO
			.findByCondition(queryPriceDO, null, null, 0, 1);
		if (ListUtil.isNotEmpty(priceDOList)) {
			PriceContactLetterReportPriceDO priceDO = priceDOList.get(0);
			PriceContactLetterCompanyReportPriceDO queryCompanyReportPriceDO = new PriceContactLetterCompanyReportPriceDO();
			queryCompanyReportPriceDO.setReportPriceId(priceDO.getReportPriceId());
			List<PriceContactLetterCompanyReportPriceDO> companyReportPriceDOList = priceContactLetterCompanyReportPriceDAO
				.findByCondition(queryCompanyReportPriceDO, null, null, 0, 100);
			if (ListUtil.isNotEmpty(companyReportPriceDOList)) {
				for (PriceContactLetterCompanyReportPriceDO companyReportPriceDO : companyReportPriceDOList) {
					PriceContactLetterCompanyReportPriceDetailDO queryCompanyReportPriceDetailDO = new PriceContactLetterCompanyReportPriceDetailDO();
					queryCompanyReportPriceDetailDO.setCompanyReportPriceId(companyReportPriceDO
						.getId());
					queryCompanyReportPriceDetailDO.setProductId(reportPriceDetailQueryOrder
						.getProductId());
					List<PriceContactLetterCompanyReportPriceDetailDO> companyReportPriceDetailDOList = priceContactLetterCompanyReportPriceDetailDAO
						.findByCondition(queryCompanyReportPriceDetailDO, null, null, 0, 100);
					if (ListUtil.isNotEmpty(companyReportPriceDetailDOList)) {
						for (PriceContactLetterCompanyReportPriceDetailDO companyReportPriceDetailDO : companyReportPriceDetailDOList) {
							PriceContactLetterCompanyReportPriceDetailInfo companyReportPriceDetailInfo = new PriceContactLetterCompanyReportPriceDetailInfo();
							BeanCopier.staticCopy(companyReportPriceDetailDO,
								companyReportPriceDetailInfo);
							companyReportPriceDetailInfo
								.setInsuranceAmount(companyReportPriceDetailDO.getInsuranceAmount()
									.toStandardString());
							companyReportPriceDetailInfo.setBorkerAmount(companyReportPriceDetailDO
								.getBorkerAmount().toStandardString());
							companyReportPriceDetailInfo
								.setExpectPremiumAmount(companyReportPriceDetailDO
									.getExpectPremiumAmount().toStandardString());
							companyReportPriceDetailInfo
								.setPremiumAmount(companyReportPriceDetailDO.getPremiumAmount()
									.toStandardString());
							priceDetailInfoList.add(companyReportPriceDetailInfo);
						}
					}
				}
			}
		}
		return priceDetailInfoList;
	}
	
	@Override
	public List<PriceContactLetterCompanyReportPriceDetailInfo> queryReportPriceDetail(	long priceContactLetterId,
																						long customerUserId) {
		List<PriceContactLetterCompanyReportPriceDetailInfo> priceDetailInfoList = new ArrayList<PriceContactLetterCompanyReportPriceDetailInfo>();
		
		PriceContactLetterReportPriceDO queryPriceDO = new PriceContactLetterReportPriceDO();
		queryPriceDO.setContactLetterId(priceContactLetterId);
		List<PriceContactLetterReportPriceDO> priceDOList = priceContactLetterReportPriceDAO
			.findByCondition(queryPriceDO, null, null, 0, 1);
		if (ListUtil.isNotEmpty(priceDOList)) {
			PriceContactLetterReportPriceDO priceDO = priceDOList.get(0);
			PriceContactLetterCompanyReportPriceDO queryCompanyReportPriceDO = new PriceContactLetterCompanyReportPriceDO();
			queryCompanyReportPriceDO.setReportPriceId(priceDO.getReportPriceId());
			queryCompanyReportPriceDO.setCustomerUserId(customerUserId);
			List<PriceContactLetterCompanyReportPriceDO> companyReportPriceDOList = priceContactLetterCompanyReportPriceDAO
				.findByCondition(queryCompanyReportPriceDO, null, null, 0, 100);
			if (ListUtil.isNotEmpty(companyReportPriceDOList)) {
				for (PriceContactLetterCompanyReportPriceDO companyReportPriceDO : companyReportPriceDOList) {
					PriceContactLetterCompanyReportPriceDetailDO queryCompanyReportPriceDetailDO = new PriceContactLetterCompanyReportPriceDetailDO();
					queryCompanyReportPriceDetailDO.setCompanyReportPriceId(companyReportPriceDO
						.getId());
					List<PriceContactLetterCompanyReportPriceDetailDO> companyReportPriceDetailDOList = priceContactLetterCompanyReportPriceDetailDAO
						.findByCondition(queryCompanyReportPriceDetailDO, null, null, 0, 100);
					if (ListUtil.isNotEmpty(companyReportPriceDetailDOList)) {
						for (PriceContactLetterCompanyReportPriceDetailDO companyReportPriceDetailDO : companyReportPriceDetailDOList) {
							PriceContactLetterCompanyReportPriceDetailInfo companyReportPriceDetailInfo = new PriceContactLetterCompanyReportPriceDetailInfo();
							BeanCopier.staticCopy(companyReportPriceDetailDO,
								companyReportPriceDetailInfo);
							companyReportPriceDetailInfo
								.setInsuranceAmount(companyReportPriceDetailDO.getInsuranceAmount()
									.toStandardString());
							companyReportPriceDetailInfo.setBorkerAmount(companyReportPriceDetailDO
								.getBorkerAmount().toStandardString());
							companyReportPriceDetailInfo
								.setExpectPremiumAmount(companyReportPriceDetailDO
									.getExpectPremiumAmount().toStandardString());
							companyReportPriceDetailInfo
								.setPremiumAmount(companyReportPriceDetailDO.getPremiumAmount()
									.toStandardString());
							priceDetailInfoList.add(companyReportPriceDetailInfo);
						}
					}
				}
			}
		}
		return priceDetailInfoList;
		
	}
	
	@Override
	public PriceContactLetterInfo findById(long id) {
		PriceContactLetterDO priceContactLetterDo = priceContactLetterDAO.findById(id);
		return queryPriceContactLetterInfoDetail(priceContactLetterDo, false);
	}
	
	@Override
	public PriceContactLetterInfo forwardFindById(long id) {
		PriceContactLetterDO priceContactLetterDo = priceContactLetterDAO.findById(id);
		return queryPriceContactLetterInfoDetail(priceContactLetterDo, true);
	}
	
	/**
	 * 查询询价方案标的信息
	 * @param priceContactLetterInfo
	 */
	private void queryPriceDemandInfos(PriceContactLetterInfo priceContactLetterInfo,
										boolean clearId) {
		PriceContactLetterDemandQueryOrder demandQueryOrder = new PriceContactLetterDemandQueryOrder();
		demandQueryOrder.setContactLetterId(priceContactLetterInfo.getId());
		List<PriceContactLetterDemandInfo> demandInfoList = priceContactLetterDemandService
			.queryList(demandQueryOrder).getPageList();
		priceContactLetterInfo.setDemandInfos(demandInfoList);
		if (ListUtil.isNotEmpty(demandInfoList)) {
			for (PriceContactLetterDemandInfo demandInfo : demandInfoList) {
				queryInsuranceReportPrice(demandInfo);
				queryPriceDemandDetailInfos(demandInfo);
				queryPriceDemandDetailTwoInfos(demandInfo);
				queryReportPriceScheme(demandInfo, clearId);
				queryAskCompanyInfo(demandInfo, clearId);
			}
		}
	}
	
	/**
	 * 查询询价公司
	 * @param demandInfo
	 */
	private void queryAskCompanyInfo(PriceContactLetterDemandInfo demandInfo, boolean clearId) {
		PriceContactLetterAskCompanyDO askCompanyDO = new PriceContactLetterAskCompanyDO();
		askCompanyDO.setLetterDemandId(demandInfo.getId());
		List<PriceContactLetterAskCompanyDO> askCompanyDOList = priceContactLetterAskCompanyDAO
			.findByCondition(askCompanyDO, null, null, 0, 1000);
		if (ListUtil.isNotEmpty(askCompanyDOList)) {
			List<PriceContactLetterAskCompanyInfo> askCompanyInfoList = new ArrayList<PriceContactLetterAskCompanyInfo>();
			for (PriceContactLetterAskCompanyDO letterAskCompanyDO : askCompanyDOList) {
				PriceContactLetterAskCompanyInfo askCompanyInfo = new PriceContactLetterAskCompanyInfo();
				BeanCopier.staticCopy(letterAskCompanyDO, askCompanyInfo);
				askCompanyInfoList.add(askCompanyInfo);
				queryAskCompanyChargeInfo(askCompanyInfo);
				if (clearId) {
					askCompanyInfo.setId(0);
				}
			}
			demandInfo.setAskCompanyInfoList(askCompanyInfoList);
		}
	}
	
	private void queryAskCompanyChargeInfo(PriceContactLetterAskCompanyInfo askCompanyInfo) {
		PriceContactLetterAskCompanyChargeDO chargeDO = new PriceContactLetterAskCompanyChargeDO();
		chargeDO.setAskCompanyId(askCompanyInfo.getId());
		List<PriceContactLetterAskCompanyChargeDO> chargeDOList = priceContactLetterAskCompanyChargeDAO
			.findByCondition(chargeDO, null, null, 0, 100);
		StringBuilder catalogIds = new StringBuilder();
		StringBuilder catalogNames = new StringBuilder();
		StringBuilder intentionFees = new StringBuilder();
		StringBuilder protocolFees = new StringBuilder();
		List<PriceContactLetterAskCompanyChargeInfo> chargeInfos = new ArrayList<PriceContactLetterAskCompanyChargeInfo>();
		for (PriceContactLetterAskCompanyChargeDO askCompanyChargeDO : chargeDOList) {
			boolean isFirst = true;
			if (!isFirst) {
				catalogIds.append(",").append(askCompanyChargeDO.getCatalogId());
				catalogNames.append(",").append(askCompanyChargeDO.getCatalogName());
				intentionFees.append(",").append(askCompanyChargeDO.getIntentionFee());
				protocolFees.append(",").append(askCompanyChargeDO.getProtocolFee());
			}
			catalogIds.append(askCompanyChargeDO.getCatalogId());
			catalogNames.append(askCompanyChargeDO.getCatalogName());
			intentionFees.append(askCompanyChargeDO.getIntentionFee());
			protocolFees.append(askCompanyChargeDO.getProtocolFee());
			PriceContactLetterAskCompanyChargeInfo companyChargeInfo = new PriceContactLetterAskCompanyChargeInfo();
			BeanCopier.staticCopy(askCompanyChargeDO, companyChargeInfo);
			chargeInfos.add(companyChargeInfo);
		}
		askCompanyInfo.setCatalogIds(catalogIds.toString());
		askCompanyInfo.setCatalogNames(catalogNames.toString());
		askCompanyInfo.setIntentionFees(intentionFees.toString());
		askCompanyInfo.setProtocolFees(protocolFees.toString());
		askCompanyInfo.setChargeInfos(chargeInfos);
	}
	
	/**
	 * 查询询价方案的建议
	 * @param demandInfo
	 */
	private void queryReportPriceScheme(PriceContactLetterDemandInfo demandInfo, boolean clearId) {
		PriceContactLetterSchemeQueryOrder schemeQueryOrder = new PriceContactLetterSchemeQueryOrder();
		schemeQueryOrder.setLetterDemandId(demandInfo.getId());
		List<PriceContactLetterSchemeInfo> schemeInfoList = priceContactLetterSchemeService
			.queryList(schemeQueryOrder).getPageList();
		if (ListUtil.isNotEmpty(schemeInfoList)) {
			PriceContactLetterSchemeInfo letterSchemeInfo = schemeInfoList.get(0);
			demandInfo.setSchemeInfo(letterSchemeInfo);
			PriceContactLetterSchemeDetailQueryOrder schemeDetailQueryOrder = new PriceContactLetterSchemeDetailQueryOrder();
			schemeDetailQueryOrder.setLetterSchemeId(letterSchemeInfo.getId());
			List<PriceContactLetterSchemeDetailInfo> schemeDetailInfoList = priceContactLetterSchemeDetailService
				.queryList(schemeDetailQueryOrder).getPageList();
			letterSchemeInfo.setSchemeDetailInfoList(schemeDetailInfoList);
			Map<String, List<PriceContactLetterSchemeDetailInfo>> map = new HashMap<String, List<PriceContactLetterSchemeDetailInfo>>();
			for (PriceContactLetterSchemeDetailInfo detailInfo : schemeDetailInfoList) {
				List<PriceContactLetterSchemeDetailInfo> existList = map.get(String
					.valueOf(detailInfo.getParentCatalogId()));
				if (ListUtil.isEmpty(existList)) {
					existList = new ArrayList<PriceContactLetterSchemeDetailInfo>();
					map.put(String.valueOf(detailInfo.getParentCatalogId()), existList);
				}
				existList.add(detailInfo);
			}
			JSONArray jsonArray = new JSONArray();
			JSONObject extraKind = new JSONObject();
			for (String key : map.keySet()) {
				
				List<PriceContactLetterSchemeDetailInfo> existList = map.get(key);
				JSONArray array = new JSONArray();
				for (PriceContactLetterSchemeDetailInfo detailInfo : existList) {
					if (clearId) {
						detailInfo.setId(0);
					}
					array.add(JSON.toJSON(detailInfo));
				}
				jsonArray.add(array);
				
				InsuranceCatalogRelationDO relationDO = new InsuranceCatalogRelationDO();
				relationDO.setParentId(NumberUtil.parseLong(key));
				List<InsuranceCatalogRelationDO> relationDOs = insuranceCatalogRelationDAO
					.findByCondition(relationDO, null, null, 0, 1000);
				if (ListUtil.isNotEmpty(relationDOs)) {
					JSONArray dataList = new JSONArray();
					for (InsuranceCatalogRelationDO info : relationDOs) {
						JSONObject json = new JSONObject();
						json.put("catalogId", info.getChildId());
						json.put("catalogName", info.getChildName());
						dataList.add(json);
					}
					extraKind.put(key, dataList);
					
				}
				
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("detail", jsonArray);
			jsonObject.put("extraKinds", extraKind);
			letterSchemeInfo.setSchemeDetail(jsonObject.toString());
		}
	}
	
	/**
	 * 查询询价方案里面的标的明细二
	 * @param demandInfo
	 */
	private void queryPriceDemandDetailTwoInfos(PriceContactLetterDemandInfo demandInfo) {
		PriceContactLetterDemandDetailTwoQueryOrder twoQueryOrder = new PriceContactLetterDemandDetailTwoQueryOrder();
		twoQueryOrder.setLetterDemandId(demandInfo.getId());
		List<PriceContactLetterDemandDetailTwoInfo> demandDetailTwoInfoList = priceContactLetterDemandDetailTwoService
			.queryList(twoQueryOrder).getPageList();
		demandInfo.setDemandDetailTwoInfoList(demandDetailTwoInfoList);
	}
	
	/**
	 * 查询询价方案里面的标的明细一
	 * @param demandInfo
	 */
	private void queryPriceDemandDetailInfos(PriceContactLetterDemandInfo demandInfo) {
		PriceContactLetterDemandDetailQueryOrder demandDetailQueryOrder = new PriceContactLetterDemandDetailQueryOrder();
		demandDetailQueryOrder.setLetterDemandId(demandInfo.getId());
		List<PriceContactLetterDemandDetailInfo> demandDetailInfoList = priceContactLetterDemandDetailService
			.queryList(demandDetailQueryOrder).getPageList();
		demandInfo.setDemandDetailInfoList(demandDetailInfoList);
	}
	
	/**
	 * 查询保险公司的报价信息
	 * @param demandInfo
	 */
	private void queryInsuranceReportPrice(PriceContactLetterDemandInfo demandInfo) {
		//		PriceContactLetterReportPriceQueryOrder reportPriceQueryOrder = new PriceContactLetterReportPriceQueryOrder();
		//		reportPriceQueryOrder.setLetterDemandId(demandInfo.getId());
		//		List<PriceContactLetterCompanyReportPriceInfo> reportPriceInfoList = priceContactLetterReportPriceService
		//			.queryList(reportPriceQueryOrder).getPageList();
		//		if (ListUtil.isNotEmpty(reportPriceInfoList)) {
		//			demandInfo.setReportPriceInfo(reportPriceInfoList.get(0));
		//			PriceContactLetterReportPriceDetailQueryOrder detailQueryOrder = new PriceContactLetterReportPriceDetailQueryOrder();
		//			detailQueryOrder.setReportPriceId(reportPriceInfoList.get(0).getId());
		//			List<PriceContactLetterCompanyReportPriceDetailInfo> priceDetailInfoList = priceContactLetterReportPriceService
		//				.queryList(detailQueryOrder).getPageList();
		//			demandInfo.getReportPriceInfo().setDetailInfoList(priceDetailInfoList);
		//
		//		}
		
	}
	
	@Override
	public QueryBaseBatchResult<PriceContactLetterFormInfo> queryFormList(	PriceContactLetterQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterFormInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterFormInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterFormInfo> pageList = new ArrayList<PriceContactLetterFormInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterExtraDo priceContactLetterDO = new PriceContactLetterExtraDo();
			BeanCopier.staticCopy(queryOrder, priceContactLetterDO);
			long totalCount = extraDAO
				.findFormPriceContactLetterByConditionCount(priceContactLetterDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterExtraDo> recordList = extraDAO
				.findFormPriceContactLetterByCondition(priceContactLetterDO,
					component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterExtraDo item : recordList) {
				PriceContactLetterFormInfo info = new PriceContactLetterFormInfo();
				BeanCopier.staticCopy(item, info);
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
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
	public QueryBaseBatchResult<PriceContactLetterInfo> queryList(	PriceContactLetterQueryOrder queryOrder) {
		QueryBaseBatchResult<PriceContactLetterInfo> batchResult = new QueryBaseBatchResult<PriceContactLetterInfo>();
		
		try {
			queryOrder.check();
			List<PriceContactLetterInfo> pageList = new ArrayList<PriceContactLetterInfo>(
				(int) queryOrder.getPageSize());
			
			PriceContactLetterDO priceContactLetterDO = new PriceContactLetterDO();
			BeanCopier.staticCopy(queryOrder, priceContactLetterDO);
			long totalCount = priceContactLetterDAO.findByConditionCount(priceContactLetterDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PriceContactLetterDO> recordList = priceContactLetterDAO.findByCondition(
				priceContactLetterDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			for (PriceContactLetterDO item : recordList) {
				PriceContactLetterInfo info = new PriceContactLetterInfo();
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
	
	/**
	 * 编号
	 * @return
	 */
	private synchronized String genPriceCode() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String priceCode = "X" + year + "-";
		String seqName = SysDateSeqNameEnum.PRICE_CONTACT_LETTER.code() + priceCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		priceCode += "-" + StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return priceCode;
	}
	
	@Override
	public String priceSchemeCode(PriceContactLetterInfo letterInfo) {
		return letterInfo.getPriceTemplate();
	}
	
	@Override
	public FormBaseResult saveReportPrice(final PriceContactLetterReportPriceOrder priceOrder) {
		return commonFormSaveProcess(priceOrder, "报价方案增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
						"获取表单信息出错");
				}
				long id = priceOrder.getReportPriceId();
				if (id <= 0) {
					PriceContactLetterReportPriceDO doObj = new PriceContactLetterReportPriceDO();
					BeanCopier.staticCopy(priceOrder, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					PriceContactLetterDO priceContactLetterDO = priceContactLetterDAO
						.findById(priceOrder.getContactLetterId());
					priceContactLetterDO.setStatus("REPORT_END");
					priceContactLetterDAO.update(priceContactLetterDO);
					priceContactLetterReportPriceDAO.insert(doObj);
					if (ListUtil.isNotEmpty(priceOrder.getParamOrders())) {
						for (PriceContactLetterCompanyReportPriceOrder detailOrder : priceOrder
							.getParamOrders().get(0).getCompanyReportPriceOrders()) {
							PriceContactLetterCompanyReportPriceDO detailDO = new PriceContactLetterCompanyReportPriceDO();
							BeanCopier.staticCopy(detailOrder, detailDO,
								UnBoxingConverter.getInstance());
							detailDO.setReportPriceId(doObj.getReportPriceId());
							priceContactLetterCompanyReportPriceDAO.insert(detailDO);
							List<Map> list = JSON.parseArray(
								JSON.parseObject(detailOrder.getAskCompanyOrdersData()).getString(
									"lists"), Map.class);
							
							for (Map map : list) {
								PriceContactLetterCompanyReportPriceDetailDO priceDetailDO = new PriceContactLetterCompanyReportPriceDetailDO();
								MiscUtil.setInfoPropertyByMap(map, priceDetailDO);
								priceDetailDO.setCompanyReportPriceId(detailDO.getId());
								priceDetailDO.setId(0);
								priceContactLetterCompanyReportPriceDetailDAO.insert(priceDetailDO);
							}
						}
					}
					
				} else {
					PriceContactLetterReportPriceDO doObj = priceContactLetterReportPriceDAO
						.findByReportPriceId(priceOrder.getReportPriceId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"信息不存在");
					}
					BeanCopier.staticCopy(priceOrder, doObj, UnBoxingConverter.getInstance());
					priceContactLetterReportPriceDAO.update(doObj);
					updatePriceContactLetterReportPriceDetail(priceOrder);
					
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public void updatePriceContactLetterStatus(FormInfo formInfo) {
		PriceContactLetterDO priceContactLetterDO = priceContactLetterDAO.findByFormId(formInfo
			.getFormId());
		priceContactLetterDO.setStatus("REPORT_END");
		priceContactLetterDAO.update(priceContactLetterDO);
	}
	
	private PriceContactLetterDemandDetailTwoDO orderConvertDemandDetailTwoDO(	PriceContactLetterDemandDetailTwoOrder demandDetailTwoOrder) {
		PriceContactLetterDemandDetailTwoDO demandDetailTwoDO = new PriceContactLetterDemandDetailTwoDO();
		BeanCopier.staticCopy(demandDetailTwoOrder, demandDetailTwoDO,
			UnBoxingConverter.getInstance());
		return demandDetailTwoDO;
	}
	
	private PriceContactLetterDemandDetailDO orderConvertDemandDetailDO(PriceContactLetterDemandDetailOrder demandDetailOrder) {
		PriceContactLetterDemandDetailDO demandDetailDO = new PriceContactLetterDemandDetailDO();
		BeanCopier.staticCopy(demandDetailOrder, demandDetailDO, UnBoxingConverter.getInstance());
		return demandDetailDO;
	}
	
	private void updatePriceContactLetterReportPriceDetail(	PriceContactLetterReportPriceOrder priceOrder) {
		PriceContactLetterCompanyReportPriceDO detailDO = new PriceContactLetterCompanyReportPriceDO();
		List<PriceContactLetterCompanyReportPriceDO> priceDetailDOs = priceContactLetterCompanyReportPriceDAO
			.findByCondition(detailDO, null, null, 0, 100);
		if (ListUtil.isNotEmpty(priceDetailDOs)) {
			for (PriceContactLetterCompanyReportPriceDO priceDetailDO : priceDetailDOs) {
				boolean exist = false;
				for (PriceContactLetterCompanyReportPriceOrder detailOrder : priceOrder
					.getParamOrders().get(0).getCompanyReportPriceOrders()) {
					if (detailOrder.getId() == priceDetailDO.getId()) {
						exist = true;
						break;
					}
				}
				if (!exist) {
					priceContactLetterCompanyReportPriceDAO.deleteById(priceDetailDO.getId());
				}
				
				priceContactLetterCompanyReportPriceDetailDAO
					.deleteByCompanyReportPriceId(priceDetailDO.getId());
			}
		}
		
		for (PriceContactLetterCompanyReportPriceOrder detailOrder : priceOrder.getParamOrders()
			.get(0).getCompanyReportPriceOrders()) {
			PriceContactLetterCompanyReportPriceDO reportPriceDetailDO = new PriceContactLetterCompanyReportPriceDO();
			BeanCopier
				.staticCopy(detailOrder, reportPriceDetailDO, UnBoxingConverter.getInstance());
			detailDO.setReportPriceId(priceOrder.getReportPriceId());
			List<PriceContactLetterCompanyReportPriceDetailDO> l = JSON.parseArray(JSON
				.parseObject(detailOrder.getAskCompanyOrdersData()).getString("lists"),
				PriceContactLetterCompanyReportPriceDetailDO.class);
			for (PriceContactLetterCompanyReportPriceDetailDO companyReportPriceDetailDO : l) {
				companyReportPriceDetailDO.setCompanyReportPriceId(detailDO.getId());
				priceContactLetterCompanyReportPriceDetailDAO.insert(companyReportPriceDetailDO);
			}
			if (detailOrder.getId() > 0) {
				priceContactLetterCompanyReportPriceDAO.update(reportPriceDetailDO);
			} else {
				priceContactLetterCompanyReportPriceDAO.insert(reportPriceDetailDO);
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		String s = "{\"lists\":[{\"parentCatalogId\":\"17\",\"parentCatalogName\":\"博恩财产\",\"letterSchemeId\":\"48\",\"version\":\"0\",\"id\":\"58\",\"deductibleRate\":\"0\",\"liabilityNameId\":\"0\",\"catalogId\":\"0\",\"expectPremiumAmount\":\"0\",\"productName\":\"fdasfdas\",\"productId\":\"38\",\"limitAmount\":\"1\",\"nonDeductible\":\"1\",\"premiumRate\":\"1\",\"insuranceAmount\":\"1\",\"intentionBrokerRate\":\"1\",\"chargeRate\":\"1.00\",\"premiumAmount\":\"10000\",\"borkerAmountRate\":\"1.00\",\"borkerAmount\":\"100\"}],\"others\":{\"quotationTime\":\"2017-03-07\",\"lineTime\":\"2017-03-30\",\"remark\":\"\",\"pathName_PRODUCT1_PROTOCOL\":\"\"}}";
		JSONObject j = JSON.parseObject(s);
		List<PriceContactLetterCompanyReportPriceDetailDO> l = JSON.parseArray(
			j.getString("lists"), PriceContactLetterCompanyReportPriceDetailDO.class);
		System.out.println(l);
	}
	
}