package com.born.insurance.ws.service.sms;

import com.born.insurance.ws.info.sms.SMSInfo;

import javax.jws.WebService;

/**
 * 短信接口
 *
 * @author heh
 */
@WebService
public interface SMSService {
    /**
     * 序列号注册
     * @return
     */
    SMSInfo registCdKey();
    /**
     * 注销序列号
     * @return
     */
    SMSInfo logoutCdKey();
    /**
     * 企业信息注册
     * @param ename 企业名称(最多60字节)，必须输入
     * @param linkman 联系人姓名(最多20字节)，必须输入
     * @param phonenum 联系电话(最多20字节)，必须输入
     * @param mobile 联系手机(最多15字节)，必须输入
     * @param email 电子邮件(最多60字节)，必须输入
     * @param fax 联系传真(最多20字节)，必须输入
     * @param address 公司地址(最多60字节)，必须输入
     * @param postcode 邮政编码(最多6字节)，必须输入
     * @return
     */
    SMSInfo registdetailinfo(String ename,String linkman,String phonenum,String mobile,String email,String fax,String address,String postcode);
    /**
     * 发送即时短信
     * @param mobileNumber 手机号(多个电话用逗号“,“ 隔开)
     * @param smsContent 短信内容
     * @param addserial 附加号(附加码或置为空)
     * @return
     */
    SMSInfo sendSMS(String mobileNumber, String smsContent,String addserial);
    /**
     * 发送定时短信
     * @param mobileNumber 手机号(多个电话用逗号“,“ 隔开)
     * @param smsContent 短信内容
     * @param addserial 附加号
     * @param sendtime 定时时间(时间格式yyyymmddhhmmss)
     * @return
     */
    SMSInfo sendTimeSMS(String mobileNumber, String smsContent,String addserial,String sendtime);
    /**
     * 接收上行短信
     * @return
     */
    SMSInfo getMo();
    /**
     * 查询余额
     * @return
     */
    SMSInfo querybalance();
    /**
     * 充值操作
     * @param cardNo 充值卡卡号
     * @param cardPass 充值卡密码
     * @return
     */
    SMSInfo chargeUp(String cardNo, String cardPass);
    /**
     * 修改密码
     * @param password 用户原密码
     * @param newPassword 用户新密码
     * @return
     */
    SMSInfo changePassword(String password,String newPassword);
}
