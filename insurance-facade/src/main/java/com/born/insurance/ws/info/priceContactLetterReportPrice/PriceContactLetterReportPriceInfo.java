package com.born.insurance.ws.info.priceContactLetterReportPrice;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Administrator on 2017-3-9.
 */
public class PriceContactLetterReportPriceInfo extends BaseToStringInfo {
    private long reportPriceId;
    private long formId;
    private long contactLetterId;

    private List<PriceContactLetterCompanyReportPriceInfo> companyReportPriceInfos;

    public long getContactLetterId() {
        return contactLetterId;
    }

    public void setContactLetterId(long contactLetterId) {
        this.contactLetterId = contactLetterId;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public long getReportPriceId() {
        return reportPriceId;
    }

    public void setReportPriceId(long reportPriceId) {
        this.reportPriceId = reportPriceId;
    }

    public List<PriceContactLetterCompanyReportPriceInfo> getCompanyReportPriceInfos() {
        return companyReportPriceInfos;
    }

    public void setCompanyReportPriceInfos(List<PriceContactLetterCompanyReportPriceInfo> companyReportPriceInfos) {
        this.companyReportPriceInfos = companyReportPriceInfos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
