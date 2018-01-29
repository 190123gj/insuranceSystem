package com.born.insurance.ws.info.insuranceProtocol;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.common.ProvinceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-1-22.
 */
public class SalesAreaCommInfo extends BaseToStringInfo {
    private String salesAreasCollect;

    private List<ProvinceInfo> oneCityList = new ArrayList<ProvinceInfo>();


    private  List<ProvinceInfo> twoCityList = new ArrayList<ProvinceInfo>();

    public List<ProvinceInfo> getOneCityList() {
        return oneCityList;
    }

    public void setOneCityList(List<ProvinceInfo> oneCityList) {
        this.oneCityList = oneCityList;
    }

    public String getSalesAreasCollect() {
        return salesAreasCollect;
    }

    public void setSalesAreasCollect(String salesAreasCollect) {
        this.salesAreasCollect = salesAreasCollect;
    }

    public List<ProvinceInfo> getTwoCityList() {
        return twoCityList;
    }

    public void setTwoCityList(List<ProvinceInfo> twoCityList) {
        this.twoCityList = twoCityList;
    }
}
