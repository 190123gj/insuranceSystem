package com.born.insurance.ws.info.priceContactLetterAskCompany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * Created by Administrator on 2017-2-22.
 */
public class PriceContactLetterAskCompanyChargeInfo extends BaseToStringInfo {
    private long id;

    private long askCompanyId;

    private long catalogId;

    private String catalogName;

    private double intentionFee;

    private double protocolFee;

    private long version;

    private String ext;


    public long getAskCompanyId() {
        return askCompanyId;
    }

    public void setAskCompanyId(long askCompanyId) {
        this.askCompanyId = askCompanyId;
    }

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
