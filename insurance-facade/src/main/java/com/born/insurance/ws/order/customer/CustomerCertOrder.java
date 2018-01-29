package com.born.insurance.ws.order.customer;

import com.born.insurance.ws.order.base.ProcessOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by Administrator on 2017-1-20.
 */
public class CustomerCertOrder extends ProcessOrder {
    private long id;

    private String customerId;

    private String customerName;

    private String certType;

    private String certNo;

    private String certPic;

    private String certExpDate;

    private String certTypeName;

    private String longTime;


    public String getCertExpDate() {
        return certExpDate;
    }

    public void setCertExpDate(String certExpDate) {
        this.certExpDate = certExpDate;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCertPic() {
        return certPic;
    }

    public void setCertPic(String certPic) {
        this.certPic = certPic;
    }

    public String getCertTypeName() {
        return certTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        this.certTypeName = certTypeName;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
