/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.service.common;

import java.util.List;

import com.born.insurance.dal.daointerface.IndustryDAO;
import com.born.insurance.dal.dataobject.IndustryDO;
import com.born.insurance.ws.info.common.IndustryInfo;
import com.born.insurance.ws.order.common.IndustryQueryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.RegionDAO;
import com.born.insurance.dal.dataobject.RegionDO;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.order.common.RegionOrder;
import com.born.insurance.ws.order.common.RegionQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.BasicDataService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("basicDataService")
public class BasicDataServiceImpl extends BaseAutowiredDomainService implements BasicDataService {
	@Autowired
	protected RegionDAO regionDAO;

	@Autowired
	private IndustryDAO industryDAO;



	@Override
	public QueryBaseBatchResult<IndustryInfo> queryIndustry(IndustryQueryOrder order) {

		QueryBaseBatchResult<IndustryInfo> baseBatchResult = new QueryBaseBatchResult<IndustryInfo>();

		IndustryDO queryCondition = new IndustryDO();

		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);

		long totalSize = industryDAO.findByConditionCount(queryCondition);

		PageComponent component = new PageComponent(order, totalSize);

		List<IndustryDO> pageList = industryDAO.findByCondition(queryCondition,
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());

		List<IndustryInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (IndustryDO industry : pageList) {
				IndustryInfo info = new IndustryInfo();
				BeanCopier.staticCopy(industry, info);
				list.add(info);
			}
		}

		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<RegionInfo> queryRegion(RegionQueryOrder order) {
		
		QueryBaseBatchResult<RegionInfo> baseBatchResult = new QueryBaseBatchResult<RegionInfo>();
		
		RegionDO queryCondition = new RegionDO();
		
		if (order != null)
			BeanCopier.staticCopy(order, queryCondition);
		
		if (StringUtil.isBlank(order.getSortCol())) {
			order.setSortCol("sort_order");
			order.setSortOrder("asc");
		}
		
		long totalSize = regionDAO.findByConditionCount(queryCondition);
		
		PageComponent component = new PageComponent(order, totalSize);
		
		List<RegionDO> pageList = regionDAO.findByCondition(queryCondition,
			component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
			order.getSortOrder());
		
		List<RegionInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (RegionDO region : pageList) {
				RegionInfo info = new RegionInfo();
				BeanCopier.staticCopy(region, info);
				info.setHasChildren(BooleanEnum.getByCode(region.getHasChildren()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public InsuranceBaseResult saveRegion(final RegionOrder order) {
		return commonProcess(order, "保存行政区域", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				RegionDO region = null;
				if (order.getId() > 0) {
					region = regionDAO.findById(order.getId());
					if (region == null) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"未找到行政区域");
					}
					//修改
					if (!StringUtil.equals(region.getCode(), order.getCode())) {
						RegionDO exist = regionDAO.findByCode(order.getCode());
						if (exist != null) {
							throw ExceptionFactory.newFcsException(
								InsuranceResultEnum.DO_ACTION_STATUS_ERROR, "编码已存在");
						}
					}
				} else {
					RegionDO exist = regionDAO.findByCode(order.getCode());
					if (exist != null) {
						throw ExceptionFactory.newFcsException(
							InsuranceResultEnum.DO_ACTION_STATUS_ERROR, "编码已存在");
					}
					//新增
					region = new RegionDO();
				}
				
				BeanCopier.staticCopy(order, region);
				//设置level等属性
				if ("root".equals(order.getParentCode())) {
					region.setLevel(1);
				} else {
					RegionDO parent = regionDAO.findByCode(order.getParentCode());
					if (parent == null) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"上级未找到");
					}
					region.setLevel(parent.getLevel() + 1);
				}
				if (region.getId() > 0) {
					regionDAO.update(region);
				} else {
					regionDAO.insert(region);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceBaseResult delRegion(int id) {
		InsuranceBaseResult result = createResult();
		try {
			logger.info("删除行政区域：id {}", id);
			regionDAO.deleteById(id);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			logger.error("删除行政区域出错");
		}
		
		return result;
	}
	
}
