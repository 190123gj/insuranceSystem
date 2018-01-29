package com.born.insurance.biz.service.insuranceCatalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.insurance.dal.daointerface.InsuranceCatalogRelationDAO;
import com.born.insurance.dal.dataobject.InsuranceCatalogRelationDO;
import com.born.insurance.util.NumberUtil;
import com.yjf.common.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.InsuranceCatalogDAO;
import com.born.insurance.dal.daointerface.InsuranceCatalogLiabilityDAO;
import com.born.insurance.dal.daointerface.InsuranceConditionDAO;
import com.born.insurance.dal.dataobject.InsuranceCatalogDO;
import com.born.insurance.dal.dataobject.InsuranceCatalogLiabilityDO;
import com.born.insurance.dal.dataobject.InsuranceConditionDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.InsuranceCatalogTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogInfo;
import com.born.insurance.ws.info.insuranceCatalog.InsuranceCatalogLiabilityInfo;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogOrder;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogQueryOrder;
import com.born.insurance.ws.order.insuranceCatalogLiability.InsuranceCatalogLiabilityOrder;
import com.born.insurance.ws.order.insuranceConditions.InsuranceConditionsOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.insuranceCatalog.InsuranceCatalogService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by wqh on 2016-11-18.
 */
@Service("insuranceCatalogService")
public class InsuranceCatalogServiceImpl extends BaseAutowiredDomainService implements
																			InsuranceCatalogService {
	@Autowired
	protected InsuranceCatalogDAO insuranceCatalogDAO;
	
	@Autowired
	protected InsuranceConditionDAO insuranceConditionDAO;
	
	@Autowired
	protected InsuranceCatalogLiabilityDAO insuranceCatalogLiabilityDAO;
	@Autowired
	protected InsuranceCatalogRelationDAO insuranceCatalogRelationDAO;
	
	@Override
	public InsuranceBaseResult save(final InsuranceCatalogOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				long id = order.getCatalogId();
				if (id <= 0) {
					InsuranceCatalogDO doObj = new InsuranceCatalogDO();
					BeanCopier.staticCopy(order, doObj);
					doObj.setType(order.getType().getCode());
					doObj.setLastDepth(order.getType().getLastDepth());
					doObj.setRawAddTime(now);
					long catalogId = insuranceCatalogDAO.insert(doObj);
					order.setCatalogId(catalogId);
					if (order.getParentCatalogId() > 0) {
						InsuranceCatalogDO catalogDO = insuranceCatalogDAO.findById(order
							.getParentCatalogId());
						doObj.setNodePath(catalogDO.getNodePath() + doObj.getCatalogId() + ".");
						doObj.setDepth(catalogDO.getDepth() + 1);
						insuranceCatalogDAO.update(doObj);
					} else {
						doObj.setNodePath(doObj.getCatalogId() + ".");
						doObj.setDepth(1);
						insuranceCatalogDAO.update(doObj);
					}
					
					saveConditionsAndLiability(order);
					
				} else {
					InsuranceCatalogDO doObj = insuranceCatalogDAO.findById(order.getCatalogId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"分类信息不存在");
					}
					doObj.setCatalogCode(order.getCatalogCode());
					doObj.setCatalogName(order.getCatalogName());
					doObj.setPriceTemplate(order.getPriceTemplate());
					doObj.setIsMain(order.getIsMain());
					doObj.setIsLifeInsurance(order.getIsLifeInsurance());
					doObj.setAbbr1(order.getAbbr1());
					doObj.setAbbr2(order.getAbbr2());
					doObj.setAbbr3(order.getAbbr3());
					insuranceCatalogDAO.update(doObj);
					updateConditionsAndLiability(order);
				}
				saveCatalogRelation(order);
				return null;
			}
		}, null, null);
	}
	
	private void updateConditionsAndLiability(InsuranceCatalogOrder order) {
		if (order.getType() == InsuranceCatalogTypeEnum.kinds) {
			InsuranceConditionDO condition = new InsuranceConditionDO();
			condition.setOwnerId(order.getCatalogId());
			condition.setType(InsuranceCatalogTypeEnum.kinds.getCode());
			List<InsuranceConditionDO> conditionDOs = insuranceConditionDAO.findByCondition(
				condition, null, null, 0, 9999);
			if (ListUtil.isNotEmpty(conditionDOs)) {
				for (InsuranceConditionDO insuranceC : conditionDOs) {
					boolean exist = false;
					for (InsuranceConditionsOrder conditionsOrder : order.getConditionOrders()) {
						if (insuranceC.getConditionId() == conditionsOrder.getConditionId()) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						insuranceConditionDAO.deleteById(insuranceC.getConditionId());
					}
					
				}
			}
			
			if (ListUtil.isNotEmpty(order.getConditionOrders())) {
				for (InsuranceConditionsOrder conditionsOrder : order.getConditionOrders()) {
					if (conditionsOrder.getConditionId() > 0) {
						InsuranceConditionDO conditionDO = initInsuranceConditionDO(order,
							conditionsOrder);
						insuranceConditionDAO.update(conditionDO);
					} else {
						InsuranceConditionDO conditionDO = initInsuranceConditionDO(order,
							conditionsOrder);
						insuranceConditionDAO.insert(conditionDO);
					}
					
				}
			}
			
			InsuranceCatalogLiabilityDO insuranceCatalogLiabilityDO = new InsuranceCatalogLiabilityDO();
			insuranceCatalogLiabilityDO.setOwnerId(order.getCatalogId());
			List<InsuranceCatalogLiabilityDO> liabilityDOs = insuranceCatalogLiabilityDAO
				.findByCondition(insuranceCatalogLiabilityDO, null, null, 0, 9999);
			if (ListUtil.isNotEmpty(liabilityDOs)) {
				for (InsuranceCatalogLiabilityDO catalogLiabilityDO : liabilityDOs) {
					boolean exist = false;
					if (ListUtil.isNotEmpty(order.getLiabilityOrders())) {
						for (InsuranceCatalogLiabilityOrder liabilityOrder : order
							.getLiabilityOrders()) {
							if (catalogLiabilityDO.getId() == liabilityOrder.getId()) {
								exist = true;
								break;
							}
						}
					}
					
					if (!exist) {
						insuranceCatalogLiabilityDAO.deleteById(catalogLiabilityDO.getId());
					}
					
				}
			}
			
			if (ListUtil.isNotEmpty(order.getLiabilityOrders())) {
				for (InsuranceCatalogLiabilityOrder liabilityOrder : order.getLiabilityOrders()) {
					if (liabilityOrder.getId() > 0) {
						InsuranceCatalogLiabilityDO liabilityDO = initInsuranceCatalogLiabilityDO(
							order, liabilityOrder);
						insuranceCatalogLiabilityDAO.update(liabilityDO);
					} else {
						InsuranceCatalogLiabilityDO liabilityDO = initInsuranceCatalogLiabilityDO(
							order, liabilityOrder);
						insuranceCatalogLiabilityDAO.insert(liabilityDO);
					}
					
				}
			}
		}
	}
	
	private InsuranceCatalogLiabilityDO initInsuranceCatalogLiabilityDO(InsuranceCatalogOrder order,
																		InsuranceCatalogLiabilityOrder liabilityOrder) {
		InsuranceCatalogLiabilityDO liabilityDO = new InsuranceCatalogLiabilityDO();
		BeanCopier.staticCopy(liabilityOrder, liabilityDO);
		liabilityDO.setType(InsuranceCatalogTypeEnum.kinds.getCode());
		liabilityDO.setOwnerId(order.getCatalogId());
		return liabilityDO;
	}
	
	private InsuranceConditionDO initInsuranceConditionDO(InsuranceCatalogOrder order,
															InsuranceConditionsOrder conditionsOrder) {
		InsuranceConditionDO conditionDO = new InsuranceConditionDO();
		BeanCopier.staticCopy(conditionsOrder, conditionDO);
		conditionDO.setOwnerId(order.getCatalogId());
		conditionDO.setType(InsuranceCatalogTypeEnum.kinds.getCode());
		return conditionDO;
	}
	
	private void saveCatalogRelation(InsuranceCatalogOrder order) {
		if (order.getType() == InsuranceCatalogTypeEnum.kinds) {
			if (StringUtil.equals(BooleanEnum.NO.getCode(), order.getIsMain())) {
				if (StringUtil.isEmpty(order.getParentNames())
					|| StringUtil.isEmpty(order.getParentIds())) {
					return;
				}
				
				String[] parentId = order.getParentIds().split(",");
				String[] parentName = order.getParentNames().split(",");
				if (parentId.length != parentName.length) {
					return;
				}
				InsuranceCatalogDO catalogDO = insuranceCatalogDAO.findById(NumberUtil
					.parseLong(parentId[0]));
				InsuranceCatalogDO child = insuranceCatalogDAO.findById(order.getCatalogId());
				if (catalogDO != null) {
					child.setIsLifeInsurance(catalogDO.getIsLifeInsurance());
					insuranceCatalogDAO.update(child);
				}
				insuranceCatalogRelationDAO.deleteByChildId(order.getCatalogId());
				for (int i = 0; i < parentId.length; i++) {
					InsuranceCatalogRelationDO relationDO = new InsuranceCatalogRelationDO();
					relationDO.setChildId(order.getCatalogId());
					relationDO.setChildName(order.getCatalogName());
					relationDO.setParentId(NumberUtil.parseLong(parentId[i]));
					relationDO.setParentName(parentName[i]);
					relationDO.setType("PARENT");
					insuranceCatalogRelationDAO.insert(relationDO);
				}
				
			} else if (StringUtil.equals(BooleanEnum.YES.getCode(), order.getIsMain())) {
				if (StringUtil.isEmpty(order.getChildrenNames())
					|| StringUtil.isEmpty(order.getChildrenIds())) {
					return;
				}
				
				String[] childrenId = order.getChildrenIds().split(",");
				String[] childrenName = order.getChildrenNames().split(",");
				if (childrenId.length != childrenName.length) {
					return;
				}
				insuranceCatalogRelationDAO.deleteByChildId(order.getCatalogId());
				for (int i = 0; i < childrenId.length; i++) {
					InsuranceCatalogDO catalogDO = insuranceCatalogDAO.findById(NumberUtil
						.parseLong(childrenId[0]));
					catalogDO.setIsLifeInsurance(order.getIsLifeInsurance());
					insuranceCatalogDAO.update(catalogDO);
					insuranceCatalogRelationDAO
						.deleteByChildId(NumberUtil.parseLong(childrenId[i]));
					InsuranceCatalogRelationDO relationDO = new InsuranceCatalogRelationDO();
					relationDO.setParentId(order.getCatalogId());
					relationDO.setParentName(order.getCatalogName());
					relationDO.setChildId(NumberUtil.parseLong(childrenId[i]));
					relationDO.setChildName(childrenName[i]);
					relationDO.setType("PARENT");
					insuranceCatalogRelationDAO.insert(relationDO);
				}
			}
		}
	}
	
	private void saveConditionsAndLiability(InsuranceCatalogOrder order) {
		if (order.getType() == InsuranceCatalogTypeEnum.kinds) {
			if (ListUtil.isNotEmpty(order.getConditionOrders())) {
				for (InsuranceConditionsOrder conditionsOrder : order.getConditionOrders()) {
					InsuranceConditionDO conditionDO = initInsuranceConditionDO(order,
						conditionsOrder);
					insuranceConditionDAO.insert(conditionDO);
				}
			}
			
			if (ListUtil.isNotEmpty(order.getLiabilityOrders())) {
				for (InsuranceCatalogLiabilityOrder liabilityOrder : order.getLiabilityOrders()) {
					InsuranceCatalogLiabilityDO liabilityDO = initInsuranceCatalogLiabilityDO(
						order, liabilityOrder);
					insuranceCatalogLiabilityDAO.insert(liabilityDO);
				}
			}
		}
	}
	
	@Override
	public InsuranceCatalogInfo findPriceTemplateById(long id) {
		InsuranceCatalogInfo info = findById(id);
		if (info == null) {
			return null;
		}
		if (info.getPriceTemplate() > 0) {
			return info;
		} else {
			return findPriceTemplateById(info.getParentCatalogId());
		}
	}
	
	@Override
	public InsuranceCatalogInfo findById(long id) {
		InsuranceCatalogDO insuranceCatalogDo = insuranceCatalogDAO.findById(id);
		if (insuranceCatalogDo != null) {
			InsuranceCatalogInfo insuranceCatalogInfo = new InsuranceCatalogInfo();
			BeanCopier.staticCopy(insuranceCatalogDo, insuranceCatalogInfo);
			insuranceCatalogInfo.setLastDepth(BooleanEnum.getByCode(insuranceCatalogDo
				.getLastDepth()));
			insuranceCatalogInfo.setType(InsuranceCatalogTypeEnum.getByCode(insuranceCatalogDo
				.getType()));
			return insuranceCatalogInfo;
		}
		return null;
	}
	
	@Override
	public InsuranceBaseResult deleteById(long id) {
		InsuranceBaseResult baseResult = new InsuranceBaseResult();
		try {
			InsuranceCatalogDO catalogDO = new InsuranceCatalogDO();
			catalogDO.setParentCatalogId(id);
			long count = insuranceCatalogDAO.findByConditionCount(catalogDO, null);
			if (count > 0) {
				baseResult.setMessage("已有下级分类，不允许删除");
				return baseResult;
			}
			insuranceCatalogDAO.deleteById(id);
			baseResult.setSuccess(true);
		} catch (Exception e) {
			baseResult.setSuccess(false);
			baseResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		return baseResult;
	}
	
	@Override
	public QueryBaseBatchResult<InsuranceCatalogInfo> queryList(InsuranceCatalogQueryOrder queryOrder) {
		QueryBaseBatchResult<InsuranceCatalogInfo> batchResult = new QueryBaseBatchResult<InsuranceCatalogInfo>();
		
		try {
			queryOrder.check();
			List<InsuranceCatalogInfo> pageList = new ArrayList<InsuranceCatalogInfo>(
				(int) queryOrder.getPageSize());
			
			InsuranceCatalogDO insuranceCatalogDO = new InsuranceCatalogDO();
			BeanCopier.staticCopy(queryOrder, insuranceCatalogDO);
			long totalCount = insuranceCatalogDAO.findByConditionCount(insuranceCatalogDO,
				queryOrder.getCertainIds());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<InsuranceCatalogDO> recordList = insuranceCatalogDAO.findByCondition(
				insuranceCatalogDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
				component.getFirstRecord(), component.getPageSize(), queryOrder.getCertainIds());
			for (InsuranceCatalogDO item : recordList) {
				InsuranceCatalogInfo info = new InsuranceCatalogInfo();
				BeanCopier.staticCopy(item, info);
				info.setLastDepth(BooleanEnum.getByCode(item.getLastDepth()));
				info.setHaschildren(info.getLastDepth() == BooleanEnum.NO ? "YES" : "NO");
				info.setType(InsuranceCatalogTypeEnum.getByCode(item.getType()));
				setRelation(info);
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
	
	private void setRelation(InsuranceCatalogInfo info) {
		if (StringUtil.equals(info.getIsMain(), BooleanEnum.NO.getCode())) {
			InsuranceCatalogRelationDO queryRelationDo = new InsuranceCatalogRelationDO();
			queryRelationDo.setChildId(info.getCatalogId());
			List<InsuranceCatalogRelationDO> relationDOs = insuranceCatalogRelationDAO
				.findByCondition(queryRelationDo, null, null, 0, 999);
			
			if (ListUtil.isNotEmpty(relationDOs)) {
				StringBuffer parentIds = new StringBuffer();
				StringBuffer parentNames = new StringBuffer();
				boolean isFirst = true;
				for (InsuranceCatalogRelationDO relationDO : relationDOs) {
					if (isFirst) {
						parentIds.append(relationDO.getParentId());
						parentNames.append(relationDO.getParentName());
					} else {
						parentIds.append(",").append(relationDO.getParentId());
						parentNames.append(",").append(relationDO.getParentName());
					}
					isFirst = false;
				}
				info.setParentIds(parentIds.toString());
				info.setParentNames(parentNames.toString());
			}
			
		} else if (StringUtil.equals(info.getIsMain(), BooleanEnum.YES.getCode())) {
			InsuranceCatalogRelationDO queryRelationDo = new InsuranceCatalogRelationDO();
			queryRelationDo.setParentId(info.getCatalogId());
			List<InsuranceCatalogRelationDO> relationDOs = insuranceCatalogRelationDAO
				.findByCondition(queryRelationDo, null, null, 0, 999);
			
			if (ListUtil.isNotEmpty(relationDOs)) {
				StringBuffer childrenIds = new StringBuffer();
				StringBuffer childrenNames = new StringBuffer();
				boolean isFirst = true;
				for (InsuranceCatalogRelationDO relationDO : relationDOs) {
					if (isFirst) {
						childrenIds.append(relationDO.getChildId());
						childrenNames.append(relationDO.getChildName());
					} else {
						childrenIds.append(",").append(relationDO.getChildId());
						childrenNames.append(",").append(relationDO.getChildName());
					}
					isFirst = false;
				}
				info.setChildrenIds(childrenIds.toString());
				info.setChildrenNames(childrenNames.toString());
			}
		}
	}
	
	@Override
	public List<InsuranceCatalogLiabilityInfo> findInsuranceCatalogLiabilitys(long catalogId) {
		List<InsuranceCatalogLiabilityInfo> list = new ArrayList<InsuranceCatalogLiabilityInfo>();
		List<InsuranceCatalogLiabilityDO> queryList = insuranceCatalogLiabilityDAO
			.queryList(catalogId);
		for (InsuranceCatalogLiabilityDO insuranceCatalogLiabilityDO : queryList) {
			InsuranceCatalogLiabilityInfo info = new InsuranceCatalogLiabilityInfo();
			BeanCopier.staticCopy(insuranceCatalogLiabilityDO, info);
			list.add(info);
		}
		return list;
	}
}