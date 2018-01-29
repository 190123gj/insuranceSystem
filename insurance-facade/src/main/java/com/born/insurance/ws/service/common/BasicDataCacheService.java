package com.born.insurance.ws.service.common;

import com.born.insurance.biz.service.common.SysClearCacheService;
import com.born.insurance.ws.info.common.IndustryInfo;
import com.born.insurance.ws.info.common.ProvinceInfo;
import com.born.insurance.ws.info.common.RegionInfo;

import java.util.List;



public interface BasicDataCacheService extends SysClearCacheService {


	/**
	 * 根据父节点查询行业
	 * @param parentCode
	 * @return
	 */
	List<IndustryInfo> queryIndustry(String parentCode);


	/**
	 * 根据父节点查询地区
	 * @param parentCode
	 * @return
	 */
	List<RegionInfo> queryRegion(String parentCode);


	List<ProvinceInfo> queryProvinceCity();

	RegionInfo queryRegionByCode(String code);
	

}
