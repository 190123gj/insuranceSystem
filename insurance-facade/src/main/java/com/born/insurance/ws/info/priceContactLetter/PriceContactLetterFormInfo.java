package com.born.insurance.ws.info.priceContactLetter;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.info.common.FormVOInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.priceContactLetterDemand.PriceContactLetterDemandInfo;

public class PriceContactLetterFormInfo extends FormVOInfo {

 	/**
	* id
	*/
	private long id;
 	/**
	*
	*/
	private Date rawAddTime;
 	/**
	* 客户
	*/
	private long customerUserId;
 	/**
	* 客户
	*/
	private String customerUserName;
 	/**
	* 询价方案名称
	*/
	private String name;
 	/**
	* 询价方案编码
	*/
	private String billNo;
 	/**
	* 扩展字段
	*/
	private String ext;
 	/**
	*
	*/
	private Date rawUpdateTime;
 	/**
	* 版本
	*/
	private long version;

	private long creatorId;

	private String creatorName;

	private String status;
	private String specialReq;

	private String askDate;



	private List<PriceContactLetterDemandInfo> demandInfos;

  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getCustomerUserId() {
        return customerUserId;
	}

	public void setCustomerUserId(long customerUserId) {
        this.customerUserId = customerUserId;
	}
	public String getCustomerUserName() {
        return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
	}
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public String getBillNo() {
        return billNo;
	}

	public void setBillNo(String billNo) {
        this.billNo = billNo;
	}
	public String getExt() {
        return ext;
	}

	public void setExt(String ext) {
        this.ext = ext;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getVersion() {
        return version;
	}

	public void setVersion(long version) {
        this.version = version;
	}


	public List<PriceContactLetterDemandInfo> getDemandInfos() {
		return demandInfos;
	}

	public void setDemandInfos(List<PriceContactLetterDemandInfo> demandInfos) {
		this.demandInfos = demandInfos;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAskDate() {
		return askDate;
	}

	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}

	public String getSpecialReq() {
		return specialReq;
	}

	public void setSpecialReq(String specialReq) {
		this.specialReq = specialReq;
	}

	/**
     * @return
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
}	