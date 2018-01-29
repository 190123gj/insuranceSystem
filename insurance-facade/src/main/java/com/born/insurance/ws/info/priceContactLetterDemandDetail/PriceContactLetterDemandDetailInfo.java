package com.born.insurance.ws.info.priceContactLetterDemandDetail;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;

public class PriceContactLetterDemandDetailInfo extends BaseToStringInfo {

	private long id;

	private long letterDemandId;

	private String productName;

	private String model;

	private long salesVolume;

	private String salesArea;

	private String businessObject;

	private long businessIncome;

	private String vehicleUse;

	private long num;

	private long seatsNum;

	private String travelArea;

	private long shippingType;

	private String goodsName;

	private String packingCondition;

	private String expectedValue;

	private String transportRoute;

	private String frequency;

	private String oneLimit;

	private String vehicleType;

	private String tonnage;

	private String vehicleAge;

	private String transportationArea;

	private String goodsTypes;

	private String remark;

	private String dangerousGoods;

	private String mainBusiness;

	private String asetAddress;

	private String purpose;

	private String buildingStructure;

	private long version;

	private String ext;

	public String getAsetAddress() {
		return asetAddress;
	}

	public void setAsetAddress(String asetAddress) {
		this.asetAddress = asetAddress;
	}

	public String getBuildingStructure() {
		return buildingStructure;
	}

	public void setBuildingStructure(String buildingStructure) {
		this.buildingStructure = buildingStructure;
	}

	public long getBusinessIncome() {
		return businessIncome;
	}

	public void setBusinessIncome(long businessIncome) {
		this.businessIncome = businessIncome;
	}

	public String getBusinessObject() {
		return businessObject;
	}

	public void setBusinessObject(String businessObject) {
		this.businessObject = businessObject;
	}

	public String getDangerousGoods() {
		return dangerousGoods;
	}

	public void setDangerousGoods(String dangerousGoods) {
		this.dangerousGoods = dangerousGoods;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsTypes() {
		return goodsTypes;
	}

	public void setGoodsTypes(String goodsTypes) {
		this.goodsTypes = goodsTypes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLetterDemandId() {
		return letterDemandId;
	}

	public void setLetterDemandId(long letterDemandId) {
		this.letterDemandId = letterDemandId;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public String getOneLimit() {
		return oneLimit;
	}

	public void setOneLimit(String oneLimit) {
		this.oneLimit = oneLimit;
	}

	public String getPackingCondition() {
		return packingCondition;
	}

	public void setPackingCondition(String packingCondition) {
		this.packingCondition = packingCondition;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public long getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(long salesVolume) {
		this.salesVolume = salesVolume;
	}

	public long getSeatsNum() {
		return seatsNum;
	}

	public void setSeatsNum(long seatsNum) {
		this.seatsNum = seatsNum;
	}

	public long getShippingType() {
		return shippingType;
	}

	public void setShippingType(long shippingType) {
		this.shippingType = shippingType;
	}

	public String getTonnage() {
		return tonnage;
	}

	public void setTonnage(String tonnage) {
		this.tonnage = tonnage;
	}

	public String getTransportationArea() {
		return transportationArea;
	}

	public void setTransportationArea(String transportationArea) {
		this.transportationArea = transportationArea;
	}

	public String getTransportRoute() {
		return transportRoute;
	}

	public void setTransportRoute(String transportRoute) {
		this.transportRoute = transportRoute;
	}

	public String getTravelArea() {
		return travelArea;
	}

	public void setTravelArea(String travelArea) {
		this.travelArea = travelArea;
	}

	public String getVehicleAge() {
		return vehicleAge;
	}

	public void setVehicleAge(String vehicleAge) {
		this.vehicleAge = vehicleAge;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleUse() {
		return vehicleUse;
	}

	public void setVehicleUse(String vehicleUse) {
		this.vehicleUse = vehicleUse;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
}	