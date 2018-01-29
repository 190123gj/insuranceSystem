package com.born.insurance.biz.service.common;

import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.info.common.IndustryInfo;
import com.born.insurance.ws.info.common.ProvinceInfo;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.order.common.IndustryQueryOrder;
import com.born.insurance.ws.order.common.RegionQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import com.born.insurance.ws.service.common.BasicDataService;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("basicDataCacheService")
public class BasicDataCacheServiceImpl implements BasicDataCacheService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BasicDataService basicDataService;
	
	//根节点
	private final static String ROOT = "root";
	
	//行政区域缓存
	private static Map<String, List<RegionInfo>> regionMap = Maps.newHashMap();
	
	private static Map<String, RegionInfo> regions = Maps.newHashMap();

	//行业信息缓存
	private static Map<String, List<IndustryInfo>> intustryMap = Maps.newHashMap();
	
	@Override
	public void clearCache() {
		if (intustryMap != null) {
			intustryMap.clear();
		}

		if (regionMap != null) {
			regionMap.clear();
		}
		
		if (regions != null) {
			regions.clear();
		}
	}


	@Override
	public List<IndustryInfo> queryIndustry(String parentCode) {

		if (StringUtil.isEmpty(parentCode)) {
			parentCode = ROOT;
		}

		List<IndustryInfo> data = intustryMap.get(parentCode);
		if (data != null) {
			logger.info("从缓存中获取行业信息...");
			return data;
		} else {
			logger.info("从接口获取行业信息...");
			IndustryQueryOrder order = new IndustryQueryOrder();
			order.setParentCode(parentCode);
			order.setPageSize(999);
			QueryBaseBatchResult<IndustryInfo> result = basicDataService.queryIndustry(order);
			data = result.getPageList();
			intustryMap.put(parentCode, data);
			return data;
		}

	}

	
	@Override
	public List<ProvinceInfo> queryProvinceCity() {
		List<RegionInfo> regionInfos = queryRegion("China");
		if (ListUtil.isNotEmpty(regionInfos)) {
			List<ProvinceInfo> provinceInfos = new ArrayList<ProvinceInfo>();
			for (RegionInfo regionInfo : regionInfos) {
				ProvinceInfo provinceInfo = new ProvinceInfo(regionInfo.getCode(),
					regionInfo.getName());
				provinceInfos.add(provinceInfo);
				if (StringUtil.notEquals(provinceInfo.getCode(), "500000")
					&& StringUtil.notEquals(provinceInfo.getCode(), "310000")
					&& StringUtil.notEquals(provinceInfo.getCode(), "120000")
					&& StringUtil.notEquals(provinceInfo.getCode(), "110000")) {
					List<RegionInfo> cityInfos = queryRegion(regionInfo.getCode());
					if (ListUtil.isNotEmpty(cityInfos)) {
						StringBuffer sb = new StringBuffer();
						boolean isFirst = true;
						for (RegionInfo city : cityInfos) {
							if (!isFirst) {
								sb.append(";");
							}
							sb.append(city.getName()).append(",").append(city.getCode());
							if (isFirst) {
								isFirst = false;
							}
						}
						provinceInfo.setChildren(sb.toString());
					}
					
				}
			}
			return provinceInfos;
		}
		return null;
	}
	
	@Override
	public List<RegionInfo> queryRegion(String parentCode) {
		if (StringUtil.isEmpty(parentCode)) {
			parentCode = ROOT;
		}
		List<RegionInfo> data = regionMap.get(parentCode);
		if (data != null) {
			logger.info("从缓存中获取区域信息...");
			return data;
		} else {
			logger.info("从接口中获取区域信息...");
			RegionQueryOrder order = new RegionQueryOrder();
			order.setParentCode(parentCode);
			order.setPageSize(999);
			QueryBaseBatchResult<RegionInfo> result = basicDataService.queryRegion(order);
			data = result.getPageList();
			regionMap.put(parentCode, data);
			return data;
		}
	}
	
	@Override
	public RegionInfo queryRegionByCode(String code) {
		if (regions.get(code) != null) {
			return regions.get(code);
		} else {
			RegionQueryOrder order = new RegionQueryOrder();
			order.setCode(code);
			order.setPageSize(1);
			RegionInfo regionInfo = basicDataService.queryRegion(order).getPageList().get(0);
			regions.put(regionInfo.getCode(), regionInfo);
			return regionInfo;
		}
		
	}
}
