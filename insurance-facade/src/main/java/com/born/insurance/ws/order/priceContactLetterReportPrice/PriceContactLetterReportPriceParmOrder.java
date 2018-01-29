package com.born.insurance.ws.order.priceContactLetterReportPrice;

import java.util.List;

import com.born.insurance.ws.order.base.ProcessOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.order.base.FormOrderBase;


public class PriceContactLetterReportPriceParmOrder extends ProcessOrder {


	private List<PriceContactLetterCompanyReportPriceOrder> companyReportPriceOrders;


	public List<PriceContactLetterCompanyReportPriceOrder> getCompanyReportPriceOrders() {
		return companyReportPriceOrders;
	}

	public void setCompanyReportPriceOrders(List<PriceContactLetterCompanyReportPriceOrder> companyReportPriceOrders) {
		this.companyReportPriceOrders = companyReportPriceOrders;
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