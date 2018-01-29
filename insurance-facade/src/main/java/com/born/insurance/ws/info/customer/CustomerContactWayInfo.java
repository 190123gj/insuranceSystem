package com.born.insurance.ws.info.customer;

import com.born.insurance.ws.enums.ContactTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by Administrator on 2016-12-29.
 */
public class CustomerContactWayInfo extends BaseToStringInfo {
    private long id;

    private String way;

    private ContactTypeEnum type;

    private long wayTypeId;

    private String wayTypeName;

    private long userId;

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

    public ContactTypeEnum getType() {
        return type;
    }

    public void setType(ContactTypeEnum type) {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
