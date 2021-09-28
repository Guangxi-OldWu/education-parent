package com.wuzhangze.order.controller;


import com.wuzhangze.commonutil.R;
import com.wuzhangze.commonutil.ResultCode;
import com.wuzhangze.order.service.PayLogService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
@RestController
@RequestMapping(ApiManager.V + "/eduorder/pay-log")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @ApiOperation(value = "生成微信支付二维码")
    @GetMapping("/qrcode/{orderNo}")
    public R getQrcode(@PathVariable String orderNo) {
        // 返回信息，包含二维码地址 和 其他信息
        Map map = payLogService.getQrcode(orderNo);
        return R.ok().data(map);
    }

    // 前端写了，每三秒访问一次这个接口看看支付了没有
    @ApiOperation(value = "查询订单支付状态")
    @GetMapping("/status/{orderNo}")
    public R getOrderStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.getPayStatus(orderNo);
        if(map == null) {
            return R.err().message("支付出错了");
        }
        // 支付成功
        if(map.get("trade_state").equals("SUCCESS")) {
            // 添加记录到支付表，更新订单表状态
            payLogService.updateOrderStatus(map);
            return R.ok();
        }
        return R.ok().code(ResultCode.IS_PAY.code).message("支付中");
    }

}

