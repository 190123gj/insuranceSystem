package com.born.insurance.ws.info.formTemplate;

import java.util.Date;
import java.util.List;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.FormTemplateTypeEnum;
import com.born.insurance.ws.order.base.ProcessOrder;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateInfo extends BaseToStringInfo {
    private long id;

    private String name;

    private String code;

    private FormTemplateTypeEnum type;

    private Date rawAddTime;

    private Date rawUpdateTime;

    private List<FormTemplateTabInfo> tabs;

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

    public List<FormTemplateTabInfo> getTabs() {
        return tabs;
    }

    public void setTabs(List<FormTemplateTabInfo> tabs) {
        this.tabs = tabs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
