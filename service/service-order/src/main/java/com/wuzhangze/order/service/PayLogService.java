package com.wuzhangze.order.service;

import com.wuzhangze.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo 订单编号
     * @return 二维码地址和其他信息
     */
    Map getQrcode(String orderNo);

    /**
     * 获取订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> getPayStatus(String orderNo);

    /**
     * 支付成功，添加记录到支付表，更新订单表状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
