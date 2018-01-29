package com.born.insurance.ws.info.priceContactLetterAskCompany;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Administrator on 2017-2-22.
 */
public class PriceContactLetterAskCompanyInfo extends BaseToStringInfo {
    private long id;

    private long letterDemandId;

    private long companyUserId;

    private String companyUserName;

    private long catalogId;

    private String catalogIds;

    private String catalogName;

    private String catalogNames;

    private double intentionFee;

    private double protocolFee;

    private String intentionFees;

    private String protocolFees;

    private long contactUserId;

    private String contactUserName;

    private String contactUserMobile;

    private String contactUserEmail;

    private long version;

    private String ext;

    private List<PriceContactLetterAskCompanyChargeInfo> chargeInfos;

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public long getLetterDemandId() {
        return letterDemandId;
    }

    public void setLetterDemandId(long letterDemandId) {
        this.letterDemandId = letterDemandId;
    }

    public String getContactUserEmail() {
        return contactUserEmail;
    }

    public void setContactUserEmail(String contactUserEmail) {
        this.contactUserEmail = contactUserEmail;
    }

    public long getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(long contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getContactUserMobile() {
        return contactUserMobile;
    }

    public void setContactUserMobile(String contactUserMobile) {
        this.contactUserMobile = contactUserMobile;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getIntentionFee() {
        return intentionFee;
    }

    public void setIntentionFee(double intentionFee) {
        this.intentionFee = intentionFee;
    }

    public double getProtocolFee() {
        return protocolFee;
    }

    public void setProtocolFee(double protocolFee) {
        this.protocolFee = protocolFee;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getCatalogIds() {
        return catalogIds;
    }

    public void setCatalogIds(String catalogIds) {
        this.catalogIds = catalogIds;
    }

    public String getIntentionFees() {
        return intentionFees;
    }

    public void setIntentionFees(String intentionFees) {
        this.intentionFees = intentionFees;
    }

    public String getProtocolFees() {
        return protocolFees;
    }

    public void setProtocolFees(String protocolFees) {
        this.protocolFees = protocolFees;
    }

    public String getCatalogNames() {
        return catalogNames;
    }

    public void setCatalogNames(String catalogNames) {
        this.catalogNames = catalogNames;
    }

    public List<PriceContactLetterAskCompanyChargeInfo> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<PriceContactLetterAskCompanyChargeInfo> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
