package com.born.insurance.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-1-5.
 */
public enum InsuranceCatalogTypeEnum {

    catalog("catalog", "NO","分类"),
    product("product","YES","产品"),

    kinds("kinds", "YES","险种");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    private final String lastDepth;

    /**
     * 构造一个<code>BooleanEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private InsuranceCatalogTypeEnum(String code, String lastDepth, String message) {
        this.code = code;
        this.message = message;
        this.lastDepth = lastDepth;
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

    public String getLastDepth() {
        return lastDepth;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return BooleanEnum
     */
    public static InsuranceCatalogTypeEnum getByCode(String code) {
        for (InsuranceCatalogTypeEnum _enum : values()) {
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
    public static List<InsuranceCatalogTypeEnum> getAllEnum() {
        List<InsuranceCatalogTypeEnum> list = new ArrayList<InsuranceCatalogTypeEnum>();
        for (InsuranceCatalogTypeEnum _enum : values()) {
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
        for (InsuranceCatalogTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }
}
