package com.born.insurance.ws.order.priceContactLetterReportPrice;

import com.born.insurance.ws.base.QueryPermissionPageBase;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by Administrator on 2017-4-17.
 */
public class ReportPriceDetailQueryOrder extends QueryPermissionPageBase {
	private long priceContactId;
	private long productId;
	
	public long getPriceContactId() {
		return priceContactId;
	}
	
	public void setPriceContactId(long priceContactId) {
		this.priceContactId = priceContactId;
	}
	
	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
