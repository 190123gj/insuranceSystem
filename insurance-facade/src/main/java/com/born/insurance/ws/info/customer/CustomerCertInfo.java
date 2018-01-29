package com.born.insurance.ws.info.customer;

import com.born.insurance.ws.enums.CertTypeEnum;
import com.born.insurance.ws.enums.CustomerTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-1-20.
 */
public class CustomerCertInfo extends BaseToStringInfo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3160410087669892492L;

	private long id;

    private long userId;

    private String customerId;

    private String customerName;

    private CertTypeEnum certType;

    private String certNo;
    
    private String certTypeName;

    private Date certExpDate;

    private String longTime;

    private String hiddenUrls;
    private List<CommonAttachmentInfo> commonAttachementList;
    private List<CommonAttachmentInfo> attaches;



    public Date getCertExpDate() {
        return certExpDate;
    }

    public void setCertExpDate(Date certExpDate) {
        this.certExpDate = certExpDate;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public CertTypeEnum getCertType() {
        return certType;
    }

    public void setCertType(CertTypeEnum certType) {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

	public String getCertTypeName() {
		return certTypeName;
	}

	public void setCertTypeName(String certTypeName) {
		this.certTypeName = certTypeName;
	}

    public List<CommonAttachmentInfo> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<CommonAttachmentInfo> attaches) {
        this.attaches = attaches;
    }

    public List<CommonAttachmentInfo> getCommonAttachementList() {
        return commonAttachementList;
    }

    public void setCommonAttachementList(List<CommonAttachmentInfo> commonAttachementList) {
        this.commonAttachementList = commonAttachementList;
    }

    public String getHiddenUrls() {
        return hiddenUrls;
    }

    public void setHiddenUrls(String hiddenUrls) {
        this.hiddenUrls = hiddenUrls;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    @Override
    public String toString() {
        return "CustomerCertInfo{" +
                "attaches=" + attaches +
                ", id=" + id +
                ", userId=" + userId +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", certType=" + certType +
                ", certNo='" + certNo + '\'' +
                ", certTypeName='" + certTypeName + '\'' +
                ", certExpDate=" + certExpDate +
                ", longTime='" + longTime + '\'' +
                ", hiddenUrls='" + hiddenUrls + '\'' +
                ", commonAttachementList=" + commonAttachementList +
                '}';
    }
}
