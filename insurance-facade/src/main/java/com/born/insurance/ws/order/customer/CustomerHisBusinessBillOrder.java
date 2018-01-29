package com.born.insurance.ws.order.customer;

import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.ProcessOrder;

import java.util.Date;

/**
 * Created by Administrator on 2016-12-28.
 */
public class CustomerHisBusinessBillOrder extends ProcessOrder{
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
