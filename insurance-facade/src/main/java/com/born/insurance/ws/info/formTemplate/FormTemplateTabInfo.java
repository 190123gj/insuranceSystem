package com.born.insurance.ws.info.formTemplate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.enums.FormTemplateTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateTabInfo extends BaseToStringInfo {
    private long id;

    private long templateId;

    private String name;

    private String code;

    private Date rawAddTime;

    private Date rawUpdateTime;

    List<FormTemplateFieldInfo> fields;

    List<FormTemplateGridInfo> grids;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
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

    public List<FormTemplateGridInfo> getGrids() {
        return grids;
    }

    public void setGrids(List<FormTemplateGridInfo> grids) {
        this.grids = grids;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
