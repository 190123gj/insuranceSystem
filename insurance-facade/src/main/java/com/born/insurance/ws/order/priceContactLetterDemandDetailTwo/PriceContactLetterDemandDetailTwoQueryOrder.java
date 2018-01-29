package com.born.insurance.ws.order.priceContactLetterDemandDetailTwo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterDemandDetailTwoQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 建造日期
	*/	
	private Date constructionDate;
 	/**
	* 标id
	*/	
	private long letterDemandId;
 	/**
	* 车位/载客
	*/	
	private String parking;
 	/**
	* 车牌号
	*/	
	private String licensePlateNumber;
 	/**
	* 行驶证车主
	*/	
	private String drivingLicenseOwner;
 	/**
	* 初次登记日期
	*/	
	private Date initialRegistrationDate;
 	/**
	* 船舶类型
	*/	
	private String shipType;
 	/**
	* 船名
	*/	
	private String shipName;
 	/**
	* 车架号
	*/	
	private String frameNumber;
 	/**
	* 危险货物名称
	*/	
	private String dangerousGoodsName;
 	/**
	* 扩展字段
	*/	
	private String ext;
 	/**
	* 单次航程
	*/	
	private String singleVoyage;
 	/**
	* 版本
	*/	
	private long version;
 	/**
	* id
	*/	
	private long id;
 	/**
	* 厂牌型号
	*/	
	private String brandModel;
 	/**
	* 发动机号
	*/	
	private String engineNumber;
 	/**
	* 航行区域
	*/	
	private String navigationArea;
 	/**
	* 总吨\吨位
	*/	
	private String grossTonnage;
 	/**
	* 改建日期
	*/	
	private Date alterationDate;
 
  	public Date getConstructionDate() {
        return constructionDate;
	}

	public void setConstructionDate(Date constructionDate) {
        this.constructionDate = constructionDate;
	}
	public long getLetterDemandId() {
        return letterDemandId;
	}

	public void setLetterDemandId(long letterDemandId) {
        this.letterDemandId = letterDemandId;
	}
	public String getParking() {
        return parking;
	}

	public void setParking(String parking) {
        this.parking = parking;
	}
	public String getLicensePlateNumber() {
        return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
	}
	public String getDrivingLicenseOwner() {
        return drivingLicenseOwner;
	}

	public void setDrivingLicenseOwner(String drivingLicenseOwner) {
        this.drivingLicenseOwner = drivingLicenseOwner;
	}
	public Date getInitialRegistrationDate() {
        return initialRegistrationDate;
	}

	public void setInitialRegistrationDate(Date initialRegistrationDate) {
        this.initialRegistrationDate = initialRegistrationDate;
	}
	public String getShipType() {
        return shipType;
	}

	public void setShipType(String shipType) {
        this.shipType = shipType;
	}
	public String getShipName() {
        return shipName;
	}

	public void setShipName(String shipName) {
        this.shipName = shipName;
	}
	public String getFrameNumber() {
        return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
	}
	public String getDangerousGoodsName() {
        return dangerousGoodsName;
	}

	public void setDangerousGoodsName(String dangerousGoodsName) {
        this.dangerousGoodsName = dangerousGoodsName;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public String getSingleVoyage() {
        return singleVoyage;
	}

	public void setSingleVoyage(String singleVoyage) {
        this.singleVoyage = singleVoyage;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
	}
	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public String getBrandModel() {
        return brandModel;
	}

	public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
	}
	public String getEngineNumber() {
        return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
	}
	public String getNavigationArea() {
        return navigationArea;
	}

	public void setNavigationArea(String navigationArea) {
        this.navigationArea = navigationArea;
	}
	public String getGrossTonnage() {
        return grossTonnage;
	}

	public void setGrossTonnage(String grossTonnage) {
        this.grossTonnage = grossTonnage;
	}
	public Date getAlterationDate() {
        return alterationDate;
	}

	public void setAlterationDate(Date alterationDate) {
        this.alterationDate = alterationDate;
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