package com.wuzhangze.ucenter.client;

import com.wuzhangze.commonutil.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author OldWu
 * @date 2021/9/5 0:04
 */
@Component
@FeignClient(value = "service-sms")
public interface SmsClient {
    @ApiOperation(value = "发送验证码")
    @PostMapping("/send")
    public R sendSMS(@RequestParam String phone);

    @ApiOperation(value = "检查验证码")
    @PostMapping("/checkCode")
    public R checkCode(@RequestParam String phone, @RequestParam String code);
}
