package com.wuzhangze.msm.serviec;

import com.tencentcloudapi.sms.v20210111.models.SendStatus;

/**
 * @author OldWu
 * @date 2021/9/4 15:38
 */
public interface SmsService {

    /**
     * 发送验证码
     * @param phone 手机号
     */
    boolean sendSMS(String phone);

    /**
     * 检查验证码是否正确
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    boolean checkCode(String phone,String code);
}
