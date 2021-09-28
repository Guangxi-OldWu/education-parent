package com.wuzhangze.order.service;

import com.wuzhangze.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单，返回订单号
     * @param courseId 课程id
     * @param memberId 会员id
     * @return 订单号
     */
    String createOrder(String courseId, String memberId);
}
