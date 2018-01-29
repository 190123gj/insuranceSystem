package com.born.insurance.ws.order.priceContactLetterDemandDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterDemandDetailQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 标id
	*/	
	private long letterDemandId;
 	/**
	* 运输区域
	*/	
	private String transportationArea;
 	/**
	* 型号
	*/	
	private String model;
 	/**
	* 销售额
	*/	
	private long salesVolume;
 	/**
	* 包装条件
	*/	
	private String packingCondition;
 	/**
	* 业务对象
	*/	
	private String businessObject;
 	/**
	* 车辆类型
	*/	
	private String vehicleType;
 	/**
	* 行驶区域
	*/	
	private String travelArea;
 	/**
	* 运输方式\船舶种类
	*/	
	private long shippingType;
 	/**
	* 产品名称
	*/	
	private String productName;
 	/**
	* 单次限额
	*/	
	private String oneLimit;
 	/**
	* 货物名称
	*/	
	private String goodsName;
 	/**
	* 年预计货值
	*/	
	private String expectedValue;
 	/**
	* 车辆用途
	*/	
	private String vehicleUse;
 	/**
	* 频次
	*/	
	private String frequency;
 	/**
	* 业务收入
	*/	
	private long businessIncome;
 	/**
	* 运输路线
	*/	
	private String transportRoute;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 版本
	*/	
	private long version;
 	/**
	* 货物种类
	*/	
	private String goodsTypes;
 	/**
	* id
	*/	
	private long id;
 	/**
	* 数量
	*/	
	private long num;
 	/**
	* 销售区域
	*/	
	private String salesArea;
 	/**
	* 座位数
	*/	
	private long seatsNum;
 	/**
	* 平均总吨/车位/载客/吨位
	*/	
	private String tonnage;
 
  	public long getLetterDemandId() {
        return letterDemandId;
	}

	public void setLetterDemandId(long letterDemandId) {
        this.letterDemandId = letterDemandId;
	}
	public String getTransportationArea() {
        return transportationArea;
	}

	public void setTransportationArea(String transportationArea) {
        this.transportationArea = transportationArea;
	}
	public String getModel() {
        return model;
	}

	public void setModel(String model) {
        this.model = model;
	}
	public long getSalesVolume() {
        return salesVolume;
	}

	public void setSalesVolume(long salesVolume) {
        this.salesVolume = salesVolume;
	}
	public String getPackingCondition() {
        return packingCondition;
	}

	public void setPackingCondition(String packingCondition) {
        this.packingCondition = packingCondition;
	}
	public String getBusinessObject() {
        return businessObject;
	}

	public void setBusinessObject(String businessObject) {
        this.businessObject = businessObject;
	}
	public String getVehicleType() {
        return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
	}
	public String getTravelArea() {
        return travelArea;
	}

	public void setTravelArea(String travelArea) {
        this.travelArea = travelArea;
	}
	public long getShippingType() {
        return shippingType;
	}

	public void setShippingType(long shippingType) {
        this.shippingType = shippingType;
	}
	public String getProductName() {
        return productName;
	}

	public void setProductName(String productName) {
        this.productName = productName;
	}
	public String getOneLimit() {
        return oneLimit;
	}

	public void setOneLimit(String oneLimit) {
        this.oneLimit = oneLimit;
	}
	public String getGoodsName() {
        return goodsName;
	}

	public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
	}
	public String getExpectedValue() {
        return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
	}
	public String getVehicleUse() {
        return vehicleUse;
	}

	public void setVehicleUse(String vehicleUse) {
        this.vehicleUse = vehicleUse;
	}
	public String getFrequency() {
        return frequency;
	}

	public void setFrequency(String frequency) {
        this.frequency = frequency;
	}
	public long getBusinessIncome() {
        return businessIncome;
	}

	public void setBusinessIncome(long businessIncome) {
        this.businessIncome = businessIncome;
	}
	public String getTransportRoute() {
        return transportRoute;
	}

	public void setTransportRoute(String transportRoute) {
        this.transportRoute = transportRoute;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
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
	public long getNum() {
        return num;
	}

	public void setNum(long num) {
        this.num = num;
	}
	public String getSalesArea() {
        return salesArea;
	}

	public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
	}
	public long getSeatsNum() {
        return seatsNum;
	}

	public void setSeatsNum(long seatsNum) {
        this.seatsNum = seatsNum;
	}
	public String getTonnage() {
        return tonnage;
	}

	public void setTonnage(String tonnage) {
        this.tonnage = tonnage;
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