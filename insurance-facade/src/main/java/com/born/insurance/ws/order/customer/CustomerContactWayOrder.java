package com.born.insurance.ws.order.customer;

import com.born.insurance.ws.order.base.ProcessOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by Administrator on 2016-12-28.
 */
public class CustomerContactWayOrder extends ProcessOrder{
    private long id;

    private String way;

    private String type;

    private long wayTypeId;

    private String wayTypeName;

    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getWayTypeId() {
        return wayTypeId;
    }

    public void setWayTypeId(long wayTypeId) {
        this.wayTypeId = wayTypeId;
    }

    public String getWayTypeName() {
        return wayTypeName;
    }

    public void setWayTypeName(String wayTypeName) {
        this.wayTypeName = wayTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
