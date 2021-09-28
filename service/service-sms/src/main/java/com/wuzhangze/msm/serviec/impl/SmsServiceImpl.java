package com.wuzhangze.msm.serviec.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.wuzhangze.msm.properties.SmsProperties;
import com.wuzhangze.msm.serviec.SmsService;
import com.wuzhangze.msm.util.SmsUtils;
import com.wuzhangze.servicebase.exception.LzException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;
// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author OldWu
 * @date 2021/9/4 15:38
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsProperties smsProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendSMS(String phone) {
        String code = new Random().nextInt(999999) + "";
        SendStatus sendStatus = SmsUtils.sendSmsCode(smsProperties, code, phone);
        if(sendStatus.getCode().equals("Ok")) {
            stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCode(String phone, String code) {
        String c = stringRedisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(c) && c.equals(code))
            return true;
        return false;
    }
}
