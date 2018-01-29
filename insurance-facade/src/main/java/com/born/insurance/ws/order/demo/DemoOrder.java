package com.born.insurance.ws.order.demo;

import com.born.insurance.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Created by Administrator on 2016-11-18.
 */
public class DemoOrder extends FormOrderBase {
	
	
	
    private long id;


    private String realName;

    private Money amount = new Money(0, 0);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
