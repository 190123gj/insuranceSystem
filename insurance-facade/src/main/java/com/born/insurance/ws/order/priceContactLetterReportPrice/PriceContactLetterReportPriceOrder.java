package com.born.insurance.ws.order.priceContactLetterReportPrice;

import java.util.List;

import com.born.insurance.ws.order.base.FormOrderBase;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class PriceContactLetterReportPriceOrder extends FormOrderBase {

	private long reportPriceId;

	private long contactLetterId;

	private List<PriceContactLetterReportPriceParmOrder> paramOrders;


	public List<PriceContactLetterReportPriceParmOrder> getParamOrders() {
		return paramOrders;
	}

	public void setParamOrders(List<PriceContactLetterReportPriceParmOrder> paramOrders) {
		this.paramOrders = paramOrders;
	}

	public long getContactLetterId() {
		return contactLetterId;
	}

	public void setContactLetterId(long contactLetterId) {
		this.contactLetterId = contactLetterId;
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