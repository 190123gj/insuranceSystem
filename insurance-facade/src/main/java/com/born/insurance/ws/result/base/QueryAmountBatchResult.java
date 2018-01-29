package com.born.insurance.ws.result.base;

import com.yjf.common.lang.util.money.Money;

/**
 *
 * @author wuzj
 */
public class QueryAmountBatchResult<T> extends QueryBaseBatchResult<T> {
	
	private static final long serialVersionUID = -8061006080471127437L;
	
	Money totalAmount = Money.zero();
	
	public Money getTotalAmount() {
		return this.totalAmount;
	}
	
	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryAmountBatchResult [totalCount=");
		builder.append(totalCount);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", pageNumber=");
		builder.append(pageNumber);
		builder.append(", pageList=");
		builder.append(pageList);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
