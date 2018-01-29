package com.born.insurance.ws.info.customer;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * Created by Administrator on 2017-1-20.
 */
public class CustomerHisBusinessBillInfo extends BaseToStringInfo {
    private long hisBillId;

    private String billNo;

    private long billCustomerId;

    private String billCustomerName;

    private long catalogId;

    private String catalogName;

    private Money premiumAmount = new Money(0, 0);

    private String insuranceDate;

    private String beginDate;

    private String endDate;

    private long insuranceCompanyId;

    private String insuranceCompanyName;

    private String remark;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public long getBillCustomerId() {
        return billCustomerId;
    }

    public void setBillCustomerId(long billCustomerId) {
        this.billCustomerId = billCustomerId;
    }

    public String getBillCustomerName() {
        return billCustomerName;
    }

    public void setBillCustomerName(String billCustomerName) {
        this.billCustomerName = billCustomerName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getHisBillId() {
        return hisBillId;
    }

    public void setHisBillId(long hisBillId) {
        this.hisBillId = hisBillId;
    }

    public long getInsuranceCompanyId() {
        return insuranceCompanyId;
    }

    public void setInsuranceCompanyId(long insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public Money getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Money premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CustomerHisBusinessBillInfo{" +
                "beginDate='" + beginDate + '\'' +
                ", hisBillId=" + hisBillId +
                ", billNo='" + billNo + '\'' +
                ", billCustomerId=" + billCustomerId +
                ", billCustomerName='" + billCustomerName + '\'' +
                ", catalogId=" + catalogId +
                ", catalogName='" + catalogName + '\'' +
                ", premiumAmount=" + premiumAmount +
                ", insuranceDate='" + insuranceDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", insuranceCompanyId=" + insuranceCompanyId +
                ", insuranceCompanyName='" + insuranceCompanyName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
