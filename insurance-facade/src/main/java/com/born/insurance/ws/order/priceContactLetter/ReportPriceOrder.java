package com.born.insurance.ws.order.priceContactLetter;

import java.util.List;

import com.born.insurance.ws.order.base.FormOrderBase;
import com.born.insurance.ws.order.priceContactLetterReportPrice.PriceContactLetterCompanyReportPriceOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReportPriceOrder extends FormOrderBase {
	
	/**
	 * id
	 */
	private long letterDemandId;
	
	List<PriceContactLetterCompanyReportPriceOrder> priceOrders;
	
	public long getLetterDemandId() {
		return letterDemandId;
	}
	
	public void setLetterDemandId(long letterDemandId) {
		this.letterDemandId = letterDemandId;
	}
	
	public List<PriceContactLetterCompanyReportPriceOrder> getPriceOrders() {
		return priceOrders;
	}
	
	public void setPriceOrders(List<PriceContactLetterCompanyReportPriceOrder> priceOrders) {
		this.priceOrders = priceOrders;
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