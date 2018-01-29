package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-1-5.
 */
public enum LifeInsuranceTypeEnum {

	LIFEINSURANCE("isLifeInsurance","寿险"),

	NOTLIFEINSURANCE("noIsLifeInsurance", "非寿险");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;



    /**
     * 构造一个<code>BooleanEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private LifeInsuranceTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the message.
     */
    public String message() {
        return message;
    }



    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return BooleanEnum
     */
    public static LifeInsuranceTypeEnum getByCode(String code) {
        for (LifeInsuranceTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<BooleanEnum>
     */
    public static List<LifeInsuranceTypeEnum> getAllEnum() {
        List<LifeInsuranceTypeEnum> list = new ArrayList<LifeInsuranceTypeEnum>();
        for (LifeInsuranceTypeEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public static List<String> getAllEnumCode() {
        List<String> list = new ArrayList<String>();
        for (LifeInsuranceTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
