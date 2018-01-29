package com.born.insurance.ws.order.priceContactLetterReportPrice;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PriceContactLetterReportPriceDetailQueryOrder extends QueryPermissionPageBase {

 	/**
	* 标id
	*/
	private long letterDemandId;



 	/**
	* id
	*/
	private long id;
 	/**
	* 经纪费
	*/
	private long brokerAmount;
 	/**
	* 保险费
	*/
	private long premiumAmount;
 	/**
	* 询价期限
	*/
	private Date askDate;
 	/**
	* 报价时间
	*/
	private String createDate;
 	/**
	* 特别约定
	*/
	private String specialAgreement;
 	/**
	* 前提条件
	*/
	private String quotationPrerequisite;
 	/**
	* 类型
	*/
	private String type;
 	/**
	* 差异内容
	*/
	private String differenceContent;
 	/**
	* 扩展字段
	*/
	private String ext;
 	/**
	* 版本
	*/
	private long version;


	private long reportPriceId;

  	public long getLetterDemandId() {
        return letterDemandId;
	}

	public void setLetterDemandId(long letterDemandId) {
        this.letterDemandId = letterDemandId;
	}
	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public long getBrokerAmount() {
        return brokerAmount;
	}

	public void setBrokerAmount(long brokerAmount) {
        this.brokerAmount = brokerAmount;
	}
	public long getPremiumAmount() {
        return premiumAmount;
	}

	public void setPremiumAmount(long premiumAmount) {
        this.premiumAmount = premiumAmount;
	}
	public Date getAskDate() {
        return askDate;
	}

	public void setAskDate(Date askDate) {
        this.askDate = askDate;
	}
	public String getCreateDate() {
        return createDate;
	}

	public void setCreateDate(String createDate) {
        this.createDate = createDate;
	}
	public String getSpecialAgreement() {
        return specialAgreement;
	}

	public void setSpecialAgreement(String specialAgreement) {
        this.specialAgreement = specialAgreement;
	}
	public String getQuotationPrerequisite() {
        return quotationPrerequisite;
	}

	public void setQuotationPrerequisite(String quotationPrerequisite) {
        this.quotationPrerequisite = quotationPrerequisite;
	}
	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
	}
	public String getDifferenceContent() {
        return differenceContent;
	}

	public void setDifferenceContent(String differenceContent) {
        this.differenceContent = differenceContent;
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


	public long getReportPriceId() {
		return reportPriceId;
	}

	public void setReportPriceId(long reportPriceId) {
		this.reportPriceId = reportPriceId;
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