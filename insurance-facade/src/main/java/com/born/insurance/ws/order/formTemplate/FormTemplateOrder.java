package com.born.insurance.ws.order.formTemplate;

import com.born.insurance.ws.enums.FormTemplateTypeEnum;
import com.born.insurance.ws.order.base.ProcessOrder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateOrder extends ProcessOrder {
    private long id;

    private String name;

    private String code;

    private FormTemplateTypeEnum type;

    private Date rawAddTime;

    private Date rawUpdateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FormTemplateTypeEnum getType() {
        return type;
    }

    public void setType(FormTemplateTypeEnum type) {
        this.type = type;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
