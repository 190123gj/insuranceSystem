package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqh on 2017-1-5.
 */
public enum conditionTypeEnum {
    product("product","产品"),

    CATALOG("catalog", "分类"),

    KINDS("kinds", "险种");

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
    private conditionTypeEnum(String code, String message) {
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
    public static conditionTypeEnum getByCode(String code) {
        for (conditionTypeEnum _enum : values()) {
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
    public static List<conditionTypeEnum> getAllEnum() {
        List<conditionTypeEnum> list = new ArrayList<conditionTypeEnum>();
        for (conditionTypeEnum _enum : values()) {
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
        for (conditionTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
