package com.wuzhangze.msm.controller;

import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.msm.serviec.SmsService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author OldWu
 * @date 2021/9/4 15:37
 */
@RequestMapping(ApiManager.V + "/edusms/sms")
@RestController
public class SmsController {

    @Resource
    private SmsService smsService;

    @ApiOperation(value = "发送验证码")
    @PostMapping("/send/{phone}")
    public R sendSMS(@PathVariable String phone) {
        boolean sendTag = smsService.sendSMS(phone);
        return sendTag ? R.ok() : R.err().message("发送短信失败");
    }

    @ApiOperation(value = "检查验证码")
    @PostMapping("/checkCode")
    public R checkCode(@RequestParam String phone,
                       @RequestParam String code) {
        boolean resultTag = smsService.checkCode(phone, code);
        return resultTag ? R.ok() : R.err().message("验证码错误或已过期");
    }
}
