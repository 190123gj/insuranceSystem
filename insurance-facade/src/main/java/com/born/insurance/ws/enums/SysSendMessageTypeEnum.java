package com.born.insurance.ws.enums;

/**
 *                       
 * @Filename SysSendMessageTypeEnum.java
 *
 * @Description 站内消息类别
 *
 * @Version 1.0
 *
 * @Author sxiaomeng
 *
 * @Email sxm@yiji.com
 *       
 * @History
 *<li>Author: sxiaomeng</li>
 *<li>Date: 2015-3-9</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public enum SysSendMessageTypeEnum {

    /**注册 */
    REGISTER("REGISTER", "注册"),

    /**实名认证 */
    REAL_NAME("REAL_NAME", "实名认证"),

    /**充值 */
    RECHARGE("RECHARGE", "充值"),

    /**提现 */
    WITHDRAW("WITHDRAW", "提现"),

    /**红包 */
    GIVE_MONEY("GIVE_MONEY", "红包"),

    /**增益卷 */
    ADD_TICKTET("ADD_TICKTET", "增益卷"),
    
    /**加息卷 */
    GAIN_AMOUNT("GAIN_AMOUNT", "加息卷"),

    /**体验金 */
    EXPERIENCE_MONEY("EXPERIENCE_MONEY", "体验金"),

    /**积分 */
    POINTS("POINTS", "积分"),

    /**手机变更 */
    PHONE_CHANGE("PHONE_CHANGE", "手机变更"),

    /**邮箱变更 */
    EMAIL_CHANGE("EMAIL_CHANGE", "邮箱变更"),

    /**投资 */
    INVESTE("INVESTE", "投资"),

    /**经纪人分润 */
    BROKER("BROKER", "经纪人分润"),

    /**借款人还款 */
    BORROWERS_REFUND("BORROWERS_REFUND", "借款人还款"),
    
    /**会员等级升级 */
    USER_LEVEL_UPGRADE("USER_LEVEL_UPGRADE", "会员等级升级"),
    
    /**投资人变更为经纪人 */
    INVESTER_CHANGE_BROKER("INVESTER_CHANGE_BROKER", "投资人变更为经纪人"),
    
    /**债权转让 */
    TRANSFER_SUCCESS("TRANSFER_SUCCESS", "债权转让"),
    
    VIRTUAL_EXPERIENCE_AMOUNT("VIRTUAL_EXPERIENCE_AMOUNT","虚拟体验金");


    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    /**
     *
     * @param code 枚举值
     * @param message 枚举描述
     */
    private SysSendMessageTypeEnum(String code, String message) {
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
     * @return CertifyStatusEnum
     */
    public static SysSendMessageTypeEnum getByCode(String code) {
        for (SysSendMessageTypeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<CertifyStatusEnum>
     */
    public static java.util.List<SysSendMessageTypeEnum> getAllEnum() {
        java.util.List<SysSendMessageTypeEnum> list = new java.util.ArrayList<SysSendMessageTypeEnum>(
                values().length);
        for (SysSendMessageTypeEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public static java.util.List<String> getAllEnumCode() {
        java.util.List<String> list = new java.util.ArrayList<String>(values().length);
        for (SysSendMessageTypeEnum _enum : values()) {
            list.add(_enum.code());
        }
        return list;
    }

    /**
     * 通过code获取msg
     * @param code 枚举值
     * @return
     */
    public static String getMsgByCode(String code) {
        if (code == null) {
            return null;
        }
        SysSendMessageTypeEnum _enum = getByCode(code);
        if (_enum == null) {
            return null;
        }
        return _enum.getMessage();
    }

    /**
     * 获取枚举code
     * @param _enum
     * @return
     */
    public static String getCode(SysSendMessageTypeEnum _enum) {
        if (_enum == null) {
            return null;
        }
        return _enum.getCode();
    }
}
