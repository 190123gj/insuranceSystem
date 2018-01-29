package com.born.insurance.ws.order.sysParam;



import com.born.insurance.ws.order.base.ValidateOrderBase;
import com.yjf.common.service.Order;

import java.util.Date;

/**
 * Created by wqh on 2014/5/22.
 */
public class SysParamOrder extends ValidateOrderBase implements Order {
    private static final long serialVersionUID = 547166821741361821L;
    /** 参数名称*/
    private String paramName;
    /** 参数值*/
    private String paramValue;
    /** 扩展属性一 */
    private String extendAttributeOne;
    /** 扩展属性二 */
    private String extendAttributeTwo;
    /** 创建时间 */
    private Date rawAddTime;
   /** 更新时间 */
    private Date rawUpdateTime;
   /** 描述 */
    private String description;


    @Override
    public void check() {
        this.validateHasText(this.getParamName(), "参数名称");
        this.validateHasText(this.getParamValue(), "paramValue");
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysParamOrder [paramName=");
        builder.append(paramName);
        builder.append(", paramValue=");
        builder.append(paramValue);
        builder.append(", extendAttributeOne=");
        builder.append(extendAttributeOne);
        builder.append(", extendAttributeTwo=");
        builder.append(extendAttributeTwo);
        builder.append(", rawAddTime=");
        builder.append(rawAddTime);
        builder.append(", rawUpdateTime=");
        builder.append(rawUpdateTime);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getExtendAttributeOne() {
        return extendAttributeOne;
    }

    public void setExtendAttributeOne(String extendAttributeOne) {
        this.extendAttributeOne = extendAttributeOne;
    }

    public String getExtendAttributeTwo() {
        return extendAttributeTwo;
    }

    public void setExtendAttributeTwo(String extendAttributeTwo) {
        this.extendAttributeTwo = extendAttributeTwo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
