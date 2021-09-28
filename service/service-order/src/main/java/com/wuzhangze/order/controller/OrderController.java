package com.wuzhangze.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhangze.commonutil.JwtUtils;
import com.wuzhangze.commonutil.R;
import com.wuzhangze.order.entity.Order;
import com.wuzhangze.order.service.OrderService;
import com.wuzhangze.servicebase.api.ApiManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author OldWu
 * @since 2021-09-10
 */
@RestController
@RequestMapping(ApiManager.V + "/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "生成订单，返回订单编号")
    @PostMapping("/create/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 创建订单，生成订单号
        String orderNo = orderService.createOrder(courseId,memberId);
        return R.ok().data("orderNo",orderNo);
    }

    @ApiOperation(value = "根据订单编号查询订单信息")
    @GetMapping("/{no}")
    public R getOrderByOrderNo(@PathVariable String no) {
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", no));
        return R.ok().obj(order);
    }

    @ApiOperation(value = "根据会员id 和 课程id 查询该课程是否被会员购买。0未购买/1已购买")
    @GetMapping("/status/{memberId}/{courseId}")
    public boolean getOrderStatus(@PathVariable String memberId,
                            @PathVariable String courseId){
        int count = orderService.count(new QueryWrapper<Order>()
                .eq("member_id", memberId)
                .eq("course_id", courseId)
                .eq("status",1));

        return count > 0;
    }

}

