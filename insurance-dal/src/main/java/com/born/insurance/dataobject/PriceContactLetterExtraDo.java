package com.born.insurance.dataobject;

import java.util.Date;

/**
 * Created by Administrator on 2017-1-11.
 */
public class PriceContactLetterExtraDo extends SimpleFormDO {
    private long id;

    private String billNo;

    private String priceType;

    private String priceTemplate;

    private long catalogId;

    private long businessConditionId;

    private String businessConditions;

    private String name;

    private long customerUserId;

    private String customerUserName;

    private long formId;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private long version;

    private String ext;

    private long creatorId;

    private String creatorName;

    private String status;

    private String customerType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceTemplate() {
        return priceTemplate;
    }

    public void setPriceTemplate(String priceTemplate) {
        this.priceTemplate = priceTemplate;
    }

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    public long getBusinessConditionId() {
        return businessConditionId;
    }

    public void setBusinessConditionId(long businessConditionId) {
        this.businessConditionId = businessConditionId;
    }

    public String getBusinessConditions() {
        return businessConditions;
    }

    public void setBusinessConditions(String businessConditions) {
        this.businessConditions = businessConditions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public long getFormId() {
        return formId;
    }

    @Override
    public void setFormId(long formId) {
        this.formId = formId;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
