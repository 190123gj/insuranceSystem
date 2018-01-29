package com.born.insurance.ws.order.formTemplate;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;
import com.born.insurance.ws.enums.FormTemplateTypeEnum;

/**
 * Created by Administrator on 2017-1-3.
 */
public class FormTemplateQueryOrder extends QueryPermissionPageBase {
    private FormTemplateTypeEnum type;


    public FormTemplateTypeEnum getType() {
        return type;
    }

    public void setType(FormTemplateTypeEnum type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
