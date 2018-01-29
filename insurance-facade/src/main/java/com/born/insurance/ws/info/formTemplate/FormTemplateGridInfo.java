package com.born.insurance.ws.info.formTemplate;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateGridInfo extends BaseToStringInfo {
    private long id;

    private long tabId;

    private String name;

    private String code;

    private Date rawAddTime;

    private Date rawUpdateTime;

    List<FormTemplateFieldInfo> fields;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTabId() {
        return tabId;
    }

    public void setTabId(long tabId) {
        this.tabId = tabId;
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

    public List<FormTemplateFieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FormTemplateFieldInfo> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
