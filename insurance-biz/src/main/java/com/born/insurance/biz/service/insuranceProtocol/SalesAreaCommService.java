package com.born.insurance.biz.service.insuranceProtocol;

import com.born.insurance.ws.info.common.CityInfo;
import com.born.insurance.ws.info.common.ProvinceInfo;
import com.born.insurance.ws.info.insuranceProtocol.SalesAreaCommInfo;
import com.yjf.common.lang.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.daointerface.SalesAreaDAO;
import com.born.insurance.dal.dataobject.SalesAreaDO;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.SalesAreaTypeEnum;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import com.yjf.common.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-1-22.
 */
public class SalesAreaCommService extends BaseAutowiredDomainService {
	@Autowired
	protected SalesAreaDAO salesAreaDAO;
	
	@Autowired
	protected BasicDataCacheService basicDataCacheService;


    protected void querySalesAreas(SalesAreaCommInfo protocolInfo,long ownId,SalesAreaTypeEnum salesAreaTypeEnum) {
        SalesAreaDO queryDo = new SalesAreaDO();
        queryDo.setProductId(ownId);
        queryDo.setType(salesAreaTypeEnum.getCode());
        queryDo.setCityCode("-");
        List<SalesAreaDO> salesAreaDOs = salesAreaDAO.findByCondition(queryDo, null, null, 0, 32);
        if (ListUtil.isNotEmpty(salesAreaDOs)) {
            JSONArray jsonArray = new JSONArray();
            for (SalesAreaDO areaDO : salesAreaDOs) {
                ProvinceInfo provinceInfo = new ProvinceInfo();
                provinceInfo.setCode(areaDO.getProvinceCode());
                provinceInfo.setName(areaDO.getProvinceName());
                if (StringUtil.equals(areaDO.getIsAllProvince(), BooleanEnum.YES.getCode())) {
                    protocolInfo.getOneCityList().add(provinceInfo);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put(provinceInfo.getCode(),new JSONArray());
					jsonArray.add(jsonObject);
                } else {
                    protocolInfo.getTwoCityList().add(provinceInfo);
                    JSONObject jsonObject = new JSONObject();
                    queryDo = new SalesAreaDO();
                    queryDo.setProductId(ownId);
                    queryDo.setType(salesAreaTypeEnum.getCode());
                    queryDo.setProvinceCode(areaDO.getProvinceCode());
                    List<SalesAreaDO> cityAreaList = salesAreaDAO.findByCondition(queryDo, null,
                            null, 0, 32);
                    if (ListUtil.isNotEmpty(cityAreaList)) {
                        List<CityInfo> cityInfoList = new ArrayList<CityInfo>();
                        provinceInfo.setCityInfoList(cityInfoList);
                        JSONArray cityJSONArray = new JSONArray();
                        for (SalesAreaDO c : cityAreaList) {
                            if (StringUtil.notEquals(c.getCityCode(), "-")) {
                                cityJSONArray.add(c.getCityCode());
                                CityInfo cityInfo = new CityInfo();
                                cityInfo.setCode(c.getCityCode());
                                cityInfo.setName(c.getCityName());
                                cityInfoList.add(cityInfo);
                            }
                        }
                        jsonObject.put(areaDO.getProvinceCode(), cityJSONArray);
                    }
                    jsonArray.add(jsonObject);
                }
            }
            protocolInfo.setSalesAreasCollect(jsonArray.toJSONString());
        }
    }
	
	protected void saveSalesAreas(long ownId, String salesAreas, SalesAreaTypeEnum salesAreaTypeEnum) {
		salesAreaDAO
			.deleteByProductIdAndType(ownId, salesAreaTypeEnum.getCode());
		if (StringUtil.isEmpty(salesAreas)) {
			return;
		}
		
		JSONArray provinceCityJsonArray = JSON.parseArray(salesAreas);
		for (int i = 0; i < provinceCityJsonArray.size(); i++) {
			JSONObject provinceCityJson = provinceCityJsonArray.getJSONObject(i);
			for (String province : provinceCityJson.keySet()) {
				RegionInfo provinceRegionInfo = basicDataCacheService.queryRegionByCode(province);
				JSONArray cityJsonArray = provinceCityJson.getJSONArray(province);
				String isAllProvince = BooleanEnum.NO.getCode();
				if (cityJsonArray != null && cityJsonArray.size() > 0) {
					for (int j = 0; j < cityJsonArray.size(); j++) {
						String city = cityJsonArray.getString(j);
						RegionInfo cityRegionInfo = basicDataCacheService.queryRegionByCode(city);
						SalesAreaDO citySales = new SalesAreaDO();
						citySales.setProductId(ownId);
						citySales.setProvinceCode(province);
						citySales.setProvinceName(provinceRegionInfo.getName());
						citySales.setCityCode(cityRegionInfo.getCode());
						citySales.setCityName(cityRegionInfo.getName());
						citySales.setType(salesAreaTypeEnum.getCode());
						salesAreaDAO.insert(citySales);
					}
				} else {
					isAllProvince = BooleanEnum.YES.getCode();
				}
				
				SalesAreaDO salesAreaDO = new SalesAreaDO();
				salesAreaDO.setProductId(ownId);
				salesAreaDO.setProvinceCode(province);
				salesAreaDO.setProvinceName(provinceRegionInfo.getName());
				salesAreaDO.setCityCode("-");
				salesAreaDO.setCityName("-");
				salesAreaDO.setIsAllProvince(isAllProvince);
				salesAreaDO.setType(salesAreaTypeEnum.getCode());
				salesAreaDAO.insert(salesAreaDO);
			}
		}
	}
}
